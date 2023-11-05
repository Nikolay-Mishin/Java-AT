package utils.fs;

import org.apache.commons.lang.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.base.Model;
import utils.constant.RequestConstants.METHOD_LOWER_CASE;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static config.WebConfig.BASE_CONFIG;
import static java.lang.System.out;
import static utils.Helper.isNull;
import static utils.Helper.toUpperCaseFirst;
import static utils.fs.FS.getPath;
import static utils.fs.FS.readFile;
import static utils.reflections.Reflection.getClassSimpleName;
import static utils.reflections.Reflection.invoke;

public class JsonSchema {

    private JSONObject jsonData;
    private JSONObject obj;
    private JSONArray arr;

    public JsonSchema(String jsonString) {
        setData(jsonString);
    }

    public JsonSchema() {}

    public JsonSchema path(METHOD_LOWER_CASE method, Class<? extends Model<?>> modelClass, Object... pathList) throws IOException {
        return _path(getJsonSchemaPath(method, modelClass, pathList), pathList);
    }

    public JsonSchema path(Class<? extends Model<?>> modelClass, Object... pathList) throws IOException {
        return _path(getJsonSchemaPath(modelClass, pathList), pathList);
    }

    public JsonSchema path(Object... pathList) throws IOException {
        return _path(getJsonSchemaPath(pathList), pathList);
    }

    public JSONObject data() {
        out.println(jsonData);
        return jsonData;
    }

    private void setData(String jsonString) {
        jsonData = new JSONObject(jsonString);
    }

    private JsonSchema _path(String path, Object... pathList) throws IOException {
        path +=  ".json";
        out.println(path);
        if (pathList.length > 0) setData(readFile(path));
        return this;
    }

    private static String getJsonSchemaPath(METHOD_LOWER_CASE method, Class<? extends Model<?>> modelClass, Object... pathList){
        return _getJsonSchemaPath(pathList, getJsonSchemaName(method, modelClass));
    }

    private static String getJsonSchemaPath(Class<? extends Model<?>> modelClass, Object... pathList){
        return _getJsonSchemaPath(pathList, getJsonSchemaName(modelClass));
    }

    private static String getJsonSchemaPath(Object... pathList) {
        return _getJsonSchemaPath(pathList);
    }

    private static String _getJsonSchemaPath(Object... pathList){
        return getPath(BASE_CONFIG.getJsonSchemaRoot(), pathList);
    }

    private static String getJsonSchemaName(METHOD_LOWER_CASE method, Class<? extends Model<?>> modelClass){
        return method + getJsonSchemaName(modelClass).toLowerCase();
    }

    private static String getJsonSchemaName(Class<? extends Model<?>> modelClass){
        return getClassSimpleName(modelClass);
    }

    public String[] keys() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return _keys(null);
    }

    public String[] keys(String path) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return _keys(path);
    }

    public <T> T get(String path) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return _get(path, "object");
    }

    public <T> T get(String path, String type) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return _get(path, type);
    }

    private String[] _keys(String path) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return (isNull(path) ? jsonData : (JSONObject) get(path)).keySet().toArray(String[]::new);
    }

    private <T> T _get(String path, String type) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        type = type.toLowerCase();
        String[] pathList = path.split("\\.");
        boolean isValue = pathList.length == 1;
        Object value = pathList[pathList.length - 1];
        pathList = (String[]) ArrayUtils.remove(pathList, pathList.length - 1);
        for (String key : pathList) object(key);
        value = invoke(isValue ? jsonData : obj, "get" + (type.equals("object") || type.equals("array") ? "JSON" : "") + toUpperCaseFirst(type), value);
        obj = null;
        return (T) value;
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
