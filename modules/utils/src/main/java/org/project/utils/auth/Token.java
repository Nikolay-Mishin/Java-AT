package org.project.utils.auth;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import io.restassured.response.Response;

import static org.project.utils.Helper.*;
import static org.project.utils.config.WebConfig.config;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.base.*;
import org.project.utils.json.JsonSchema;

public class Token extends Register<String, Token> {

    protected static String[] keys;
    protected static Map<String, Object> keysMap;

    protected HashMap<String, String> token;
    protected String key;
    protected String value;
    protected String path;

    @ConstructorProperties({"key", "value"})
    public Token(String key, String value) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        init(key, value, value);
    }

    @ConstructorProperties({"key", "value", "path"})
    public Token(String key, String value, String path) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        init(key, value, path);
    }

    @ConstructorProperties({"tokens"})
    public Token(Response tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        init(tokens);
    }

    @ConstructorProperties({"tokens"})
    public Token(JsonSchema tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        init(tokens);
    }

    protected void init(String key, String value, String path) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        token = new HashMap<String, String>("key", "value", "path").values(key, value, path);
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public void refreshTokens(Response tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        _refreshTokens(tokens);
    }

    public void refreshTokens(JsonSchema tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        _refreshTokens(tokens);
    }

    public Token token(String key) throws ClassNotFoundException {
        return getRegister(key);
    }

    public HashMap<String, String> token() {
        return token;
    }

    public String key() {
        return key;
    }

    public String value() {
        return value;
    }

    public String path() {
        return path;
    }

    public void print() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        debug(token);
    }

    public void printTokens() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        printRegister();
    }

    protected void init(Object tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        if (isNull(keys)) setKeys(tokens);
        _refreshTokens(tokens);
    }

    protected static void setKeys(Object tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        JsonSchema jsonData = new JsonSchema(config().getTokenKeys());
        keys = jsonData.keys();
        //keysMap = new HashMap<String, String>(keys).values(jsonData, "string");
        keysMap = jsonData.toMap();
        debug("setKeys: " + keysMap);
    }

    protected void _refreshTokens(Object tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        debug("refreshTokens: " + tokens);
        debug("jsonNotNull: " + jsonNotNull(tokens));
        for (String key : keys) {
            String path = key(key);
            String token = null;
            if (!isJson(tokens)) token = invoke(tokens, "path", path);
            else if (jsonNotNull(tokens)) token = invoke(tokens, "get", path, "string");
            token(key, new Token(key, token, path));
        }
    }

    protected static boolean isJson(Object tokens) {
        return isInstance(tokens, JsonSchema.class);
    }

    protected static boolean jsonNotNull(Object tokens) {
        return isJson(tokens) && notNull(((JsonSchema) tokens).data());
    }

    protected String key(String key) {
        return (String) keysMap.get(key);
    }

    protected void token(String key, Token token) throws ClassNotFoundException {
        registerMap(Token.class, key, token);
    }

}
