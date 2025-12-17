package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

import org.project.utils.config.TestBaseConfig;
import org.project.utils.json.JsonSchema;
import org.project.utils.request.Request;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestJson<T extends TestBaseConfig> extends TestApi<T> {
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
    public TestJson() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("TestJson:init");
        jsonVer = c.getJsonVer();
        jsonDownloads = c.getJsonDownloads();
        jsonGet = c.getJson();
        jsonK = c.getJsonK();
        jsonV = c.getJsonV();
        jsonUrl = c.getJsonUrl();
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
     * @return Map {String, Object}
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Map<String, Object> map(String uri, String endpoint, String key, String k, String v)
        throws MalformedURLException, URISyntaxException, ReflectiveOperationException
    {
        setReq(uri, endpoint);
        return map = json.toMap(key, k, v);
    }

    /**
     *
     * @param endpoint String
     * @param uri String
     * @param key String
     * @param k String
     * @param v String
     * @param urlPath String
     * @return String
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static String url(String uri, String endpoint, String key, String k, String v, String urlPath)
        throws MalformedURLException, URISyntaxException, ReflectiveOperationException
    {
        map(uri, endpoint, key, k, v);
        url = (String) map.get(urlPath);
        debug(url);
        return url;
    }

    /**
     *
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void testJson() throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testJson");
        map(uri, endpoint);

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

        debug(table(map));

        url(uri, endpoint, jsonGet, jsonK, jsonV, jsonUrl);

        debug(json.arrayToMap(jsonGet));
        debug(json.toMap(jsonGet, o -> ((JSONObject) o).get(jsonK).equals(jsonV)));

        debug(table(map));
        debug(json.toTable());
        debug(json.arrayToTable(jsonGet));
        debug(json.toTable(jsonGet, jsonK, jsonV));
        debug(json.toTable(jsonGet, o -> ((JSONObject) o).get(jsonK).equals(jsonV)));

        setReq(uri, GET, "path", 1);

        debug(map);
        debug(path(url));
    }

}
