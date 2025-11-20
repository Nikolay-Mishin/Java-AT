package org.project.utils.fs;

import static java.lang.String.join;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.apache.commons.io.IOUtils.*;

import jdk.jfr.Description;

import static org.project.utils.Helper.*;
import static org.project.utils.fs.Reader.bufReader;
import static org.project.utils.fs.Writer.bufWriter;
import static org.project.utils.stream.InputStream.*;
import static org.project.utils.stream.OutputStream.output;

public class FS {

    @Description("Generate url path")
    public static String path(Object... pathList) {
        debug(Arrays.toString(pathList));
        return join("/", Arrays.stream(pathList.length == 1 && !(pathList[0] instanceof String) ? (Object[]) pathList[0] : pathList)
            .map(path -> isInstance(path, Object[].class) ? path(path) : path.toString())
            .toArray(String[]::new));
    }

    @Description("Get string from Path")
    public static String path(Path path) {
        return path.toString();
    }

    @Description("Get Path from string")
    public static Path pathStr(String path) {
        return Paths.get(path);
    }

    @Description("Get Path from string")
    public static Path pathStr(String... pathList) {
        return Paths.get(Arrays.toString(pathList));
    }

    @Description("Get Path from string")
    public static Path pathOf(String path) {
        return Path.of(path);
    }

    @Description("Get Path from string")
    public static Path pathOf(String... pathList) {
        return Path.of(Arrays.toString(pathList));
    }

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

    public static Stream<File> folderList(final String path) throws IOException {
        return folderPathList(path).map(Path::toFile);
    }

    public static Stream<File> fileList(final String path) throws IOException {
        return pathList(path).map(Path::toFile);
    }

    public static Stream<Path> folderPathList(final String path) throws IOException {
        return readDir(path, Files::isDirectory);
    }

    public static Stream<Path> pathList(final String path) throws IOException {
        return readDir(path, Files::isRegularFile);
    }

    public static Stream<Path> readDir(final String path, final Predicate<? super Path> filter) throws IOException {
        return readDir(path).filter(filter);
    }

    public static Stream<Path> readDir(final String path) throws IOException {
        return Files.walk(pathStr(path));
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
