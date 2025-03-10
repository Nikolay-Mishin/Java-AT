package org.project.utils.fs;

import jdk.jfr.Description;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.String.join;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.isInstance;

public class FS {

    @Description("Generate url path")
    public static String path(Object... pathList) {
        debug(Arrays.toString(pathList));
        return join("/", Arrays.stream(pathList.length == 1 && !(pathList[0] instanceof String) ? (Object[]) pathList[0] : pathList)
            .map(path -> isInstance(path, Object[].class) ? path(path) : path.toString())
            .toArray(String[]::new));
    }

    protected static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public static String readFile(String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(path);
        String data = readFromInputStream(inputStream);
        debug(data);
        return data;
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
