package org.project.utils.fs;

import java.io.*;
import java.io.File;
import java.nio.file.Path;

import static org.project.utils.stream.OutputStream.output;

public class Writer {

    public static FileWriter writer(String path) throws IOException {
        return new FileWriter(path);
    }

    public static FileWriter writer(Path path) throws IOException {
        return writer(path.toString());
    }

    public static FileWriter writer(File file) throws IOException {
        return new FileWriter(file);
    }

    public static OutputStreamWriter writer(OutputStream output) throws IOException {
        return new OutputStreamWriter(output);
    }

    public static BufferedWriter bufWriter(String path) throws IOException {
        return bufWriter(output(path));
    }

    public static BufferedWriter bufWriter(Path path) throws IOException {
        return bufWriter(output(path));
    }

    public static BufferedWriter bufWriter(File file) throws IOException {
        return bufWriter(output(file));
    }

    public static BufferedWriter bufWriter(OutputStream output) throws IOException {
        return new BufferedWriter(writer(output));
    }

}
