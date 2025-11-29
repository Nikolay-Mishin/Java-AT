package org.project.utils.auth;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import io.restassured.response.Response;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.notNull;
import static org.project.utils.fs.File.path;

import org.project.utils.json.JsonSchema;
import org.project.utils.reflection.SingleInstance;
import org.project.utils.request.AuthBaseRequests;
import org.project.utils.request.Request;

public class Auth<T> extends SingleInstance<Auth<T>> {
    protected static Auth instance;
    protected AuthBaseRequests<T> req;
    protected String baseUrl;
    protected Request auth;
    protected final AuthToken token;

    @ConstructorProperties({"token"})
    public Auth(AuthToken token) {
        this.token = token;
    }

    public static Auth instance() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        return SingleInstance.instance();
    }

    public static <T> AuthBaseRequests req() {
        return instance.req;
    }

    public static <T> AuthBaseRequests req(String baseUrl) throws MalformedURLException, URISyntaxException {
        return instance.req = /*notNull(instance.req) ? instance.req.init(baseUrl) : */new AuthBaseRequests<>(baseUrl);
    }

    public static String baseUrl() {
        return instance.baseUrl;
    }

    public static String baseUrl(Object... pathList) throws MalformedURLException, URISyntaxException {
        return baseUrl(path(pathList));
    }

    public static String baseUrl(String baseUrl) throws MalformedURLException, URISyntaxException {
        auth(baseUrl);
        return instance.baseUrl = baseUrl;
    }

    public static Request auth() {
        return instance.auth;
    }

    public static Request auth(String baseUrl) throws MalformedURLException, URISyntaxException {
        return auth(req(baseUrl));
    }

    public static Request auth(AuthBaseRequests<?> req) {
        return auth(req.post());
    }

    public static Request auth(Request req) {
        return instance.auth = req;
    }

    public static AuthToken token() {
        return instance.token;
    }

    public static Auth init(AuthBaseRequests<?> req, AuthToken token)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException
    {
        debug("Auth: req, AuthToken");
        init(token);
        auth(req);
        return instance();
    }

    public static Auth init(AuthBaseRequests<?> req, Response tokens)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        debug("Auth: req, Response");
        init(tokens);
        auth(req);
        return instance();
    }

    public static Auth init(AuthBaseRequests<?> req, JsonSchema tokens)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        debug("Auth: req, JsonSchema");
        init(tokens);
        auth(req);
        return instance();
    }

    public static Auth init(AuthBaseRequests<?> req, Object... pathList)
        throws IOException, URISyntaxException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException
    {
        debug("Auth: req, pathList");
        init(pathList);
        auth(req);
        return instance();
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
        throws IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException, InstantiationException
    {
        debug("Auth: pathList");
        baseUrl(pathList);
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
        throws IOException, URISyntaxException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException
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
