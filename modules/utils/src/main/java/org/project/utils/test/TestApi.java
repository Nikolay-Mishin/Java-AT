package org.project.utils.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import static io.restassured.http.ContentType.ANY;
import static org.project.utils.request.RequestOptions.getHeaders;
import static org.testng.Assert.assertEquals;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import static org.project.utils.Helper.debug;
import static org.project.utils.constant.RequestConstants.METHOD.GET;

import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import org.project.utils.config.ApiConfig;
import org.project.utils.request.Request;

public class TestApi {
    protected static String ver = "142.0.7444.61";
    protected static String uri = "https://googlechromelabs.github.io/";
    protected static String endpoint = "chrome-for-testing/" + ver + ".json";

    public static void main(String[] args)
        throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, URISyntaxException, ClassNotFoundException, InstantiationException, NoSuchFieldException
    {
        //testApi();
        testHeaders();
    }

    public static void testApi() throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Response resp1 = new Request(GET, "store/order", 0).response();

        debug(resp1);
        debug(resp1.asString());
        debug(resp1.asPrettyString());
        debug(resp1.asInputStream());
        debug(resp1.asByteArray());
        debug(resp1.contentType());
        debug(resp1.headers());
        debug(resp1.sessionId());
        debug(resp1.cookies());
        debug(resp1.detailedCookies());

        Request req1 = new Request(GET, "store/order", 1);
        String resp2 = req1.string();

        debug(req1);
        debug(resp2);

        Request req = new Request(GET).uri(uri);
        Response resp = req.response();
        req.printFullPath();
        req.printUrl();
        debug(req.baseUri());
        debug(resp);
        debug(resp);

        assertEquals(resp.getStatusCode(), 200);
    }

    public static void testHeaders() throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Request req = new Request(GET, "store/order", 0);

        debug(req.statusCode());
        debug(req.contentType());
        debug(req.headers());
        debug(req.cookies());
        debug(req.detailedCookies());
        debug(req.sessionId());
        debug(req.auth());
        debug(req.auth().basic("login", "password"));
        debug(req.auth());


        //req.header("Authorization", "");
        //req.header("Cache-Control", "no-cache");
        //req.header("Accept", "*/*");
        //req.accept(ANY);
        //req.header("Accept-Encoding", "gzip, deflate, br");
        //req.header("Connection", "keep-alive");
        //req.header("Content-Length", "");
        //req.header("Host", "tds-test.itorum.ru");
        //req.header("Origin", "https://tds-test.itorum.ru");
        //req.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 YaBrowser/25.10.0.0 Safari/537.36");
        //req.header("User-Agent", "PostmanRuntime/7.49.1");
        //req.header("kamaz-user-agent", "TDS-Frontend");

        debug(req.headers());

        RequestSpecification spec = new ApiConfig().get();

        debug(req.body());

        debug(spec instanceof FilterableRequestSpecification);
        debug(req.getHeaders());
        debug(new Headers());
        debug(getHeaders(spec));
    }

}
