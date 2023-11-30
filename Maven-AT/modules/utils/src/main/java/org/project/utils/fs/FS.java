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
import static java.lang.System.out;
import static org.project.utils.Helper.isInstance;
import static org.project.utils.config.Config.config;

public class FS {

    @Description("Generate url path")
    public static String getPath(Object... pathList) {
        out.println(Arrays.toString(pathList));
        return join("/", Arrays.stream(pathList.length == 1 && !(pathList[0] instanceof String) ? (Object[]) pathList[0] : pathList)
            .map(path -> isInstance(path, Object[].class) ? getPath(path) : path.toString())
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
        out.println(data);
        return data;
    }

    public static Stream<Path> folderList(final String path) throws IOException {
        return readDir(path, Files::isRegularFile);
    }

    public static Stream<Path> fileList(final String path) throws IOException {
        return readDir(path, Files::isRegularFile);
    }

    public static Stream<Path> readDir(final String path, final Predicate<? super Path> filter) throws IOException {
        return readDir(path).filter(filter);
    }

    public static Stream<Path> readDir(final String path) throws IOException {
        return Files.walk(Paths.get(path));
    }

    public static void printFile(final Path file) {
        out.println("printFile");
        out.println(file);
        out.println(file.getFileName());
        out.println(file.getParent());
        out.println(config().getJsonSchemaRoot());
        out.println(file.getParent().toString().replace("\\", "/").replace(config().getJsonSchemaRoot() + "/", ""));
    }

    public static void printFile(final File file) {
        out.println("printFile");
        out.println(file);
        out.println(file.getName());
        out.println(file.getParent());
        out.println(config().getJsonSchemaRoot());
        out.println(file.getParent().replace("\\", "/").replace(config().getJsonSchemaRoot() + "/", ""));
    }

}
