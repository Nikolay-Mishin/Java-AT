package utils.base;

import io.restassured.response.Response;
import utils.Register;
import utils.fs.JsonSchema;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import static config.WebConfig.BASE_CONFIG;
import static utils.Helper.isInstance;
import static utils.Helper.isNull;
import static utils.reflections.Reflection.invoke;

public class Token extends Register<String, String> {

    protected static String[] keys;
    protected static HashMap<String, String> keysMap;

    protected String key;
    protected String value;
    protected String path;

    @ConstructorProperties({"key", "value"})
    public Token(String key, String value) {
        init(key, value, value);
    }

    @ConstructorProperties({"key", "value", "path"})
    public Token(String key, String value, String path) {
        init(key, value, path);
    }

    protected void init(String key, String value, String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    @ConstructorProperties({"tokens"})
    public Token(Response tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        init(tokens);
    }

    @ConstructorProperties({"tokens"})
    public Token(JsonSchema tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        init(tokens);
    }

    public void refreshTokens(Response tokens) {
        _refreshTokens(tokens);
    }

    public void refreshTokens(JsonSchema tokens) {
        _refreshTokens(tokens);
    }

    public String getToken(String key) {
        return getRegister(key);
    }

    public void printTokens() {
        printRegister();
    }

    protected void init(Object tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (isNull(keys)) setKeys();
        _refreshTokens(tokens);
    }

    protected static void setKeys() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        JsonSchema jsonData = new JsonSchema(BASE_CONFIG.getTokenKeys());
        keys = jsonData.keys();
        keysMap = new HashMap<String, String>(keys).values(jsonData, "string");
    }

    protected void _refreshTokens(Object tokens) {
        boolean isJson = isInstance(tokens, JsonSchema.class);
        for (String key : keys) {
            String path = getKey(key);
            String token = null;
            try {
                token = !isJson ? invoke(tokens, "path", path) : invoke(tokens, "get", path, "string");
            } catch (NoSuchMethodException ignored) {}
            catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            setToken(key, token);
        }
    }

    protected String getKey(String key) {
        return keysMap.get(key);
    }

    protected void setToken(String key, String token) {
        register(key, token);
    }

}
