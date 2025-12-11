package org.project.utils.test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;

import static org.project.utils.Helper.debug;
import static org.project.utils.fs.Attributes.printAttrs;
import static org.project.utils.fs.Attributes.printCustomAttrs;
import static org.project.utils.fs.File.delete;
import static org.project.utils.fs.File.pathStr;

public class TestFS extends TestZip {
    protected static String chromedriverRoot;
    protected static String chromedriverFile;
    protected static String chromedriverPathStr;
    protected static Path chromedriverPath;
    protected static String fsDelete;
    protected static String fsFile;
    protected static String attrsTest;
    protected static String attrK;
    protected static String attrV;

    public TestFS() {
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

    public static void main(String[] args) throws IOException {
        testFS();
        testAttrs();
    }

    public static void testFS() throws IOException {
        debug("testFS");
        for (FileStore store: FileSystems.getDefault().getFileStores()) {
            long total = store.getTotalSpace() / 1024;
            long used = (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024;
            long avail = store.getUsableSpace() / 1024;
            System.out.format("%-20s %12d %12d %12d%n", store, total, used, avail);
        }

        debug(FileSystems.getDefault().getRootDirectories());
        debug(FileSystems.getDefault().getRootDirectories());
        debug(FileSystems.getDefault().getRootDirectories());
        debug(FileSystems.getDefault().getPath(chromedriverRoot));
        debug(FileSystems.getDefault().getPath(chromedriverRoot).getFileSystem());
        debug(FileSystems.getDefault().getPath(chromedriverRoot).getFileSystem().getRootDirectories());
        debug(FileSystems.getDefault().getPath(chromedriverRoot).getFileSystem().getFileStores());
        debug(FileSystems.getDefault().getPath(chromedriverRoot).getFileSystem().supportedFileAttributeViews());

        debug(FileSystems.getDefault().getPath(chromedriverPathStr));
        debug(FileSystems.getDefault().getPath(chromedriverPathStr).toFile());
        debug(FileSystems.getDefault().getPath(chromedriverPathStr).toFile().canExecute());

        debug(Path.of(chromedriverPathStr));
        debug(Path.of(chromedriverPathStr).toFile());
        debug(Path.of(chromedriverPathStr).toFile().canExecute());

        debug(delete(fsDelete));
        debug(delete(FileSystems.getDefault().getPath(fsFile).toFile()));

        debug(FileSystems.getDefault().supportedFileAttributeViews());
    }

    public static void testAttrs() {
        debug("testAttrs");
        debug(FileSystems.getDefault().supportedFileAttributeViews());
        printAttrs();
        printAttrs(attrsTest);
        printAttrs("/" + attrsTest);
        printAttrs(filenameZip);
        printAttrs(chromedriverPath);
        printCustomAttrs(filenameZip, attrK);
        printCustomAttrs(filenameZip, attrK, attrV);
        printCustomAttrs(filenameZip, attrK);
    }

}
