package utils.fs;

import org.json.*;

import java.io.IOException;

import static java.lang.System.out;
import static utils.fs.FS.readFile;

public class JsonParser {

    private JSONObject jsonData;
    private JSONObject obj;
    private JSONArray arr;

    public JsonParser(String jsonString) {
        setData(jsonString);
    }

    public JsonParser() {}

    private void setData(String jsonString) {
        jsonData = new JSONObject(jsonString);
    }

    public JSONObject path(String path) throws IOException {
        setData(readFile(path));
        return data();
    }

    public JSONObject data() {
        out.println(jsonData);
        return jsonData;
    }

    public JsonParser object(String key) {
        obj = (obj == null ? jsonData : obj).getJSONObject(key);
        return this;
    }

    public JsonParser array(String key) {
        arr = arr == null ? jsonData.getJSONArray(key) : arr;
        return this;
    }

    public JsonParser array(int key) {
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
