package org.project.utils.fs;

import static java.lang.String.join;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import jdk.jfr.Description;

import static org.apache.commons.io.IOUtils.*;

import static org.project.utils.Helper.*;
import static org.project.utils.Helper.debug;

public class FS {
    protected static Class<BasicFileAttributes> baseAttrs = BasicFileAttributes.class;
    protected static Class<DosFileAttributes> dosAttrs = DosFileAttributes.class;
    protected static Class<PosixFileAttributes> posAttrs = PosixFileAttributes.class;
    protected static Class<UserDefinedFileAttributeView> userAttrsView = UserDefinedFileAttributeView.class;

    @Description("Generate url path")
    public static Path pathStr(String path) {
        return Paths.get(path);
    }

    @Description("Generate url path")
    public static Path pathStr(String... pathList) {
        return Paths.get(Arrays.toString(pathList));
    }

    @Description("Generate url path")
    public static Path pathOf(String path) {
        return Path.of(path);
    }

    @Description("Generate url path")
    public static Path pathOf(String... pathList) {
        return Path.of(Arrays.toString(pathList));
    }

    @Description("Generate url path")
    public static String path(Object... pathList) {
        debug(Arrays.toString(pathList));
        return join("/", Arrays.stream(pathList.length == 1 && !(pathList[0] instanceof String) ? (Object[]) pathList[0] : pathList)
            .map(path -> isInstance(path, Object[].class) ? path(path) : path.toString())
            .toArray(String[]::new));
    }

    public static String readFile(String path) throws IOException {
        return readFile(new FileInputStream(path));
    }

    public static String readFile(InputStream input) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            debug("Error reading file.");
        }
        return resultStringBuilder.toString();
    }

    public static void writeFile(String path, InputStream input) throws IOException {
        /*try (FileOutputStream output = new FileOutputStream(path)) {
            copy(input, output);
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing file.");
        }*/
        writeFile(path, input.readAllBytes());
        input.close();
    }

    public static void writeFile(String path, byte[] data) {
        try (FileOutputStream output = new FileOutputStream(path)) {
            writeStream(output, data);
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing file.");
        }
    }

    public static void writeFile(String path, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(data);
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing file.");
        }
    }

    public static void writeStream(OutputStream output, InputStream input) throws IOException {
        writeStream(output, input.readAllBytes());
        input.close();
    }

    public static void writeStream(OutputStream output, byte[] data) {
        try (output) {
            //output.write(data);
            write(data, output);
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing file.");
        }
    }

    public static void writeStream(OutputStream output, String data) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output))) {
            bw.write(data);
            debug("Successfully write to the file.");
        } catch (IOException e) {
            debug("Error writing file.");
        }
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

    public static Stream<Path> readDir(final String path, final Predicate<? super Path> filter) throws IOException {
        return readDir(path).filter(filter);
    }

    public static Stream<Path> readDir(final String path) throws IOException {
        return Files.walk(pathStr(path));
    }

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
            int attr = view.read(name, StandardCharsets.UTF_8.encode(path.toString()));
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
            int attr = view.write(name, StandardCharsets.UTF_8.encode(value));
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

    public static void printFile(final Path file) {
        debug(file);
        debug(file.getFileName());
        debug(file.getParent());
    }

    public static void printFile(final File file) {
        debug(file);
        debug(file.getName());
        debug(file.getParent());
    }

}
