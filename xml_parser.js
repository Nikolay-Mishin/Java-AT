import { parseXml } from './scripts.js';
import { log, error } from 'console';
import { writeFileSync as writeFile, readFileSync as readFile} from 'fs';

Array.prototype.diff = function (a) { return this.filter(i => a.indexOf(i) < 0); };

let xmlStr = "";
let i = 0;
const xmlCount = 8;
const rootPath = "";
const tde3dPath = "/tde3d/import";
const url = `https://testlink.itorum.ru/testlink/images${tde3dPath}`;
const img = `<img src="${url}/$1.png" $2>`;
const basePath = "E:/Work/Itorum/Testlink/import/";
const logsPath = "E:/Work/Itorum/Testlink/import/logs.txt";
const fileName = "WORK-2024-03-26 ($i).xml";
const _path = basePath + fileName;
const loadPath = basePath + "load/";
const xmlPath = basePath + "xml/" + fileName;
const path1 = true;
const test = false;
const timeout = 300; // 300

writeFile(logsPath, "");

const match = (str, re) => str.match(re) || [],
    last2Items = arr => arr.slice(-2),
    _reImg = '<img src="(.+\.png)" alt="(.+)"(.*)>',
    _reImg2 = '<img src="(.+?\.png)" alt="(.+?)"(.*?)>',
    reImg = new RegExp(_reImg, "g"),
    _re = new RegExp(_reImg2, "g"),
    reSrc = new RegExp('(https:.+\.png)'),
    _reXml = '<img src=".+/attachment/($src)/.+\.png" (.+)>',
    reXml = new RegExp(_reXml, "g"),
    toBuffer = (data, encoding) => Buffer.from(data, encoding),
    getLog = () => readFile(logsPath).toString(),
    saveLog = str => writeFile(logsPath, `${getLog()}\n${str}`),
    writeBlob = async (link, blob, name) => {
        const type = blob.type.replace("image/", "").replace("application/", "");
        const path = `${loadPath}${name}.${type}`;
        log(link);
        //log(path);
        //log(blob);
        if (type === "xml") {
            saveLog(`error: ${path}`);
            return;
        };
        const buf = toBuffer(await blob.arrayBuffer(), 'base64'); // decode
        //log(buf);
        try {
            return readFile(path);
        } catch (e) {
            //const exist = getLog().match(link);
            //log(exist);
            //if (exist) return;
            if (!test) writeFile(path, buf);
            saveLog(link);
        }
    },
    loadFile = async (link, name) => {
        const resp = await fetch(link);
        const blob = await resp.blob();
        await writeBlob(link, blob, name);
    },
    loadFileCatch = async (link, name) => {
        fetch(link)
            .then(resp => resp.blob())
            .then(async blob => await writeBlob(link, blob, name))
            .catch(e => {
                log(link);
                log(e);
                saveLog(`error: ${link}`);
            });
    },
    parseLink = (link) => {
        const arr = link?.split("/") || [];
        const items = last2Items(arr);
        //log(arr);
        //log(items);
        return items;
    },
    loadLink = async (link, data = {}, re = _re) => {
        //log(re);
        //log(link);
        const exec = reSrc.exec(link) || [];
        link = exec[1];
        //log(link);
        //log(exec);
        //log(_exec);
        if (!link) return;
        const items = parseLink(link, re);
        const src = items[0];
        const alt = items[1];
        const width = items[2];
        //log(items);
        //log({src, alt, width});
        const { id = 0, stepNumber = 0 } = data;
        const name = path1 ? src : `${id}-${stepNumber}_${src}`;
        const _img = img.replace("$1", name);
        //log("img: " + _img);
        xmlStr = xmlStr.replace(new RegExp(_reXml.replace("$src", src), "g"), _img);
        //await loadFile(link, name);
        //await loadFileCatch(link, name);
        i++;
        setTimeout(loadFileCatch, timeout*i, link, name);
    },
    loadAllLink = (link, data, re = _re) => {
        //log("loadLink");
        //log(link);
        const matches = match(link, re);
        if (!matches.length) return;
        //log(matches);
        matches.forEach(async link => await loadLink(link, data, re));
    };

const parseCase = testcase => {
    //log("testcase: " + testcase.length);
    //log(testcase);
    testcase.forEach(testcase => {
        const { externalid, summary } = testcase;
        const id = parseInt(externalid[0]);
        //log("id: " + id);
        //log("summary");
        //log(summary);
        //log("parse: summary");
        loadAllLink(summary[0], { id });
        const { steps } = testcase;
        //log("steps");
        //log(steps);
        steps[0].step.forEach(step => {
            //log("step");
            //log(step);
            const { step_number, actions, expectedresults } = step;
            const stepNumber = parseInt(step_number[0]);
            const data = { id, stepNumber };
            //log("stepNumber: " + stepNumber);
            //log("actions");
            //log(actions);
            //log("expectedresults");
            //log(expectedresults);
            //log("parse: actions");
            loadAllLink(actions[0], data);
            //log("parse: expectedresults");
            loadAllLink(expectedresults[0], data);
        });
    });
};

const parseSuite = testsuite => {
    const { testcase } = testsuite;
    if (testcase) parseCase(testcase);
    if (testsuite.testsuite) {
        testsuite = testsuite.testsuite;
        testsuite.forEach(suite => parseSuite(suite));
    }
    //log("testsuite");
    //log(testsuite);
};

const replaceXml = (path, str) => {
    //log("xml: " + path);
    const _str = path1 ? str.replace(reXml, img) : xmlStr;
    //log(_str);
    if (!test) writeFile(path, _str);
};

for (let i = 1; i <= xmlCount; i++) {
    const path = _path.replace("$i", i);
    //log("path: " + path);
    //if (test && i > 2) continue;
    const { testsuite } = parseXml(path, [], xml => xml);
    const xml = readFile(path);
    xmlStr = xml.toString();
    const matches = match(xmlStr, _re);
    log("matches: " + matches.length);
    //log(matches);
    /*const matchesImg = match(xmlStr, reImg);
    log("matches: " + matchesImg.length);
    log(matchesImg);
    const diff = matchesImg.diff(matches);
    log(diff);*/
    matches.forEach(async link => await loadLink(link));
    replaceXml(xmlPath.replace("$i", i), xmlStr);
}
