package org.project.utils.fs;

import static java.nio.file.Files.createDirectories;

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

import static org.project.utils.fs.FS.*;
import static org.project.utils.stream.InputStream.*;

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
        out = out.toAbsolutePath();
        try (ZipInputStream zipIn = zipIn(src)) {
            for (ZipEntry ze; (ze = zipIn.getNextEntry()) != null; ) {
                Path resolve = out.resolve(ze.getName()).normalize();
                if (!resolve.startsWith(out)) {
                    // see: https://snyk.io/research/zip-slip-vulnerability
                    throw new RuntimeException("Entry with an illegal path: " + ze.getName());
                }
                if (ze.isDirectory()) {
                    createDirectories(resolve);
                } else {
                    createDirectories(resolve.getParent());
                    Files.copy(zipIn, resolve);
                }
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
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(src))) {
            ZipEntry ze = zipIn.getNextEntry();
            while (ze != null) {
                File file = new File(out, ze.getName());

                if (ze.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parent = file.getParentFile();

                    if (!parent.exists()) {
                        parent.mkdirs();
                    }

                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                        int bufferSize = Math.toIntExact(ze.getSize());
                        byte[] buffer = new byte[bufferSize > 0 ? bufferSize : 1];
                        int location;

                        while ((location = zipIn.read(buffer)) != -1) {
                            bos.write(buffer, 0, location);
                        }
                    }
                }
                ze = zipIn.getNextEntry();
            }
        }
    }

}
