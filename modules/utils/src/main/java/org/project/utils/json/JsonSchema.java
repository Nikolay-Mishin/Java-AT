package org.project.utils.json;

import static java.lang.String.valueOf;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.json.JSONArray;
import org.json.JSONObject;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.isNull;
import static org.project.utils.Helper.map;
import static org.project.utils.Helper.pop;
import static org.project.utils.Helper.toLowerCaseFirst;
import static org.project.utils.Helper.toUpperCaseFirst;
import static org.project.utils.config.WebConfig.config;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.fs.FS.readFile;
import static org.project.utils.fs.File.exist;
import static org.project.utils.reflection.Reflection.getClassSimpleName;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.base.HashMap;
import org.project.utils.constant.RequestConstants.METHOD_LOWER_CASE;
import org.project.utils.fs.FS;
import org.project.utils.Helper;
import org.project.utils.request.Request;

public class JsonSchema {

    private JSONObject jsonData;
    private JSONObject obj;
    private JSONArray arr;
    private Request req;
    private static String delimiter = "\\.";

    public JsonSchema() {}

    public JsonSchema(String jsonString) {
        data(jsonString);
    }

    public JsonSchema(Request req) throws ReflectiveOperationException {
        this.req = req;
        data(req.string());
    }

    public static JsonSchema jsonSchema(String endpoint)
        throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return new JsonSchema(new Request(GET, endpoint));
    }

    public static JsonSchema jsonSchema(String endpoint, String uri)
        throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return new JsonSchema(new Request(GET, endpoint).uri(uri));
    }

    public static JsonSchema jsonSchema(METHOD_LOWER_CASE method, Class<?> modelClass, Object... pathList) throws IOException {
        return new JsonSchema().path(method, modelClass, pathList);
    }

    public static JsonSchema jsonSchema(Class<?> modelClass, Object... pathList) throws IOException {
        return new JsonSchema().path(modelClass, pathList);
    }

    public static JsonSchema jsonSchema(Object... pathList) throws IOException {
        return new JsonSchema().path(pathList);
    }

    public static String delimiter() {
        return delimiter;
    }

    public static String delimiter(String s) {
        return delimiter = s;
    }

    public static List<List<Object>> toTable(JSONObject o) {
        return Helper.table(toMap(o));
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

    public static <T> List<T> toList(JSONArray a, Predicate<T> filter) {
        return Helper.toList(a, filter);
    }

    public Request req() {
        return req;
    }

    public JSONObject data() {
        return jsonData;
    }

    private void data(String jsonString) {
        //debug(jsonString);
        jsonData = new JSONObject(jsonString == "" ? "{}" : jsonString);
        debug(jsonData);
    }

    public JsonSchema path(METHOD_LOWER_CASE method, Class<?> modelClass, Object... pathList) throws IOException {
        return _path(method, modelClass, pathList);
    }

    public JsonSchema path(Class<?> modelClass, Object... pathList) throws IOException {
        return _path(modelClass, pathList);
    }

    public JsonSchema path(Object... pathList) throws IOException {
        return _path(pathList);
    }

    public List<List<Object>> toTable() {
        return toTable(jsonData);
    }

    public List<List<Object>> toTable(String k) throws ReflectiveOperationException {
        return Helper.table(toMap(k));
    }

    public List<List<Object>> arrayToTable(String k) throws ReflectiveOperationException {
        return Helper.table(arrayToMap(k));
    }

    public List<List<Object>> toTable(String key, String k, String v) throws ReflectiveOperationException {
        return Helper.table(toMap(key, k, v));
    }

    public <T> List<List<Object>> toTable(String k, Predicate<T> filter) throws ReflectiveOperationException {
        return Helper.table(toMap(k, filter));
    }

    public Map<String, Object> toMap() {
        return jsonData.toMap();
    }

    public Map<String, Object> toMap(String k) throws ReflectiveOperationException {
        return toMap((JSONObject) get(k));
    }

    public Map<String, Object> arrayToMap(String k) throws ReflectiveOperationException {
        return toObject(k).toMap();
    }

    public Map<String, Object> toMap(String key, String k, String v) throws ReflectiveOperationException {
        return toObject(key, k, v).toMap();
    }

    public <T> Map<String, Object> toMap(String k, Predicate<T> filter) throws ReflectiveOperationException {
        return toObject(k, filter).toMap();
    }

    protected JSONObject toObject(JSONArray a) {
        if (a.length() == 1) return (JSONObject) a.get(0);
        AtomicInteger i = new AtomicInteger();
        JSONArray names = new JSONArray(map(a.toList(), String[]::new, o -> valueOf(i.getAndIncrement())));
        return a.toJSONObject(names);
    }

    protected JSONObject toObject(String k) throws ReflectiveOperationException {
        return toObject((JSONArray) get(k, "array"));
    }

    protected JSONObject toObject(String key, String k, String v) throws ReflectiveOperationException {
        return toObject(toArray(key, k, v));
    }

    protected <T> JSONObject toObject(String k, Predicate<T> filter) throws ReflectiveOperationException {
        return toObject(toArray(k, filter));
    }

    public JSONArray toArray(String key, String k, String v) throws ReflectiveOperationException {
        return new JSONArray(toList(key, k, v));
    }

    public <T> JSONArray toArray(String k, Predicate<T> filter) throws ReflectiveOperationException {
        return new JSONArray(toList(k, filter));
    }

    public List<Object> toList(String k) throws ReflectiveOperationException {
        return toList((JSONArray) get(k, "array"));
    }

    public List<JSONObject> toList(String key, String k, String v) throws ReflectiveOperationException {
        return toList(key, o -> o.get(k).equals(v));
    }

    public <T> List<T> toList(String k, Predicate<T> filter) throws ReflectiveOperationException {
        return toList((JSONArray) get(k, "array"), filter);
    }

    private JsonSchema _path(METHOD_LOWER_CASE method, Class<?> modelClass, Object... pathList) throws IOException {
        return _path(pathList, jsonSchemaName(method, modelClass));
    }

    private JsonSchema _path(Class<?> modelClass, Object... pathList) throws IOException {
        return _path(pathList, jsonSchemaName(modelClass));
    }

    private JsonSchema _path(Object... pathList) throws IOException {
        String path = FS.path(config().getJsonRoot(), pathList) + ".json";
        debug(Arrays.toString(pathList));
        debug(path);
        if (exist(path)) data(readFile(path));
        return this;
    }

    private static String jsonSchemaName(METHOD_LOWER_CASE method, Class<?> modelClass) {
        return method + toUpperCaseFirst(jsonSchemaName(modelClass));
    }

    private static String jsonSchemaName(Class<?> modelClass) {
        return toLowerCaseFirst(getClassSimpleName(modelClass));
    }

    public String[] keys() throws ReflectiveOperationException {
        return _keys(null);
    }

    public String[] keys(String path) throws ReflectiveOperationException {
        return _keys(path);
    }

    public JSONObject get(String path) throws ReflectiveOperationException {
        return _get(path, "object");
    }

    public JSONArray getArray(String path) throws ReflectiveOperationException {
        return _get(path, "array");
    }

    public String string(String path) throws ReflectiveOperationException {
        return _get(path, "string");
    }

    public boolean getBoolean(String path) throws ReflectiveOperationException {
        return _get(path, "boolean");
    }

    public int getInt(String path) throws ReflectiveOperationException {
        return _get(path, "int");
    }

    public long getLong(String path) throws ReflectiveOperationException {
        return _get(path, "long");
    }

    public float getFloat(String path) throws ReflectiveOperationException {
        return _get(path, "float");
    }

    public Number number(String path) throws ReflectiveOperationException {
        return _get(path, "number");
    }

    public double getDouble(String path) throws ReflectiveOperationException {
        return _get(path, "double");
    }

    public BigInteger bigInt(String path) throws ReflectiveOperationException {
        return _get(path, "bigInteger");
    }

    public <T> T bigDec(String path) throws ReflectiveOperationException {
        return _get(path, "bigDecimal");
    }

    public <T> T get(String path, String type) throws ReflectiveOperationException {
        return _get(path, type);
    }

    public <E extends Enum<E>> E getEnum(Class<E> clazz, String path) {
        return jsonData.getEnum(clazz, path);
    }

    private String[] _keys(String path) throws ReflectiveOperationException {
        return HashMap.keys(isNull(path) ? jsonData : get(path), String[]::new);
    }

    @SuppressWarnings("unchecked")
    private <T> T _get(String path, String type) throws ReflectiveOperationException {
        //type = type.toLowerCase();
        String[] pathList = parsePath(path);
        boolean isValue = pathList.length == 1;
        T value = (T) pathList[pathList.length - 1];
        pathList = pop(pathList);
        for (String key : pathList) object(key);
        value = invoke(isValue ? jsonData : obj, "get" + (type.equals("object") || type.equals("array") ? "JSON" : "") + toUpperCaseFirst(type), value);
        obj = null;
        return value;
    }

    public static String[] parsePath(String path) {
        return path.split(delimiter);
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
