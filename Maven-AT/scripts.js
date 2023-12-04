import { log, error } from 'console';
import { cwd, argv } from 'process';
import { normalize } from 'path';
import { readFileSync as readFile, statSync as stat } from 'fs';
import { exec as executor } from 'child_process';
import { parseString } from 'xml2js';

export const root = cwd(),
    parseArgs = (argList, sep = /^\-+/) => {
        let args = {}, optList, opt;
        argList.slice(2).forEach(arg => {
            optList = arg.trim().replace(sep, "").split("=");
            opt = optList[0];
            args[opt] = optList[1] || true;
        });
        return args;
    },
    args = parseArgs(argv);

log(args);

const getFn = (fn = () => { }, $this = () => { }) => (fn instanceof Function ? fn : bind(fn, $this)) || (() => { });

export const getScripts = () => {
    try {
        return JSON.parse(readFile("scripts.json"));
    } catch (e) {
        error(e.message);
        return {};
    }
}, scripts = Object.assign({
    "deploy": {
        "fn": (key, arg, url, execStr) => deploy(key, arg, url, execStr),
        "exec": "mvn deploy:deploy-file -Durl=file:${url} -Dfile=target/${artifactId}-${version}.${packaging} -DgroupId=${groupId} -DartifactId=${artifactId} -Dpackaging=${packaging} -Dversion=${version}"
    },
    "clean": {
        "fn": (exec, pom, cb = scripts.clean.cb) => parseExec(exec, pom, { cb }),
        "cb": (k, v) => k === "groupId" ? v.replace(".", "\\") : normalize(v),
        "exec": "rmdir /s /q ${url}\\${groupId}\\${artifactId}\\${version}"
    }
}, getScripts()),
    bind = (fn, $this = () => { }) => new Function(`return ${fn}`).bind($this)(),
    fn = ({ fn = () => { } }, $this = () => { }) => getFn(fn, $this),
    cb = ({ cb = () => { } }, $this = () => { }) => getFn(cb, $this);

export const mapObj = (obj, cb = (k, v) => [k, v], keys = []) => Object.fromEntries(Object.entries(obj).map(([k, v]) => keys.length === 0 || keys.includes(k) ? cb(k, v) : [k, v])),
    exec = execStr => executor(execStr, { encoding: 'utf8' }, (err, stdout, stderr) => {
        if (err) {
            error(`error: ${err.message}`);
            return;
        } else if (stderr) {
            error(`stderr: ${stderr}`);
            return;
        }
        log(`stdout: ${stdout}`);
        return stdout;
    }),
    parseExec = (execStr, src, { regexp = /\$\{(\w+)\}/g, cb = (k, v) => v } = {}) => execStr.replace(regexp, str => cb(cb.k = str.replace(regexp, "$1"), src[cb.k])),
    toUtf8 = str => Buffer.from(str, 'utf-8').toString();

export const run = scripts => Object.keys(args).forEach(arg => runScript(scripts, arg, args[arg], root)),
    runScript = (scripts, key, arg, url) => {
        const script = scripts[key] || {},
            { exec, cb } = script;
        fn(script, mavenAT[key])(arg, url, exec, cb);
    };

const $mavenAT = {
    root, args, scripts, bind, fn, cb,
    mapObj, exec, parseExec, toUtf8,
    run, runScript,
    log, error, cwd, argv, normalize, readFile, stat, executor, parseString
};

export const cleanCb = cb(scripts.clean, $mavenAT),
    clean = fn(scripts.clean, $mavenAT),
    deploy = (arg, url, execStr, cleanFn = clean) => {
        log("\n");
        log(`deploy: ${arg}`);
        const { groupId, artifactId, version, packaging, repositories = [], url: $url } = parsePom(url),
            pom = { groupId, artifactId, version, packaging, repositories, url: $url };
        log(pom);
        log(url);
        if (arg === true) {
            if (args.repo === true) repositories.forEach(({ id, name, url }) => deploy(name || id.split(".").pop(), url, execStr, cleanFn));
            if (args.root === true) deploy(artifactId, url, execStr, cleanFn);
        } else if (artifactId === arg && packaging === "jar") {
            const deploy = parseExec(execStr, pom),
                clean = cleanFn(scripts.clean.exec, pom, cleanCb);
            log(deploy);
            log(clean);
            if (args.test !== true) {
                exec(clean);
                exec(deploy);
            }
        }
    },
    parseXmlCb = (obj, keys = []) => mapObj(obj, (k, v = []) => [k, v instanceof Array ? (v.length > 1 ? v :
        (v[0] instanceof Object && Object.keys(v[0]).length === 1 ? Object.values(v[0])[0].map(singleObj => parseXmlCb(singleObj)) : v[0])) : v], keys),
    parsePomCb = ({ project = {} }, keys = []) => parseXmlCb(project, keys),
    parsePom = (path, keys = ["groupId", "artifactId", "version", "packaging", "repositories"]) => {
        path = normalize(path).replace("file:", "").replace("${basedir}", root).replace(/\\/g, "/").replace("/repo", "");
        const pom = parseXml(path + "/pom.xml", keys, parsePomCb);
        pom.url = path + "/repo";
        return pom;
    },
    parseXml = (path, keys = [], cb = parseXmlCb) => {
        log(path);
        let xml = {};
        try {
            parseString(readFile(path), (err, result) => err ? error(err) : (xml = result));
        } catch (e) {
            error(e.message);
            return xml;
        }
        return cb(xml, keys);
    };

export const mavenAT = Object.assign({ cleanCb, clean, deploy, parseXmlCb, parsePomCb, parsePom, parseXml }, $mavenAT);

export default mavenAT;
