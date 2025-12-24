package org.project.utils.fs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.DriverConfig.config;
import static org.project.utils.fs.Version.getBuildVersion;
import static org.project.utils.fs.Version.getMajorVersion;
import static org.project.utils.fs.Version.getVersion;
import static org.project.utils.fs.Zip.loadZip;
import static org.project.utils.fs.Zip.unzip;
import static org.project.utils.json.JsonSchema.getMap;

import org.project.utils.config.DriverBaseConfig;

/**
 *
 * <p>{@code known-good-versions.json} The versions for which all CfT assets are available for download. Useful for bisecting.
 * <p>{@code known-good-versions-with-downloads.json} Same as above, but with an extra downloads property for each version, listing the full download URLs per asset.
 * <p>{@code last-known-good-versions.json} The latest versions for which all CfT assets are available for download, for each Chrome release channel (Stable/Beta/Dev/Canary).
 * <p>{@code last-known-good-versions-with-downloads.json} Same as above, but with an extra downloads property for each channel, listing the full download URLs per asset.
 * <p>{@code latest-patch-versions-per-build.json} The latest versions for which all CfT assets are available for download, for each known combination of MAJOR.MINOR.BUILD versions.
 * <p>{@code latest-patch-versions-per-build-with-downloads.json} Same as above, but with an extra downloads property for each version, listing the full download URLs per asset.
 * <p>{@code latest-versions-per-milestone.json} The latest versions for which all CfT assets are available for download, for each Chrome milestone.
 * <p>{@code latest-versions-per-milestone-with-downloads.json} Same as above, but with an extra downloads property for each milestone, listing the full download URLs per asset.
 */
public class Library {
    /**
     *
     */
    protected static DriverBaseConfig c = config();
    /**
     *
     */
    protected static String chrome = c.getChrome();
    /**
     *
     */
    protected static String chromeDriver = c.getChromeDriver();
    /**
     *
     */
    protected static String chromeVersion = getVersion(chrome);
    /**
     *
     */
    protected static String chromeBuildVersion = getBuildVersion();
    /**
     *
     */
    protected static String chromeMilestone = String.valueOf(getMajorVersion());
    /**
     *
     */
    protected static String chromeDriverVersion = getVersion(chromeDriver);
    /**
     *
     */
    protected static String chromeDriverBuildVersion = getBuildVersion();
    /**
     *
     */
    protected static String chromeDriverMilestone = String.valueOf(getMajorVersion());
    /**
     *
     */
    protected static boolean updateLibrary = !chromeDriverVersion.equals(chromeVersion);
    /**
     *
     */
    protected static String apiVer = c.getApiVer();
    /**
     *
     */
    protected static String apiBuild = c.getApiBuild();
    /**
     *
     */
    protected static String apiMilestone = c.getApiMilestone();
    /**
     *
     */
    protected static String uri = c.getApiUri();
    /**
     *
     */
    protected static String libEndpointK = c.getLibEndpoint();
    /**
     *
     */
    protected static Map<String, Object> libEndpoints = getMap(c.getLibEndpoints());
    /**
     *
     */
    @SuppressWarnings("unchecked")
    protected static Map<String, Object> libEndpoint = (Map<String, Object>) libEndpoints.get(libEndpointK);
    /**
     *
     */
    protected static String endpoint = endpoint(c.getApiEndpoint() + libEndpoint.get("endpoint").toString());
    /**
     *
     */
    protected static String key = key(libEndpoint.get("key").toString());
    /**
     *
     */
    protected static String k = c.getJsonK();
    /**
     *
     */
    protected static String v = c.getJsonV();
    /**
     *
     */
    protected static String urlK = c.getUrlK();
    /**
     *
     */
    protected static String root = c.getLibRoot();
    /**
     *
     */
    protected static String out = c.getLibOut();
    /**
     *
     */
    protected static boolean libWrite = c.getLibWrite();

    /**
     *
     * @return String
     */
    public static String chrome() {
        return chrome;
    }

    /**
     *
     * @return String
     */
    public static String chromeDriver() {
        return chromeDriver;
    }

    /**
     *
     * @return String
     */
    public static String chromeVersion() {
        return chromeVersion;
    }

    /**
     *
     * @return String
     */
    public static String chromeDriverVersion() {
        return chromeDriverVersion;
    }

    /**
     *
     * @return boolean
     */
    public static boolean isUpdateLibrary() {
        return updateLibrary;
    }

    /**
     *
     * @return boolean
     */
    public static boolean checkLib() {
        debug("checkLib");
        debug("chrome: " + chrome);
        debug("chromeDriver: " + chromeDriver);
        debug("chromeVersion: " + chromeVersion);
        debug("chromeDriverVersion: " + chromeDriverVersion);
        debug("chromeBuildVersion: " + chromeBuildVersion);
        debug("chromeDriverBuildVersion: " + chromeDriverBuildVersion);
        debug("chromeMilestone: " + chromeMilestone);
        debug("chromeDriverMilestone: " + chromeDriverMilestone);
        debug("updateLib: " + updateLibrary);
        debug("apiVer: " + apiVer);
        debug("apiBuild: " + apiBuild);
        debug("apiMilestone: " + apiMilestone);
        debug("uri: " + uri);
        debug("libEndpointK: " + libEndpointK);
        debug("libEndpoints: " + libEndpoints);
        debug("libEndpoint: " + libEndpoint);
        debug("endpoint: " + endpoint);
        debug("jsonGet: " + key);
        debug("jsonK: " + k);
        debug("jsonV: " + v);
        debug("jsonUrl: " + urlK);
        debug("root: " + root);
        debug("out: " + out);
        debug("libWrite: " + libWrite);
        return updateLibrary;
    }

    /**
     *
     * @return String
     */
    public static String endpoint() {
        return endpoint(c.getApiEndpoint());
    }

    /**
     *
     * @return String
     */
    public static String endpoint(String e) {
        return endpoint = e.replace(apiVer, chromeVersion);
    }

    /**
     *
     * @return String
     */
    public static String key() {
        return key(c.getJson());
    }

    /**
     *
     * @return String
     */
    public static String key(String k) {
        return key = k.replace(apiBuild, chromeBuildVersion).replace(apiMilestone + ".", chromeMilestone + ".");
    }

    /**
     *
     */
    public static void updateLib() throws Exception {
        updateLib(libWrite);
    }

    /**
     *
     * @param writeLibrary boolean
     */
    public static void updateLib(boolean writeLibrary) throws Exception {
        if (checkLib()) {
            debug("updateLib");
            if (writeLibrary) writeLib();
            else loadLib();
        }
    }

    /**
     *
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void loadLib() throws Exception {
        debug("loadLib");
        loadZip(uri, endpoint, key, k, v, urlK, root);
    }

    /**
     *
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void writeLib() throws Exception {
        debug("writeLib");
        unzip(uri, endpoint, key, k, v, urlK, out, root);
    }

}
