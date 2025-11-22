package org.project.utils.fs;

import static java.lang.String.join;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.zip.ZipEntry;

import static org.apache.commons.io.FileUtils.deleteDirectory;

import jdk.jfr.Description;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.isInstance;

public class File {

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
        return Paths.get(path(pathList));
    }

    @Description("Get Path from string")
    public static Path pathOf(String path) {
        return Path.of(path);
    }

    @Description("Get Path from string")
    public static Path pathOf(String... pathList) {
        return Path.of(path(pathList));
    }

    public static Path absolute(String path) {
        return absolute(pathStr(path));
    }

    public static Path absolute(Path path) {
        return path.toAbsolutePath();
    }

    public static Path resolve(String path) {
        return resolve(pathStr(path));
    }

    public static Path resolve(Path path) {
        return path.normalize();
    }

    public static Path resolve(String path, String other) {
        return resolve(pathStr(path), pathStr(other));
    }

    public static Path resolve(Path path, String other) {
        return resolve(path, pathStr(other));
    }

    public static Path resolve(Path path, Path other) {
        return path.resolve(other).normalize();
    }

    public static boolean isDir(final String path) throws IOException {
        return isDir(pathStr(path));
    }

    public static boolean isDir(final Path path) throws IOException {
        return pathIsDir(path);
    }

    public static boolean isDir(final java.io.File file) throws IOException {
        return pathIsDir(file.toPath());
    }

    public static boolean isDir(final ZipEntry ze) {
        return ze.isDirectory();
    }

    public static boolean isFile(final String path) throws IOException {
        return !isDir(path);
    }

    public static boolean isFile(final Path path) throws IOException {
        return !isDir(path);
    }

    public static boolean isFile(final java.io.File file) throws IOException {
        return !isDir(file);
    }

    // check if the file/directory is already there
    // see if the path that's already in place is a file or directory
    protected static boolean pathIsDir(final Path path) throws IOException {
        //return isDirectory(path);
        return !exist(path) ? pathIsFile(path) : Files.isDirectory(path);
    }

    // see if the file portion it doesn't have an extension
    protected static boolean pathIsFile(final Path path) {
        //return new File(path(path)).getName().lastIndexOf('.') == -1;
        return path(path.getFileName()).lastIndexOf('.') == -1;
    }

    public static boolean exist(final String path) throws IOException {
        return Files.exists(pathStr(path));
    }

    public static boolean exist(final Path path) throws IOException {
        return Files.exists(path);
    }

    public static boolean exist(final java.io.File file) throws IOException {
        return Files.exists(file.toPath());
    }

    public static boolean delete(final String path) throws IOException {
        return delete(new java.io.File(path));
    }

    public static boolean delete(final Path path) throws IOException {
        return delete(path.toFile());
    }

    public static boolean delete(final java.io.File file) throws IOException {
        return isDir(file) ? deleteDir(file) : file.delete();
    }

    public static boolean deleteDir(final String path) throws IOException {
        return deleteDir(new java.io.File(path));
    }

    public static boolean deleteDir(final Path path) throws IOException {
        return deleteDir(path.toFile());
    }

    public static boolean deleteDir(final java.io.File file) throws IOException {
        deleteDirectory(file);
        return !exist(file);
    }

}
