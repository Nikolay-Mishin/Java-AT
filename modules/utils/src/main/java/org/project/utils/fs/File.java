package org.project.utils.fs;

import static java.lang.String.join;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.ZipEntry;

import static org.apache.commons.io.FileUtils.deleteDirectory;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.isArray;
import static org.project.utils.Helper.map;

/**
 *
 */
public class File {

    /**
     * Generate url path
     * @param pathList Object[]
     * @return String
     */
    public static String path(Object... pathList) {
        debug(Arrays.toString(pathList));
        return join("/", map(pathList, String[]::new, path -> isArray(path) ? path((Object[]) path) : path.toString()));
    }

    /**
     * Get string from Path
     * @param path Path
     * @return String
     */
    public static String path(Path path) {
        return path.toString();
    }

    /**
     * Get Path from string
     * @param path String
     * @return Path
     */
    public static Path pathStr(String path) {
        return Paths.get(path);
    }

    /**
     * Get Path from string
     * @param pathList String[]
     * @return Path
     */
    public static Path pathStr(String... pathList) {
        return Paths.get(path((Object[]) pathList));
    }

    /**
     * Get Path from string
     * @param path String
     * @return Path
     */
    public static Path pathOf(String path) {
        return Path.of(path);
    }

    /**
     * Get Path from string
     * @param pathList String[]
     * @return Path
     */
    public static Path pathOf(String... pathList) {
        return Path.of(path((Object[]) pathList));
    }

    /**
     *
     * @param path String
     * @return Path
     */
    public static Path absolute(String path) {
        return absolute(pathStr(path));
    }

    /**
     *
     * @param file File
     * @return Path
     */
    public static Path absolute(java.io.File file) {
        return absolute(file.toPath());
    }

    /**
     *
     * @param path Path
     * @return Path
     */
    public static Path absolute(Path path) {
        return path.toAbsolutePath();
    }

    /**
     *
     * @param path String
     * @return Path
     */
    public static Path resolve(String path) {
        return resolve(pathStr(path));
    }

    /**
     *
     * @param file File
     * @return Path
     */
    public static Path resolve(java.io.File file) {
        return resolve(file.toPath());
    }

    /**
     *
     * @param path Path
     * @return Path
     */
    public static Path resolve(Path path) {
        return path.normalize();
    }

    /**
     *
     * @param path String
     * @param other String
     * @return Path
     */
    public static Path resolve(String path, String other) {
        return resolve(pathStr(path), pathStr(other));
    }

    /**
     *
     * @param file File
     * @param other String
     * @return Path
     */
    public static Path resolve(java.io.File file, String other) {
        return resolve(file.toPath(), pathStr(other));
    }

    /**
     *
     * @param path Path
     * @param other String
     * @return Path
     */
    public static Path resolve(Path path, String other) {
        return resolve(path, pathStr(other));
    }

    /**
     *
     * @param path Path
     * @param other Path
     * @return Path
     */
    public static Path resolve(Path path, Path other) {
        return path.resolve(other).normalize();
    }

    /**
     *
     * @param path {@code final} String
     * @return boolean
     * @throws IOException throws
     */
    public static boolean isDir(final String path) throws IOException {
        return isDir(pathStr(path));
    }

    /**
     *
     * @param file {@code final} File
     * @return boolean
     * @throws IOException throws
     */
    public static boolean isDir(final java.io.File file) throws IOException {
        return pathIsDir(file.toPath());
    }

    /**
     *
     * @param path {@code final} Path
     * @return boolean
     * @throws IOException throws
     */
    public static boolean isDir(final Path path) throws IOException {
        return pathIsDir(path);
    }

    /**
     *
     * @param ze {@code final} ZipEntry
     * @return boolean
     */
    public static boolean isDir(final ZipEntry ze) {
        return ze.isDirectory();
    }

    /**
     *
     * @param path {@code final} String
     * @return boolean
     * @throws IOException throws
     */
    public static boolean isFile(final String path) throws IOException {
        return !isDir(path);
    }

    /**
     *
     * @param file {@code final} File
     * @return boolean
     * @throws IOException throws
     */
    public static boolean isFile(final java.io.File file) throws IOException {
        return !isDir(file);
    }

    /**
     *
     * @param path {@code final} Path
     * @return boolean
     * @throws IOException throws
     */
    public static boolean isFile(final Path path) throws IOException {
        return !isDir(path);
    }

    /**
     * check if the file/directory is already there
     * <p>see if the path that's already in place is a file or directory
     * @param path {@code final} Path
     * @return boolean
     * @throws IOException throws
     */
    protected static boolean pathIsDir(final Path path) throws IOException {
        //return isDirectory(path);
        return !exist(path) ? pathIsFile(path) : Files.isDirectory(path);
    }

    /**
     * see if the file portion it doesn't have an extension
     * @param path {@code final} Path
     * @return boolean
     */
    protected static boolean pathIsFile(final Path path) {
        //return new File(path(path)).getName().lastIndexOf('.') == -1;
        return path(path.getFileName()).lastIndexOf('.') == -1;
    }

    /**
     *
     * @param path {@code final} String
     * @return boolean
     * @throws IOException throws
     */
    public static boolean exist(final String path) throws IOException {
        return Files.exists(pathStr(path));
    }

    /**
     *
     * @param file {@code final} File
     * @return boolean
     * @throws IOException throws
     */
    public static boolean exist(final java.io.File file) throws IOException {
        return Files.exists(file.toPath());
    }

    /**
     *
     * @param path {@code final} Path
     * @return boolean
     * @throws IOException throws
     */
    public static boolean exist(final Path path) throws IOException {
        return Files.exists(path);
    }

    /**
     *
     * @param path {@code final} String
     * @return boolean
     * @throws IOException throws
     */
    public static boolean delete(final String path) throws IOException {
        return delete(new java.io.File(path));
    }

    /**
     *
     * @param path {@code final} Path
     * @return boolean
     * @throws IOException throws
     */
    public static boolean delete(final Path path) throws IOException {
        return delete(path.toFile());
    }

    /**
     *
     * @param file {@code final} File
     * @return boolean
     * @throws IOException throws
     */
    public static boolean delete(final java.io.File file) throws IOException {
        return isDir(file) ? deleteDir(file) : file.delete();
    }

    /**
     *
     * @param path {@code final} String
     * @return boolean
     * @throws IOException throws
     */
    public static boolean deleteDir(final String path) throws IOException {
        return deleteDir(new java.io.File(path));
    }

    /**
     *
     * @param path {@code final} Path
     * @return boolean
     * @throws IOException throws
     */
    public static boolean deleteDir(final Path path) throws IOException {
        return deleteDir(path.toFile());
    }

    /**
     *
     * @param file {@code final} File
     * @return boolean
     * @throws IOException throws
     */
    public static boolean deleteDir(final java.io.File file) throws IOException {
        deleteDirectory(file);
        return !exist(file);
    }

}
