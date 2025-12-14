package org.project.utils.auth;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import io.restassured.response.Response;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.notNull;
import static org.project.utils.constant.RequestConstants.getMethod;
import static org.project.utils.constant.RequestConstants.METHOD.POST;
import static org.project.utils.fs.File.path;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.constant.RequestConstants.METHOD;
import org.project.utils.json.JsonSchema;
import org.project.utils.reflection.SingleInstance;
import org.project.utils.request.AuthBaseRequests;
import org.project.utils.request.Request;

/**
 *
 */
public class Auth extends SingleInstance<Auth> {
    /**
     *
     */
    protected static Auth i;
    /**
     *
     */
    protected METHOD method = POST;
    /**
     *
     */
    protected AuthBaseRequests<?> req;
    /**
     *
     */
    protected String baseUrl;
    /**
     *
     */
    protected Request auth;
    /**
     *
     */
    protected final AuthToken token;

    /**
     *
     * @param token AuthToken
     */
    @ConstructorProperties({"token"})
    public Auth(AuthToken token) {
        this.token = token;
    }

    /**
     *
     * @return Auth
     * @throws NoSuchFieldException throws
     * @throws ClassNotFoundException throws
     * @throws IllegalAccessException throws
     */
    public static Auth instance() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        return SingleInstance.instance();
    }

    /**
     *
     * @return METHOD
     */
    public static METHOD method() {
        return i.method;
    }

    /**
     *
     * @param method METHOD
     * @return METHOD
     */
    public static METHOD method(METHOD method) {
        return i.method = method;
    }

    /**
     *
     * @return AuthBaseRequests of T
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T> AuthBaseRequests<T> req() {
        return (AuthBaseRequests<T>) i.req;
    }

    /**
     *
     * @param req AuthBaseRequests of T
     * @return AuthBaseRequests of T
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T> AuthBaseRequests<T> req(AuthBaseRequests<T> req) {
        return (AuthBaseRequests<T>) (i.req = req);
    }

    /**
     *
     * @param baseUrl String
     * @return AuthBaseRequests of T
     * @param <T> T
     * @throws Exception throws
     */
    @SuppressWarnings("unchecked")
    public static <T> AuthBaseRequests<T> req(String baseUrl) throws Exception {
        return req(notNull(i.req) ? (AuthBaseRequests<T>) req().init(method(), baseUrl) : new AuthBaseRequests<>(baseUrl));
    }

    /**
     *
     * @return String
     */
    public static String baseUrl() {
        return i.baseUrl;
    }

    /**
     *
     * @param pathList Object[]
     * @return String
     * @throws Exception throws
     */
    public static String baseUrl(Object... pathList) throws Exception {
        return baseUrl(path(pathList));
    }

    /**
     *
     * @param baseUrl String
     * @return String
     * @throws Exception throws
     */
    public static String baseUrl(String baseUrl) throws Exception {
        auth(baseUrl);
        return i.baseUrl = baseUrl;
    }

    /**
     *
     * @return Request
     */
    public static Request auth() {
        return i.auth;
    }

    /**
     *
     * @param baseUrl String
     * @return Request
     * @throws Exception throws
     */
    public static Request auth(String baseUrl) throws Exception {
        return auth(req(baseUrl));
    }

    /**
     *
     * @param req AuthBaseRequests of T
     * @return Request
     * @param <T> T
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    public static <T> Request auth(AuthBaseRequests<T> req) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return auth((Request) invoke(req, getMethod(method())));
    }

    /**
     *
     * @param req Request
     * @return Request
     */
    public static Request auth(Request req) {
        return i.auth = req;
    }

    /**
     *
     * @return AuthToken
     */
    public static AuthToken token() {
        return i.token;
    }

    /**
     *
     * @param req AuthBaseRequests of T
     * @param token AuthToken
     * @return Auth
     * @param <T> T
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchFieldException throws
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     */
    public static <T> Auth init(AuthBaseRequests<T> req, AuthToken token) throws ReflectiveOperationException, MalformedURLException, URISyntaxException {
        debug("Auth: req, AuthToken");
        init(token);
        auth(req);
        return instance();
    }

    /**
     *
     * @param req AuthBaseRequests of T
     * @param tokens Response
     * @return Auth
     * @param <T> T
     * @throws IOException throws
     * @throws ReflectiveOperationException throws
     * @throws URISyntaxException throws
     */
    public static <T> Auth init(AuthBaseRequests<T> req, Response tokens)
        throws IOException, ReflectiveOperationException, URISyntaxException {
        debug("Auth: req, Response");
        init(tokens);
        auth(req);
        return instance();
    }

    /**
     *
     * @param req AuthBaseRequests of T
     * @param tokens JsonSchema
     * @return Auth
     * @param <T> T
     * @throws IOException throws
     * @throws ReflectiveOperationException throws
     * @throws URISyntaxException throws
     */
    public static <T> Auth init(AuthBaseRequests<T> req, JsonSchema tokens)
        throws IOException, ReflectiveOperationException, URISyntaxException {
        debug("Auth: req, JsonSchema");
        init(tokens);
        auth(req);
        return instance();
    }

    /**
     *
     * @param req AuthBaseRequests of T
     * @param pathList Object[]
     * @return Auth
     * @param <T> T
     * @throws Exception throws
     */
    public static <T> Auth init(AuthBaseRequests<T> req, Object... pathList) throws Exception {
        debug("Auth: req, pathList");
        init(pathList);
        auth(req);
        return instance();
    }

    /**
     *
     * @param token AuthToken
     * @return Auth
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchFieldException throws
     */
    public static Auth init(AuthToken token)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException
    {
        debug("Auth: AuthToken");
        return instance(token);
    }

    /**
     *
     * @param tokens Response
     * @return Auth
     * @throws IOException throws
     * @throws ReflectiveOperationException throws
     */
    public static Auth init(Response tokens)
        throws IOException, ReflectiveOperationException {
        debug("Auth: Response");
        return init(new AuthToken(tokens));
    }

    /**
     *
     * @param tokens JsonSchema
     * @return Auth
     * @throws IOException throws
     * @throws ReflectiveOperationException throws
     */
    public static Auth init(JsonSchema tokens)
        throws IOException, ReflectiveOperationException {
        debug("Auth: JsonSchema");
        return init(new AuthToken(tokens));
    }

    /**
     *
     * @param pathList Object[]
     * @return Auth
     * @throws Exception throws
     */
    public static Auth init(Object... pathList) throws Exception {
        debug("Auth: pathList");
        init(new AuthToken(pathList));
        baseUrl(pathList);
        return instance();
    }

    /**
     *
     * @param tokens Response
     * @throws ReflectiveOperationException throws
     */
    public static void refreshTokens(Response tokens) throws ReflectiveOperationException {
        token().refreshTokens(tokens);
    }

    /**
     *
     * @param tokens JsonSchema
     * @throws ReflectiveOperationException throws
     */
    public static void refreshTokens(JsonSchema tokens) throws ReflectiveOperationException {
        token().refreshTokens(tokens);
    }

    /**
     *
     * @return Token
     * @throws ClassNotFoundException throws
     */
    public static Token accessToken() throws ClassNotFoundException {
        return token().accessToken();
    }

    /**
     *
     * @return Token
     * @throws ClassNotFoundException throws
     */
    public static Token refreshToken() throws ClassNotFoundException {
        return token().refreshToken();
    }

    /**
     *
     * @return Token
     * @throws ClassNotFoundException throws
     */
    public static Token fileToken() throws ClassNotFoundException {
        return token().fileToken();
    }

    /**
     *
     * @param pathList Object[]
     * @throws Exception throws
     */
    public static void printTokens(Object... pathList) throws Exception {
        init(pathList);
        printTokens();
    }

    /**
     *
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws IllegalAccessException throws
     */
    public static void printTokens() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        token().printTokens();
        debug("instance: " + instance(Auth.class));
        debug("auth: " + i);
        debug("SingleInstance: " + SingleInstance.i);
    }

}
