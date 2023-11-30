package org.project.utils.auth;

import io.restassured.response.Response;
import org.project.utils.json.JsonSchema;
import org.project.utils.reflection.SingleInstance;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import static java.lang.System.out;

public class Auth extends SingleInstance<Auth> {

    protected static Auth auth;
    protected final AuthToken token;

    @ConstructorProperties({"token"})
    public Auth(AuthToken token) {
        this.token = token;
    }

    public static Auth auth() {
        return auth;
    }

    public static AuthToken token() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return auth.token;
    }

    public static void init(AuthToken token) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        auth = instance(token);
    }

    public static void refreshTokens(Response tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        token().refreshTokens(tokens);
    }

    public static void refreshTokens(JsonSchema tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        token().refreshTokens(tokens);
    }

    public static Token getAccessToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return token().getAccessToken();
    }

    public static Token getRefreshToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return token().getRefreshToken();
    }

    public static Token getFileToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return token().getFileToken();
    }

    public static void printTokens() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        token().printTokens();
        out.println(getInstance(Auth.class));
        Auth instance = getInstance(Auth.class);
        out.println(instance);
        out.println("auth: " + auth);
        out.println("instance: " + instance);
        out.println("SingleInstance: " + SingleInstance.instance);
    }

}
