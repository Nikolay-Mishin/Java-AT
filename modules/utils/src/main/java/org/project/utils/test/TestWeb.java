package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.JavascriptExecutor;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.sb;
import static org.project.utils.auth.Auth.accessTokenK;
import static org.project.utils.auth.Auth.authEndpoint;
import static org.project.utils.auth.Auth.authType;
import static org.project.utils.auth.Auth.fileTokenK;
import static org.project.utils.auth.Auth.password;
import static org.project.utils.auth.Auth.passwordK;
import static org.project.utils.auth.Auth.refreshTokenK;
import static org.project.utils.auth.Auth.token;
import static org.project.utils.auth.Auth.tokenK;
import static org.project.utils.auth.Auth.tokenKeys;
import static org.project.utils.auth.Auth.tokenV;
import static org.project.utils.auth.Auth.username;
import static org.project.utils.auth.Auth.usernameK;
import static org.project.utils.constant.RequestConstants.METHOD.POST;
import static org.project.utils.reflection.Reflection.invoke;
import static org.project.utils.request.Request.getParams;
import static org.project.utils.request.Request.getParamsSlash;
import static org.project.utils.windriver.WebDriver.ls;

import org.project.utils.auth.Auth;
import org.project.utils.base.Model;
import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.request.Request;
import org.project.utils.test.model.AuthModel;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestWeb<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestWinDriver<T, W, D> {
    /**
     *
     */
    protected static String baseHost;
    /**
     *
     */
    protected static String host;
    /**
     *
     */
    protected static String hostUrl;
    /**
     *
     */
    protected static String rootApi;
    /**
     *
     */
    protected static String api;
    /**
     *
     */
    protected static String hostApi;
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
    protected static String dbUrl;
    /**
     *
     */
    protected static String dbLogin;
    /**
     *
     */
    protected static String dbPassword;
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
    protected static String token;
    /**
     *
     */
    protected static String tokenKeys;
    /**
     *
     */
    protected static ContentType contentType;
    /**
     *
     */
    protected static ContentType accept;
    /**
     *
     */
    protected static String userAgent;
    /**
     *
     */
    protected static String userAgentPostman;
    /**
     *
     */
    protected static String baseHeaders;
    /**
     *
     */
    protected static String headers;
    /**
     *
     */
    protected static String system;
    /**
     *
     */
    protected static String user;
    /**
     *
     */
    protected static String endpoint;
    /**
     *
     */
    protected static int endpointId;
    /**
     *
     */
    protected static String endpointUrl;
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
     */
    @ConstructorProperties({})
    public TestWeb() throws Exception {
        debug("TestWeb:init");
        Auth.init();
        baseHost = w.getBaseHost();
        host = w.getHost();
        hostUrl = w.getHostUrl();
        rootApi = w.getRootApi();
        api = w.getBaseApi();
        hostApi = w.getBaseHostApi();
        username = username();
        password = password();
        //db
        dbUrl = w.getDbUrl();
        dbLogin = w.getDbLogin();
        dbPassword = w.getDbPassword();
        //auth
        authType = authType();
        authEndpoint = authEndpoint();
        usernameK = usernameK();
        passwordK = passwordK();
        //tokens
        accessTokenK = accessTokenK();
        refreshTokenK = refreshTokenK();
        fileTokenK = fileTokenK();
        tokenK = tokenK();
        token = tokenV();
        tokenKeys = tokenKeys();
        //headers
        contentType = w.getContentType();
        accept = w.getAccept();
        userAgent = w.getUserAgent();
        userAgentPostman = w.getUserAgentPostman();
        baseHeaders = w.getBaseHeaders();
        headers = w.getHeaders();
        //System
        system = c.getUserName();
        user = c.getUser();
        //TestWeb
        endpoint = c.getEndpoint();
        endpointId = c.getEndpointId();
        endpointUrl = url(c.getEndpointId(), token);
        debug("getParams: " + getParamsSlash(tokenK, token));
    }

    /**
     *
     * @param project int
     * @param token String
     * @return String
     */
    public static String url(int project, String token) {
        return sb(endpoint, project, getParams(tokenK, token));
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
        setReq(POST, authEndpoint);
        debug("fullPath: " + req.fullPath());
        resp(model);
        debug(resp.asPrettyString());
        setTokens(resp);
        return resp;
    }

    /**
     *
     * @return RequestSpecification
     */
    public static RequestSpecification auth() throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
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
        return auth(req, authType, args);
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
     * @throws ReflectiveOperationException throws
     * @throws MalformedURLException throws
     */
    public static void testWeb() throws ReflectiveOperationException, MalformedURLException {
        webDriver = org.project.utils.windriver.WebDriver.start(url);
        assertNotNull(webDriver);

        // Cast WebDriver to JavascriptExecutor
        JavascriptExecutor js = webDriver;

        // Execute JavaScript to retrieve item from localStorage
        String setItemScript = "localStorage.setItem(arguments[0], arguments[1])";
        String getItemsScript = "return JSON.stringify(localStorage);";
        String getItemScript = "return localStorage.getItem('accessToken');";

        //js.executeScript(setItemScript, "accessToken", "");
        //js.executeScript(setItemScript, "refreshToken", "");
        String localStorageData = (String) js.executeScript(getItemsScript);
        String localStorageItem = (String) js.executeScript(getItemScript);
        //webDriver.executeScript(setItemScript);

        // Print the localStorage data
        debug("localStorage item: " + localStorageItem);
        debug("localStorage data: " + localStorageData);

        ls.set("accessToken", "");
        ls.set("refreshToken", "");

        // Print the localStorage data
        debug("localStorage item: " + ls.get("accessToken"));
        debug("localStorage data: " + ls.items());

        // Navigate to the webpage where localStorage data is stored
        debug("project: " + endpointUrl);
        webDriver.get(endpointUrl);

        /*
        assertFalse(driverWeb.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).isSelected());
        driverWeb.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).click();
        assertTrue(driverWeb.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).isSelected());
        //count total number of checkbox
        debug(driverWeb.findElements(By.cssSelector("input[type='checkbox']")).size());
        */
        //quit();
    }

}
