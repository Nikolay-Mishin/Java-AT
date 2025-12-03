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
    protected static String chromedriverRoot = "lib/chromedriver";
    protected static String chromedriverFile = "chromedriver.exe";
    protected static Path chromedriverPath = pathStr(chromedriverRoot, chromedriverFile);
    protected static String attrsTest = "home/user/example.txt";

    public static void main(String[] args) throws IOException {
        //testFS();
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
        debug(FileSystems.getDefault().getPath("lib/chromedriver"));
        debug(FileSystems.getDefault().getPath("lib/chromedriver").getFileSystem());
        debug(FileSystems.getDefault().getPath("lib/chromedriver").getFileSystem().getRootDirectories());
        debug(FileSystems.getDefault().getPath("lib/chromedriver").getFileSystem().getFileStores());
        debug(FileSystems.getDefault().getPath("lib/chromedriver").getFileSystem().supportedFileAttributeViews());

        debug(FileSystems.getDefault().getPath("lib/chromedriver/chromedriver.exe"));
        debug(FileSystems.getDefault().getPath("lib/chromedriver/chromedriver.exe").toFile());
        debug(FileSystems.getDefault().getPath("lib/chromedriver/chromedriver.exe").toFile().canExecute());

        debug(Path.of("lib/chromedriver/chromedriver.exe"));
        debug(Path.of("lib/chromedriver/chromedriver.exe").toFile());
        debug(Path.of("lib/chromedriver/chromedriver.exe").toFile().canExecute());

        debug(delete("filename1.zip"));
        debug(delete(FileSystems.getDefault().getPath("filename.txt").toFile()));

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
        printCustomAttrs(filenameZip, "customAttr");
        printCustomAttrs(filenameZip, "customAttr", "value");
        printCustomAttrs(filenameZip, "customAttr");
    }

}
