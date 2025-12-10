package org.project.utils.auth;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import io.restassured.response.Response;

import org.project.utils.json.JsonSchema;

public class AuthToken extends Token {

    public AuthToken(Response tokens) throws ReflectiveOperationException {
        super(tokens);
    }

    public AuthToken(JsonSchema tokens) throws ReflectiveOperationException {
        super(tokens);
    }

    public AuthToken(Object... pathList) throws ReflectiveOperationException, IOException {
        super(pathList);
    }

    public String getAccessToken() throws ClassNotFoundException {
        return accessToken().value;
    }

    public String getRefreshToken() throws ClassNotFoundException {
        return refreshToken().value;
    }

    public String getFileToken() throws ClassNotFoundException {
        return fileToken().value;
    }

    public Token accessToken() throws ClassNotFoundException {
        return token("access");
    }

    public Token refreshToken() throws ClassNotFoundException {
        return token("refresh");
    }

    public Token fileToken() throws ClassNotFoundException {
        return token("file");
    }

    public void printTokens() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        super.printTokens();
        accessToken().print();
        refreshToken().print();
        fileToken().print();
    }

}
