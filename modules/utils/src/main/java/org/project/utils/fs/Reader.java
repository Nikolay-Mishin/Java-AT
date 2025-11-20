package org.project.utils.fs;

import java.io.*;
import java.nio.file.Path;

import static org.project.utils.stream.InputStream.input;

public class Reader {

    public static FileReader reader(String path) throws IOException {
        return new FileReader(path);
    }

    public static FileReader reader(Path path) throws IOException {
        return reader(path.toString());
    }

    public static FileReader reader(File file) throws IOException {
        return new FileReader(file);
    }

    public static InputStreamReader reader(InputStream input) throws IOException {
        return new InputStreamReader(input);
    }

    public static BufferedReader bufReader(String path) throws IOException {
        return bufReader(input(path));
    }

    public static BufferedReader bufReader(Path path) throws IOException {
        return bufReader(path.toString());
    }

    public static BufferedReader bufReader(File file) throws IOException {
        return bufReader(input(file));
    }

    public static BufferedReader bufReader(InputStream input) throws IOException {
        return new BufferedReader(reader(input));
    }

}
