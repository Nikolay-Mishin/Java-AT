package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.last;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.fs.FS.absolute;
import static org.project.utils.fs.FS.delete;
import static org.project.utils.fs.FS.isDir;
import static org.project.utils.fs.FS.isFile;
import static org.project.utils.fs.FS.mkdirs;
import static org.project.utils.fs.FS.pathStr;
import static org.project.utils.fs.FS.readDir;
import static org.project.utils.fs.FS.resolve;
import static org.project.utils.fs.FS.writeFile;
import static org.project.utils.fs.Zip.unzip;
import static org.project.utils.fs.Zip.unzipFile;
import static org.project.utils.fs.Zip.unzipPass;
import static org.project.utils.fs.Zip.unzipSelenium;

import org.project.utils.config.TestBaseConfig;
import org.project.utils.request.Request;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestZip<T extends TestBaseConfig> extends TestJson<T> {
    /**
     *
     */
    protected static String outZip;
    /**
     *
     */
    protected static String filename;
    /**
     *
     */
    protected static String filenameZip;
    /**
     *
     */
    protected static String filenameTxt;
    /**
     *
     */
    protected static String mkdirRoot;
    /**
     *
     */
    protected static String mkdir;
    /**
     *
     */
    protected static String readDir;

    /**
     *
     */
    @ConstructorProperties({})
    public TestZip() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("TestZip:init");
        outZip = c.getZipOut();
        filename = c.getZipFilename();
        filenameZip = c.getZipFilenameFull();
        filenameTxt = c.getZipFilenameTxt();
        mkdirRoot = c.getZipMkdirRoot();
        mkdir = c.getZipMkdir();
        readDir = c.getZipReadDir();
    }

    /**
     *
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void testZip() throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testZip");
        url(endpoint, uri, jsonGet, jsonK, jsonV, jsonUrl);

        req = new Request(GET).uri(url);

        InputStream inputStream = req.stream();
        byte[] bytes = req.bytes();
        String str = req.string();

        //debug(str);
        //debug(req.pretty());

        debug(req.statusCode());
        debug(req.statusCode());

        debug(inputStream.toString());

        String last = last(url);
        debug(last);

        writeFile(outZip + last, inputStream);
        writeFile(filenameZip, bytes);
        writeFile(filenameTxt, str);
        writeFile(filenameTxt, TestJson.req.string() + "\n" + "\n" + TestJson.req.pretty());

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

        debug(new File(outZip).toString());

        debug(readDir(readDir).toList());
        debug(readDir(outZip).toList());

        debug(mkdirs(mkdir));

        debug(delete(filenameTxt));
        debug(delete(mkdirRoot));

        unzip(filenameZip, outZip);
        unzipFile(filenameZip, outZip + "1");
        unzipPass(filenameZip, outZip + "2");
        unzipSelenium(filenameZip, outZip + "3");

        debug(filenameZip);
    }

}
