package utils.fs;

import org.json.*;

import java.io.IOException;

import static config.WebConfig.BASE_CONFIG;
import static java.lang.System.out;
import static utils.fs.FS.getPath;
import static utils.fs.FS.readFile;

public class JsonSchema {

    private JSONObject jsonData;
    private JSONObject obj;
    private JSONArray arr;

    public JsonSchema(String jsonString) {
        setData(jsonString);
    }

    public JsonSchema() {}

    private void setData(String jsonString) {
        jsonData = new JSONObject(jsonString);
    }

    public static String getJsonSchemaPath(Object... pathList){
        return getPath(BASE_CONFIG.getJsonSchemaRoot(), pathList);
    }

    public JSONObject path(Object... pathList) throws IOException {
        String path = getPath(pathList) + ".json";
        out.println(path);
        setData(readFile(path));
        return data();
    }

    public JSONObject data() {
        out.println(jsonData);
        return jsonData;
    }

    public JsonSchema object(String key) {
        obj = (obj == null ? jsonData : obj).getJSONObject(key);
        return this;
    }

    public JsonSchema array(String key) {
        arr = arr == null ? jsonData.getJSONArray(key) : arr;
        return this;
    }

    public JsonSchema array(int key) {
        arr = arr.getJSONArray(key);
        return this;
    }

    public JSONArray parseExample() {
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");
        JSONArray arr = obj.getJSONArray("posts"); // notice that `"posts": [...]`
        for (int i = 0; i < arr.length(); i++) {
            String post_id = arr.getJSONObject(i).getString("post_id");
        }
        return arr;
    }

}
