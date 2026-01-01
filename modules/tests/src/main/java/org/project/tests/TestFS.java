package org.project.tests;

import static java.nio.file.FileSystems.getDefault;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.FileStore;

import static org.project.utils.Helper.debug;
import static org.project.utils.fs.Attributes.supportedTypes;
import static org.project.utils.fs.Attributes.printAttrs;
import static org.project.utils.fs.Attributes.printUserAttrs;
import static org.project.utils.fs.File.delete;
import static org.project.utils.fs.File.pathStr;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.fs.Attributes;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestFS<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestVersion<T, W, D> {
    /**
     *
     */
    protected static String chromedriverRoot;
    /**
     *
     */
    protected static String chromedriverFile;
    /**
     *
     */
    protected static String chromedriverPathStr;
    /**
     *
     */
    protected static Path chromedriverPath;
    /**
     *
     */
    protected static String fsDelete;
    /**
     *
     */
    protected static String fsFile;
    /**
     *
     */
    protected static String attrsTest;
    /**
     *
     */
    protected static String attrK;
    /**
     *
     */
    protected static String attrV;

    /**
     *
     */
    @ConstructorProperties({})
    public TestFS() {
        debug("TestFS:init");
        chromedriverRoot = c.getChromeDriverRoot();
        chromedriverFile = c.getChromeDriverFile();
        chromedriverPathStr = chromedriverRoot + chromedriverFile;
        chromedriverPath = pathStr(chromedriverRoot, chromedriverFile);
        fsDelete = c.getFsDelete();
        fsFile = c.getFsFile();
        attrsTest = c.getFsAttrs();
        attrK = c.getFsAttrsK();
        attrV = c.getFsAttrsV();
    }

    /**
     *
     * @throws IOException throws
     */
    public static void testFS() throws IOException {
        debug("testFS");
        for (FileStore store: getDefault().getFileStores()) {
            long total = store.getTotalSpace() / 1024;
            long used = (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024;
            long avail = store.getUsableSpace() / 1024;
            System.out.format("%-20s %12d %12d %12d%n", store, total, used, avail);
        }

        debug(getDefault().getRootDirectories());
        debug(getDefault().getRootDirectories());
        debug(getDefault().getRootDirectories());
        debug(getDefault().getPath(chromedriverRoot));
        debug(getDefault().getPath(chromedriverRoot).getFileSystem());
        debug(getDefault().getPath(chromedriverRoot).getFileSystem().getRootDirectories());
        debug(getDefault().getPath(chromedriverRoot).getFileSystem().getFileStores());
        debug(getDefault().getPath(chromedriverRoot).getFileSystem().supportedFileAttributeViews());

        debug(getDefault().getPath(chromedriverPathStr));
        debug(getDefault().getPath(chromedriverPathStr).toFile());
        debug(getDefault().getPath(chromedriverPathStr).toFile().canExecute());

        debug(Path.of(chromedriverPathStr));
        debug(Path.of(chromedriverPathStr).toFile());
        debug(Path.of(chromedriverPathStr).toFile().canExecute());

        debug(delete(fsDelete));
        debug(delete(getDefault().getPath(fsFile).toFile()));

        debug(getDefault().supportedFileAttributeViews());
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     * @throws IOException throws
     */
    public static void testAttrs() throws ReflectiveOperationException, IOException {
        debug("testAttrs");
        debug(supportedTypes());
        testSystemAttrs();
        testCustomAttrs();
    }

    /**
     *
     * @throws IOException throws
     */
    public static void testSystemAttrs() throws IOException {
        debug("testSystemAttrs");
        printAttrs(attrsTest);
        printAttrs("/" + attrsTest);
        printAttrs();
        printAttrs(filenameZip);
        printAttrs(chromedriverPath);
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     */
    public static void testCustomAttrs() throws ReflectiveOperationException {
        debug("testCustomAttrs");
        printUserAttrs(filenameZip, attrK, attrV);
        printUserAttrs(filenameZip, attrK);
        printUserAttrs(filenameZip, "name");
        new Attributes(filenameZip);
    }

}
