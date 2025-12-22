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

/**
 * @param <T> extends TestBaseConfig
 */
public class TestZip<T extends TestBaseConfig> extends TestJson<T> {
    /**
     *
     */
    protected static String rootZip;
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
     * @throws NoSuchFieldException throws
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    @ConstructorProperties({})
    public TestZip() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("TestZip:init");
        rootZip = c.getZipRoot();
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
     * @param url String
     * @param pathList String[]
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void loadZip(String url, String... pathList) throws IOException, URISyntaxException, ReflectiveOperationException {
        loadZip(url, pathStr(pathList));
    }

    /**
     *
     * @param url String
     * @param file File
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void loadZip(String url, File file) throws IOException, URISyntaxException, ReflectiveOperationException {
        loadZip(url, file.toPath());
    }

    /**
     *
     * @param url String
     * @param path Path
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void loadZip(String url, Path path) throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("loadZip");
        setReq(url, GET);
        writeFile(path, req.stream());
    }

    /**
     *
     * @param url String
     * @param out String
     * @param pathList String[]
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void loadZip(String url, String out, String... pathList) throws IOException, URISyntaxException, ReflectiveOperationException {
        loadZip(url, out, pathStr(pathList));
    }

    /**
     *
     * @param url String
     * @param out String
     * @param file File
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void loadZip(String url, String out, File file) throws IOException, URISyntaxException, ReflectiveOperationException {
        loadZip(url, out, file.toPath());
    }

    /**
     *
     * @param url String
     * @param out String
     * @param path Path
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void loadZip(String url, String out, Path path) throws IOException, URISyntaxException, ReflectiveOperationException {
        loadZip(url, path);
        debug("unZip");
        unzip(path, resolve(out));
    }

    /**
     *
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void testZip() throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testZip");
        debug(delete(rootZip));

        loadJson(uri, endpoint, jsonGet, jsonK, jsonV, jsonUrl);

        String last = last(url);

        debug(last);
        debug(resolve(rootZip, last));
        debug(filenameZip);

        loadZip(url, outZip, resolve(rootZip, last));

        debug(req.statusCode());

        byte[] bytes = req.bytes();
        String str = req.string();

        //debug(str);
        //debug(req.pretty());

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

        debug(new File(outZip).toString());

        writeFile(filenameZip, bytes);

        debug(mkdirs(mkdir));

        debug(delete(filenameTxt));
        debug(delete(mkdirRoot));

        //unzip(filenameZip, outZip);
        unzipFile(filenameZip, outZip + "1");
        unzipPass(filenameZip, outZip + "2");
        unzipSelenium(filenameZip, outZip + "3");

        debug(readDir(readDir).toList());
        debug(readDir(outZip).toList());

        debug(filenameZip);
    }

}
