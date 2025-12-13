package org.project.utils.stream;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipInputStream;

/**
 *
 */
public class InputStream {

    /**
     *
     * @param path String
     * @return FileInputStream
     * @throws IOException throws
     */
    public static FileInputStream input(String path) throws IOException {
        return new FileInputStream(path);
    }

    /**
     *
     * @param path Path
     * @return FileInputStream
     * @throws IOException throws
     */
    public static FileInputStream input(Path path) throws IOException {
        return input(path.toString());
    }

    /**
     *
     * @param file File
     * @return FileInputStream
     * @throws IOException throws
     */
    public static FileInputStream input(File file) throws IOException {
        return new FileInputStream(file);
    }

    /**
     *
     * @param path String
     * @return BufferedInputStream
     * @throws IOException throws
     */
    public static BufferedInputStream bufIn(String path) throws IOException {
        return bufIn(input(path));
    }

    /**
     *
     * @param path Path
     * @return BufferedInputStream
     * @throws IOException throws
     */
    public static BufferedInputStream bufIn(Path path) throws IOException {
        return bufIn(input(path));
    }

    /**
     *
     * @param file File
     * @return BufferedInputStream
     * @throws IOException throws
     */
    public static BufferedInputStream bufIn(File file) throws IOException {
        return bufIn(input(file));
    }

    /**
     *
     * @param input InputStream
     * @return BufferedInputStream
     */
    public static BufferedInputStream bufIn(java.io.InputStream input) {
        return new BufferedInputStream(input);
    }

    /**
     *
     * @param b byte[]
     * @return ByteArrayInputStream
     */
    public static ByteArrayInputStream arrayIn(byte[] b) {
        return new ByteArrayInputStream(b);
    }

    /**
     *
     * @param path String
     * @return ZipInputStream
     * @throws IOException throws
     */
    public static ZipInputStream zipIn(String path) throws IOException {
        return zipIn(input(path));
    }

    /**
     *
     * @param path Path
     * @return ZipInputStream
     * @throws IOException throws
     */
    public static ZipInputStream zipIn(Path path) throws IOException {
        return zipIn(input(path));
    }

    /**
     *
     * @param file File
     * @return ZipInputStream
     * @throws IOException throws
     */
    public static ZipInputStream zipIn(File file) throws IOException {
        return zipIn(input(file));
    }

    /**
     *
     * @param input InputStream
     * @return ZipInputStream
     */
    public static ZipInputStream zipIn(java.io.InputStream input) {
        return new ZipInputStream(input);
    }

    /**
     *
     * @param path String
     * @return byte[]
     * @throws IOException throws
     */
    public static byte[] bytes(String path) throws IOException {
        return bytes(input(path));
    }

    /**
     *
     * @param path Path
     * @return byte[]
     * @throws IOException throws
     */
    public static byte[] bytes(Path path) throws IOException {
        return bytes(input(path));
    }

    /**
     *
     * @param file File
     * @return byte[]
     * @throws IOException throws
     */
    public static byte[] bytes(File file) throws IOException {
        return bytes(input(file));
    }

    /**
     *
     * @param input InputStream
     * @return byte[]
     * @throws IOException throws
     */
    public static byte[] bytes(java.io.InputStream input) throws IOException {
        return input.readAllBytes();
    }

}
