package org.project.utils.stream;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipOutputStream;

/**
 *
 */
public class OutputStream {

    /**
     *
     * @param path String
     * @return FileOutputStream
     * @throws IOException throws
     */
    public static FileOutputStream output(String path) throws IOException {
        return new FileOutputStream(path);
    }

    /**
     *
     * @param path Path
     * @return FileOutputStream
     * @throws IOException throws
     */
    public static FileOutputStream output(Path path) throws IOException {
        return output(path.toString());
    }

    /**
     *
     * @param file File
     * @return FileOutputStream
     * @throws IOException throws
     */
    public static FileOutputStream output(File file) throws IOException {
        return new FileOutputStream(file);
    }

    /**
     *
     * @param path String
     * @return BufferedOutputStream
     * @throws IOException throws
     */
    public static BufferedOutputStream bufOut(String path) throws IOException {
        return bufOut(output(path));
    }

    /**
     *
     * @param path Path
     * @return BufferedOutputStream
     * @throws IOException throws
     */
    public static BufferedOutputStream bufOut(Path path) throws IOException {
        return bufOut(output(path));
    }

    /**
     *
     * @param file File
     * @return BufferedOutputStream
     * @throws IOException throws
     */
    public static BufferedOutputStream bufOut(File file) throws IOException {
        return bufOut(output(file));
    }

    /**
     *
     * @param output OutputStream
     * @return BufferedOutputStream
     */
    public static BufferedOutputStream bufOut(java.io.OutputStream output) {
        return new BufferedOutputStream(output);
    }

    /**
     *
     * @param size int
     * @return ByteArrayOutputStream
     */
    public static ByteArrayOutputStream arrayOut(int size) {
        return new ByteArrayOutputStream(size);
    }

    /**
     *
     * @param path String
     * @return ZipOutputStream
     * @throws IOException  throws
     */
    public static ZipOutputStream zipOut(String path) throws IOException {
        return zipOut(output(path));
    }

    /**
     *
     * @param path Path
     * @return ZipOutputStream
     * @throws IOException  throws
     */
    public static ZipOutputStream zipOut(Path path) throws IOException {
        return zipOut(output(path));
    }

    /**
     *
     * @param file File
     * @return ZipOutputStream
     * @throws IOException throws
     */
    public static ZipOutputStream zipOut(File file) throws IOException {
        return zipOut(output(file));
    }

    /**
     *
     * @param output OutputStream
     * @return ZipOutputStream
     */
    public static ZipOutputStream zipOut(java.io.OutputStream output) {
        return new ZipOutputStream(output);
    }

}
