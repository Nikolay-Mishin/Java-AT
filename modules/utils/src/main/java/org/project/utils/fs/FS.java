package org.project.utils.fs;

import static java.nio.file.Files.createDirectories;

import java.io.*;
import java.io.File;
import java.nio.file.*;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

import static org.apache.commons.io.IOUtils.*;

import static org.project.utils.Helper.*;
import static org.project.utils.fs.Writer.bufWriter;
import static org.project.utils.stream.InputStream.*;
import static org.project.utils.stream.OutputStream.output;

public class FS extends Reader {

    public static String readFile(String path) throws IOException {
        return readFile(input(path));
    }

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

    public static void writeFile(Path path, InputStream input) throws IOException {
        writeFile(path(path), input);
    }

    public static void writeFile(String path, InputStream input) throws IOException {
        //writeFile(path, bytes(input));
        try (FileOutputStream output = output(path)) {
            copy(input, output);
            debug("Successfully write to the file: " + path);
        } catch (IOException e) {
            debug("Error writing file.");
        }
        input.close();
    }

    public static void writeFile(String path, byte[] data) {
        try (FileOutputStream output = output(path)) {
            writeStream(output, data);
        } catch (IOException e) {
            debug("Error writing file.");
        }
    }

    public static void writeFile(String path, String data) {
        try (BufferedWriter bw = bufWriter(path)) {
            bw.write(data);
            debug("Successfully write to the file: " + path);
        } catch (IOException e) {
            debug("Error writing file.");
        }
    }

    public static void writeStream(OutputStream output, InputStream input) throws IOException {
        writeStream(output, bytes(input));
        input.close();
    }

    public static void writeStream(OutputStream output, byte[] data) {
        try (output) {
            //output.write(data);
            write(data, output);
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing stream.");
        }
    }

    public static void writeStream(OutputStream output, String data) {
        try (BufferedWriter bw = bufWriter(output)) {
            bw.write(data);
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing file.");
        }
    }

    public static boolean mkdirs(String path) throws IOException {
        return mkdirs(new File(path));
    }

    public static boolean mkdirs(Path path) throws IOException {
        //return mkdirs(path.toFile());
        return createDirectories(isDir(path) ? path : path.getParent()).isAbsolute();
    }

    public static boolean mkdirs(File file) throws IOException {
        //return (isDir(file) ? file : file.getParentFile()).mkdirs();
        return mkdirs(file.toPath());
    }

    public static boolean mkdirs(ZipEntry ze, Path path) throws IOException {
        return mkdirs(isDir(ze) ? path : path.getParent());
    }

    public static boolean deleteDirWalk(final String path) throws IOException {
        try (Stream<File> fileStream = dirReverseToFile(path)) {
            fileStream.forEach(File::delete);
            //fileStream.forEach(File::delete()); // ошибки игнорируются
        }
        return exist(path);
    }

    public static void printFile(final String path) {
        printFile(path(path));
    }

    public static void printFile(final Path path) {
        debug(path);
        debug(path.getFileName());
        debug(path.getParent());
    }

    public static void printFile(final File file) {
        debug(file);
        debug(file.getName());
        debug(file.getParent());
    }

}
