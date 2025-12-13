package org.project.utils.fs;

import static java.nio.file.Files.createDirectories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

import static org.apache.commons.io.IOUtils.copy;
import static org.apache.commons.io.IOUtils.write;

import static org.project.utils.Helper.debug;
import static org.project.utils.fs.Writer.bufWriter;
import static org.project.utils.stream.InputStream.input;
import static org.project.utils.stream.OutputStream.output;

import org.project.utils.function.ConsumerVoidWithExceptions;

/**
 *
 */
public class FS extends Reader {

    /**
     *
     * @param path String
     * @return String
     * @throws IOException throws
     */
    public static String readFile(String path) throws IOException {
        return readFile(input(path));
    }

    /**
     *
     * @param input InputStream
     * @return String
     */
    public static String readFile(InputStream input) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = bufReader(input)) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            debug("Error reading file.");
        }
        return sb.toString();
    }

    /**
     *
     * @param path Path
     * @param input InputStream
     * @throws IOException throws
     */
    public static void writeFile(Path path, InputStream input) throws IOException {
        writeFile(path(path), input);
    }

    /**
     *
     * @param path String
     * @param input InputStream
     * @throws IOException throws
     */
    public static void writeFile(String path, InputStream input) throws IOException {
        writeFile(path, () -> writeStream(output(path), input));
    }

    /**
     *
     * @param path String
     * @param data byte[]
     */
    public static void writeFile(String path, byte[] data) throws IOException {
        writeFile(path, () -> writeStream(output(path), data));
    }

    /**
     *
     * @param path String
     * @param data String
     */
    public static void writeFile(String path, String data) throws IOException {
        writeFile(path, () -> writeStream(output(path), data));
    }

    /**
     *
     * @param path String
     * @param cb ConsumerVoidWithExceptions {T, E}
     */
    public static <T, E extends IOException> void writeFile(String path, ConsumerVoidWithExceptions<T, E> cb) throws IOException {
        debug("writeFile: " + path);
        mkdirs(path);
        cb.accept();
    }

    /**
     *
     * @param output OutputStream
     * @param data String
     */
    public static void writeStream(OutputStream output, String data) {
        try (BufferedWriter bw = bufWriter(output)) {
            bw.write(data);
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing file.");
        }
    }

    /**
     *
     * @param output OutputStream
     * @param input InputStream
     * @throws IOException throws
     */
    public static void writeStream(OutputStream output, InputStream input) throws IOException {
        //writeStream(output, bytes(input));
        writeStream(output, () -> copy(input, output), input::close);
    }

    /**
     *
     * @param output OutputStream
     * @param data byte[]
     */
    public static void writeStream(OutputStream output, byte[] data) throws IOException {
        //output.write(data);
        writeStream(output, () -> write(data, output));
    }

    /**
     *
     * @param output OutputStream
     * @param cb Supplier {T}
     */
    public static <T, E extends IOException> void writeStream(OutputStream output, ConsumerVoidWithExceptions<T, E> cb) throws E {
        writeStream(output, cb, () -> {});
    }

    /**
     *
     * @param output OutputStream
     * @param cb Supplier {T}
     * @param close Supplier {T}
     */
    public static <T, E extends IOException> void writeStream(OutputStream output, ConsumerVoidWithExceptions<T, E> cb, ConsumerVoidWithExceptions<T, E> close) throws E
    {
        try (output) {
            cb.accept();
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing file.");
        }
        close.accept();
    }

    /**
     *
     * @param path String
     * @return boolean
     * @throws IOException throws
     */
    public static boolean mkdirs(String path) throws IOException {
        //return mkdirs(new File(path));
        return mkdirs(pathStr(path));
    }

    /**
     *
     * @param path Path
     * @return boolean
     * @throws IOException throws
     */
    public static boolean mkdirs(Path path) throws IOException {
        //return mkdirs(path.toFile());
        return createDirectories(isDir(path) ? path : path.getParent()).isAbsolute();
    }

    /**
     *
     * @param file File
     * @return boolean
     * @throws IOException throws
     */
    public static boolean mkdirs(File file) throws IOException {
        //return (isDir(file) ? file : file.getParentFile()).mkdirs();
        return mkdirs(file.toPath());
    }

    /**
     *
     * @param ze ZipEntry
     * @param path String
     * @return boolean
     * @throws IOException throws
     */
    public static boolean mkdirs(ZipEntry ze, String path) throws IOException {
        return mkdirs(ze, pathStr(path));
    }

    /**
     *
     * @param ze ZipEntry
     * @param file File
     * @return boolean
     * @throws IOException throws
     */
    public static boolean mkdirs(ZipEntry ze, File file) throws IOException {
        //return mkdirs(isDir(ze) ? file : file.getParentFile());
        return mkdirs(file.toPath());
    }

    /**
     *
     * @param ze ZipEntry
     * @param path Path
     * @return boolean
     * @throws IOException throws
     */
    public static boolean mkdirs(ZipEntry ze, Path path) throws IOException {
        return mkdirs(isDir(ze) ? path : path.getParent());
    }

    /**
     *
     * @param path {@code final} String
     * @return boolean
     * @throws IOException throws
     */
    public static boolean deleteDirWalk(final String path) throws IOException {
        try (Stream<File> fileStream = dirReverseToFile(path)) {
            fileStream.forEach(File::delete);
            //fileStream.forEach(File::delete()); // ошибки игнорируются
        }
        return exist(path);
    }

    /**
     *
     * @param path {@code final} String
     */
    public static void printFile(final String path) {
        printFile(pathStr(path));
    }

    /**
     *
     * @param path {@code final} Path
     */
    public static void printFile(final Path path) {
        debug(path);
        debug(path.getFileName());
        debug(path.getParent());
    }

    /**
     *
     * @param file {@code final} File
     */
    public static void printFile(final File file) {
        debug(file);
        debug(file.getName());
        debug(file.getParent());
    }

}
