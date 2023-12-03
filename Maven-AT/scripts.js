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

export const mapObj = (obj, cb = (k, v) => [k, v]) => Object.fromEntries(Object.entries(obj).map(([k, v]) => cb(k, v))),
    exec = execStr => executor(execStr, (err, stdout, stderr) => {
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
    parseExec = (execStr, src, { regexp = /\$\{(\w+)\}/g, cb = (k, v) => v } = {}) => execStr.replace(regexp, str => cb(cb.k = str.replace(regexp, "$1"), src[cb.k]));

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
            "fn": (k, v) => k === "groupId" ? v.replace(".", "\\") : v,
            "exec": "rmdir /s /q ${url}\\${groupId}\\${artifactId}\\${version}"
        }
    }, getScripts()),
    bind = (fn, $this = () => { }) => new Function(`return ${fn}`).bind($this)(),
    fn = ({ fn: $fn = () => { } }, $this = () => { }) => ($fn instanceof Function ? $fn : bind($fn, $this)) || (() => { });

export const run = scripts => Object.keys(args).forEach(arg => runScript(scripts, arg, args[arg], root)),
    runScript = (scripts, key, arg, url) => {
        const script = scripts[key] || {};
        fn(script, mavenAT[key])(key, arg, url, script.exec || "");
    };

const $mavenAT = {
    root, args, mapObj, exec, parseExec, scripts, bind, fn, run, runScript,
    log, error, cwd, argv, normalize, readFile, stat, executor, parseString
};

export const cleanCb = fn(scripts.clean, $mavenAT),
    clean = (exec, pom, cb = cleanCb) => parseExec(exec, pom, { cb }),
    deploy = (key, arg, url, execStr, cleanFn = clean) => {
        log("\n");
        log(`${key}: ${arg}`);
        const _root = (url = normalize(url.replace("file:", "").replace("${basedir}", root)).replace(/\\/g, "/")).replace("/repo", ""),
            pom = parsePom(_root),
            { artifactId, packaging, repositories } = pom;
        log(pom);
        if (packaging !== "jar") return;
        else if (arg === true) {
            if (args.repo) repositories.forEach(({ id, name, url }) => deploy(key, name || id.split(".").pop(), url, execStr));
            if (args.root) deploy(key, artifactId, url, execStr);
        } else if (artifactId === arg) {
            const deploy = parseExec(execStr, pom),
                clean = cleanFn(scripts.clean.exec, pom, cleanCb);
            log(deploy);
            log(clean);
            if (!args.test) {
                exec(clean);
                //exec(deploy);
            }
        }
    },
    parsePom = path => {
        const { project } = parseXml(path + "/pom.xml"),
            { groupId, artifactId, version, packaging = ["jar"], repositories = [] } = project || {},
            //parse = obj => Object.fromEntries(Object.entries(obj).map(([k, v]) => [k, setProperty(v)])),
            parse = obj => mapObj(obj, (k, v) => [k, setProperty(v)]),
            setProperty = v => v instanceof Array && v.length > 1 ? v :
                (v[0] instanceof Object && Object.keys(v[0]).length === 1 ? Object.values(v[0])[0].map(repo => parse(repo)) : (v[0] || [])),
            pom = !groupId ? {} : parse({ groupId, artifactId, version, packaging, repositories });
        pom.url = path + "/repo";
        return pom;
    },
    parseXml = path => {
        log(path);
        let xml = {};
        try {
            parseString(readFile(path), (err, result) => err ? error(err) : (xml = result));
        } catch (e) {
            error(e.message);
        }
        return xml;
    };

export const mavenAT = Object.assign({ cleanCb, clean, deploy, parsePom, parseXml }, $mavenAT);

export default mavenAT;
