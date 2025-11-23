package org.project.utils.auth;

import io.restassured.response.Response;
import org.project.utils.json.JsonSchema;
import org.project.utils.reflection.SingleInstance;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.debug;
import static org.project.utils.json.JsonSchema.jsonSchema;

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

    public static AuthToken token() {
        return auth.token;
    }

    public static void init(AuthToken token)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        auth = instance(token);
    }

    public static void init(JsonSchema json)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException
    {
        init(new AuthToken(json));
    }

    public static void init(Object... pathList)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException
    {
        init(jsonSchema(pathList));
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

    public static void printTokens() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        token().printTokens();
        debug(instance(Auth.class));
        Auth instance = instance(Auth.class);
        debug(instance);
        debug("auth: " + auth);
        debug("instance: " + instance);
        debug("SingleInstance: " + SingleInstance.instance);
    }

}
