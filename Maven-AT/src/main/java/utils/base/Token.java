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
    public Token(JsonSchema jsonSchema, String... keys) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        _init(jsonSchema, keys);
    }

    private void init(String token, String refreshToken, String fileToken) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.fileToken = fileToken;
    }

    private void _init(Object tokens, String... keys) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        boolean isJson = isInstance(tokens, JsonSchema.class);
        String tokenKey = getKey("token", keys);
        String refreshTokenKey = getKey("refreshToken", keys);
        String fileTokenKey = getKey("fileToken", keys);
        String token = !isJson ? invoke(tokens, "path", tokenKey) : invoke(tokens, "get", tokenKey, "String");
        String refreshToken = !isJson ? invoke(tokens, "path", refreshTokenKey) : invoke(tokens, "get", refreshTokenKey, "String");
        String fileToken = !isJson ? invoke(tokens, "path", fileTokenKey) : invoke(tokens, "get", fileTokenKey, "String");
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
