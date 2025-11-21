package org.project.utils.json;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.json.*;

import static org.project.utils.Helper.*;
import static org.project.utils.config.WebConfig.config;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.fs.FS.readFile;
import static org.project.utils.reflection.Reflection.*;

import org.project.utils.base.HashMap;
import org.project.utils.base.Model;
import org.project.utils.constant.RequestConstants.METHOD_LOWER_CASE;
import org.project.utils.fs.FS;
import org.project.utils.Helper;
import org.project.utils.request.Request;

public class JsonSchema {

    private JSONObject jsonData;
    private JSONObject obj;
    private JSONArray arr;
    private Request req;

    public JsonSchema() {}

    public JsonSchema(String jsonString) {
        data(jsonString);
    }

    public JsonSchema(Request req) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        this.req = req;
        data(req.string());
    }

    public Request req() {
        return req;
    }

    public static JsonSchema jsonSchema(String endpoint)
        throws MalformedURLException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        return new JsonSchema(new Request(GET, endpoint));
    }

    public static JsonSchema jsonSchema(String endpoint, String uri)
        throws MalformedURLException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        return new JsonSchema(new Request(GET, endpoint).uri(uri));
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

    public Map<String, Object> arrayToMap(String k) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return toObject(k).toMap();
    }

    public Map<String, Object> toMap(String key, String k, String v) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return toObject(key, k, v).toMap();
    }

    public <T> Map<String, Object> toMap(String k, Predicate<? super T> filter) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return toObject(k, filter).toMap();
    }

    protected JSONObject toObject(JSONArray a) {
        if (a.length() == 1) return (JSONObject) a.get(0);
        AtomicInteger i = new AtomicInteger();
        JSONArray names = new JSONArray(Helper.toArray(a.toList(), String[]::new, o -> String.valueOf(i.getAndIncrement())));
        return a.toJSONObject(names);
    }

    protected JSONObject toObject(String k) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return toObject((JSONArray) get(k, "array"));
    }

    protected JSONObject toObject(String key, String k, String v) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return toObject(toArray(key, k, v));
    }

    protected <T> JSONObject toObject(String k, Predicate<? super T> filter) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return toObject(toArray(k, filter));
    }

    public JSONArray toArray(String key, String k, String v) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return new JSONArray(toList(key, k, v));
    }

    public <T> JSONArray toArray(String k, Predicate<? super T> filter) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return new JSONArray(toList(k, filter));
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
