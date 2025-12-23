package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.table;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.fs.FS.path;
import static org.project.utils.json.JsonSchema.jsonSchema;
import static org.project.utils.json.JsonSchema.toList;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.json.JsonSchema;
import org.project.utils.request.Request;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestJson<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestApi<T, W, D> {
    /**
     *
     */
    protected static JsonSchema json;
    /**
     *
     */
    protected static Map<String, Object> map;
    /**
     *
     */
    protected static String jsonVer;
    /**
     *
     */
    protected static String jsonDownloads;
    /**
     *
     */
    protected static String jsonDownloadsK;
    /**
     *
     */
    protected static String jsonGet;
    /**
     *
     */
    protected static String jsonK;
    /**
     *
     */
    protected static String jsonV;
    /**
     *
     */
    protected static String jsonUrl;

    /**
     *
     */
    @ConstructorProperties({})
    public TestJson() {
        debug("TestJson:init");
        jsonVer = win.getJsonVer();
        jsonDownloads = win.getJsonDownloads();
        jsonDownloadsK = win.getJsonDownloadsK();
        jsonGet = win.getJson();
        jsonK = win.getJsonK();
        jsonV = win.getJsonV();
        jsonUrl = win.getUrlK();
    }

    /**
     *
     * @param uri String
     * @param pathList Object[]
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request setReq(String uri, Object... pathList) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return req = json(uri, pathList).getReq();
    }

    /**
     *
     * @param uri String
     * @param pathList Object[]
     * @return JsonSchema
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static JsonSchema json(String uri, Object... pathList) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return json = jsonSchema(uri, pathList);
    }

    /**
     *
     * @param endpoint String
     * @param uri String
     * @return Map {String, Object}
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Map<String, Object> map(String uri, String endpoint) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        setReq(uri, endpoint);
        return map = json.toMap();
    }

    /**
     *
     * @param endpoint String
     * @param uri String
     * @param key String
     * @param k String
     * @param v String
     * @param urlK String
     * @return String
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static String loadJson(String uri, String endpoint, String key, String k, String v, String urlK)
        throws IOException, URISyntaxException, ReflectiveOperationException
    {
        debug("loadJson");
        return setJson(JsonSchema.loadJson(uri, endpoint, key, k, v, urlK));
    }

    /**
     *
     * @param jsonO JsonSchema
     * @return String
     */
    public static String setJson(JsonSchema jsonO) {
        debug("setJson");
        json = jsonO;
        req = json.getReq();
        map = json.getMap();
        return url = json.getUrl();
    }

    /**
     *
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void testJson() throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testJson");
        loadJson(uri, endpointVer, jsonGet, jsonK, jsonV, jsonUrl);
        map = json.toMap();

        debug(req.string());
        debug(req.pretty());
        debug(req.statusCode());

        String version = json.string(jsonVer);
        JSONArray chromedriver = json.getArray(jsonGet);

        debug(version);
        debug(chromedriver);
        debug(map);

        debug(map.get(jsonDownloads));
        debug(json.toMap(jsonDownloads));

        List<JSONObject> list = json.toList(jsonGet, o -> o.get(jsonK).equals(jsonV));
        debug(list);
        debug(json.toList(jsonGet, jsonK, jsonV));

        List<JSONObject> list1 = toList(chromedriver, o -> o.get(jsonK).equals(jsonV));
        debug(list1);
        debug(toList(chromedriver, jsonK, jsonV));

        debug("tableMap: " + table(map));

        map = json.getMap();

        debug("tableMap: " + table(map));

        debug(json.arrayToMap(jsonGet));
        debug(json.toMap(jsonGet, o -> ((JSONObject) o).get(jsonK).equals(jsonV)));

        debug(json.toTable());
        debug(json.arrayToTable(jsonGet));
        debug(json.toTable(jsonGet, jsonK, jsonV));
        debug(json.toTable(jsonGet, o -> ((JSONObject) o).get(jsonK).equals(jsonV)));

        setReq(uri, GET, "path", 1);

        debug(map);
        debug(path(url));
    }

}
