package org.project.utils.test;

import java.io.IOException;
import java.net.URISyntaxException;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.TestConfig.config;
import static org.project.utils.constant.ContentType.getContentType;
import static org.project.utils.constant.ContentType.getAccept;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.request.RequestOptions.getHeaders;

import org.project.utils.config.ApiConfig;
import org.project.utils.request.Request;

public class TestApi extends BaseTest {
    protected static String uri;
    protected static String endpoint;
    protected static String endpointTest;

    public TestApi() {
        c = config();
        uri = c.getApiUri();
        endpoint = c.getEndpoint();
        endpointTest = c.getEndpointTest();
    }

    public static void main(String[] args) throws Exception {
        testApi();
        testHeaders();
    }

    public static void testApi()
        throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testApi");
        Request req = new Request(GET, endpointTest, 0);
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

        req.url(endpointTest, 1);
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
    }

    public static void testHeaders() throws IOException, URISyntaxException, ReflectiveOperationException {
        testHeaders(false);
    }

    public static void testHeaders(boolean setHeaders) throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testHeaders");
        Request req = new Request(GET, endpointTest, 0);

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
