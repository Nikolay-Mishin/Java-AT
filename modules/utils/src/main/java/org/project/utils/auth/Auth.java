package org.project.utils.auth;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.notNull;
import static org.project.utils.constant.RequestConstants.getMethod;
import static org.project.utils.constant.RequestConstants.METHOD.POST;
import static org.project.utils.fs.File.path;
import static org.project.utils.reflection.Reflection.getGenericClass;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.base.HashMap;
import org.project.utils.base.Model;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.config.WebConfig;
import org.project.utils.constant.RequestConstants.METHOD;
import org.project.utils.json.JsonSchema;
import org.project.utils.reflection.SingleInstance;
import org.project.utils.request.AuthBaseRequests;
import org.project.utils.request.Request;
import org.project.utils.test.model.AuthModel;

/**
 *
 */
public class Auth extends SingleInstance<Auth> {
    /**
     *
     */
    protected static WebBaseConfig c = WebConfig.config();
    /**
     *
     */
    protected static Auth i;
    /**
     *
     */
    protected static METHOD method = POST;
    /**
     *
     */
    protected String baseUrl;
    /**
     *
     */
    protected AuthBaseRequests<?> req;
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
     */
    protected static String username;
    /**
     *
     */
    protected static String password;
    /**
     *
     */
    protected static String authType;
    /**
     *
     */
    protected static String authEndpoint;
    /**
     *
     */
    protected static String usernameK;
    /**
     *
     */
    protected static String passwordK;
    /**
     *
     */
    protected static String accessTokenK;
    /**
     *
     */
    protected static String refreshTokenK;
    /**
     *
     */
    protected static String fileTokenK;
    /**
     *
     */
    protected static String tokenK;
    /**
     *
     */
    protected static String tokenV;
    /**
     *
     */
    protected static String tokenKeys;
    /**
     *
     */
    protected static AuthModel model;
    /**
     *
     */
    protected static String accessToken;
    /**
     *
     */
    protected static String refreshToken;
    /**
     *
     */
    protected static String fileToken;

    /**
     *
     * @param token AuthToken
     */
    @ConstructorProperties({"token"})
    public Auth(AuthToken token) {
        this.token = token;
        config(c);
    }

    /**
     *
     * @throws Exception throws
     */
    @ConstructorProperties({})
    public Auth() throws Exception {
        config(c);
        token = new AuthToken(authEndpoint);
    }

    /**
     *
     * @param c T
     * @param <T> T
     * @throws Exception throws
     */
    @ConstructorProperties({"c"})
    public <T extends WebBaseConfig> Auth(T c) throws Exception {
        config(c);
        token = new AuthToken(authEndpoint);
    }

    /**
     *
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T extends WebBaseConfig> T config() {
        return (T) c;
    }

    /**
     *
     * @param c T
     * @param <T> T
     */
    public static <T extends WebBaseConfig> void config(T c) {
        username = c.getUserLogin();
        password = c.getUserPassword();
        //auth
        authType = c.getAuthType();
        authEndpoint = c.getEndpointAuth();
        usernameK = c.getUsernameKey();
        passwordK = c.getPasswordKey();
        //tokens
        accessTokenK = c.getAccessTokenKey();
        refreshTokenK = c.getRefreshTokenKey();
        fileTokenK = c.getFileTokenKey();
        tokenK = c.getTokenKey();
        tokenV = c.getToken();
        tokenKeys = c.getTokenKeys();
    }

    /**
     *
     * @return Auth
     * @throws NoSuchFieldException throws
     * @throws ClassNotFoundException throws
     * @throws IllegalAccessException throws
     */
    public static Auth instance() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        return SingleInstance.getInstance();
    }

    /**
     *
     * @return METHOD
     */
    public static METHOD method() {
        return method;
    }

    /**
     *
     * @param m METHOD
     * @return METHOD
     */
    public static METHOD method(METHOD m) {
        return method = m;
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
     * @return String
     */
    public static String username() {
        return username;
    }

    /**
     *
     * @return String
     */
    public static String password() {
        return password;
    }

    /**
     *
     * @return String
     */
    public static String authType() {
        return authType;
    }

    /**
     *
     * @return String
     */
    public static String authEndpoint() {
        return authEndpoint;
    }

    /**
     *
     * @return String
     */
    public static String usernameK() {
        return usernameK;
    }

    /**
     *
     * @return String
     */
    public static String passwordK() {
        return passwordK;
    }

    /**
     *
     * @return String
     */
    public static String accessTokenK() {
        return accessTokenK;
    }

    /**
     *
     * @return String
     */
    public static String refreshTokenK() {
        return refreshTokenK;
    }

    /**
     *
     * @return String
     */
    public static String fileTokenK() {
        return fileTokenK;
    }

    /**
     *
     * @return String
     */
    public static String tokenK() {
        return tokenK;
    }

    /**
     *
     * @return String
     */
    public static String tokenV() {
        return tokenV;
    }

    /**
     *
     * @return String
     */
    public static String tokenKeys() {
        return tokenKeys;
    }

    /**
     *
     * @return String
     */
    public static AuthModel getModel() {
        return model;
    }

    /**
     *
     * @return String
     */
    public static String accessTokenV() {
        return accessToken;
    }

    /**
     *
     * @return String
     */
    public static String refreshTokenV() {
        return refreshToken;
    }

    /**
     *
     * @return String
     */
    public static String fileTokenV() {
        return fileToken;
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
    public static <T> Auth init(AuthBaseRequests<T> req, Response tokens) throws IOException, ReflectiveOperationException, URISyntaxException {
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
    public static <T> Auth init(AuthBaseRequests<T> req, JsonSchema tokens) throws IOException, ReflectiveOperationException, URISyntaxException {
        debug("Auth: req, JsonSchema");
        init(tokens);
        auth(req);
        return instance();
    }

    /**
     *
     * @param tokens Response
     * @return Auth
     * @throws IOException throws
     * @throws ReflectiveOperationException throws
     */
    public static Auth init(Response tokens) throws IOException, ReflectiveOperationException {
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
    public static Auth init(JsonSchema tokens) throws IOException, ReflectiveOperationException {
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
     * @param token AuthToken
     * @return Auth
     * @throws ReflectiveOperationException throws
     */
    public static Auth init(AuthToken token) throws ReflectiveOperationException {
        debug("Auth: AuthToken");
        return instance(token);
    }

    /**
     *
     * @return Auth
     * @throws Exception throws
     */
    public static Auth init() throws Exception {
        debug("Auth: init");
        return init(c);
    }

    /**
     *
     * @param config T
     * @return Auth
     * @param <T> T
     * @throws Exception throws
     */
    public static <T extends WebBaseConfig> Auth init(T config) throws Exception {
        debug("Auth: config");
        instance(config);
        baseUrl(authEndpoint);
        return instance();
    }

    /**
     *
     * @return Auth
     */
    public static AuthModel model() throws ReflectiveOperationException {
        return model(AuthModel.class, usernameK, username, passwordK, password);
    }

    /**
     *
     * @param authModel Class Auth
     * @param args Object[]
     * @return Auth
     */
    public static AuthModel model(Class<AuthModel> authModel, Object... args) throws ReflectiveOperationException {
        return model = new Model<>(authModel, args).get();
    }

    /**
     *
     * @return Response
     */
    public static Response authReq() throws Exception {
        return auth(model());
    }

    /**
     *
     * @param authModel Class Auth
     * @param args Object[]
     * @return Response
     */
    public static Response auth(Class<AuthModel> authModel, Object... args) throws Exception {
        return auth(model(authModel, args));
    }

    /**
     *
     * @param model Auth
     * @return Response
     */
    public static Response auth(AuthModel model) throws Exception {
        Request req = auth();
        debug("fullPath: " + req.fullPath());
        Response resp = req.response(model);
        debug(resp.asPrettyString());
        setTokens(resp);
        debug("tokens: " + token());
        debug("accessToken: " + accessToken());
        debug("value: " + getAccessToken());
        refreshTokens(resp);
        debug("tokens: " + token());
        debug("accessToken: " + accessToken());
        debug("value: " + getAccessToken());
        return resp;
    }

    /**
     *
     * @return RequestSpecification
     */
    public static RequestSpecification setAuth() throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return auth(authType, accessToken);
    }

    /**
     *
     * @param args Object[]
     * @return RequestSpecification
     */
    public static RequestSpecification auth(Object... args) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return auth(authType, args);
    }

    /**
     *
     * @param authType String
     * @param args Object[]
     * @return RequestSpecification
     */
    public static RequestSpecification auth(String authType, Object... args) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return auth(auth(), authType, args);
    }

    /**
     *
     * @param req Request
     * @param authType String
     * @param args Object[]
     * @return RequestSpecification
     */
    public static RequestSpecification auth(Request req, String authType, Object... args) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return authType.isEmpty() ? null : invoke(req, authType, args);
    }

    /**
     *
     * @param resp Response
     */
    public static void setTokens(Response resp) {
        accessToken(resp);
        refreshToken(resp);
        fileToken(resp);
    }

    /**
     *
     * @param resp Response
     * @return String
     */
    public static String accessToken(Response resp) {
        return accessToken = token(resp, accessTokenK);
    }

    /**
     *
     * @param resp Response
     * @return String
     */
    public static String refreshToken(Response resp) {
        return refreshToken = token(resp, refreshTokenK);
    }

    /**
     *
     * @param resp Response
     * @return String
     */
    public static String fileToken(Response resp) {
        return fileToken = token(resp, fileTokenK);
    }

    /**
     *
     * @param resp Response
     * @param k String
     * @return String
     */
    public static String token(Response resp, String k) {
        String token = resp.path(k);
        debug(k + ": " + token);
        return token;
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
     * @return String
     * @throws ClassNotFoundException throws
     */
    public static String getAccessToken() throws ClassNotFoundException {
        return token().getAccessToken();
    }

    /**
     *
     * @return String
     * @throws ClassNotFoundException throws
     */
    public static String getRefreshToken() throws ClassNotFoundException {
        return token().getRefreshToken();
    }

    /**
     *
     * @return String
     * @throws ClassNotFoundException throws
     */
    public static String getFileToken() throws ClassNotFoundException {
        return token().getFileToken();
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
