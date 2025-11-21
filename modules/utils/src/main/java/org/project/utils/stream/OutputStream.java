package org.project.utils.stream;

import java.io.*;
import java.nio.file.Path;
import java.util.zip.ZipOutputStream;

public class OutputStream {

    public static FileOutputStream output(String path) throws IOException {
        return new FileOutputStream(path);
    }

    public static FileOutputStream output(Path path) throws IOException {
        return output(path.toString());
    }

    public static FileOutputStream output(File file) throws IOException {
        return new FileOutputStream(file);
    }

    public static BufferedOutputStream bufOut(String path) throws IOException {
        return bufOut(output(path));
    }

    public static BufferedOutputStream bufOut(Path path) throws IOException {
        return bufOut(output(path));
    }

    public static BufferedOutputStream bufOut(File file) throws IOException {
        return bufOut(output(file));
    }

    public static BufferedOutputStream bufOut(java.io.OutputStream output) {
        return new BufferedOutputStream(output);
    }

    public static ByteArrayOutputStream arrayOut(int size) {
        return new ByteArrayOutputStream(size);
    }

    public static ZipOutputStream zipOut(String path) throws IOException {
        return zipOut(output(path));
    }

    public static ZipOutputStream zipOut(Path path) throws IOException {
        return zipOut(output(path));
    }

    public static ZipOutputStream zipOut(File file) throws IOException {
        return zipOut(output(file));
    }

    public static ZipOutputStream zipOut(java.io.OutputStream output) {
        return new ZipOutputStream(output);
    }

}
