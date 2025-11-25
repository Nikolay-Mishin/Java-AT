package org.project.utils.test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Map;

import static org.project.utils.Helper.*;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.fs.FS.*;
import static org.project.utils.fs.Zip.*;
import static org.project.utils.json.JsonSchema.jsonSchema;

import org.project.utils.json.JsonSchema;
import org.project.utils.request.Request;

public class TestZip extends TestJson {
    protected static String rootTest = "tests/";
    protected static String outZip = rootTest + "zip/";
    protected static String nameZip = "filename";
    protected static String filename = rootTest + nameZip;
    protected static String filenameZip = filename + ".zip";
    protected static String filenameTxt = filename + ".txt";
    protected static String mkdirRoot = rootTest + "testDir/";
    protected static String mkdir = mkdirRoot + "1";
    protected static String readDir = "src/main/resources";

    public static void main(String[] args)
        throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, URISyntaxException, ClassNotFoundException, InstantiationException, NoSuchFieldException
    {
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

        writeFile(outZip + last, inputStream);
        writeFile(filenameZip, bytes);
        writeFile(filenameTxt, str);
        writeFile(filenameTxt, req.string() + "\n" + "\n" + req.pretty());

        Path resolve = resolve(outZip);
        Path resolve1 = resolve(outZip, outZip);
        debug(resolve);
        debug(resolve1);

        debug(absolute(filenameZip).isAbsolute());
        debug(absolute(outZip).isAbsolute());

        debug(absolute(filenameZip));
        debug(absolute(outZip));

        debug(isDir(filenameZip));
        debug(isFile(filenameZip));
        debug(isDir(outZip));
        debug(isFile(outZip));

        debug(isDir(pathStr(filenameZip)) ? pathStr(filenameZip) : pathStr(filenameZip).getParent());
        debug(isDir(pathStr(outZip)) ? pathStr(outZip) : pathStr(outZip).getParent());

        debug(pathStr(filenameZip).getParent());
        debug(pathStr(outZip).getParent());

        debug(mkdirs(outZip));
        debug(mkdirs(filenameZip));
        debug(mkdirs(mkdir));

        debug(new File(outZip).toString());

        debug(readDir(readDir).toList());
        debug(readDir(outZip).toList());

        debug(delete(filenameTxt));
        debug(delete(mkdirRoot));

        unzip(filenameZip, outZip);
        unzipFile(filenameZip, outZip + "1");
        unzipPass(filenameZip, outZip + "2");
        unzipSelenium(filenameZip, outZip + "3");

        debug(filenameZip);
    }

}
