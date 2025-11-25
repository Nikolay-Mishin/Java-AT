package org.project.utils.auth;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import io.restassured.response.Response;

import static org.project.utils.Helper.debug;

import org.project.utils.json.JsonSchema;
import org.project.utils.reflection.SingleInstance;
import org.project.utils.request.AuthBaseRequests;

public class Auth extends SingleInstance<Auth> {

    protected static Auth instance;
    protected static AuthBaseRequests<?> auth;
    protected static String baseUrl;
    protected final AuthToken token;

    @ConstructorProperties({"token"})
    public Auth(AuthToken token) {
        this.token = token;
    }

    public static Auth instance() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        return SingleInstance.instance();
    }

    public static AuthBaseRequests<?> auth() {
        return auth;
    }

    public static AuthBaseRequests<?> auth(String baseUrl) {
        return auth = new AuthBaseRequests<>(baseUrl);
    }

    public static AuthBaseRequests<?> auth(AuthBaseRequests<?> req) {
        return auth = req;
    }

    public static String baseUrl() {
        return baseUrl;
    }

    public static String baseUrl(String baseUrl) {
        auth(baseUrl);
        return Auth.baseUrl = baseUrl;
    }

    public static AuthToken token() {
        return instance.token;
    }

    public static Auth init(AuthBaseRequests<?> req, AuthToken token)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException
    {
        debug("Auth: req, AuthToken");
        auth(req);
        return init(token);
    }

    public static Auth init(AuthBaseRequests<?> req, Response tokens)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        debug("Auth: req, Response");
        auth(req);
        return init(tokens);
    }

    public static Auth init(AuthBaseRequests<?> req, JsonSchema tokens)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        debug("Auth: req, JsonSchema");
        auth(req);
        return init(tokens);
    }

    public static Auth init(AuthBaseRequests<?> req, Object... pathList)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        debug("Auth: req, pathList");
        auth(req);
        return init(pathList);
    }

    public static Auth init(AuthToken token)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException
    {
        debug("Auth: AuthToken");
        return instance(token);
    }

    public static Auth init(Response tokens)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        debug("Auth: Response");
        return init(new AuthToken(tokens));
    }

    public static Auth init(JsonSchema tokens)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        debug("Auth: JsonSchema");
        return init(new AuthToken(tokens));
    }

    public static Auth init(Object... pathList)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        debug("Auth: pathList");
        return init(new AuthToken(pathList));
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
