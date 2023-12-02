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
    args = parseArgs(argv),
    mavenAT = { root, args };

export default mavenAT;

log(mavenAT);

export const exec = execStr => executor(execStr, (err, stdout, stderr) => {
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
    parseExec = (execStr, src, regexp = /\$\{(\w+)\}/g) => execStr.replace(regexp, str => src[str.replace(regexp, "$1")]);

export const _scripts = JSON.parse(readFile("scripts.json")),
    scripts = {
        "deploy": {
            "fn": (key, arg, url, execStr) => deploy(key, arg, url, execStr),
            "exec": "mvn deploy:deploy-file -Durl=file:${url} -Dfile=target/${artifactId}-${version}.${packaging} -DgroupId=${groupId} -DartifactId=${artifactId} -Dpackaging=${packaging} -Dversion=${version}"
        },
        "clean": {
            "exec": "rmdir /s /q ${url}/org/project/modules/${version}"
        }
    };

log(_scripts);
log(scripts);

export const run = scripts => Object.keys(args).forEach(arg => runScript(scripts, arg, args[arg], root)),
    runScript = (scripts, key, arg, url) => {
        const script = scripts[key],
            { fn, exec = "" } = script;
        if (fn) fn(key, arg, url, exec);
    },
    deploy = (key, arg, url, execStr) => {
        log("\n");
        log(`${key}: ${arg}`);
        const _root = (url = normalize(url.replace("file:", "").replace("${basedir}", root)).replace(/\\/g, "/")).replace("/repo", ""),
            pom = parsePom(_root),
            { artifactId, packaging, repositories } = pom;
        log(pom);
        if (packaging !== "jar") return;
        else if (arg === true) {
            repositories.forEach(({ id, name, url }) => deploy(key, name || (id = id.split("."))[id.length - 1], url, execStr));
            if (args.root === true) deploy(key, artifactId, url, execStr);
        } else if (artifactId === arg) {
            const deploy = parseExec(execStr, pom),
                clean = parseExec(scripts.clean.exec, pom);
            log(deploy);
            log(clean);
            //exec(clean);
            //exec(deploy);
        }
    },
    parsePom = path => {
        const { project } = parseXml(path + "/pom.xml"),
            { groupId, artifactId, version, packaging = ["jar"], repositories = [] } = project || {},
            parse = obj => Object.fromEntries(Object.entries(obj).map(([k, v]) => [k, setProperty(v)])),
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
