package org.project.utils.fs;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.*;
import java.io.File;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.Set;

import static org.project.utils.Helper.*;
import static org.project.utils.fs.FS.pathStr;

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

    public static BasicFileAttributes baseAttrs(final Path path) {
        return attrs(path, baseAttrs);
    }

    public static DosFileAttributes dosAttrs(final String path) {
        return attrs(pathStr(path), dosAttrs);
    }

    public static DosFileAttributes dosAttrs(final Path path) {
        return attrs(path, dosAttrs);
    }

    public static PosixFileAttributes posAttrs(final String path) {
        return attrs(pathStr(path), posAttrs);
    }

    public static PosixFileAttributes posAttrs(final Path path) {
        return attrs(path, posAttrs);
    }

    public static <T extends BasicFileAttributes> T attrs(final Path path, Class<T> clazz) {
        File file = new File(path.toString());
        if (file.isFile()) {
            try {
                return Files.readAttributes(path, clazz);
            } catch (IOException e) {
                //Logger.getLogger(MGSiap.class.getName()).log(Level.SEVERE, null, e);
                debug("Error reading attributes.");
            }
        }
        return null;
    }

    public static UserDefinedFileAttributeView customAttrView(Path path) {
        return Files.getFileAttributeView(path, userAttrsView);
    }

    public static String customAttr(String path, String name) {
        return customAttr(pathStr(path), name);
    }

    public static String customAttr(Path path, String name) {
        try {
            UserDefinedFileAttributeView view = customAttrView(path);
            debug(view);
            int attr = view.read(name, UTF_8.encode(path.toString()));
            debug("Read: " + attr);
            // Implementation details omitted for brevity
            return "Custom Attribute Value";
        } catch (IOException e) {
            debug("Error reading view.");
            debug(e);
            e.printStackTrace();
            return null;
        }
    }

    public static void customAttr(String path, String name, String value) {
        customAttr(pathStr(path), name, value);
    }

    public static void customAttr(Path path, String name, String value) {
        try {
            UserDefinedFileAttributeView view = customAttrView(path);
            debug(view);
            int attr = view.write(name, UTF_8.encode(value));
            debug("Write: " + attr);
        } catch (IOException e) {
            debug("Error write view.");
            debug(e);
            e.printStackTrace();
        }
    }

    public static void printAttrs() {
        printAttrs("");
    }

    public static void printAttrs(String path) {
        printAttrs(pathStr(""));
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
        printCustomAttrs(pathStr(""), name, value);
    }

    public static void printCustomAttrs(Path path, String name, String value) {
        customAttr(path, name);
        customAttr(path, name, value);
    }

    public static void printBaseAttrs() {
        printBaseAttrs("");
    }

    public static void printBaseAttrs(String path) {
        printBaseAttrs(pathStr(""));
    }

    public static void printBaseAttrs(Path path) {
        BasicFileAttributes attrs = attrs("");
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
        printPosAttrs(pathStr(""));
    }

    public static void printPosAttrs(Path path) {
        PosixFileAttributes attrs = posAttrs("");
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
        printDosAttrs(pathStr(""));
    }

    public static void printDosAttrs(Path path) {
        DosFileAttributes attrs = dosAttrs("");
        debug(attrs);
    }

}
