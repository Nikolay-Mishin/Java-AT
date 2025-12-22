package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.last;
import static org.project.utils.fs.FS.absolute;
import static org.project.utils.fs.FS.delete;
import static org.project.utils.fs.FS.isDir;
import static org.project.utils.fs.FS.isFile;
import static org.project.utils.fs.FS.mkdirs;
import static org.project.utils.fs.FS.pathStr;
import static org.project.utils.fs.FS.readDir;
import static org.project.utils.fs.FS.resolve;
import static org.project.utils.fs.FS.writeFile;
import static org.project.utils.fs.Zip.unzipFile;
import static org.project.utils.fs.Zip.unzipPass;
import static org.project.utils.fs.Zip.unzipSelenium;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.fs.Zip;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestZip<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestJson<T, W, D> {
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
    public TestZip() throws NoSuchFieldException, IllegalAccessException {
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
     * @param endpoint String
     * @param uri String
     * @param key String
     * @param k String
     * @param v String
     * @param urlK String
     * @param out String
     * @param rootZip String
     * @return String
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static String loadZip(String uri, String endpoint, String key, String k, String v, String urlK, String out, String rootZip)
        throws IOException, URISyntaxException, ReflectiveOperationException
    {
        debug("loadZip");
        return setJson(Zip.loadZip(uri, endpoint, key, k, v, urlK, out, rootZip));
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
        loadZip(uri, endpoint, jsonGet, jsonK, jsonV, jsonUrl, outZip, rootZip);

        debug(req.statusCode());

        String last = last(url);

        debug(last);
        debug(resolve(rootZip, last));
        debug(filenameZip);

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

        unzipFile(filenameZip, outZip + "1");
        unzipPass(filenameZip, outZip + "2");
        unzipSelenium(filenameZip, outZip + "3");

        debug(readDir(readDir).toList());
        debug(readDir(outZip).toList());

        debug(filenameZip);
    }

}
