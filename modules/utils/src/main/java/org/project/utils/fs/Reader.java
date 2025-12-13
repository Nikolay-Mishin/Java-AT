package org.project.utils.fs;

import static java.nio.file.Files.list;
import static java.nio.file.Files.walk;
import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.project.utils.stream.InputStream.input;

/**
 *
 */
public class Reader extends org.project.utils.fs.File {

    /**
     *
     * @param path String
     * @return FileReader
     * @throws IOException throws
     */
    public static FileReader reader(String path) throws IOException {
        return new FileReader(path);
    }

    /**
     *
     * @param path Path
     * @return FileReader
     * @throws IOException throws
     */
    public static FileReader reader(Path path) throws IOException {
        return reader(path.toString());
    }

    /**
     *
     * @param file File
     * @return FileReader
     * @throws IOException throws
     */
    public static FileReader reader(File file) throws IOException {
        return new FileReader(file);
    }

    /**
     *
     * @param input InputStream
     * @return InputStreamReader
     * @throws IOException throws
     */
    public static InputStreamReader reader(InputStream input) throws IOException {
        return new InputStreamReader(input);
    }

    /**
     *
     * @param path String
     * @return BufferedReader
     * @throws IOException throws
     */
    public static BufferedReader bufReader(String path) throws IOException {
        return bufReader(input(path));
    }

    /**
     *
     * @param path Path
     * @return BufferedReader
     * @throws IOException throws
     */
    public static BufferedReader bufReader(Path path) throws IOException {
        return bufReader(path.toString());
    }

    /**
     *
     * @param file File
     * @return BufferedReader
     * @throws IOException throws
     */
    public static BufferedReader bufReader(File file) throws IOException {
        return bufReader(input(file));
    }

    /**
     *
     * @param input InputStream
     * @return BufferedReader
     * @throws IOException throws
     */
    public static BufferedReader bufReader(InputStream input) throws IOException {
        return new BufferedReader(reader(input));
    }

    /**
     *
     * @param path {@code final} String
     * @return Stream {File}
     * @throws IOException throws
     */
    public static Stream<File> folderList(final String path) throws IOException {
        return folderPathList(path).map(Path::toFile);
    }

    /**
     *
     * @param path {@code final} String
     * @return Stream {File}
     * @throws IOException throws
     */
    public static Stream<File> fileList(final String path) throws IOException {
        return pathList(path).map(Path::toFile);
    }

    /**
     *
     * @param path {@code final} String
     * @return Stream {Path}
     * @throws IOException throws
     */
    public static Stream<Path> folderPathList(final String path) throws IOException {
        return readDir(path, Files::isDirectory);
    }

    /**
     *
     * @param path {@code final} String
     * @return Stream {Path}
     * @throws IOException throws
     */
    public static Stream<Path> pathList(final String path) throws IOException {
        return readDir(path, Files::isRegularFile);
    }

    /**
     *
     * @param path {@code final} String
     * @param filter {@code final} Predicate
     * @return Stream {Path}
     * @throws IOException throws
     */
    public static Stream<Path> readDir(final String path, final Predicate<Path> filter) throws IOException {
        return readDir(path).filter(filter);
    }

    /**
     *
     * @param path {@code final} String
     * @return Stream {Path}
     * @throws IOException throws
     */
    public static Stream<Path> readDir(final String path) throws IOException {
        return walk(pathStr(path));
    }

    /**
     *
     * @param path {@code final} String
     * @return Stream {File}
     * @throws IOException throws
     */
    public static Stream<File> rootFolderList(final String path) throws IOException {
        return rootFolderPathList(path).map(Path::toFile);
    }

    /**
     *
     * @param path {@code final} String
     * @return Stream {File}
     * @throws IOException throws
     */
    public static Stream<File> rootFileList(final String path) throws IOException {
        return rootPathList(path).map(Path::toFile);
    }

    /**
     *
     * @param path {@code final} String
     * @return Stream {Path}
     * @throws IOException throws
     */
    public static Stream<Path> rootFolderPathList(final String path) throws IOException {
        return readRootDir(path, Files::isDirectory);
    }

    /**
     *
     * @param path {@code final} String
     * @return Stream {Path}
     * @throws IOException throws
     */
    public static Stream<Path> rootPathList(final String path) throws IOException {
        return readRootDir(path, Files::isRegularFile);
    }

    /**
     *
     * @param path String
     * @param filter {@code final} Predicate
     * @return Stream {Path}
     * @throws IOException throws
     */
    public static Stream<Path> readRootDir(final String path, final Predicate<Path> filter) throws IOException {
        return readRootDir(path).filter(filter);
    }

    /**
     *
     * @param dir {@code final} String
     * @return Stream {Path}
     * @throws IOException throws
     */
    public static Stream<Path> readRootDir(final String dir) throws IOException {
        return list(pathStr(dir));
    }

    /**
     *
     * @param files Collection {File}
     * @return Stream {Path}
     */
    public Stream<Path> pathsStream(Collection<File> files) {
        return files.stream().map(File::toPath);
    }

    /**
     *
     * @param files Collection {File}
     * @return List {Path}
     */
    public List<Path> pathList(Collection<File> files) {
        try (Stream<Path> pathStream = pathsStream(files)) {
            //return pathStream.collect(Collectors.toList());
            return pathStream.toList();
        }
    }

    /**
     *
     * @param files Collection {File}
     * @return Path[]
     */
    public Path[] pathArray(Collection<File> files) {
        try (Stream<Path> pathStream = pathsStream(files)) {
            return pathStream.toArray(Path[]::new);
        }
    }

    /**
     *
     * @param keyExtractor Function
     * @return Comparator
     * @param <T> T
     * @param <U> U
     */
    public static <T, U extends Comparable<U>> Comparator<T> sort(Function<T, U> keyExtractor) {
        return comparing(keyExtractor);
    }

    /**
     *
     * @return Comparator
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<T> sortReverse() {
        return (Comparator<T>) reverseOrder().reversed();
    }

    /**
     *
     * @param path {@code final} String
     * @return Stream
     * @throws IOException throws
     */
    public static Stream<Path> dirReverse(final String path) throws IOException {
        //return sortDir(path, reverseOrder());
        return sortDir(path, sortReverse());
    }

    /**
     *
     * @param path {@code final} String
     * @return Stream
     * @throws IOException throws
     */
    public static Stream<File> dirReverseToFile(final String path) throws IOException {
        //return sortDir(path, reverseOrder(), Path::toFile);
        return sortDir(path, sortReverse(), Path::toFile);
    }

    /**
     *
     * @param path {@code final} String
     * @param comparator Comparator
     * @param mapper Function
     * @return Stream
     * @param <R> R
     * @throws IOException throws
     */
    public static <R> Stream<R> sortDir(final String path, Comparator<Path> comparator, Function<Path, R> mapper) throws IOException {
        return sortDir(path, comparator).map(mapper);
    }

    /**
     *
     * @param path String
     * @param comparator Comparator
     * @return Stream
     * @throws IOException throws
     */
    public static Stream<Path> sortDir(final String path, Comparator<Path> comparator) throws IOException {
        return readDir(path).sorted(comparator);
    }

    /**
     *
     * @param clazz Class
     * @param classpath String
     * @return InputStream
     */
    public static InputStream getRes(Class<?> clazz, String classpath) {
        return getRes(clazz, classpath, true);
    }

    /**
     *
     * @param clazz Class
     * @param classpath String
     * @param absolute boolean
     * @return InputStream
     */
    public static InputStream getRes(Class<?> clazz, String classpath, boolean absolute) {
        return clazz.getResourceAsStream((absolute ? "/" : "") + classpath);
    }

}
