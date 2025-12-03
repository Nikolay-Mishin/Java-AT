package org.project.utils.stream;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipInputStream;

public class InputStream {

    public static FileInputStream input(String path) throws IOException {
        return new FileInputStream(path);
    }

    public static FileInputStream input(Path path) throws IOException {
        return input(path.toString());
    }

    public static FileInputStream input(File file) throws IOException {
        return new FileInputStream(file);
    }

    public static BufferedInputStream bufIn(String path) throws IOException {
        return bufIn(input(path));
    }

    public static BufferedInputStream bufIn(Path path) throws IOException {
        return bufIn(input(path));
    }

    public static BufferedInputStream bufIn(File file) throws IOException {
        return bufIn(input(file));
    }

    public static BufferedInputStream bufIn(java.io.InputStream input) {
        return new BufferedInputStream(input);
    }

    public static ByteArrayInputStream arrayIn(byte[] b) {
        return new ByteArrayInputStream(b);
    }

    public static ZipInputStream zipIn(String path) throws IOException {
        return zipIn(input(path));
    }

    public static ZipInputStream zipIn(Path path) throws IOException {
        return zipIn(input(path));
    }

    public static ZipInputStream zipIn(File file) throws IOException {
        return zipIn(input(file));
    }

    public static ZipInputStream zipIn(java.io.InputStream input) {
        return new ZipInputStream(input);
    }

    public static byte[] bytes(String path) throws IOException {
        return bytes(input(path));
    }

    public static byte[] bytes(Path path) throws IOException {
        return bytes(input(path));
    }

    public static byte[] bytes(File file) throws IOException {
        return bytes(input(file));
    }

    public static byte[] bytes(java.io.InputStream input) throws IOException {
        return input.readAllBytes();
    }

}
