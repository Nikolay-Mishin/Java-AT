package org.project.utils.fs;

import static java.lang.String.join;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import jdk.jfr.Description;
import static org.apache.commons.io.IOUtils.*;

import static org.project.utils.Helper.*;

public class FS {

    @Description("Generate url path")
    public static String path(Object... pathList) {
        debug(Arrays.toString(pathList));
        return join("/", Arrays.stream(pathList.length == 1 && !(pathList[0] instanceof String) ? (Object[]) pathList[0] : pathList)
            .map(path -> isInstance(path, Object[].class) ? path(path) : path.toString())
            .toArray(String[]::new));
    }

    public static String readFile(String path) throws IOException {
        return readFile(new FileInputStream(path));
    }

    public static String readFile(InputStream input) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            debug("Error reading file.");
        }
        return resultStringBuilder.toString();
    }

    public static void writeFile(String path, InputStream input) throws IOException {
        /*try (FileOutputStream output = new FileOutputStream(path)) {
            copy(input, output);
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing file.");
        }*/
        writeFile(path, input.readAllBytes());
        input.close();
    }

    public static void writeFile(String path, byte[] data) {
        try (FileOutputStream output = new FileOutputStream(path)) {
            writeStream(output, data);
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing file.");
        }
    }

    public static void writeFile(String path, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(data);
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing file.");
        }
    }

    public static void writeStream(OutputStream output, InputStream input) throws IOException {
        writeStream(output, input.readAllBytes());
        input.close();
    }

    public static void writeStream(OutputStream output, byte[] data) {
        try (output) {
            //output.write(data);
            write(data, output);
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing file.");
        }
    }

    public static void writeStream(OutputStream output, String data) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output))) {
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
        return Files.walk(Paths.get(path));
    }

    public static void printFile(final Path file) {
        debug(file);
        debug(file.getFileName());
        debug(file.getParent());
    }

    public static void printFile(final File file) {
        debug(file);
        debug(file.getName());
        debug(file.getParent());
    }

}
