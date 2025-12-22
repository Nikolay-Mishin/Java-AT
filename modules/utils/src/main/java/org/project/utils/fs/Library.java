package org.project.utils.fs;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.DriverConfig.config;
import static org.project.utils.fs.Version.getVersion;
import static org.project.utils.fs.Zip.loadZip;
import static org.project.utils.json.JsonSchema.loadJson;

import org.project.utils.config.DriverBaseConfig;

/**
 *
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
    protected static String chromeDriverVersion = getVersion(chromeDriver);
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
    protected static String uri = c.getApiUri();
    /**
     *
     */
    protected static String endpoint = c.getApiEndpoint();
    /**
     *
     */
    protected static String key = c.getJson();
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
    public static boolean checkLibrary() {
        debug("checkLibrary");
        debug("chrome: " + chrome);
        debug("chromeDriver: " + chromeDriver);
        debug("chrome: " + chromeVersion);
        debug("chromeDriver: " + chromeDriverVersion);
        debug("updateLibrary: " + updateLibrary);
        debug("apiVer: " + apiVer);
        debug("uri: " + uri);
        debug("endpoint: " + endpoint());
        debug("jsonGet: " + key);
        debug("jsonK: " + k);
        debug("jsonV: " + v);
        debug("jsonUrl: " + urlK);
        debug("root: " + root);
        debug("out: " + out);
        return updateLibrary;
    }

    /**
     *
     * @return String
     */
    public static String endpoint() {
        return endpoint = endpoint.replace(apiVer, chromeVersion);
    }

    /**
     *
     */
    public static void updateLibrary() {
        if (checkLibrary()) {
            debug("updateLibrary");
        }
    }

    /**
     *
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void loadLibrary()
        throws ReflectiveOperationException, IOException, URISyntaxException
    {
        debug("loadLibrary");
        loadJson(uri, endpoint, key, k, v, urlK);
    }

    /**
     *
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void writeLibrary()
        throws ReflectiveOperationException, IOException, URISyntaxException
    {
        debug("writeLibrary");
        loadZip(uri, endpoint, key, k, v, urlK, out, root);
    }

}
