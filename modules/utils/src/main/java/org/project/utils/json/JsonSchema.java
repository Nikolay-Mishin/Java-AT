package org.project.utils.json;

import static java.lang.String.valueOf;

import java.beans.ConstructorProperties;
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

import static org.project.utils.Helper._equals;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.isNull;
import static org.project.utils.Helper.map;
import static org.project.utils.Helper.pop;
import static org.project.utils.Helper.sb;
import static org.project.utils.Helper.toLowerCaseFirst;
import static org.project.utils.Helper.toUpperCaseFirst;
import static org.project.utils.config.WebConfig.config;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.fs.FS.readFile;
import static org.project.utils.fs.File.exist;
import static org.project.utils.reflection.Reflection.getClassSimpleName;
import static org.project.utils.reflection.Reflection.invoke;
import static org.project.utils.request.Request.paramsStr;

import org.project.utils.base.HashMap;
import org.project.utils.constant.RequestConstants.METHOD_LOWER_CASE;
import org.project.utils.fs.FS;
import org.project.utils.Helper;
import org.project.utils.request.Request;

/**
 *
 */
public class JsonSchema {
    /**
     *
     */
    private JSONObject jsonData;
    /**
     *
     */
    private JSONObject obj;
    /**
     *
     */
    private JSONArray arr;
    /**
     *
     */
    private Request req;
    /**
     *
     */
    private static String delimiter = "\\.";

    /**
     *
     */
    @ConstructorProperties({})
    public JsonSchema() {}

    /**
     *
     * @param jsonString String
     */
    @ConstructorProperties({"jsonString"})
    public JsonSchema(String jsonString) {
        data(jsonString);
    }

    /**
     *
     * @param req Request
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"req"})
    public JsonSchema(Request req) throws ReflectiveOperationException {
        this.req = req;
        data(req.string());
    }

    /**
     *
     * @param args Object[]
     */
    @ConstructorProperties({"args"})
    public JsonSchema(Object... args) {
        data(paramsStr("{\n", "\n}", ",\n", a -> sb("    \"", a, "\""), (a, sep) -> sb(": \"", a, "\"" + sep), args));
    }

    /**
     *
     * @param endpoint String
     * @return JsonSchema
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static JsonSchema jsonSchema(String endpoint) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return new JsonSchema(new Request(GET, endpoint));
    }

    /**
     *
     * @param endpoint String
     * @param uri String
     * @return JsonSchema
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static JsonSchema jsonSchema(String endpoint, String uri) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return new JsonSchema(new Request(GET, endpoint).uri(uri));
    }

    /**
     *
     * @param method METHOD_LOWER_CASE
     * @param modelClass Class
     * @param pathList Object[]
     * @return JsonSchema
     * @throws IOException throws
     */
    public static JsonSchema jsonSchema(METHOD_LOWER_CASE method, Class<?> modelClass, Object... pathList) throws IOException {
        return new JsonSchema().path(method, modelClass, pathList);
    }

    /**
     *
     * @param modelClass Class
     * @param pathList Object[]
     * @return JsonSchema
     * @throws IOException throws
     */
    public static JsonSchema jsonSchema(Class<?> modelClass, Object... pathList) throws IOException {
        return new JsonSchema().path(modelClass, pathList);
    }

    /**
     *
     * @param pathList Object[]
     * @return JsonSchema
     * @throws IOException throws
     */
    public static JsonSchema jsonSchema(Object... pathList) throws IOException {
        return new JsonSchema().path(pathList);
    }

    /**
     *
     * @param args Object[]
     * @return JsonSchema
     */
    public static JsonSchema jsonSchemaArgs(Object... args) {
        return new JsonSchema(args);
    }

    /**
     *
     * @return String
     */
    public static String delimiter() {
        return delimiter;
    }

    /**
     *
     * @param s String
     * @return String
     */
    public static String delimiter(String s) {
        return delimiter = s;
    }

    /**
     *
     * @param o JSONObject
     * @return List {List {Object}}
     */
    public static List<List<Object>> toTable(JSONObject o) {
        return Helper.table(toMap(o));
    }

    /**
     *
     * @param o JSONObject
     * @return Map {String, Object}
     */
    public static Map<String, Object> toMap(JSONObject o) {
        return o.toMap();
    }

    /**
     *
     * @param args Object[]
     * @return Map {String, Object}
     */
    public static Map<String, Object> toMap(Object... args) {
        return jsonSchemaArgs(args).toMap();
    }

    /**
     *
     * @param a JSONArray
     * @return List {Object}
     */
    public static List<Object> toList(JSONArray a) {
        return a.toList();
    }

    /**
     *
     * @param a JSONArray
     * @param k String
     * @param v String
     * @return List {JSONObject}
     */
    public static List<JSONObject> toList(JSONArray a, String k, String v) {
        return toList(a, o -> o.get(k).equals(v));
    }

    /**
     *
     * @param a JSONArray
     * @param filter Predicate
     * @return List {T}
     * @param <T> T
     */
    public static <T> List<T> toList(JSONArray a, Predicate<T> filter) {
        return Helper.toList(a, filter);
    }

    /**
     *
     * @return Request
     */
    public Request req() {
        return req;
    }

    /**
     *
     * @return JSONObject
     */
    public JSONObject data() {
        return jsonData;
    }

    /**
     *
     * @param jsonString String
     */
    private void data(String jsonString) {
        //debug(jsonString);
        jsonData = new JSONObject(_equals(jsonString, "") ? "{}" : jsonString);
        debug(jsonData);
    }

    /**
     *
     * @param method METHOD_LOWER_CASE
     * @param modelClass Class
     * @param pathList Object[]
     * @return JsonSchema
     * @throws IOException throws
     */
    public JsonSchema path(METHOD_LOWER_CASE method, Class<?> modelClass, Object... pathList) throws IOException {
        return _path(method, modelClass, pathList);
    }

    /**
     *
     * @param modelClass Class
     * @param pathList Object[]
     * @return JsonSchema
     * @throws IOException throws
     */
    public JsonSchema path(Class<?> modelClass, Object... pathList) throws IOException {
        return _path(modelClass, pathList);
    }

    /**
     *
     * @param pathList Object[]
     * @return JsonSchema
     * @throws IOException throws
     */
    public JsonSchema path(Object... pathList) throws IOException {
        return _path(pathList);
    }

    /**
     *
     * @return List {List {Object}}
     */
    public List<List<Object>> toTable() {
        return toTable(jsonData);
    }

    /**
     *
     * @param k String
     * @return List {List {Object}}
     * @throws ReflectiveOperationException throws
     */
    public List<List<Object>> toTable(String k) throws ReflectiveOperationException {
        return Helper.table(toMap(k));
    }

    /**
     *
     * @param k String
     * @return List {List {Object}}
     * @throws ReflectiveOperationException throws
     */
    public List<List<Object>> arrayToTable(String k) throws ReflectiveOperationException {
        return Helper.table(arrayToMap(k));
    }

    /**
     *
     * @param key String
     * @param k String
     * @param v String
     * @return List {List {Object}}
     * @throws ReflectiveOperationException throws
     */
    public List<List<Object>> toTable(String key, String k, String v) throws ReflectiveOperationException {
        return Helper.table(toMap(key, k, v));
    }

    /**
     *
     * @param k String
     * @param filter Predicate
     * @return List {List {Object}}
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public <T> List<List<Object>> toTable(String k, Predicate<T> filter) throws ReflectiveOperationException {
        return Helper.table(toMap(k, filter));
    }

    /**
     *
     * @return Map {String, Object}
     */
    public Map<String, Object> toMap() {
        return jsonData.toMap();
    }

    /**
     *
     * @param k String
     * @return Map {String, Object}
     * @throws ReflectiveOperationException throws
     */
    public Map<String, Object> toMap(String k) throws ReflectiveOperationException {
        return toMap(get(k));
    }

    /**
     *
     * @param k String
     * @return Map {String, Object}
     * @throws ReflectiveOperationException throws
     */
    public Map<String, Object> arrayToMap(String k) throws ReflectiveOperationException {
        return toObject(k).toMap();
    }

    /**
     *
     * @param key String
     * @param k String
     * @param v String
     * @return Map {String, Object}
     * @throws ReflectiveOperationException throws
     */
    public Map<String, Object> toMap(String key, String k, String v) throws ReflectiveOperationException {
        return toObject(key, k, v).toMap();
    }

    /**
     *
     * @param k String
     * @param filter Predicate
     * @return Map {String, Object}
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public <T> Map<String, Object> toMap(String k, Predicate<T> filter) throws ReflectiveOperationException {
        return toObject(k, filter).toMap();
    }

    /**
     *
     * @param a JSONArray
     * @return JSONObject
     */
    protected JSONObject toObject(JSONArray a) {
        if (a.length() == 1) return (JSONObject) a.get(0);
        AtomicInteger i = new AtomicInteger();
        JSONArray names = new JSONArray(map(a.toList(), String[]::new, o -> valueOf(i.getAndIncrement())));
        return a.toJSONObject(names);
    }

    /**
     *
     * @param k String
     * @return JSONObject
     * @throws ReflectiveOperationException throws
     */
    protected JSONObject toObject(String k) throws ReflectiveOperationException {
        return toObject((JSONArray) get(k, "array"));
    }

    /**
     *
     * @param key String
     * @param k String
     * @param v String
     * @return JSONObject
     * @throws ReflectiveOperationException throws
     */
    protected JSONObject toObject(String key, String k, String v) throws ReflectiveOperationException {
        return toObject(toArray(key, k, v));
    }

    /**
     *
     * @param k String
     * @param filter Predicate
     * @return JSONObject
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    protected <T> JSONObject toObject(String k, Predicate<T> filter) throws ReflectiveOperationException {
        return toObject(toArray(k, filter));
    }

    /**
     *
     * @param key String
     * @param k String
     * @param v String
     * @return JSONArray
     * @throws ReflectiveOperationException throws
     */
    public JSONArray toArray(String key, String k, String v) throws ReflectiveOperationException {
        return new JSONArray(toList(key, k, v));
    }

    /**
     *
     * @param k String
     * @param filter Predicate
     * @return JSONArray
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public <T> JSONArray toArray(String k, Predicate<T> filter) throws ReflectiveOperationException {
        return new JSONArray(toList(k, filter));
    }

    /**
     *
     * @param k String
     * @return List {Object}
     * @throws ReflectiveOperationException throws
     */
    public List<Object> toList(String k) throws ReflectiveOperationException {
        return toList((JSONArray) get(k, "array"));
    }

    /**
     *
     * @param key String
     * @param k String
     * @param v String
     * @return List {JSONObject}
     * @throws ReflectiveOperationException throws
     */
    public List<JSONObject> toList(String key, String k, String v) throws ReflectiveOperationException {
        return toList(key, o -> o.get(k).equals(v));
    }

    /**
     *
     * @param k String
     * @param filter Predicate
     * @return List {T}
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public <T> List<T> toList(String k, Predicate<T> filter) throws ReflectiveOperationException {
        return toList((JSONArray) get(k, "array"), filter);
    }

    /**
     *
     * @param method METHOD_LOWER_CASE
     * @param modelClass Class
     * @param pathList Object[]
     * @return JsonSchema
     * @throws IOException throws
     */
    private JsonSchema _path(METHOD_LOWER_CASE method, Class<?> modelClass, Object... pathList) throws IOException {
        return _path(pathList, jsonSchemaName(method, modelClass));
    }

    /**
     *
     * @param modelClass Class
     * @param pathList Object[]
     * @return JsonSchema
     * @throws IOException throws
     */
    private JsonSchema _path(Class<?> modelClass, Object... pathList) throws IOException {
        return _path(pathList, jsonSchemaName(modelClass));
    }

    /**
     *
     * @param pathList Object[]
     * @return JsonSchema
     * @throws IOException throws
     */
    private JsonSchema _path(Object... pathList) throws IOException {
        String path = FS.path(config().getJsonRoot(), pathList) + ".json";
        debug(Arrays.toString(pathList));
        debug(path);
        if (exist(path)) data(readFile(path));
        return this;
    }

    /**
     *
     * @param method METHOD_LOWER_CASE
     * @param modelClass Class
     * @return String
     */
    private static String jsonSchemaName(METHOD_LOWER_CASE method, Class<?> modelClass) {
        return method + toUpperCaseFirst(jsonSchemaName(modelClass));
    }

    /**
     *
     * @param modelClass Class
     * @return String
     */
    private static String jsonSchemaName(Class<?> modelClass) {
        return toLowerCaseFirst(getClassSimpleName(modelClass));
    }

    /**
     *
     * @return String[]
     * @throws ReflectiveOperationException throws
     */
    public String[] keys() throws ReflectiveOperationException {
        return _keys(null);
    }

    /**
     *
     * @param path String
     * @return String[]
     * @throws ReflectiveOperationException throws
     */
    public String[] keys(String path) throws ReflectiveOperationException {
        return _keys(path);
    }

    /**
     *
     * @param path String
     * @return JSONObject
     * @throws ReflectiveOperationException throws
     */
    public JSONObject get(String path) throws ReflectiveOperationException {
        return _get(path, "object");
    }

    /**
     *
     * @param path String
     * @return JSONArray
     * @throws ReflectiveOperationException throws
     */
    public JSONArray getArray(String path) throws ReflectiveOperationException {
        return _get(path, "array");
    }

    /**
     *
     * @param path String
     * @return String
     * @throws ReflectiveOperationException throws
     */
    public String string(String path) throws ReflectiveOperationException {
        return _get(path, "string");
    }

    /**
     *
     * @param path String
     * @return boolean
     * @throws ReflectiveOperationException throws
     */
    public boolean getBoolean(String path) throws ReflectiveOperationException {
        return _get(path, "boolean");
    }

    /**
     *
     * @param path String
     * @return int
     * @throws ReflectiveOperationException throws
     */
    public int getInt(String path) throws ReflectiveOperationException {
        return _get(path, "int");
    }

    /**
     *
     * @param path String
     * @return long
     * @throws ReflectiveOperationException throws
     */
    public long getLong(String path) throws ReflectiveOperationException {
        return _get(path, "long");
    }

    /**
     *
     * @param path String
     * @return float
     * @throws ReflectiveOperationException throws
     */
    public float getFloat(String path) throws ReflectiveOperationException {
        return _get(path, "float");
    }

    /**
     *
     * @param path String
     * @return Number
     * @throws ReflectiveOperationException throws
     */
    public Number number(String path) throws ReflectiveOperationException {
        return _get(path, "number");
    }

    /**
     *
     * @param path String
     * @return double
     * @throws ReflectiveOperationException throws
     */
    public double getDouble(String path) throws ReflectiveOperationException {
        return _get(path, "double");
    }

    /**
     *
     * @param path String
     * @return BigInteger
     * @throws ReflectiveOperationException throws
     */
    public BigInteger bigInt(String path) throws ReflectiveOperationException {
        return _get(path, "bigInteger");
    }

    /**
     *
     * @param path String
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public <T> T bigDec(String path) throws ReflectiveOperationException {
        return _get(path, "bigDecimal");
    }

    /**
     *
     * @param path String
     * @param type String
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public <T> T get(String path, String type) throws ReflectiveOperationException {
        return _get(path, type);
    }

    /**
     *
     * @param clazz Class E
     * @param path String
     * @return E
     * @param <E> extends Enum {E}
     */
    public <E extends Enum<E>> E getEnum(Class<E> clazz, String path) {
        return jsonData.getEnum(clazz, path);
    }

    /**
     *
     * @param path String
     * @return String[]
     * @throws ReflectiveOperationException throws
     */
    private String[] _keys(String path) throws ReflectiveOperationException {
        return HashMap.keys(isNull(path) ? jsonData : get(path), String[]::new);
    }

    /**
     *
     * @param path String
     * @param type String
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
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

    /**
     *
     * @param path String
     * @return String[]
     */
    public static String[] parsePath(String path) {
        return path.split(delimiter);
    }

    /**
     *
     * @param key String
     * @return JsonSchema
     */
    public JsonSchema object(String key) {
        obj = (obj == null ? jsonData : obj).getJSONObject(key);
        return this;
    }

    /**
     *
     * @param key String
     * @return JsonSchema
     */
    public JsonSchema array(String key) {
        arr = arr == null ? jsonData.getJSONArray(key) : arr;
        return this;
    }

    /**
     *
     * @param key int
     * @return JsonSchema
     */
    public JsonSchema array(int key) {
        arr = arr.getJSONArray(key);
        return this;
    }

    /**
     *
     * @return JSONArray
     */
    public JSONArray parseExample() {
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");
        JSONArray arr = obj.getJSONArray("posts"); // notice that `"posts": [...]`
        for (int i = 0; i < arr.length(); i++) {
            String post_id = arr.getJSONObject(i).getString("post_id");
        }
        return arr;
    }

}
