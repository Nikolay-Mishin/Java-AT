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
