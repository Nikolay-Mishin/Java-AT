package org.project.tests;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.isNull;
import static org.project.utils.constant.ContentType.getContentType;
import static org.project.utils.constant.ContentType.getAccept;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.request.Request.query;
import static org.project.utils.request.Request.req;
import static org.project.utils.request.RequestOptions.getHeaders;

import org.project.utils.config.ApiConfig;
import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.constant.RequestConstants.METHOD;
import org.project.utils.request.Request;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestApi<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestConfig<T, W, D> {
    /**
     *
     */
    protected static Request req;
    /**
     *
     */
    protected static Response resp;
    /**
     *
     */
    protected static String url;
    /**
     *
     */
    protected static String apiVer;
    /**
     *
     */
    protected static String uri;
    /**
     *
     */
    protected static String endpoint;
    /**
     *
     */
    protected static String endpointVer;
    /**
     *
     */
    protected static String endpointTest;

    /**
     *
     */
    @ConstructorProperties({})
    public TestApi() {
        debug("TestApi:init");
        apiVer = win.getApiVer();
        uri = win.getApiUri();
        endpoint = win.getApiEndpoint();
        endpointVer = win.getApiEndpointVer();
        endpointTest = c.getEndpointTest();
    }

    /**
     *
     * @param method METHOD
     * @param pathList Object[]
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request setReq(METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return req = req(method, pathList);
    }

    /**
     *
     * @param uri String
     * @param method METHOD
     * @param pathList Object[]
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request setReq(String uri, METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return req = req(uri, method, pathList);
    }

    /**
     *
     * @param method METHOD
     * @param query String
     * @param pathList Object[]
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request reqQuery(METHOD method, String query, Object... pathList) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return req = query(method, query, pathList);
    }

    /**
     *
     * @param uri String
     * @param method METHOD
     * @param query String
     * @param pathList Object[]
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static Request reqQuery(String uri, METHOD method, String query, Object... pathList) throws MalformedURLException, URISyntaxException, ReflectiveOperationException
    {
        return req = query(uri, method, query, pathList);
    }

    /**
     *
     * @return Response
     * @throws ReflectiveOperationException throws
     */
    public static Response resp() throws ReflectiveOperationException {
        return resp(null);
    }

    /**
     *
     * @param body Object
     * @return Response
     * @throws ReflectiveOperationException throws
     */
    public static Response resp(Object body) throws ReflectiveOperationException {
        return resp = isNull(body) ? req.response() : req.response(body);
    }

    /**
     *
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void testApi() throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testApi");
        setReq(GET, endpointTest, 0);
        resp();

        debug(req);
        debug(resp);
        debug(resp.asString());
        debug(resp.asPrettyString());
        debug(resp.asInputStream());
        debug(resp.asByteArray());
        debug(resp.contentType());
        debug(resp.headers());
        debug(resp.sessionId());
        debug(resp.cookies());
        debug(resp.detailedCookies());

        req.url(endpointTest, 1);
        String respStr = req.string();
        debug(respStr);

        req.uri(uri);
        resp();
        debug(resp);
        req.printFullPath();
        req.printUri();
        req.printPath();

        req.url("baseUrl");
        req.printFullPath();
        req.printUri();
        req.printPath();

        req.url("url");
        req.printFullPath();
        req.printUri();
        req.printPath();

        req.baseUrl(2);
        req.printFullPath();
        req.printUri();
        req.printPath();

        req.endpoint("id");
        req.printFullPath();
        req.printUri();
        req.printPath();

        req.endpoint(1);
        req.printFullPath();
        req.printUri();
        req.printPath();

        setReq(GET);
        req.printUri();
        req.printPath();

        req.baseUrl("url");
        req.printFullPath();
        req.printUri();
        req.printPath();
    }

    /**
     *
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void testHeaders() throws IOException, URISyntaxException, ReflectiveOperationException {
        testHeaders(false);
    }

    /**
     *
     * @param setHeaders boolean
     * @throws IOException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public static void testHeaders(boolean setHeaders) throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testHeaders");
        setReq(GET, endpointTest, 0);

        debug(req.statusCode());
        debug(req.contentType());
        debug(req.headers());
        debug(req.cookies());
        debug(req.detailedCookies());
        debug(req.sessionId());

        debug("contentType: " + getContentType());
        debug("accept: " + getAccept());

        if (setHeaders) debug(req.setHeaders());
        debug(req.headers());
        debug(req.getHeaders());

        RequestSpecification spec = new ApiConfig().get();

        debug(req.body());

        debug(spec instanceof FilterableRequestSpecification);
        debug(req.getHeaders());
        debug(new Headers());
        debug(getHeaders(spec));

        AuthenticationSpecification authSpec = req.auth();
        RequestSpecification authBasic = req.basic("login", "password");
        RequestSpecification oauth2 = req.oauth2("token");

        debug(authSpec);
        debug(authBasic);
        debug(getHeaders(authBasic));
        debug(req.auth());
        debug(getHeaders(oauth2));
        debug(new Headers());
        debug(req.getHeaders());
    }

}
