package org.project.utils.test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.testng.Assert.assertEquals;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;

import static org.project.utils.Helper.debug;
import static org.project.utils.constant.ContentType.getContentType;
import static org.project.utils.constant.ContentType.getAccept;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.request.RequestOptions.getHeaders;

import org.project.utils.config.ApiConfig;
import org.project.utils.request.Request;

public class TestApi {
    protected static String ver = "142.0.7444.61";
    protected static String uri = "https://googlechromelabs.github.io/";
    protected static String endpoint = "chrome-for-testing/" + ver + ".json";

    public static void main(String[] args) throws Exception {
        //testApi();
        testHeaders();
    }

    public static void testApi() throws IOException, URISyntaxException, ReflectiveOperationException {
        testApi(false);
    }

    public static void testApi(boolean setAssert)
        throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testApi:" + setAssert);
        Request req = new Request(GET, "store/order", 0);
        Response resp = req.response();

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

        req.url("store/order", 1);
        String resp1 = req.string();
        debug(resp1);

        req.uri(uri);
        Response resp2 = req.response();
        debug(resp2);
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

        req = new Request(GET);
        req.printUri();
        req.printPath();

        req.baseUrl("url");
        req.printFullPath();
        req.printUri();
        req.printPath();

        if (setAssert) assertEquals(resp.getStatusCode(), 200);
    }

    public static void testHeaders() throws IOException, URISyntaxException, ReflectiveOperationException {
        testHeaders(false);
    }

    public static void testHeaders(boolean setHeaders) throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testHeaders");
        Request req = new Request(GET, "store/order", 0);

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
        RequestSpecification authBasic = req.auth().basic("login", "password");
        RequestSpecification oauth2 = req.auth().oauth2("token");

        debug(authSpec);
        debug(authBasic);
        debug(getHeaders(authBasic));
        debug(req.auth());
        debug(getHeaders(oauth2));
        debug(new Headers());
        debug(req.getHeaders());
    }

}
