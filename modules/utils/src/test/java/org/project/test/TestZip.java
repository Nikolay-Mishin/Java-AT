package org.project.test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Map;

import static org.project.utils.Helper.*;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.fs.Attributes.printAttrs;
import static org.project.utils.fs.FS.*;
import static org.project.utils.fs.Zip.*;
import static org.project.utils.json.JsonSchema.jsonSchema;

import org.project.utils.json.JsonSchema;
import org.project.utils.request.Request;

public class TestZip extends TestJson {

    public static void main(String[] args) throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        testZip();
    }

    public static void testZip() throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        JsonSchema json = jsonSchema(endpoint, uri);
        Request req = json.req();

        Map<String, Object> map0 = json.toMap("downloads.chromedriver", "platform", "win64");
        String url = (String) map0.get("url");
        debug(url);

        Request req1 = new Request(GET).uri(url);

        InputStream inputStream = req1.stream();
        byte[] bytes = req1.bytes();
        String str = req1.string();

        //debug(str);
        //debug(req1.pretty());

        debug(req1.statusCode());
        debug(req1.statusCode());

        debug(inputStream.toString());

        String last = last(url, "/");
        debug(last);

        String out = "test/zip/";
        String name = "filename";
        String filename = out + name;
        String filenameZip = filename + ".zip";
        String filenameTxt = filename + ".txt";
        String chromedriverRoot = "lib/chromedriver";
        String chromedriverFile = "chromedriver.exe";
        Path chromedriverPath = pathStr(chromedriverRoot, chromedriverFile);

        writeFile(out + last, inputStream);
        writeFile(filenameZip, bytes);
        writeFile(filenameTxt, str);
        writeFile(filenameTxt, req.string() + "\n" + "\n" + req.pretty());

        printAttrs();
        printAttrs(chromedriverPath);

        Path resolve = resolve(out);
        Path resolve1 = resolve(out, out);
        debug(resolve);
        debug(resolve1);

        debug(absolute(filenameZip).isAbsolute());
        debug(absolute(out).isAbsolute());

        debug(absolute(filenameZip));
        debug(absolute(out));

        debug(isDir(filenameZip));
        debug(isFile(filenameZip));
        debug(isDir(out));
        debug(isFile(out));

        debug(isDir(pathStr(filenameZip)) ? pathStr(filenameZip) : pathStr(filenameZip).getParent());
        debug(isDir(pathStr(out)) ? pathStr(out) : pathStr(out).getParent());

        debug(pathStr(filenameZip).getParent());
        debug(pathStr(out).getParent());

        debug(mkdirs(out));
        debug(mkdirs(filenameZip));
        debug(mkdirs("testDir/1"));

        debug(new File(out).toString());

        debug(readDir("src/test"));
        debug(readDir("src/test").toList());

        debug(delete("filename3.zip"));
        debug(delete("test"));
        debug(delete("testDir"));

        unzip(filenameZip, out);
        unzipFile(filenameZip, out + "1");
        unzipPass(filenameZip, out + "2");
        unzipSelenium(filenameZip, out + "3");
    }

}
