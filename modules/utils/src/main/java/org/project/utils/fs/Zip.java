package org.project.utils.fs;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import static java.lang.Math.toIntExact;
import static java.nio.file.Files.copy;
import static org.project.utils.Helper.debug;
import static org.project.utils.fs.FS.delete;
import static org.project.utils.fs.FS.isDir;
import static org.project.utils.fs.FS.mkdirs;
import static org.project.utils.fs.FS.path;
import static org.project.utils.fs.FS.pathStr;
import static org.project.utils.fs.FS.resolve;
import static org.project.utils.stream.InputStream.arrayIn;
import static org.project.utils.stream.InputStream.input;
import static org.project.utils.stream.InputStream.zipIn;
import static org.project.utils.stream.OutputStream.bufOut;

public class Zip {

    public static void unzip(String src, String out) throws IOException {
        unzip(input(src), out);
    }

    public static void unzip(Path src, Path out) throws IOException {
        unzip(path(src), path(out));
    }

    public static void unzip(InputStream src, String out) throws IOException {
        unzip(src, pathStr(out));
    }

    public static void unzip(byte[] src, String out) throws IOException {
        unzip(arrayIn(src), out);
    }

    public static void unzip(byte[] src, Path out) throws IOException {
        unzip(arrayIn(src), out);
    }

    public static void unzip(InputStream src, Path out) throws IOException {
        debug("Clear: " + out);
        debug(delete(out));
        out = out.toAbsolutePath();
        try (ZipInputStream zipIn = zipIn(src)) {
            for (ZipEntry ze; (ze = zipIn.getNextEntry()) != null; ) {
                Path resolve = resolve(out, ze.getName());
                if (!resolve.startsWith(out)) {
                    // see: https://snyk.io/research/zip-slip-vulnerability
                    throw new RuntimeException("Entry with an illegal path: " + ze.getName());
                }
                mkdirs(ze, resolve);
                copy(zipIn, resolve);
            }
        }
    }

    public static void unzipFile(String src, String out) throws IOException {
        unzip(new File(src), out);
    }

    public static void unzipFile(Path src, Path out) throws IOException {
        unzipFile(path(src), path(out));
    }

    public static void unzip(File src, Path out) throws IOException {
        unzip(src, path(out));
    }

    public static void unzip(File src, String out) throws IOException {
        try (ZipInputStream zipIn = zipIn(src)) {
            ZipEntry ze = zipIn.getNextEntry();
            while (ze != null) {
                File file = new File(out, ze.getName());
                mkdirs(ze, file);
                if (!isDir(ze)) {
                    writeFile(zipIn, ze, file);
                }
                ze = zipIn.getNextEntry();
            }
        }
    }

    public static void writeFile(ZipInputStream zipIn, ZipEntry ze, File file) throws IOException {
        try (BufferedOutputStream bos = bufOut(file)) {
            int bufferSize = toIntExact(ze.getSize());
            byte[] buffer = new byte[bufferSize > 0 ? bufferSize : 1];
            int location;

            while ((location = zipIn.read(buffer)) != -1) {
                bos.write(buffer, 0, location);
            }
        }
    }

    public static void unzipPass(String src, String out) {
        unzip(src, out, "");
    }

    public static void unzip(String src, String out, String password) {
        try {
            ZipFile zipFile = new ZipFile(src);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(out);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void unzipSelenium(String src, String out) throws IOException {
        unzipSelenium(input(src), new File(out));
    }

    public static void unzipSelenium(Path src, Path out) throws IOException {
        unzipSelenium(src.toString(), out.toString());
    }

    public static void unzipSelenium(File src, File out) throws IOException {
        unzipSelenium(input(src), out);
    }

    public static void unzipSelenium(InputStream src, File out) throws IOException {
        org.openqa.selenium.io.Zip.unzip(src, out);
    }

}
