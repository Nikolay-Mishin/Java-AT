package utils.base;

import io.restassured.response.Response;
import utils.Register;
import utils.fs.JsonSchema;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static config.WebConfig.BASE_CONFIG;
import static java.lang.System.out;
import static utils.Helper.isInstance;
import static utils.Helper.isNull;
import static utils.reflections.Reflection.invoke;

public class Token extends Register<String, Token> {

    protected static String[] keys;
    protected static HashMap<String, String> keysMap;

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

    protected void init(String key, String value, String path) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        token = new HashMap<String, String>("key", "value", "path").values(key, value, path);
        this.key = key;
        this.value = value;
        this.path = path;
    }

    @ConstructorProperties({"tokens"})
    public Token(Response tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        init(tokens);
    }

    @ConstructorProperties({"tokens"})
    public Token(JsonSchema tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        init(tokens);
    }

    public void refreshTokens(Response tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        _refreshTokens(tokens);
    }

    public void refreshTokens(JsonSchema tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        _refreshTokens(tokens);
    }

    public Token getToken(String key) throws ClassNotFoundException {
        out.println("getToken");
        out.println(Optional.ofNullable(getRegister(key)));
        return getRegister(Token.class, key);
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
        out.println(token);
    }

    public void printTokens() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        printRegister();
    }

    protected void init(Object tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        if (isNull(keys)) setKeys();
        _refreshTokens(tokens);
    }

    protected static void setKeys() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        JsonSchema jsonData = new JsonSchema(BASE_CONFIG.getTokenKeys());
        keys = jsonData.keys();
        keysMap = new HashMap<String, String>(keys).values(jsonData, "string");
    }

    protected void _refreshTokens(Object tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        boolean isJson = isInstance(tokens, JsonSchema.class);
        for (String key : keys) {
            String path = getKey(key);
            String token = null;
            try {
                token = !isJson ? invoke(tokens, "path", path) : invoke(tokens, "get", path, "string");
            }
            catch (NoSuchMethodException ignored) {}
            catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            setToken(key, new Token(key, token, path));
        }
    }

    protected String getKey(String key) {
        return keysMap.get(key);
    }

    protected void setToken(String key, Token token) throws ClassNotFoundException {
        registerMap(Token.class, key, token);
    }

}
