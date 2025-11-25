package org.project.utils.auth;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import io.restassured.response.Response;

import static org.project.utils.Helper.debug;
import static org.project.utils.json.JsonSchema.jsonSchema;

import org.project.utils.json.JsonSchema;
import org.project.utils.reflection.SingleInstance;

public class Auth extends SingleInstance<Auth> {

    protected static Auth instance;
    protected final AuthToken token;

    @ConstructorProperties({"token"})
    public Auth(AuthToken token) {
        this.token = token;
    }

    public static <T extends SingleInstance<?>> T instance() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return SingleInstance.instance();
    }

    public static Auth auth() {
        return instance;
    }

    public static AuthToken token() {
        return instance.token;
    }

    public static Auth init(AuthToken token)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException
    {
        return instance(token);
    }

    public static Auth init(JsonSchema json)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        return init(new AuthToken(json));
    }

    public static Auth init(Object... pathList)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        return init(jsonSchema(pathList));
    }

    public static void refreshTokens(Response tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        token().refreshTokens(tokens);
    }

    public static void refreshTokens(JsonSchema tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        token().refreshTokens(tokens);
    }

    public static Token accessToken() throws ClassNotFoundException {
        return token().accessToken();
    }

    public static Token refreshToken() throws ClassNotFoundException {
        return token().refreshToken();
    }

    public static Token fileToken() throws ClassNotFoundException {
        return token().fileToken();
    }

    public static void printTokens(Object... pathList)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException, NoSuchFieldException, InstantiationException
    {
        init(pathList);
        printTokens();
    }

    public static void printTokens() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        token().printTokens();
        debug("instance: " + instance(Auth.class));
        debug("auth: " + instance);
        debug("SingleInstance: " + SingleInstance.instance);
    }

}
