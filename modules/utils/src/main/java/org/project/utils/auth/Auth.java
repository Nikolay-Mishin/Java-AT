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

public class Auth extends SingleInstance<Auth> {
    protected static Auth i;
    protected AuthBaseRequests<?> req;
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

    public static <T> AuthBaseRequests<T> req() {
        return (AuthBaseRequests<T>) i.req;
    }

    public static <T> AuthBaseRequests<T> req(AuthBaseRequests<T> req) {
        return (AuthBaseRequests<T>) (i.req = req);
    }

    public static <T> AuthBaseRequests<T> req(String baseUrl) throws Exception {
        return req(notNull(i.req) ? (AuthBaseRequests<T>) req().init(baseUrl) : new AuthBaseRequests<>(baseUrl));
    }

    public static String baseUrl() {
        return i.baseUrl;
    }

    public static String baseUrl(Object... pathList) throws Exception {
        return baseUrl(path(pathList));
    }

    public static String baseUrl(String baseUrl) throws Exception {
        auth(baseUrl);
        return i.baseUrl = baseUrl;
    }

    public static Request auth() {
        return i.auth;
    }

    public static Request auth(String baseUrl) throws Exception {
        return auth(req(baseUrl));
    }

    public static <T> Request auth(AuthBaseRequests<T> req) throws MalformedURLException, URISyntaxException, NoSuchFieldException, IllegalAccessException {
        return auth(req.post());
    }

    public static Request auth(Request req) {
        return i.auth = req;
    }

    public static AuthToken token() {
        return i.token;
    }

    public static <T> Auth init(AuthBaseRequests<T> req, AuthToken token)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException, MalformedURLException, URISyntaxException {
        debug("Auth: req, AuthToken");
        init(token);
        auth(req);
        return instance();
    }

    public static <T> Auth init(AuthBaseRequests<T> req, Response tokens)
        throws IOException, ReflectiveOperationException, URISyntaxException {
        debug("Auth: req, Response");
        init(tokens);
        auth(req);
        return instance();
    }

    public static <T> Auth init(AuthBaseRequests<T> req, JsonSchema tokens)
        throws IOException, ReflectiveOperationException, URISyntaxException {
        debug("Auth: req, JsonSchema");
        init(tokens);
        auth(req);
        return instance();
    }

    public static <T> Auth init(AuthBaseRequests<T> req, Object... pathList) throws Exception {
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
        throws IOException, ReflectiveOperationException {
        debug("Auth: Response");
        return init(new AuthToken(tokens));
    }

    public static Auth init(JsonSchema tokens)
        throws IOException, ReflectiveOperationException {
        debug("Auth: JsonSchema");
        return init(new AuthToken(tokens));
    }

    public static Auth init(Object... pathList) throws Exception {
        debug("Auth: pathList");
        init(new AuthToken(pathList));
        baseUrl(pathList);
        return instance();
    }

    public static void refreshTokens(Response tokens) throws ReflectiveOperationException {
        token().refreshTokens(tokens);
    }

    public static void refreshTokens(JsonSchema tokens) throws ReflectiveOperationException {
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

    public static void printTokens(Object... pathList) throws Exception {
        init(pathList);
        printTokens();
    }

    public static void printTokens() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        token().printTokens();
        debug("instance: " + instance(Auth.class));
        debug("auth: " + i);
        debug("SingleInstance: " + SingleInstance.i);
    }

}
