package org.project.tests;

import java.beans.ConstructorProperties;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertNotNull;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.JavascriptExecutor;

import static org.project.tests.windriver.WebDriver.ls;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.sb;
import static org.project.utils.Helper.trim;
import static org.project.utils.auth.Auth.accessTokenK;
import static org.project.utils.auth.Auth.auth;
import static org.project.utils.auth.Auth.authEndpoint;
import static org.project.utils.auth.Auth.authReq;
import static org.project.utils.auth.Auth.authType;
import static org.project.utils.auth.Auth.fileTokenK;
import static org.project.utils.auth.Auth.getAuth;
import static org.project.utils.auth.Auth.password;
import static org.project.utils.auth.Auth.passwordK;
import static org.project.utils.auth.Auth.refreshTokenK;
import static org.project.utils.auth.Auth.tokenK;
import static org.project.utils.auth.Auth.tokenKeys;
import static org.project.utils.auth.Auth.tokenV;
import static org.project.utils.auth.Auth.username;
import static org.project.utils.auth.Auth.usernameK;
import static org.project.utils.constant.RequestConstants.METHOD;
import static org.project.utils.constant.RequestConstants.METHOD.POST;
import static org.project.utils.request.Request.getParams;
import static org.project.utils.request.Request.getParamsSlash;
import static org.project.utils.request.RequestOptions.getHeaders;

import org.project.utils.auth.Auth;
import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestWeb<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestProc<T, W, D> {
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
    protected static String tokenEndpoint;
    /**
     *
     */
    protected static String endpointUrl;

    /**
     *
     * @throws Exception throws
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
        endpoint = url + c.getEndpoint();
        endpointId = c.getEndpointId();
        tokenEndpoint = c.getEndpointToken();
        if (c.getAuthInit()) authInit();
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
     * @throws Exception throws
     */
    public static void authInit() throws Exception {
        authReq();
        debug("Headers:\n" + getHeaders(getAuth().spec()));
        debug("auth: " + authType);
        token();
    }

    /**
     *
     * @return String
     * @throws ReflectiveOperationException throws
     */
    public static String getToken() throws ReflectiveOperationException {
        return token(resp());
    }

    /**
     *
     * @param resp Response
     * @return String
     * @throws ReflectiveOperationException throws
     */
    public static String token(Response resp) throws ReflectiveOperationException {
        return token(resp.asString());
    }

    /**
     *
     * @param s String
     * @return String
     * @throws ReflectiveOperationException throws
     */
    public static String token(String s) throws ReflectiveOperationException {
        return token = trim(s, "\"");
    }

    /**
     *
     * @return String
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static String token() throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return token(POST, tokenEndpoint);
    }

    /**
     *
     * @param method METHOD
     * @param pathList Object[]
     * @return String
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static String token(METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        setReq(method, pathList);
        auth(req);
        debug("token: " + getToken());
        return token;
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     * @throws MalformedURLException throws
     */
    public static void testWeb() throws ReflectiveOperationException, MalformedURLException {
        web = org.project.tests.windriver.WebDriver.start(url);
        assertNotNull(web);

        // Cast WebDriver to JavascriptExecutor
        JavascriptExecutor js = web;

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
        web.get(endpointUrl);

        /*
        assertFalse(driverWeb.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).isSelected());
        driverWeb.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).click();
        assertTrue(driverWeb.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).isSelected());
        //count total number of checkbox
        debug(driverWeb.findElements(By.cssSelector("input[type='checkbox']")).size());
        */
        //quitWeb();
    }

}
