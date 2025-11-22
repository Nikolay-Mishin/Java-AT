package org.project.utils.fs;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.Set;

import static java.nio.file.Files.*;
import static org.project.utils.Helper.*;
import static org.project.utils.fs.FS.pathStr;
import static org.project.utils.fs.File.path;
import static org.project.utils.reflection.Reflection.getClassSimpleName;

public class Attributes {
    protected static Class<BasicFileAttributes> baseAttrs = BasicFileAttributes.class;
    protected static Class<DosFileAttributes> dosAttrs = DosFileAttributes.class;
    protected static Class<PosixFileAttributes> posAttrs = PosixFileAttributes.class;
    protected static Class<UserDefinedFileAttributeView> userAttrsView = UserDefinedFileAttributeView.class;

    public static BasicFileAttributes attrs(final String path) {
        return baseAttrs(path);
    }

    public static BasicFileAttributes attrs(final Path path) {
        return baseAttrs(path);
    }

    public static BasicFileAttributes baseAttrs(final String path) {
        return attrs(pathStr(path), baseAttrs);
    }

    public static BasicFileAttributes baseAttrs(final java.io.File file) {
        return attrs(file.toPath(), baseAttrs);
    }

    public static BasicFileAttributes baseAttrs(final Path path) {
        return attrs(path, baseAttrs);
    }

    public static DosFileAttributes dosAttrs(final String path) {
        return attrs(pathStr(path), dosAttrs);
    }

    public static DosFileAttributes dosAttrs(final java.io.File file) {
        return attrs(file.toPath(), dosAttrs);
    }

    public static DosFileAttributes dosAttrs(final Path path) {
        return attrs(path, dosAttrs);
    }

    public static PosixFileAttributes posAttrs(final java.io.File file) {
        return attrs(file.toPath(), posAttrs);
    }

    public static PosixFileAttributes posAttrs(final String path) {
        return attrs(pathStr(path), posAttrs);
    }

    public static PosixFileAttributes posAttrs(final Path path) {
        return attrs(path, posAttrs);
    }

    public static <A extends BasicFileAttributes> A attrs(final Path path, Class<A> type) {
        debug("Reading " + getClassSimpleName(type) + " attributes: " + path(path));
        try {
            return readAttributes(path, type);
        } catch (IOException|UnsupportedOperationException e) {
            //Logger.getLogger(MGSiap.class.getName()).log(Level.SEVERE, null, e);
            debug("Error reading attributes.");
            e.printStackTrace();
        }
        return null;
    }

    public static UserDefinedFileAttributeView customAttrView(String path) {
        return getFileAttributeView(pathStr(path), userAttrsView);
    }

    public static UserDefinedFileAttributeView customAttrView(java.io.File file) {
        return getFileAttributeView(file.toPath(), userAttrsView);
    }

    public static UserDefinedFileAttributeView customAttrView(Path path) {
        return getFileAttributeView(path, userAttrsView);
    }

    public static String customAttr(String path, String name) {
        return customAttr(pathStr(path), name);
    }

    public static String customAttr(java.io.File file, String name) {
        return customAttr(file.toPath(), name);
    }

    public static String customAttr(Path path, String name) {
        debug("Reading custom attributes" + " (" + name + "): " + path(path));
        try {
            UserDefinedFileAttributeView view = customAttrView(path);
            debug(view);
            int attr = view.read(name, UTF_8.encode(path.toString()));
            debug("Read: " + attr);
            return "Custom Attribute Value";
        } catch (IOException e) {
            debug("Error reading view.");
            e.printStackTrace();
        }
        return null;
    }

    public static String customAttr(String path, String name, String value) {
        return customAttr(pathStr(path), name, value);
    }

    public static String customAttr(java.io.File file, String name, String value) {
        return customAttr(file.toPath(), name, value);
    }

    public static String customAttr(Path path, String name, String value) {
        debug("Writing custom attributes" + " (" + name + ", " + value + "): " + path(path));
        try {
            UserDefinedFileAttributeView view = customAttrView(path);
            debug(view);
            int attr = view.write(name, UTF_8.encode(value));
            debug("Write: " + attr);
            return "Custom Attribute Value";
        } catch (IOException e) {
            debug("Error write view.");
            e.printStackTrace();
        }
        return null;
    }

    public static void printAttrs() {
        printAttrs("");
    }

    public static void printAttrs(String path) {
        printAttrs(pathStr(path));
    }

    public static void printAttrs(java.io.File file) {
        printAttrs(file.toPath());
    }

    public static void printAttrs(Path path) {
        printBaseAttrs(path);
        printPosAttrs(path);
        printDosAttrs(path);
        printCustomAttrs(path, "customAttr", "value");
    }

    public static void printCustomAttrs(String name, String value) {
        printCustomAttrs("", name, value);
    }

    public static void printCustomAttrs(String path, String name, String value) {
        printCustomAttrs(pathStr(path), name, value);
    }

    public static void printCustomAttrs(java.io.File file, String name, String value) {
        printCustomAttrs(file.toPath(), name, value);
    }

    public static void printCustomAttrs(Path path, String name, String value) {
        customAttr(path, name);
        customAttr(path, name, value);
    }

    public static void printBaseAttrs() {
        printBaseAttrs("");
    }

    public static void printBaseAttrs(String path) {
        printBaseAttrs(pathStr(path));
    }

    public static void printBaseAttrs(java.io.File file) {
        printBaseAttrs(file.toPath());
    }

    public static void printBaseAttrs(Path path) {
        BasicFileAttributes attrs = attrs(path);
        debug(attrs);
        if (notNull(attrs)) {
            debug("size             = " + attrs.size() + " bytes");
            debug("creationTime     = " + attrs.creationTime());
            debug("lastModifiedTime = " + attrs.lastModifiedTime());
            debug("lastAccessTime   = " + attrs.lastAccessTime());
            debug("isDirectory      = " + attrs.isDirectory());
            debug("isOther          = " + attrs.isOther());
            debug("isRegularFile    = " + attrs.isRegularFile());
            debug("isSymbolicLink   = " + attrs.isSymbolicLink());
        }
    }

    public static void printPosAttrs() {
        printPosAttrs("");
    }

    public static void printPosAttrs(String path) {
        printPosAttrs(pathStr(path));
    }

    public static void printPosAttrs(java.io.File file) {
        printPosAttrs(file.toPath());
    }

    public static void printPosAttrs(Path path) {
        PosixFileAttributes attrs = posAttrs(path);
        debug(attrs);
        if (notNull(attrs)) {
            debug("Owner: " + attrs.owner().getName());
            debug("Group: " + attrs.group().getName());
            Set<PosixFilePermission> permissions = attrs.permissions();
            debug("Permissions: " + permissions);
        }
    }

    public static void printDosAttrs() {
        printDosAttrs("");
    }

    public static void printDosAttrs(String path) {
        printDosAttrs(pathStr(path));
    }

    public static void printDosAttrs(java.io.File file) {
        printDosAttrs(file.toPath());
    }

    public static void printDosAttrs(Path path) {
        DosFileAttributes attrs = dosAttrs(path);
        debug(attrs);
    }

}
