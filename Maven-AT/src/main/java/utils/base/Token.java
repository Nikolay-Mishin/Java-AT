package utils.base;

import io.restassured.response.Response;
import utils.fs.JsonSchema;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import static utils.Helper.isInstance;
import static utils.Helper.isNull;
import static utils.reflections.Reflection.invoke;

public class Token {

    private String token;
    private String refreshToken;
    private String fileToken;

    @ConstructorProperties({"token", "fileToken", "refreshToken"})
    public Token(String token, String refreshToken, String fileToken) {
        init(token, refreshToken, fileToken);
    }

    @ConstructorProperties({"resp"})
    public Token(Response resp, String... keys) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        _init(resp, keys);
    }

    @ConstructorProperties({"jsonString"})
    public Token(String jsonString, String... keys) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        _init(jsonString, keys);
    }

    private void init(String token, String refreshToken, String fileToken) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.fileToken = fileToken;
    }

    private void _init(Object tokens, String... keys) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        boolean isJson = isInstance(tokens, Response.class);
        String method = !isJson ? "path" : "getString";
        tokens = !isJson ? tokens : new JsonSchema((String) tokens).data();
        String token = invoke(tokens, method, getKey("token", keys));
        String refreshToken = invoke(tokens, method, getKey("refreshToken", keys));
        String fileToken = invoke(tokens, method, getKey("fileToken", keys));
        init(token, refreshToken, fileToken);
    }

    private String getKey(String key, String... keys) {
        return switch (key) {
            case ("token") -> _getKey(key, 0, keys);
            case ("refreshToken") -> _getKey(key, 1, keys);
            case ("fileToken") -> _getKey(key, 2, keys);
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

    private String _getKey(String key, int i, String... keys) {
        String value = keys[i];
        return isNull(value) ? key : value;
    }

    public void refreshTokens(Token token) {
        this.setToken(token.token);
        this.setFileToken(token.fileToken);
        this.setRefreshToken(token.refreshToken);
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getFileToken() {
        return fileToken;
    }

    private void setToken(String token) {
        this.token = token;
    }

    private void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private void setFileToken(String fileToken) {
        this.fileToken = fileToken;
    }

}
