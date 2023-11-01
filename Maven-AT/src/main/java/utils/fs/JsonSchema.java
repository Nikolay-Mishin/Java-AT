package utils.fs;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.base.Model;
import utils.constant.RequestConstants.METHOD_LOWER_CASE;

import java.io.IOException;

import static config.WebConfig.BASE_CONFIG;
import static java.lang.System.out;
import static utils.Reflection.getClassSimpleName;
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

    public JSONObject path(METHOD_LOWER_CASE method, Class<? extends Model<?>> modelClass, Object... pathList) throws IOException {
        return _path(getJsonSchemaPath(method, modelClass, pathList), pathList);
    }

    public JSONObject path(Object... pathList) throws IOException {
        return _path(getJsonSchemaPath(pathList), pathList);
    }

    public JSONObject data() {
        out.println(jsonData);
        return jsonData;
    }

    private void setData(String jsonString) {
        jsonData = new JSONObject(jsonString);
    }

    private JSONObject _path(String path, Object... pathList) throws IOException {
        path +=  ".json";
        out.println(path);
        if (pathList.length > 0) setData(readFile(path));
        return data();
    }

    private static String getJsonSchemaPath(METHOD_LOWER_CASE method, Class<? extends Model<?>> modelClass, Object... pathList){
        return _getJsonSchemaPath(pathList, getJsonSchemaName(method, modelClass));
    }

    private static String getJsonSchemaPath(Object... pathList) {
        return _getJsonSchemaPath(pathList);
    }

    private static String _getJsonSchemaPath(Object... pathList){
        return getPath(BASE_CONFIG.getJsonSchemaRoot(), pathList);
    }

    private static String getJsonSchemaName(METHOD_LOWER_CASE method, Class<? extends Model<?>> modelClass){
        return method + getClassSimpleName(modelClass);
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
