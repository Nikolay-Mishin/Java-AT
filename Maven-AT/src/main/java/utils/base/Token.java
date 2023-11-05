package utils.base;

import io.restassured.response.Response;
import utils.Register;
import utils.fs.JsonSchema;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import static config.WebConfig.BASE_CONFIG;
import static java.lang.System.out;
import static utils.Helper.isInstance;
import static utils.Helper.isNull;
import static utils.reflections.Reflection.invoke;

public class Token extends Register<String, String> {

    private static String[] keys;
    private static java.util.HashMap<String, String> keysMap;

    @ConstructorProperties({"resp"})
    public Token(Response resp) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        init(resp);
    }

    @ConstructorProperties({"jsonString"})
    public Token(JsonSchema jsonSchema) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        init(jsonSchema);
    }

    private void init(Object tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (isNull(keys)) setKeys();
        _refreshTokens(tokens);
    }

    private static void setKeys() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        JsonSchema jsonData = new JsonSchema(BASE_CONFIG.getTokenKeys());
        keys = jsonData.keys();
        keysMap = new HashMap<String, String>(keys).values(jsonData, "string");
    }

    private String getKey(String key) {
        return keysMap.get(key);
    }

    public void refreshTokens(Response tokens) {
        _refreshTokens(tokens);
    }

    public void refreshTokens(JsonSchema tokens) {
        _refreshTokens(tokens);
    }

    private void _refreshTokens(Object tokens) {
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

    public String getToken(String key) {
        return getRegister(key);
    }

    private void setToken(String key, String token) {
        register(key, token);
    }

    public void printTokens() {
        out.println(register);
    }

}
