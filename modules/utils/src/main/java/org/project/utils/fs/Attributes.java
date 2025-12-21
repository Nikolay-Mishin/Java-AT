package org.project.utils.fs;

import static java.nio.ByteBuffer.allocate;
import static java.nio.file.FileSystems.getDefault;
import static java.nio.file.Files.getAttribute;
import static java.nio.file.Files.getFileAttributeView;
import static java.nio.file.Files.readAttributes;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.nio.file.Files.setAttribute;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.decode;
import static org.project.utils.Helper.encode;
import static org.project.utils.Helper.join;
import static org.project.utils.Helper.notNull;
import static org.project.utils.fs.FS.pathStr;
import static org.project.utils.fs.File.path;
import static org.project.utils.reflection.Reflection.getClassSimpleName;
import static org.project.utils.reflection.Reflection.invoke;
import static org.project.utils.reflection.Reflection.setField;

/**
 *
 */
public class Attributes {
    /**
     * [owner, dos, acl, basic, user]
     */
    protected static Set<String> types = getDefault().supportedFileAttributeViews();
    /**
     *
     */
    protected static Set<String> attrTypes = Set.of("basic", "dos", "posix");
    /**
     *
     */
    protected static Class<BasicFileAttributes> baseType = BasicFileAttributes.class;
    /**
     *
     */
    protected static Class<DosFileAttributes> dosType = DosFileAttributes.class;
    /**
     *
     */
    protected static Class<PosixFileAttributes> posType = PosixFileAttributes.class;
    /**
     *
     */
    protected static Class<FileAttributeView> viewType = FileAttributeView.class;
    /**
     *
     */
    protected static Class<BasicFileAttributeView> baseViewType = BasicFileAttributeView.class;
    /**
     *
     */
    protected static Class<DosFileAttributeView> dosViewType = DosFileAttributeView.class;
    /**
     *
     */
    protected static Class<PosixFileAttributeView> posViewType = PosixFileAttributeView.class;
    /**
     *
     */
    protected static Class<FileOwnerAttributeView> ownerViewType = FileOwnerAttributeView.class;
    /**
     *
     */
    protected static Class<AclFileAttributeView> aclViewType = AclFileAttributeView.class;
    /**
     *
     */
    protected static Class<UserDefinedFileAttributeView> userViewType = UserDefinedFileAttributeView.class;
    /**
     *
     */
    protected static Attributes attrs;
    /**
     *
     */
    protected Path path;
    /**
     *
     */
    protected BasicFileAttributes baseAttrs;
    /**
     *
     */
    protected DosFileAttributes dosAttrs;
    /**
     *
     */
    protected PosixFileAttributes posAttrs;
    /**
     *
     */
    protected Map<String, Object> map = new HashMap<>();
    /**
     *
     */
    protected Map<String, Object> baseMap;
    /**
     *
     */
    protected Map<String, Object> dosMap;
    /**
     *
     */
    protected Map<String, Object> posMap;
    /**
     *
     */
    protected FileAttributeView view;
    /**
     *
     */
    protected BasicFileAttributeView baseView;
    /**
     *
     */
    protected DosFileAttributeView dosView;
    /**
     *
     */
    protected PosixFileAttributeView posView;
    /**
     *
     */
    protected FileOwnerAttributeView ownerView;
    /**
     *
     */
    protected AclFileAttributeView aclView;
    /**
     *
     */
    protected UserDefinedFileAttributeView userView;

    /**
     *
     * @param path String
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"path"})
    public Attributes(String path) throws ReflectiveOperationException {
        init(pathStr(path));
    }

    /**
     *
     * @param file File
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"file"})
    public Attributes(java.io.File file) throws ReflectiveOperationException {
        init(file.toPath());
    }

    /**
     *
     * @param path Path
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"path"})
    public Attributes(Path path) throws ReflectiveOperationException {
        init(path);
    }

    /**
     *
     * @param path Path
     * @throws ReflectiveOperationException throws
     */
    public void init(Path path) throws ReflectiveOperationException {
        debug("Attributes:init");
        this.path = path;
        view = attrView(path);
        for (String t : types) {
            if (attrTypes.contains(t)) {
                t = t.replace("basic", "base").replace("posix", "pos");
                setField(this, t + "Attrs", invoke(Attributes.class, t + "Attrs", path));
                Map<String, Object> _map = setField(this, t + "Map", invoke(Attributes.class, t + "AttrsMap", path));
                map.putAll(_map);
            }
            setField(this, t + "View", invoke(Attributes.class, t + "AttrView", path));
        }
    }

    /**
     * [owner, dos, acl, basic, user]
     * @return Set {String}
     */
    public static Set<String> supportedTypes() {
        return types;
    }

    /**
     *
     * @return Attributes
     */
    public static Attributes attrs() {
        return attrs;
    }

    /**
     *
     * @param path {@code final} String
     * @return Attributes
     */
    public static Attributes attrs(final String path) throws ReflectiveOperationException {
        return new Attributes(path);
    }

    /**
     *
     * @param file {@code final} File
     * @return Attributes
     */
    public static Attributes attrs(final java.io.File file) throws ReflectiveOperationException {
        return new Attributes(file);
    }

    /**
     *
     * @param path {@code final} Path
     * @return Attributes
     */
    public static Attributes attrs(final Path path) throws ReflectiveOperationException {
        return new Attributes(path);
    }

    /**
     *
     * @param path {@code final} String
     * @return BasicFileAttributes
     */
    public static BasicFileAttributes baseAttrs(final String path) {
        return baseAttrs(pathStr(path));
    }

    /**
     *
     * @param file {@code final} File
     * @return BasicFileAttributes
     */
    public static BasicFileAttributes baseAttrs(final java.io.File file) {
        return baseAttrs(file.toPath());
    }

    /**
     *
     * @param path {@code final} Path
     * @return BasicFileAttributes
     */
    public static BasicFileAttributes baseAttrs(final Path path) {
        return attrs(path, baseType);
    }

    /**
     *
     * @param path {@code final} String
     * @return DosFileAttributes
     */
    public static DosFileAttributes dosAttrs(final String path) {
        return dosAttrs(pathStr(path));
    }

    /**
     *
     * @param file {@code final} File
     * @return DosFileAttributes
     */
    public static DosFileAttributes dosAttrs(final java.io.File file) {
        return dosAttrs(file.toPath());
    }

    /**
     *
     * @param path {@code final} Path
     * @return DosFileAttributes
     */
    public static DosFileAttributes dosAttrs(final Path path) {
        return attrs(path, dosType);
    }

    /**
     *
     * @param file {@code final} File
     * @return PosixFileAttributes
     */
    public static PosixFileAttributes posAttrs(final java.io.File file) {
        return posAttrs(file.toPath());
    }

    /**
     *
     * @param path {@code final} String
     * @return PosixFileAttributes
     */
    public static PosixFileAttributes posAttrs(final String path) {
        return posAttrs(pathStr(path));
    }

    /**
     *
     * @param path {@code final} Path
     * @return PosixFileAttributes
     */
    public static PosixFileAttributes posAttrs(final Path path) {
        return attrs(path, posType);
    }

    /**
     *
     * @param path {@code final} Path
     * @param type Class A
     * @return A
     * @param <A> {@code extends} BasicFileAttributes
     */
    public static <A extends BasicFileAttributes> A attrs(final Path path, Class<A> type) {
        debug("Reading " + getClassSimpleName(type) + ": " + path(path));
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
     * @return Map {String, Object}
     */
    public static Map<String, Object> attrsMap() {
        return attrs.map;
    }

    /**
     *
     * @param path {@code final} String
     * @return Map {String, Object}
     * @throws ReflectiveOperationException throws
     */
    public static Map<String, Object> attrsMap(final String path) throws ReflectiveOperationException {
        return new Attributes(path).map;
    }

    /**
     *
     * @param file {@code final} File
     * @return Map {String, Object}
     * @throws ReflectiveOperationException throws
     */
    public static Map<String, Object> attrsMap(final java.io.File file) throws ReflectiveOperationException {
        return new Attributes(file).map;
    }

    /**
     *
     * @param path {@code final} Path
     * @return Map {String, Object}
     * @throws ReflectiveOperationException throws
     */
    public static Map<String, Object> attrsMap(final Path path) throws ReflectiveOperationException {
        return new Attributes(path).map;
    }

    /**
     * size, creationTime, lastModifiedTime, lastAccessTime, isDirectory, isOther, isRegularFile, isSymbolicLink, fileKey
     * @param path {@code final} String
     * @return Map {String, Object}
     */
    public static Map<String, Object> baseAttrsMap(final String path) {
        return baseAttrsMap(pathStr(path));
    }

    /**
     * size, creationTime, lastModifiedTime, lastAccessTime, isDirectory, isOther, isRegularFile, isSymbolicLink, fileKey
     * @param file {@code final} File
     * @return Map {String, Object}
     */
    public static Map<String, Object> baseAttrsMap(final java.io.File file) {
        return baseAttrsMap(file.toPath());
    }

    /**
     * size, creationTime, lastModifiedTime, lastAccessTime, isDirectory, isOther, isRegularFile, isSymbolicLink, fileKey
     * @param path {@code final} Path
     * @return Map {String, Object}
     */
    public static Map<String, Object> baseAttrsMap(final Path path) {
        return baseAttrsMap(path, "*");
    }

    /**
     * size, creationTime, lastModifiedTime, lastAccessTime, isDirectory, isOther, isRegularFile, isSymbolicLink, fileKey
     * @param path {@code final} String
     * @param attrs String[]
     * @return Map {String, Object}
     */
    public static Map<String, Object> baseAttrsMap(final String path, String... attrs) {
        return baseAttrsMap(pathStr(path), attrs);
    }

    /**
     * size, creationTime, lastModifiedTime, lastAccessTime, isDirectory, isOther, isRegularFile, isSymbolicLink, fileKey
     * @param file {@code final} File
     * @param attrs String[]
     * @return Map {String, Object}
     */
    public static Map<String, Object> baseAttrsMap(final java.io.File file, String... attrs) {
        return baseAttrsMap(file.toPath(), attrs);
    }

    /**
     * size, creationTime, lastModifiedTime, lastAccessTime, isDirectory, isOther, isRegularFile, isSymbolicLink, fileKey
     * @param path {@code final} Path
     * @param attrs String[]
     * @return Map {String, Object}
     */
    public static Map<String, Object> baseAttrsMap(final Path path, String... attrs) {
        return attrs(path, attrsStr(attrs));
    }

    /**
     * size, creationTime, lastModifiedTime, lastAccessTime, isDirectory, isOther, isRegularFile, isSymbolicLink, fileKey, readonly, archive, hidden, system, attributes
     * @param path {@code final} String
     * @return Map {String, Object}
     */
    public static Map<String, Object> dosAttrsMap(final String path) {
        return dosAttrsMap(pathStr(path));
    }

    /**
     * size, creationTime, lastModifiedTime, lastAccessTime, isDirectory, isOther, isRegularFile, isSymbolicLink, fileKey, readonly, archive, hidden, system, attributes
     * @param file {@code final} File
     * @return Map {String, Object}
     */
    public static Map<String, Object> dosAttrsMap(final java.io.File file) {
        return dosAttrsMap(file.toPath());
    }

    /**
     * size, creationTime, lastModifiedTime, lastAccessTime, isDirectory, isOther, isRegularFile, isSymbolicLink, fileKey, readonly, archive, hidden, system, attributes
     * @param path {@code final} Path
     * @return Map {String, Object}
     */
    public static Map<String, Object> dosAttrsMap(final Path path) {
        return dosAttrsMap(path, "*");
    }

    /**
     * size, creationTime, lastModifiedTime, lastAccessTime, isDirectory, isOther, isRegularFile, isSymbolicLink, fileKey, readonly, archive, hidden, system, attributes
     * @param path {@code final} String
     * @param attrs String[]
     * @return Map {String, Object}
     */
    public static Map<String, Object> dosAttrsMap(final String path, String... attrs) {
        return dosAttrsMap(pathStr(path), attrs);
    }

    /**
     * size, creationTime, lastModifiedTime, lastAccessTime, isDirectory, isOther, isRegularFile, isSymbolicLink, fileKey, readonly, archive, hidden, system, attributes
     * @param file {@code final} File
     * @param attrs String[]
     * @return Map {String, Object}
     */
    public static Map<String, Object> dosAttrsMap(final java.io.File file, String... attrs) {
        return dosAttrsMap(file.toPath(), attrs);
    }

    /**
     * size, creationTime, lastModifiedTime, lastAccessTime, isDirectory, isOther, isRegularFile, isSymbolicLink, fileKey, readonly, archive, hidden, system, attributes
     * @param path {@code final} Path
     * @param attrs String[]
     * @return Map {String, Object}
     */
    public static Map<String, Object> dosAttrsMap(final Path path, String... attrs) {
        return attrs(path, "dos:" + attrsStr(attrs));
    }

    /**
     *
     * @param file {@code final} File
     * @return Map {String, Object}
     */
    public static Map<String, Object> posAttrsMap(final java.io.File file) {
        return posAttrsMap(file.toPath());
    }

    /**
     *
     * @param path {@code final} String
     * @return Map {String, Object}
     */
    public static Map<String, Object> posAttrsMap(final String path) {
        return posAttrsMap(pathStr(path));
    }

    /**
     *
     * @param path {@code final} Path
     * @return Map {String, Object}
     */
    public static Map<String, Object> posAttrsMap(final Path path) {
        return posAttrsMap(path, "*");
    }

    /**
     *
     * @param file {@code final} File
     * @param attrs String[]
     * @return Map {String, Object}
     */
    public static Map<String, Object> posAttrsMap(final java.io.File file, String... attrs) {
        return posAttrsMap(file.toPath(), attrs);
    }

    /**
     *
     * @param path {@code final} String
     * @param attrs String[]
     * @return Map {String, Object}
     */
    public static Map<String, Object> posAttrsMap(final String path, String... attrs) {
        return posAttrsMap(pathStr(path), attrs);
    }

    /**
     *
     * @param path {@code final} Path
     * @param attrs String[]
     * @return Map {String, Object}
     */
    public static Map<String, Object> posAttrsMap(final Path path, String... attrs) {
        return attrs(path, "posix:" + attrsStr(attrs));
    }

    /**
     *
     * @param attrs String[]
     * @return String
     */
    public static String attrsStr(String... attrs) {
        //return attrs.length == 0 ? "*" : join(",", attrs);
        return join(",", attrs);
    }

    /**
     *
     * @param path {@code final} Path
     * @param attrs String
     * @return Map {String, Object}
     */
    public static Map<String, Object> attrs(final Path path, String attrs) {
        debug("Reading attributes" + " (" + attrs + "): " + path(path));
        try {
            return readAttributes(path, attrs);
        } catch (IOException|UnsupportedOperationException e) {
            debug("Error reading attributes.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param path Path
     * @return String
     */
    public static List<String> userAttrs(Path path) throws IOException {
        debug("Reading user attributes: " + path(path));
        return userAttrs(userAttrView(path));
    }

    /**
     *
     * @param view UserDefinedFileAttributeView
     * @return String
     */
    public static List<String> userAttrs(UserDefinedFileAttributeView view) throws IOException {
        List<String> attrs = view.list();
        debug("User attributes: " + attrs);
        return attrs;
    }

    /**
     *
     * @param path String
     * @return FileAttributeView
     */
    public static FileAttributeView attrView(String path) {
        return getFileAttributeView(pathStr(path), viewType);
    }

    /**
     *
     * @param file File
     * @return FileAttributeView
     */
    public static FileAttributeView attrView(java.io.File file) {
        return getFileAttributeView(file.toPath(), viewType);
    }

    /**
     *
     * @param path Path
     * @return FileAttributeView
     */
    public static FileAttributeView attrView(Path path) {
        return getFileAttributeView(path, viewType);
    }

    /**
     *
     * @param path String
     * @return BasicFileAttributeView
     */
    public static BasicFileAttributeView baseAttrView(String path) {
        return getFileAttributeView(pathStr(path), baseViewType);
    }

    /**
     *
     * @param file File
     * @return BasicFileAttributeView
     */
    public static BasicFileAttributeView baseAttrView(java.io.File file) {
        return getFileAttributeView(file.toPath(), baseViewType);
    }

    /**
     *
     * @param path Path
     * @return BasicFileAttributeView
     */
    public static BasicFileAttributeView baseAttrView(Path path) {
        return getFileAttributeView(path, baseViewType);
    }

    /**
     *
     * @param path String
     * @return DosFileAttributeView
     */
    public static DosFileAttributeView dosAttrView(String path) {
        return getFileAttributeView(pathStr(path), dosViewType);
    }

    /**
     *
     * @param file File
     * @return DosFileAttributeView
     */
    public static DosFileAttributeView dosAttrView(java.io.File file) {
        return getFileAttributeView(file.toPath(), dosViewType);
    }

    /**
     *
     * @param path Path
     * @return DosFileAttributeView
     */
    public static DosFileAttributeView dosAttrView(Path path) {
        return getFileAttributeView(path, dosViewType);
    }

    /**
     *
     * @param path String
     * @return PosixFileAttributeView
     */
    public static PosixFileAttributeView posAttrView(String path) {
        return getFileAttributeView(pathStr(path), posViewType);
    }

    /**
     *
     * @param file File
     * @return PosixFileAttributeView
     */
    public static PosixFileAttributeView posAttrView(java.io.File file) {
        return getFileAttributeView(file.toPath(), posViewType);
    }

    /**
     *
     * @param path Path
     * @return PosixFileAttributeView
     */
    public static PosixFileAttributeView posAttrView(Path path) {
        return getFileAttributeView(path, posViewType);
    }

    /**
     *
     * @param path String
     * @return FileOwnerAttributeView
     */
    public static FileOwnerAttributeView ownerAttrView(String path) {
        return getFileAttributeView(pathStr(path), ownerViewType);
    }

    /**
     *
     * @param file File
     * @return FileOwnerAttributeView
     */
    public static FileOwnerAttributeView ownerAttrView(java.io.File file) {
        return getFileAttributeView(file.toPath(), ownerViewType);
    }

    /**
     *
     * @param path Path
     * @return FileOwnerAttributeView
     */
    public static FileOwnerAttributeView ownerAttrView(Path path) {
        return getFileAttributeView(path, ownerViewType);
    }

    /**
     *
     * @param path String
     * @return AclFileAttributeView
     */
    public static AclFileAttributeView aclAttrView(String path) {
        return getFileAttributeView(pathStr(path), aclViewType);
    }

    /**
     *
     * @param file File
     * @return AclFileAttributeView
     */
    public static AclFileAttributeView aclAttrView(java.io.File file) {
        return getFileAttributeView(file.toPath(), aclViewType);
    }

    /**
     *
     * @param path Path
     * @return AclFileAttributeView
     */
    public static AclFileAttributeView aclAttrView(Path path) {
        return getFileAttributeView(path, aclViewType);
    }

    /**
     *
     * @param path String
     * @return UserDefinedFileAttributeView
     */
    public static UserDefinedFileAttributeView userAttrView(String path) {
        return getFileAttributeView(pathStr(path), userViewType);
    }

    /**
     *
     * @param file File
     * @return UserDefinedFileAttributeView
     */
    public static UserDefinedFileAttributeView userAttrView(java.io.File file) {
        return getFileAttributeView(file.toPath(), userViewType);
    }

    /**
     *
     * @param path Path
     * @return UserDefinedFileAttributeView
     */
    public static UserDefinedFileAttributeView userAttrView(Path path) {
        return getFileAttributeView(path, userViewType);
    }

    /**
     *
     * @param path String
     * @param name String
     * @return String
     */
    public static String userAttr(String path, String name) {
        return userAttr(pathStr(path), name);
    }

    /**
     *
     * @param file File
     * @param name String
     * @return String
     */
    public static String userAttr(java.io.File file, String name) {
        return userAttr(file.toPath(), name);
    }

    /**
     *
     * @param path Path
     * @param name String
     * @return String
     */
    public static String userAttr(Path path, String name) {
        debug("Reading user attribute" + " (" + name + "): " + path(path));
        UserDefinedFileAttributeView view = userAttrView(path);
        try {
            debug(name + ": " + getAttribute(path, "user:name"));
        } catch (IOException|IllegalArgumentException e) {
            debug("Error getAttribute.");
            e.printStackTrace();
        }
        return getAttr(view, name);
    }

    /**
     *
     * @param view UserDefinedFileAttributeView
     * @param name String
     * @return String
     */
    public static String getAttr(UserDefinedFileAttributeView view, String name) {
        try {
            ByteBuffer buf = allocate(view.size(name));
            view.read(name, buf);
            String value = decode(buf);
            debug(name + ": " + value);
            debug(view.name());
            debug(userAttrs(view));
            return value;
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
    public static String userAttr(String path, String name, String value) {
        return userAttr(pathStr(path), name, value);
    }

    /**
     *
     * @param file File
     * @param name String
     * @param value String
     * @return String
     */
    public static String userAttr(java.io.File file, String name, String value) {
        return userAttr(file.toPath(), name, value);
    }

    /**
     *
     * @param path Path
     * @param name String
     * @param value String
     * @return String
     */
    public static String userAttr(Path path, String name, String value) {
        debug("Writing user attribute" + " (" + name + ", " + value + "): " + path(path));
        UserDefinedFileAttributeView view = userAttrView(path);
        try {
            debug(userAttrs(view));
            ByteBuffer buf = encode("name");
            debug(name + ": " + setAttribute(path, "user:name", buf));
            debug(name + ": " + getAttribute(path, "user:name"));
            debug(userAttrs(view));
        } catch (IOException|IllegalArgumentException e) {
            debug("Error setAttribute.");
            e.printStackTrace();
        }
        return setAttr(view, name, value);
    }

    /**
     *
     * @param view UserDefinedFileAttributeView
     * @param name String
     * @param value String
     * @return String
     */
    public static String setAttr(UserDefinedFileAttributeView view, String name, String value) {
        try {
            ByteBuffer buf = encode(value);
            view.write(name, buf);
            value = decode(buf);
            debug(name + ": " + value);
            return value;
        } catch (IOException e) {
            debug("Error writing view.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param view UserDefinedFileAttributeView
     * @param name String
     * @return String
     */
    public static String deleteUserAttr(UserDefinedFileAttributeView view, String name) {
        try {
            view.delete(name);
        } catch (IOException e) {
            debug("Error reading view.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param path Path
     * @return UserPrincipal
     */
    public static UserPrincipal owner(Path path) throws IOException {
        debug("Reading owner attributes: " + path(path));
        return owner(ownerAttrView(path));
    }

    /**
     *
     * @param view FileOwnerAttributeView
     * @return UserPrincipal
     */
    public static UserPrincipal owner(FileOwnerAttributeView view) throws IOException {
        UserPrincipal owner = view.getOwner();
        debug("Owner: " + owner);
        return owner;
    }

    /**
     *
     * @param path Path
     * @return String
     */
    public static String ownerName(Path path) throws IOException {
        debug("Reading owner name: " + path(path));
        return ownerName(ownerAttrView(path));
    }

    /**
     *
     * @param view FileOwnerAttributeView
     * @return String
     */
    public static String ownerName(FileOwnerAttributeView view) throws IOException {
        String ownerName = owner(view).getName();
        debug("OwnerName: " + ownerName);
        return ownerName;
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
        printDosAttrs(path);
        printPosAttrs(path);
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
        BasicFileAttributes attrs = baseAttrs(path);
        debug(attrs);
        debug(baseAttrsMap(path));
        if (notNull(attrs)) {
            debug("size             = " + attrs.size() + " bytes");
            debug("creationTime     = " + attrs.creationTime());
            debug("lastModifiedTime = " + attrs.lastModifiedTime());
            debug("lastAccessTime   = " + attrs.lastAccessTime());
            debug("isDirectory      = " + attrs.isDirectory());
            debug("isOther          = " + attrs.isOther());
            debug("isRegularFile    = " + attrs.isRegularFile());
            debug("isSymbolicLink   = " + attrs.isSymbolicLink());
            debug("fileKey          = " + attrs.fileKey());
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
        debug(dosAttrsMap(path));
        if (notNull(attrs)) {
            debug("size             = " + attrs.size() + " bytes");
            debug("creationTime     = " + attrs.creationTime());
            debug("lastModifiedTime = " + attrs.lastModifiedTime());
            debug("lastAccessTime   = " + attrs.lastAccessTime());
            debug("isDirectory      = " + attrs.isDirectory());
            debug("isOther          = " + attrs.isOther());
            debug("isRegularFile    = " + attrs.isRegularFile());
            debug("isSymbolicLink   = " + attrs.isSymbolicLink());
            debug("fileKey          = " + attrs.fileKey());
            debug("isReadOnly       = " + attrs.isReadOnly());
            debug("isArchive        = " + attrs.isArchive());
            debug("isHidden         = " + attrs.isHidden());
            debug("isSystem         = " + attrs.isSystem());
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
     * @param name String
     */
    public static void printUserAttrs(String name) {
        printUserAttrs("", name);
    }

    /**
     *
     * @param path String
     * @param name String
     */
    public static void printUserAttrs(String path, String name) {
        printUserAttrs(pathStr(path), name);
    }

    /**
     *
     * @param file File
     * @param name String
     */
    public static void printUserAttrs(java.io.File file, String name) {
        printUserAttrs(file.toPath(), name);
    }

    /**
     *
     * @param path Path
     * @param name String
     */
    public static void printUserAttrs(Path path, String name) {
        userAttr(path, name);
    }

    /**
     *
     * @param name String
     * @param value String
     */
    public static void printUserSetAttrs(String name, String value) {
        printUserAttrs("", name, value);
    }

    /**
     *
     * @param path String
     * @param name String
     * @param value String
     */
    public static void printUserAttrs(String path, String name, String value) {
        printUserAttrs(pathStr(path), name, value);
    }

    /**
     *
     * @param file File
     * @param name String
     * @param value String
     */
    public static void printUserAttrs(java.io.File file, String name, String value) {
        printUserAttrs(file.toPath(), name, value);
    }

    /**
     *
     * @param path Path
     * @param name String
     * @param value String
     */
    public static void printUserAttrs(Path path, String name, String value) {
        userAttr(path, name, value);
    }

}
