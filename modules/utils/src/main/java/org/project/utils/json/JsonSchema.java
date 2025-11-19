package org.project.utils.json;

import org.json.JSONArray;
import org.json.JSONObject;
import org.project.utils.Helper;
import org.project.utils.base.HashMap;
import org.project.utils.base.Model;
import org.project.utils.constant.RequestConstants.METHOD_LOWER_CASE;
import org.project.utils.fs.FS;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.project.utils.Helper.*;
import static org.project.utils.Helper.toList;
import static org.project.utils.config.WebConfig.config;
import static org.project.utils.fs.FS.readFile;
import static org.project.utils.reflection.Reflection.getClassSimpleName;
import static org.project.utils.reflection.Reflection.invoke;

public class JsonSchema {

    private JSONObject jsonData;
    private JSONObject obj;
    private JSONArray arr;

    public JsonSchema(String jsonString) {
        data(jsonString);
    }

    public JsonSchema() {
    }

    public static Map<String, Object> toMap(JSONObject o) {
        return o.toMap();
    }

    public static List<Object> toList(JSONArray a) {
        return a.toList();
    }

    public static List<JSONObject> toList(JSONArray a, String k, String v) {
        return toList(a, o -> o.get(k).equals(v));
    }

    public static <T> List<T> toList(JSONArray a, Predicate<? super T> filter) {
        return Helper.toList(a, filter);
    }

    public JsonSchema path(METHOD_LOWER_CASE method, Class<?> modelClass, Object... pathList) throws IOException {
        return _path(jsonSchemaPath(method, modelClass, pathList), pathList);
    }

    public JsonSchema path(Class<?> modelClass, Object... pathList) throws IOException {
        return _path(jsonSchemaPath(modelClass, pathList), pathList);
    }

    public JsonSchema path(Object... pathList) throws IOException {
        return _path(jsonSchemaPath(pathList), pathList);
    }

    public JSONObject data() {
        return jsonData;
    }

    private void data(String jsonString) {
        jsonData = new JSONObject(jsonString);
    }

    public Map<String, Object> toMap() {
        return jsonData.toMap();
    }

    public Map<String, Object> toMap(String k) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return toMap((JSONObject) get(k));
    }

    public List<Object> toList(String k) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return toList((JSONArray) get(k, "array"));
    }

    public List<JSONObject> toList(String key, String k, String v) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return toList(key, o -> o.get(k).equals(v));
    }

    public <T> List<T> toList(String k, Predicate<? super T> filter) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return toList((JSONArray) get(k, "array"), filter);
    }

    private JsonSchema _path(String path, Object... pathList) throws IOException {
        path +=  ".json";
        debug(path);
        if (pathList.length > 0) data(readFile(path));
        return this;
    }

    private static String jsonSchemaPath(METHOD_LOWER_CASE method, Class<?> modelClass, Object... pathList){
        return _jsonSchemaPath(pathList, jsonSchemaName(method, modelClass));
    }

    private static String jsonSchemaPath(Class<? extends Model<?>> modelClass, Object... pathList){
        return _jsonSchemaPath(pathList, jsonSchemaName(modelClass));
    }

    private static String jsonSchemaPath(Object... pathList) {
        return _jsonSchemaPath(pathList);
    }

    private static String _jsonSchemaPath(Object... pathList){
        return FS.path(config().getJsonRoot(), pathList);
    }

    private static String jsonSchemaName(METHOD_LOWER_CASE method, Class<?> modelClass){
        return method + jsonSchemaName(modelClass).toLowerCase();
    }

    private static String jsonSchemaName(Class<?> modelClass){
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
        return HashMap.keys(isNull(path) ? jsonData : get(path), String[]::new);
    }

    @SuppressWarnings("unchecked")
    private <T> T _get(String path, String type) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        type = type.toLowerCase();
        String[] pathList = parsePath(path);
        boolean isValue = pathList.length == 1;
        Object value = pathList[pathList.length - 1];
        pathList = removeLast(pathList);
        for (String key : pathList) object(key);
        value = invoke(isValue ? jsonData : obj, "get" + (type.equals("object") || type.equals("array") ? "JSON" : "") + toUpperCaseFirst(type), value);
        obj = null;
        return (T) value;
    }

    public static String[] parsePath(String path) {
        return path.split("\\.");
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
