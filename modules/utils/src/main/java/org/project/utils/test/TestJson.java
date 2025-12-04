package org.project.utils.test;

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

import org.project.utils.json.JsonSchema;
import org.project.utils.request.Request;

public class TestJson extends TestApi {
    protected static JsonSchema json;
    protected static Request req;
    protected static Map<String, Object> map;
    protected static String url;
    protected static String jsonVer = c.getJsonVer();
    protected static String jsonDownloads = c.getJsonDownloads();
    protected static String jsonGet = c.getJson();
    protected static String jsonK = c.getJsonK();
    protected static String jsonV = c.getJsonV();
    protected static String jsonUrl = c.getJsonUrl();

    public static void main(String[] args) throws Exception {
        testJson();
    }

    public static JsonSchema json(String endpoint, String uri) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return json = jsonSchema(endpoint, uri);
    }

    public static Request req(String endpoint, String uri) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        json(endpoint, uri);
        return req = json.req();
    }

    public static Map<String, Object> map(String endpoint, String uri) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        req(endpoint, uri);
        return map = json.toMap();
    }

    public static Map<String, Object> map(String endpoint, String uri, String key, String k, String v)
        throws MalformedURLException, URISyntaxException, ReflectiveOperationException
    {
        req(endpoint, uri);
        return map = json.toMap(key, k, v);
    }

    public static String url(String endpoint, String uri, String key, String k, String v, String urlPath)
        throws MalformedURLException, URISyntaxException, ReflectiveOperationException
    {
        map(endpoint, uri, key, k, v);
        url = (String) map.get(urlPath);
        debug(url);
        return url;
    }

    public static void testJson() throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testJson");
        map(endpoint, uri);

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

        map = json.toMap(jsonGet, jsonK, jsonV);
        url = (String) map.get(jsonUrl);

        debug(json.arrayToMap(jsonGet));
        debug(json.toMap(jsonGet, o -> ((JSONObject) o).get(jsonK).equals(jsonV)));

        debug(table(map));
        debug(json.toTable());
        debug(json.arrayToTable(jsonGet));
        debug(json.toTable(jsonGet, jsonK, jsonV));
        debug(json.toTable(jsonGet, o -> ((JSONObject) o).get(jsonK).equals(jsonV)));

        new Request(GET, "path", 1).uri(uri);

        debug(map);
        debug(path(url));
    }

}
