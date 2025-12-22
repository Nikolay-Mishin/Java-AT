package org.project.utils.fs;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.project.utils.json.JsonSchema;
import org.project.utils.request.Request;

import static java.lang.Math.toIntExact;
import static java.nio.file.Files.copy;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.last;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.fs.FS.delete;
import static org.project.utils.fs.FS.isDir;
import static org.project.utils.fs.FS.mkdirs;
import static org.project.utils.fs.FS.path;
import static org.project.utils.fs.FS.pathStr;
import static org.project.utils.fs.FS.resolve;
import static org.project.utils.json.JsonSchema.loadJson;
import static org.project.utils.request.Request.req;
import static org.project.utils.stream.InputStream.arrayIn;
import static org.project.utils.stream.InputStream.input;
import static org.project.utils.stream.InputStream.zipIn;
import static org.project.utils.stream.OutputStream.bufOut;

/**
 *
 */
public class Zip {

    /**
     *
     * @param src String
     * @param out String
     * @throws IOException throws
     */
    public static void unzip(String src, String out) throws IOException {
        unzip(input(src), out);
    }

    /**
     *
     * @param src Path
     * @param out Path
     * @throws IOException throws
     */
    public static void unzip(Path src, Path out) throws IOException {
        unzip(path(src), path(out));
    }

    /**
     *
     * @param src InputStream
     * @param out String
     * @throws IOException throws
     */
    public static void unzip(InputStream src, String out) throws IOException {
        unzip(src, pathStr(out));
    }

    /**
     *
     * @param src byte[]
     * @param out String
     * @throws IOException throws
     */
    public static void unzip(byte[] src, String out) throws IOException {
        unzip(arrayIn(src), out);
    }

    /**
     *
     * @param src byte[]
     * @param out Path
     * @throws IOException throws
     */
    public static void unzip(byte[] src, Path out) throws IOException {
        unzip(arrayIn(src), out);
    }

    /**
     *
     * @param src InputStream
     * @param out Path
     * @throws IOException throws
     */
    public static void unzip(InputStream src, Path out) throws IOException {
        debug("Clear: " + out);
        debug(delete(out));
        out = out.toAbsolutePath();
        try (ZipInputStream zipIn = zipIn(src)) {
            for (ZipEntry ze; (ze = zipIn.getNextEntry()) != null; ) {
                Path resolve = resolve(out, ze.getName());
                if (!resolve.startsWith(out)) {
                    // see: https://snyk.io/research/zip-slip-vulnerability
                    throw new RuntimeException("Entry with an illegal path: " + ze.getName());
                }
                mkdirs(ze, resolve);
                copy(zipIn, resolve);
            }
        }
    }

    /**
     *
     * @param src String
     * @param out String
     * @throws IOException throws
     */
    public static void unzipFile(String src, String out) throws IOException {
        unzip(new File(src), out);
    }

    /**
     *
     * @param src Path
     * @param out Path
     * @throws IOException throws
     */
    public static void unzipFile(Path src, Path out) throws IOException {
        unzipFile(path(src), path(out));
    }

    /**
     *
     * @param src File
     * @param out Path
     * @throws IOException throws
     */
    public static void unzip(File src, Path out) throws IOException {
        unzip(src, path(out));
    }

    /**
     *
     * @param src File
     * @param out String
     * @throws IOException throws
     */
    public static void unzip(File src, String out) throws IOException {
        try (ZipInputStream zipIn = zipIn(src)) {
            ZipEntry ze = zipIn.getNextEntry();
            while (ze != null) {
                File file = new File(out, ze.getName());
                mkdirs(ze, file);
                if (!isDir(ze)) {
                    writeFile(zipIn, ze, file);
                }
                ze = zipIn.getNextEntry();
            }
        }
    }

    /**
     *
     * @param zipIn ZipInputStream
     * @param ze ZipEntry
     * @param file File
     * @throws IOException throws
     */
    public static void writeFile(ZipInputStream zipIn, ZipEntry ze, File file) throws IOException {
        try (BufferedOutputStream bos = bufOut(file)) {
            int bufferSize = toIntExact(ze.getSize());
            byte[] buffer = new byte[bufferSize > 0 ? bufferSize : 1];
            int location;

            while ((location = zipIn.read(buffer)) != -1) {
                bos.write(buffer, 0, location);
            }
        }
    }

    /**
     *
     * @param src String
     * @param out String
     */
    public static void unzipPass(String src, String out) {
        unzip(src, out, "");
    }

    /**
     *
     * @param src String
     * @param out String
     * @param password String
     */
    public static void unzip(String src, String out, String password) {
        try {
            ZipFile zipFile = new ZipFile(src);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(out);
        }
        catch (ZipException e) { e.printStackTrace(); }
    }

    /**
     *
     * @param src String
     * @param out String
     * @throws IOException throws
     */
    public static void unzipSelenium(String src, String out) throws IOException {
        unzipSelenium(input(src), new File(out));
    }

    /**
     *
     * @param src Path
     * @param out Path
     * @throws IOException throws
     */
    public static void unzipSelenium(Path src, Path out) throws IOException {
        unzipSelenium(src.toString(), out.toString());
    }

    /**
     *
     * @param src File
     * @param out File
     * @throws IOException throws
     */
    public static void unzipSelenium(File src, File out) throws IOException {
        unzipSelenium(input(src), out);
    }

    /**
     *
     * @param src InputStream
     * @param out File
     * @throws IOException throws
     */
    public static void unzipSelenium(InputStream src, File out) throws IOException {
        org.openqa.selenium.io.Zip.unzip(src, out);
    }

    /**
     *
     * @param url String
     * @param pathList String[]
     * @return Request
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request loadZip(String url, String... pathList) throws IOException, URISyntaxException, ReflectiveOperationException {
        return loadZip(url, pathStr(pathList));
    }

    /**
     *
     * @param url String
     * @param file File
     * @return Request
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request loadZip(String url, File file) throws IOException, URISyntaxException, ReflectiveOperationException {
        return loadZip(url, file.toPath());
    }

    /**
     *
     * @param url String
     * @param path Path
     * @return Request
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request loadZip(String url, Path path) throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("loadZip");
        Request req = req(url, GET);
        FS.writeFile(path, req.stream());
        return req;
    }

    /**
     *
     * @param url String
     * @param out String
     * @param pathList String[]
     * @return Request
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request loadZip(String url, String out, String... pathList) throws IOException, URISyntaxException, ReflectiveOperationException {
        return loadZip(url, out, pathStr(pathList));
    }

    /**
     *
     * @param url String
     * @param out String
     * @param file File
     * @return Request
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request loadZip(String url, String out, File file) throws IOException, URISyntaxException, ReflectiveOperationException {
        return loadZip(url, out, file.toPath());
    }

    /**
     *
     * @param url String
     * @param out String
     * @param path Path
     * @return Request
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request loadZip(String url, String out, Path path) throws IOException, URISyntaxException, ReflectiveOperationException {
        Request req = loadZip(url, path);
        debug("unZip");
        unzip(path, resolve(out));
        return req;
    }

    /**
     *
     * @param endpoint String
     * @param uri String
     * @param key String
     * @param k String
     * @param v String
     * @param urlK String
     * @param out String
     * @param rootZip File
     * @return JsonSchema
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static JsonSchema loadZip(String uri, String endpoint, String key, String k, String v, String urlK, String out, File rootZip)
        throws IOException, URISyntaxException, ReflectiveOperationException
    {
        return loadZip(uri, endpoint, key, k, v, urlK, out, rootZip.toPath());
    }

    /**
     *
     * @param endpoint String
     * @param uri String
     * @param key String
     * @param k String
     * @param v String
     * @param urlK String
     * @param out String
     * @param rootZip Path
     * @return JsonSchema
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static JsonSchema loadZip(String uri, String endpoint, String key, String k, String v, String urlK, String out, Path rootZip)
        throws IOException, URISyntaxException, ReflectiveOperationException
    {
        return loadZip(uri, endpoint, key, k, v, urlK, out, rootZip.toString());
    }

    /**
     *
     * @param endpoint String
     * @param uri String
     * @param key String
     * @param k String
     * @param v String
     * @param urlK String
     * @param out String
     * @param rootZip String
     * @return JsonSchema
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static JsonSchema loadZip(String uri, String endpoint, String key, String k, String v, String urlK, String out, String rootZip)
        throws IOException, URISyntaxException, ReflectiveOperationException
    {
        JsonSchema json = loadJson(uri, endpoint, key, k, v, urlK);
        String url = json.getUrl();
        json.setReq(loadZip(url, out, resolve(rootZip, last(url))));
        return json;
    }

}
