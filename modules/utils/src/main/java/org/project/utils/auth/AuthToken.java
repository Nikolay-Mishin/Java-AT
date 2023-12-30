package org.project.utils.auth;

import io.restassured.response.Response;
import org.project.utils.json.JsonSchema;

import java.lang.reflect.InvocationTargetException;

public class AuthToken extends Token {

    public AuthToken(Response tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        super(tokens);
    }

    public AuthToken(JsonSchema tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        super(tokens);
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
