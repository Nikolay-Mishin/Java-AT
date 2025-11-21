package org.project.utils.fs;

import static java.nio.file.Files.walk;
import static java.util.Comparator.*;

import java.io.*;
import java.io.File;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import static org.project.utils.stream.InputStream.input;

public class Reader extends org.project.utils.fs.File {

    public static FileReader reader(String path) throws IOException {
        return new FileReader(path);
    }

    public static FileReader reader(Path path) throws IOException {
        return reader(path.toString());
    }

    public static FileReader reader(File file) throws IOException {
        return new FileReader(file);
    }

    public static InputStreamReader reader(InputStream input) throws IOException {
        return new InputStreamReader(input);
    }

    public static BufferedReader bufReader(String path) throws IOException {
        return bufReader(input(path));
    }

    public static BufferedReader bufReader(Path path) throws IOException {
        return bufReader(path.toString());
    }

    public static BufferedReader bufReader(File file) throws IOException {
        return bufReader(input(file));
    }

    public static BufferedReader bufReader(InputStream input) throws IOException {
        return new BufferedReader(reader(input));
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

    public static Stream<Path> readDir(final String path, final Predicate<Path> filter) throws IOException {
        return readDir(path).filter(filter);
    }

    public static Stream<Path> readDir(final String path) throws IOException {
        return walk(pathStr(path));
    }

    public Stream<Path> pathsStream(Collection<File> files) {
        return files.stream().map(File::toPath);
    }

    public List<Path> pathList(Collection<File> files) {
        try (Stream<Path> pathStream = pathsStream(files)) {
            //return pathStream.collect(Collectors.toList());
            return pathStream.toList();
        }
    }

    public Path[] pathArray(Collection<File> files) {
        try (Stream<Path> pathStream = pathsStream(files)) {
            return pathStream.toArray(Path[]::new);
        }
    }

    public static <T, U extends Comparable<U>> Comparator<T> sort(Function<T, U> keyExtractor) {
        return comparing(keyExtractor);
    }

    public static <T> Comparator<T> sortReverse() {
        return (Comparator<T>) reverseOrder().reversed();
    }

    public static Stream<Path> dirReverse(final String path) throws IOException {
        //return sortDir(path, reverseOrder());
        return sortDir(path, sortReverse());
    }

    public static Stream<File> dirReverseToFile(final String path) throws IOException {
        //return sortDir(path, reverseOrder(), Path::toFile);
        return sortDir(path, sortReverse(), Path::toFile);
    }

    public static <R> Stream<R> sortDir(final String path, Comparator<Path> comparator, Function<Path, R> mapper) throws IOException {
        return sortDir(path, comparator).map(mapper);
    }

    public static Stream<Path> sortDir(final String path, Comparator<Path> comparator) throws IOException {
        return readDir(path).sorted(comparator);
    }

}
