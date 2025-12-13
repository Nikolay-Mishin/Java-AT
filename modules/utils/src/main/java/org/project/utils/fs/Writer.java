package org.project.utils.fs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

import static org.project.utils.stream.OutputStream.output;

/**
 *
 */
public class Writer {

    /**
     *
     * @param path String
     * @return FileWriter
     * @throws IOException throws
     */
    public static FileWriter writer(String path) throws IOException {
        return new FileWriter(path);
    }

    /**
     *
     * @param path Path
     * @return FileWriter
     * @throws IOException throws
     */
    public static FileWriter writer(Path path) throws IOException {
        return writer(path.toString());
    }

    /**
     *
     * @param file File
     * @return FileWriter
     * @throws IOException throws
     */
    public static FileWriter writer(File file) throws IOException {
        return new FileWriter(file);
    }

    /**
     *
     * @param output OutputStream
     * @return OutputStreamWriter
     * @throws IOException throws
     */
    public static OutputStreamWriter writer(OutputStream output) throws IOException {
        return new OutputStreamWriter(output);
    }

    /**
     *
     * @param path String
     * @return BufferedWriter
     * @throws IOException throws
     */
    public static BufferedWriter bufWriter(String path) throws IOException {
        return bufWriter(output(path));
    }

    /**
     *
     * @param path Path
     * @return BufferedWriter
     * @throws IOException throws
     */
    public static BufferedWriter bufWriter(Path path) throws IOException {
        return bufWriter(output(path));
    }

    /**
     *
     * @param file File
     * @return BufferedWriter
     * @throws IOException throws
     */
    public static BufferedWriter bufWriter(File file) throws IOException {
        return bufWriter(output(file));
    }

    /**
     *
     * @param output OutputStream
     * @return BufferedWriter
     * @throws IOException throws
     */
    public static BufferedWriter bufWriter(OutputStream output) throws IOException {
        return new BufferedWriter(writer(output));
    }

}
