package org.project.utils.fs;

import jdk.jfr.Description;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.String.join;
import static java.lang.System.out;
import static java.util.Objects.requireNonNull;
import static org.project.utils.Helper.isInstance;

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

    public static Stream<Path> readDir(final String path) throws IOException {
        return readDir(path, Files::isDirectory);
    }

    public static Stream<Path> listFiles(final String path) throws IOException {
        return readDir(path, Files::isRegularFile);
    }

    protected static Stream<Path> readDir(final String path, Predicate<? super Path> filter) throws IOException {
        out.println(path);
        Path _path = Paths.get(path);
        try (final Stream<Path> paths = Files.walk(_path)) {
            try (final Stream<Path> paths1 = Files.walk(_path)) {
                Stream<Path> _paths = paths1.filter(filter);
                List<Path> collect = _paths.toList();
                out.println(collect);
                collect.forEach(System.out::println);
            }
            try (final Stream<Path> paths2 = Files.walk(_path)) {
                Stream<Path> _paths = paths2.filter(filter);
                Stream<File> map = _paths.map(Path::toFile);
                out.println(map);
                map.forEach(System.out::println);
            }
            try (final Stream<Path> paths3 = Files.walk(_path)) {
                Stream<Path> _paths = paths3.filter(filter);
                List<File> map = _paths.map(Path::toFile).toList();
                out.println(map);
                map.forEach(System.out::println);
            }
            out.println(paths);
            return paths.filter(filter);
        }
    }

    public static void listFilesForFolder(final String path) {
        listFilesForFolder(new File(path));
    }

    public static void listFilesForFolder(final File folder) {
        out.println(folder);
        for (final File fileEntry : requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                out.println(fileEntry.getPath());
            }
        }
    }

}
