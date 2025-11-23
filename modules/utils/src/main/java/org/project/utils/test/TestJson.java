package org.project.utils.test;

import org.json.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;

import static org.project.utils.Helper.*;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.fs.FS.*;
import static org.project.utils.json.JsonSchema.jsonSchema;

import org.project.utils.json.JsonSchema;
import org.project.utils.request.Request;

public class TestJson extends TestApi {

    public static void main(String[] args)
        throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, URISyntaxException, ClassNotFoundException, InstantiationException
    {
        testJson();
    }

    public static void testJson() throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        JsonSchema json = jsonSchema(endpoint, uri);
        Request req = json.req();

        debug(req.string());
        debug(req.pretty());
        debug(req.statusCode());

        String version = json.get("version", "string");
        JSONArray chromedriver = json.get("downloads.chromedriver", "array");
        Map<String, Object> map = json.toMap();

        debug(version);
        debug(chromedriver);
        debug(map);
        //debug(json.array("downloads.chromedriver"));

        debug(map.get("downloads"));
        debug(json.toMap("downloads"));

        List<JSONObject> list = json.toList("downloads.chromedriver", o -> o.get("platform").equals("win64"));
        debug(list);
        debug(json.toList("downloads.chromedriver", "platform", "win64"));

        List<JSONObject> list1 = JsonSchema.toList(chromedriver, o -> o.get("platform").equals("win64"));
        debug(list1);
        debug(JsonSchema.toList(chromedriver, "platform", "win64"));

        Map<String, Object> map0 = json.toMap("downloads.chromedriver", "platform", "win64");
        String url = (String) map0.get("url");

        debug(json.arrayToMap("downloads.chromedriver"));
        debug(json.toMap("downloads.chromedriver", o -> ((JSONObject) o).get("platform").equals("win64")));

        new Request(GET, "path", 1).uri("https://googlechromelabs.github.io/");

        debug(map0);
        debug(url);
        debug(path(url));
    }

}
