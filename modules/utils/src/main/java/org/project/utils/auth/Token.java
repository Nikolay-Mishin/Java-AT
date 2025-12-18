package org.project.utils.auth;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import io.restassured.response.Response;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.isInstance;
import static org.project.utils.Helper.isNull;
import static org.project.utils.Helper.notNull;
import static org.project.utils.config.WebConfig.config;
import static org.project.utils.json.JsonSchema.jsonSchema;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.base.HashMap;
import org.project.utils.base.Register;
import org.project.utils.json.JsonSchema;

/**
 *
 */
public class Token extends Register<String, Token> {
    /**
     *
     */
    protected static String[] keys;
    /**
     *
     */
    protected static Map<String, Object> keysMap;
    /**
     *
     */
    protected HashMap<String, String> token;
    /**
     *
     */
    protected String key;
    /**
     *
     */
    protected String value;
    /**
     *
     */
    protected String path;

    /**
     *
     * @param key String
     * @param value String
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"key", "value"})
    public Token(String key, String value) throws ReflectiveOperationException {
        debug("Token: key, value");
        init(key, value, value);
    }

    /**
     *
     * @param key String
     * @param value String
     * @param path String
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"key", "value", "path"})
    public Token(String key, String value, String path) throws ReflectiveOperationException {
        debug("Token: key, value, path");
        init(key, value, path);
    }

    /**
     *
     * @param tokens Response
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"tokens"})
    public Token(Response tokens) throws ReflectiveOperationException {
        debug("Token: Response");
        init(tokens);
    }

    /**
     *
     * @param tokens JsonSchema
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"tokens"})
    public Token(JsonSchema tokens) throws ReflectiveOperationException {
        debug("Token: JsonSchema");
        init(tokens);
    }

    /**
     *
     * @param pathList Object[]
     * @throws ReflectiveOperationException throws
     * @throws IOException throws
     */
    @ConstructorProperties({"pathList"})
    public Token(Object... pathList) throws ReflectiveOperationException, IOException {
        debug("Token: pathList");
        init(pathList);
    }

    /**
     *
     * @param tokens Object
     * @throws ReflectiveOperationException throws
     */
    protected static void setKeys(Object tokens) throws ReflectiveOperationException {
        JsonSchema jsonData = new JsonSchema(config().getTokenKeys());
        keys = jsonData.keys();
        //keysMap = new HashMap<String, String>(keys).values(jsonData, "string");
        keysMap = jsonData.toMap();
        debug("setKeys: " + keysMap);
    }

    /**
     *
     * @param tokens Object
     * @return boolean
     */
    protected static boolean isJson(Object tokens) {
        return isInstance(tokens, JsonSchema.class);
    }

    /**
     *
     * @param tokens Object
     * @return boolean
     */
    protected static boolean jsonNotNull(Object tokens) {
        return isJson(tokens) && notNull(((JsonSchema) tokens).data());
    }

    /**
     *
     * @param key String
     * @param value String
     * @param path String
     * @throws ReflectiveOperationException throws
     */
    protected void init(String key, String value, String path) throws ReflectiveOperationException {
        token = new HashMap<String, String>("key", "value", "path").values(key, value, path);
        this.key = key;
        this.value = value;
        this.path = path;
    }

    /**
     *
     * @param pathList Object[]
     * @throws ReflectiveOperationException throws
     * @throws IOException throws
     */
    protected void init(Object... pathList) throws ReflectiveOperationException, IOException {
        init(jsonSchema(pathList));
    }

    /**
     *
     * @param tokens Object
     * @throws ReflectiveOperationException throws
     */
    protected void init(Object tokens) throws ReflectiveOperationException {
        if (isNull(keys)) setKeys(tokens);
        _refreshTokens(tokens);
    }

    /**
     *
     * @param tokens Response
     * @throws ReflectiveOperationException throws
     */
    public void refreshTokens(Response tokens) throws ReflectiveOperationException {
        _refreshTokens(tokens);
    }

    /**
     *
     * @param tokens JsonSchema
     * @throws ReflectiveOperationException throws
     */
    public void refreshTokens(JsonSchema tokens) throws ReflectiveOperationException {
        _refreshTokens(tokens);
    }

    /**
     *
     * @param key String
     * @return Token
     * @throws ClassNotFoundException throws
     */
    public Token token(String key) throws ClassNotFoundException {
        return getRegister(key);
    }

    /**
     *
     * @return HashMap {String, String}
     */
    public HashMap<String, String> token() {
        return token;
    }

    /**
     *
     * @return String
     */
    public String key() {
        return key;
    }

    /**
     *
     * @return String
     */
    public String value() {
        return value;
    }

    /**
     *
     * @return String
     */
    public String path() {
        return path;
    }

    /**
     *
     * @param tokens Object
     * @throws ReflectiveOperationException throws
     */
    protected void _refreshTokens(Object tokens) throws ReflectiveOperationException {
        debug("refreshTokens: " + tokens);
        debug("isJson: " + isJson(tokens));
        debug("jsonNotNull: " + jsonNotNull(tokens));
        if (!isJson(tokens)) debug("tokens: " + ((Response) tokens).asPrettyString());
        for (String key : keys) {
            String path = key(key);
            String token = null;
            debug("path: " + path);
            try {
                if (!isJson(tokens)) {
                    //token = invoke(tokens, "path", path);
                    token = ((Response) tokens).path(path);
                }
                else if (jsonNotNull(tokens)) token = invoke(tokens, "get", path, "string");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            token(key, new Token(key, token, path));
        }
    }

    /**
     *
     * @param key String
     * @return String
     */
    protected String key(String key) {
        return (String) keysMap.get(key);
    }

    /**
     *
     * @param key String
     * @param token Token
     * @throws ClassNotFoundException throws
     */
    protected void token(String key, Token token) throws ClassNotFoundException {
        registerMap(Token.class, key, token);
    }

    /**
     *
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws IllegalAccessException throws
     */
    public void print() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        debug(token);
    }

    /**
     *
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws IllegalAccessException throws
     * @throws ClassNotFoundException throws
     */
    public void printTokens() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        printRegister();
    }

}
