package org.project.utils.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import static org.testng.Assert.assertEquals;

import io.restassured.response.Response;

import static org.project.utils.Helper.debug;
import static org.project.utils.constant.RequestConstants.METHOD.GET;

import org.project.utils.request.Request;

public class TestApi {
    protected static String ver = "142.0.7444.61";
    protected static String uri = "https://googlechromelabs.github.io/";
    protected static String endpoint = "chrome-for-testing/" + ver + ".json";

    public static void main(String[] args)
        throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, URISyntaxException, ClassNotFoundException, InstantiationException
    {
        testApi();
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

}
