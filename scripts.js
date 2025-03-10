import { log, error } from 'console';
import { cwd, argv } from 'process';
import { normalize } from 'path';
import { readFileSync as readFile, statSync as stat } from 'fs';
import { execSync as executor } from 'child_process';
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

//log(args);

const getFn = (fn = () => { }, $this = () => { }) => (fn instanceof Function ? fn : bind(new Function(`return ${fn}`), $this)()) || (() => { });

export const getScripts = () => {
        try {
            return JSON.parse(readFile("scripts.json"));
        } catch (err) {
            error(err.message);
            return {};
        }
    },
    scripts = Object.assign({
        "build": {
            "exec": "npm run build"
        },
        "deploy": {
            "fn": (key, arg, url, exec) => deploy(key, arg, url, exec),
            "exec": "mvn deploy:deploy-file -Durl=file:repo -Dfile=target/${artifactId}-${version}.${packaging} -DgroupId=${groupId} -DartifactId=${artifactId} -Dpackaging=${packaging} -Dversion=${version}"
        },
        "clean": {
            "fn": (exec, pom, cb = scripts.clean.cb) => parseExec(exec, pom, { cb }),
            "cb": (k, v) => k === "groupId" ? v.replace(".", "\\") : normalize(v),
            "exec": "rmdir /s /q repo\\${groupId}\\${artifactId}\\${version}"
        }
    }, getScripts()),
    bind = (fn, $this = () => { }) => fn.bind($this),
    fn = ({ fn = () => { } }, $this = () => { }) => getFn(fn, $this),
    cb = ({ cb = () => { } }, $this = () => { }) => getFn(cb, $this);

export const mapObj = (obj, cb = (k, v) => [k, v], keys = []) => Object.fromEntries(Object.entries(obj).map(([k, v]) => keys.length === 0 || keys.includes(k) ? cb(k, v) : [k, v])),
    exec = (exec, cwd = root) => {
        try {
            log(`cwd: ${cwd}\nrun: ${exec}`);
            executor(exec, { cwd, encoding: 'utf-8', stdio: 'inherit' });
            const { stdout, stderr } = process;
            return stdout;
        } catch (err) {
            error(err.message);
            return;
        }
    },
    parseExec = (exec, src, { regexp = /\$\{(\w+)\}/g, cb = (k, v) => v } = {}) => exec.replace(regexp, str => cb(cb.k = str.replace(regexp, "$1"), src[cb.k])),
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
    deploy = async (arg, url, execStr, cleanFn = clean) => {
        //log("\n");
        //log(`deploy: ${arg}`);
        //log(url);
        const { groupId, artifactId, version, packaging, repositories = [], root: $root, repo, target } = parsePom(url),
            pom = { groupId, artifactId, version, packaging, repositories, root: $root, repo, target };
        $mavenAT.pom = pom;
        //log(pom);
        if (arg === true) {
            if (args.repo === true) repositories.forEach(({ id, name, url }) => deploy(name || id.split(".").pop(), url, execStr, cleanFn));
            if (args.root === true) deploy(artifactId, url, execStr, cleanFn);
        } else if (packaging === "jar" && artifactId === arg) {
            const build = scripts.build.exec,
                deploy = parseExec(execStr, pom),
                clean = cleanFn(scripts.clean.exec, pom, cleanCb);
            //log(build);
            //log(clean);
            //log(deploy);
            if (args.build === true) exec(build, pom.root);
            if (args.test !== true) {
                exec(clean, pom.root);
                exec(deploy, pom.root);
            }
        }
    },
    parseXmlCb = (obj, keys = []) => mapObj(obj, (k, v = []) => [k, v instanceof Array ? (v.length > 1 ? v :
        (v[0] instanceof Object && Object.keys(v[0]).length === 1 ? Object.values(v[0])[0].map(singleObj => parseXmlCb(singleObj)) : v[0])) : v], keys),
    parsePomCb = ({ project = {} }, keys = []) => parseXmlCb(project, keys),
    parsePom = (path, keys = ["groupId", "artifactId", "version", "packaging", "repositories"]) => {
        path = normalize(path).replace("file:", "").replace("${basedir}", root).replace(/\\/g, "/").replace("/repo", "");
        const pom = parseXml(path + "/pom.xml", keys, parsePomCb);
        pom.root = path;
        pom.repo = path + "/repo";
        pom.target = path + "/target";
        return pom;
    },
    parseXml = (path, keys = [], cb = parseXmlCb) => {
        //log(path);
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
