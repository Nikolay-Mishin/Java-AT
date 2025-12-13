package org.project.utils.fs;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.getFileAttributeView;
import static java.nio.file.Files.readAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Set;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.notNull;
import static org.project.utils.fs.FS.pathStr;
import static org.project.utils.fs.File.path;
import static org.project.utils.reflection.Reflection.getClassSimpleName;

/**
 *
 */
public class Attributes {
    /**
     *
     */
    protected static Class<BasicFileAttributes> baseAttrs = BasicFileAttributes.class;
    /**
     *
     */
    protected static Class<DosFileAttributes> dosAttrs = DosFileAttributes.class;
    /**
     *
     */
    protected static Class<PosixFileAttributes> posAttrs = PosixFileAttributes.class;
    /**
     *
     */
    protected static Class<UserDefinedFileAttributeView> userAttrsView = UserDefinedFileAttributeView.class;

    /**
     *
     * @param path {@code final} String
     * @return BasicFileAttributes
     */
    public static BasicFileAttributes attrs(final String path) {
        return baseAttrs(path);
    }

    /**
     *
     * @param path {@code final} Path
     * @return BasicFileAttributes
     */
    public static BasicFileAttributes attrs(final Path path) {
        return baseAttrs(path);
    }

    /**
     *
     * @param path {@code final} String
     * @return BasicFileAttributes
     */
    public static BasicFileAttributes baseAttrs(final String path) {
        return attrs(pathStr(path), baseAttrs);
    }

    /**
     *
     * @param file {@code final} File
     * @return BasicFileAttributes
     */
    public static BasicFileAttributes baseAttrs(final java.io.File file) {
        return attrs(file.toPath(), baseAttrs);
    }

    /**
     *
     * @param path {@code final} Path
     * @return BasicFileAttributes
     */
    public static BasicFileAttributes baseAttrs(final Path path) {
        return attrs(path, baseAttrs);
    }

    /**
     *
     * @param path {@code final} String
     * @return DosFileAttributes
     */
    public static DosFileAttributes dosAttrs(final String path) {
        return attrs(pathStr(path), dosAttrs);
    }

    /**
     *
     * @param file {@code final} File
     * @return DosFileAttributes
     */
    public static DosFileAttributes dosAttrs(final java.io.File file) {
        return attrs(file.toPath(), dosAttrs);
    }

    /**
     *
     * @param path {@code final} Path
     * @return DosFileAttributes
     */
    public static DosFileAttributes dosAttrs(final Path path) {
        return attrs(path, dosAttrs);
    }

    /**
     *
     * @param file {@code final} File
     * @return PosixFileAttributes
     */
    public static PosixFileAttributes posAttrs(final java.io.File file) {
        return attrs(file.toPath(), posAttrs);
    }

    /**
     *
     * @param path {@code final} String
     * @return PosixFileAttributes
     */
    public static PosixFileAttributes posAttrs(final String path) {
        return attrs(pathStr(path), posAttrs);
    }

    /**
     *
     * @param path {@code final} Path
     * @return PosixFileAttributes
     */
    public static PosixFileAttributes posAttrs(final Path path) {
        return attrs(path, posAttrs);
    }

    /**
     *
     * @param path {@code final} Path
     * @param type Class A
     * @return A
     * @param <A> {@code extends} BasicFileAttributes
     */
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

    /**
     *
     * @param path String
     * @return UserDefinedFileAttributeView
     */
    public static UserDefinedFileAttributeView customAttrView(String path) {
        return getFileAttributeView(pathStr(path), userAttrsView);
    }

    /**
     *
     * @param file File
     * @return UserDefinedFileAttributeView
     */
    public static UserDefinedFileAttributeView customAttrView(java.io.File file) {
        return getFileAttributeView(file.toPath(), userAttrsView);
    }

    /**
     *
     * @param path Path
     * @return UserDefinedFileAttributeView
     */
    public static UserDefinedFileAttributeView customAttrView(Path path) {
        return getFileAttributeView(path, userAttrsView);
    }

    /**
     *
     * @param path String
     * @param name String
     * @return String
     */
    public static String customAttr(String path, String name) {
        return customAttr(pathStr(path), name);
    }

    /**
     *
     * @param file File
     * @param name String
     * @return String
     */
    public static String customAttr(java.io.File file, String name) {
        return customAttr(file.toPath(), name);
    }

    /**
     *
     * @param path Path
     * @param name String
     * @return String
     */
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

    /**
     *
     * @param path String
     * @param name String
     * @param value String
     * @return String
     */
    public static String customAttr(String path, String name, String value) {
        return customAttr(pathStr(path), name, value);
    }

    /**
     *
     * @param file File
     * @param name String
     * @param value String
     * @return String
     */
    public static String customAttr(java.io.File file, String name, String value) {
        return customAttr(file.toPath(), name, value);
    }

    /**
     *
     * @param path Path
     * @param name String
     * @param value String
     * @return String
     */
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

    /**
     *
     */
    public static void printAttrs() {
        printAttrs("");
    }

    /**
     *
     * @param path String
     */
    public static void printAttrs(String path) {
        printAttrs(pathStr(path));
    }

    /**
     *
     * @param file File
     */
    public static void printAttrs(java.io.File file) {
        printAttrs(file.toPath());
    }

    /**
     *
     * @param path Path
     */
    public static void printAttrs(Path path) {
        printBaseAttrs(path);
        printPosAttrs(path);
        printDosAttrs(path);
    }

    /**
     *
     * @param name String
     */
    public static void printCustomAttrs(String name) {
        printCustomAttrs("", name);
    }

    /**
     *
     * @param path String
     * @param name String
     */
    public static void printCustomAttrs(String path, String name) {
        printCustomAttrs(pathStr(path), name);
    }

    /**
     *
     * @param file File
     * @param name String
     */
    public static void printCustomAttrs(java.io.File file, String name) {
        printCustomAttrs(file.toPath(), name);
    }

    /**
     *
     * @param path Path
     * @param name String
     */
    public static void printCustomAttrs(Path path, String name) {
        customAttr(path, name);
    }

    /**
     *
     * @param name String
     * @param value String
     */
    public static void printCustomSetAttrs(String name, String value) {
        printCustomAttrs("", name, value);
    }

    /**
     *
     * @param path String
     * @param name String
     * @param value String
     */
    public static void printCustomAttrs(String path, String name, String value) {
        printCustomAttrs(pathStr(path), name, value);
    }

    /**
     *
     * @param file File
     * @param name String
     * @param value String
     */
    public static void printCustomAttrs(java.io.File file, String name, String value) {
        printCustomAttrs(file.toPath(), name, value);
    }

    /**
     *
     * @param path Path
     * @param name String
     * @param value String
     */
    public static void printCustomAttrs(Path path, String name, String value) {
        customAttr(path, name, value);
    }

    /**
     *
     */
    public static void printBaseAttrs() {
        printBaseAttrs("");
    }

    /**
     *
     * @param path String
     */
    public static void printBaseAttrs(String path) {
        printBaseAttrs(pathStr(path));
    }

    /**
     *
     * @param file File
     */
    public static void printBaseAttrs(java.io.File file) {
        printBaseAttrs(file.toPath());
    }

    /**
     *
     * @param path Path
     */
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

    /**
     *
     */
    public static void printPosAttrs() {
        printPosAttrs("");
    }

    /**
     *
     * @param path String
     */
    public static void printPosAttrs(String path) {
        printPosAttrs(pathStr(path));
    }

    /**
     *
     * @param file File
     */
    public static void printPosAttrs(java.io.File file) {
        printPosAttrs(file.toPath());
    }

    /**
     *
     * @param path Path
     */
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

    /**
     *
     */
    public static void printDosAttrs() {
        printDosAttrs("");
    }

    /**
     *
     * @param path String
     */
    public static void printDosAttrs(String path) {
        printDosAttrs(pathStr(path));
    }

    /**
     *
     * @param file File
     */
    public static void printDosAttrs(java.io.File file) {
        printDosAttrs(file.toPath());
    }

    /**
     *
     * @param path Path
     */
    public static void printDosAttrs(Path path) {
        DosFileAttributes attrs = dosAttrs(path);
        debug(attrs);
    }

}
