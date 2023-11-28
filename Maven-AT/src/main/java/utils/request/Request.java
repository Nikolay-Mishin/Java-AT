package utils.request;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import jdk.jfr.Description;
import utils.constant.RequestConstants.METHOD;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static config.ApiConfig.getRequestSpec;
import static io.restassured.RestAssured.given;
import static java.lang.System.out;
import static org.project.utils.fs.FS.getPath;
import static utils.constant.RequestConstants.METHOD.*;
import static utils.reflection.Reflection.*;

public class Request {

    private RequestSpecification request;
    private final METHOD method;
    private final String methodSend;
    private final String url;
    private final URL URL;
    private final URI URI;
    private final String endpoint;
    private Object body;

    @ConstructorProperties({})
    public Request() {
        method = null;
        methodSend = null;
        url = null;
        URL = null;
        URI = null;
        endpoint = null;
    }

    @ConstructorProperties({"method", "pathList"})
    public Request(METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException {
        request = given(getRequestSpec());
        this.method = method;
        methodSend = method.toString().toLowerCase();
        String path = getPath(pathList);
        request.basePath(path); // задаем базовый путь для запроса
        url = getFullPath();
        URL = new URL(url);
        URI = new URI(path);
        endpoint = this.method + " " + path;
        out.println(this.endpoint);
        out.println(getBaseUri());
        out.println(getBasePath());
        printFullPath();
        out.println(URI);
        out.println(URL);
    }

    @Description("Send request")
    protected Response send() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invoke(request, methodSend, URL); // вызов метода с аргументами
    }

    @Description("Builder: get response")
    public Response response() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        request = request.contentType(ContentType.JSON); // header
        request = (body != null && (method == POST || method == PUT) ? request.body(body) : request) // body json
            .when();
        return send().andReturn();
    }

    @Description("Builder: set body request")
    public Request body(Object body) {
        this.body = body;
        return this;
    }

    @Description("Get query request")
    public QueryableRequestSpecification getQuery() {
        request.get();
        return SpecificationQuerier.query(request);
    }

    @Description("Get base path")
    public String getBaseUri() {
        return getQuery().getBaseUri();
    }

    @Description("Get base path")
    public String getBasePath() {
        return getQuery().getBasePath();
    }

    @Description("Get full path")
    public String getFullPath() {
        return getQuery().getURI();
    }

    @Description("Print full path")
    public void printFullPath() {
        out.println("Full PATH is: " + url);
    }

    @Description("Get endpoint")
    public String getEndpoint() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (String) getProp(this, "endpoint");
    }

    @Description("Get url")
    public String getUrl() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (String) getProp(this, "url");
    }

    @Description("Print endpoint")
    public String printEndpoint() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (String) getProp(this, "endpoint", true);
    }

    @Description("Print method")
    public METHOD printMethod() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (METHOD) getProp(this, "method", true);
    }

    @Description("Print method string")
    public METHOD printMethodStr() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (METHOD) getPropStr(this, "method", true);
    }

    @Description("Print url")
    public String printUrl() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (String) getProp(this, "url", true);
    }

    @Description("Print request")
    public void print() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String endpoint = printEndpoint();
        String url = printUrl();
        METHOD method = printMethod();
        //METHOD methodStr = printMethodStr();
        out.println(endpoint);
        out.println(url);
        out.println(method);
        //out.println(methodStr);
    }

}
