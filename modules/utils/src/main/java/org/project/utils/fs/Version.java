package org.project.utils.fs;

import java.util.List;
import java.util.function.Function;

import static com.sun.jna.platform.win32.Version.INSTANCE;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.VerRsrc.VS_FIXEDFILEINFO;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.join;

/**
 * <p>Source - https://stackoverflow.com/a
 * <p>Posted by GEverding, Antonis Zafiropoulos and Mgamerz, modified by community. See post 'Timeline' for change history
 * <p>Retrieved 2025-12-21, License - CC BY-SA 4.0
 */
public class Version {
    /**
     *
     */
    public static List<Integer> version;
    /**
     *
     */
    public static List<Integer> build;
    /**
     *
     */
    public static int MAJOR = 0;
    /**
     *
     */
    public static int MINOR = 1;
    /**
     *
     */
    public static int BUILD = 2;
    /**
     *
     */
    public static int REVISION = 3;
    /**
     *
     */
    public static IntByReference dwDummy = new IntByReference();
    /**
     *
     */
    public static int versionLength;
    /**
     *
     */
    public static Pointer lpData;
    /**
     *
     */
    public static boolean fileInfoResult;
    /**
     *
     */
    public static boolean verQueryVal;
    /**
     *
     */
    public static PointerByReference lpBuffer = new PointerByReference();
    /**
     *
     */
    public static IntByReference puLen = new IntByReference();
    /**
     *
     */
    public static VS_FIXEDFILEINFO lpBufStructure;

    /**
     *
     * @param path String
     * @return String
     */
    public static String getVersion(String path) {
        return getVersion(path, 0);
    }

    /**
     *
     * @param path String
     * @param dwDummyV int
     * @return String
     */
    public static String getVersion(String path, int dwDummyV) {
        return join(".", version(path, dwDummyV));
    }

    /**
     *
     * @return String
     */
    public static String getBuildVersion() {
        return join(".", build);
    }

    /**
     *
     * @param path String
     * @return String
     */
    public static String getBuildVersion(String path) {
        return getBuildVersion(path, 0);
    }

    /**
     *
     * @param path String
     * @param dwDummyV int
     * @return String
     */
    public static String getBuildVersion(String path, int dwDummyV) {
        version(path, dwDummyV);
        return getBuildVersion();
    }

    /**
     *
     * @param path String
     * @return List {Integer}
     */
    protected static List<Integer> version(String path) {
        return version(path, 0);
    }

    /**
     *
     * @param path String
     * @param dwDummyV int
     * @return List {Integer}
     */
    protected static List<Integer> version(String path, int dwDummyV) {
        dwDummy.setValue(dwDummyV);
        versionLength = getVersionLength(path, dwDummy);
        lpData = getLpData(versionLength);
        fileInfoResult = getFileInfoResult(path, versionLength, lpData);
        verQueryVal = getVerQueryValue(lpData, lpBuffer, puLen);

        //debug("fileInfoResult: " + fileInfoResult);
        //debug("verQueryVal: " + verQueryVal);

        lpBufStructure = new VS_FIXEDFILEINFO(lpBuffer.getValue());
        lpBufStructure.read();

        int v1 = (lpBufStructure.dwFileVersionMS).intValue() >> 16;
        int v2 = (lpBufStructure.dwFileVersionMS).intValue() & 0xffff;
        int v3 = (lpBufStructure.dwFileVersionLS).intValue() >> 16;
        int v4 = (lpBufStructure.dwFileVersionLS).intValue() & 0xffff;

        build = List.of(v1, v2, v3);
        return version = List.of(v1, v2, v3, v4);
    }

    /**
     *
     * @param path String
     * @param dwDummy IntByReference
     * @return int
     */
    public static int getVersionLength(String path, IntByReference dwDummy) {
        return INSTANCE.GetFileVersionInfoSize(path, dwDummy);
    }

    /**
     *
     * @param versionLength int
     * @return Memory
     */
    public static Memory getLpData(int versionLength) {
        byte[] bufferArray = new byte[versionLength];
        return new Memory(bufferArray.length);
    }

    /**
     *
     * @param path String
     * @param versionLength int
     * @param lpData Pointer
     * @return boolean
     */
    public static boolean getFileInfoResult(String path, int versionLength, Pointer lpData) {
        return INSTANCE.GetFileVersionInfo(path, 0, versionLength, lpData);
    }

    /**
     *
     * @param lpData Pointer
     * @param lpBuffer PointerByReference
     * @param puLen IntByReference
     * @return boolean
     */
    public static boolean getVerQueryValue(Pointer lpData, PointerByReference lpBuffer, IntByReference puLen) {
        return INSTANCE.VerQueryValue(lpData, "\\", lpBuffer, puLen);
    }

    /**
     *
     * @param path String
     * @return int
     */
    public static int getMajorVersion(String path) {
        return version(path).get(MAJOR);
    }

    /**
     *
     * @param path String
     * @return int
     */
    public static int getMinorVersion(String path) {
        return version(path).get(MINOR);
    }

    /**
     *
     * @param path String
     * @return int
     */
    public static int getBuild(String path) {
        return version(path).get(BUILD);
    }

    /**
     *
     * @param path String
     * @return int
     */
    public static int getRevision(String path) {
        return version(path).get(REVISION);
    }

    /**
     *
     * @param path String
     * @return int
     */
    public static int getProductMajorVersion(String path) {
        return getProductVersion(path, VS_FIXEDFILEINFO::getProductVersionMajor);
    }

    /**
     *
     * @param path String
     * @return int
     */
    public static int getProductMinorVersion(String path) {
        return getProductVersion(path, VS_FIXEDFILEINFO::getProductVersionMinor);
    }

    /**
     *
     * @param path String
     * @return int
     */
    public static int getProductBuild(String path) {
        return getProductVersion(path, VS_FIXEDFILEINFO::getProductVersionBuild);
    }

    /**
     *
     * @param path String
     * @return int
     */
    public static int getProductRevision(String path) {
        return getProductVersion(path, VS_FIXEDFILEINFO::getProductVersionRevision);
    }

    /**
     *
     * @return int
     */
    public static int getMajorVersion() {
        return getProductVersion(VS_FIXEDFILEINFO::getFileVersionMajor);
    }

    /**
     *
     * @return int
     */
    public static int getMinorVersion() {
        return getProductVersion(VS_FIXEDFILEINFO::getFileVersionMinor);
    }

    /**
     *
     * @return int
     */
    public static int getBuild() {
        return getProductVersion(VS_FIXEDFILEINFO::getFileVersionBuild);
    }

    /**
     *
     * @return int
     */
    public static int getRevision() {
        return getProductVersion(VS_FIXEDFILEINFO::getFileVersionRevision);
    }

    /**
     *
     * @return int
     */
    public static int getProductMajorVersion() {
        return getProductVersion(VS_FIXEDFILEINFO::getProductVersionMajor);
    }

    /**
     *
     * @return int
     */
    public static int getProductMinorVersion() {
        return getProductVersion(VS_FIXEDFILEINFO::getProductVersionMinor);
    }

    /**
     *
     * @return int
     */
    public static int getProductBuild() {
        return getProductVersion(VS_FIXEDFILEINFO::getProductVersionBuild);
    }

    /**
     *
     * @return int
     */
    public static int getProductRevision() {
        return getProductVersion(VS_FIXEDFILEINFO::getProductVersionRevision);
    }

    /**
     *
     * @param path String
     * @param cb Function {VS_FIXEDFILEINFO, Integer}
     * @return int
     */
    public static int getProductVersion(String path, Function<VS_FIXEDFILEINFO, Integer> cb) {
        version(path);
        return getProductVersion(cb);
    }

    /**
     *
     * @param cb Function {VS_FIXEDFILEINFO, Integer}
     * @return int
     */
    public static int getProductVersion(Function<VS_FIXEDFILEINFO, Integer> cb) {
        return cb.apply(lpBufStructure);
    }

}
