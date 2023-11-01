package utils;

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
import static utils.Reflection.*;
import static utils.constant.RequestConstants.METHOD.*;
import static utils.fs.FS.getPath;

public class Request {

    private RequestSpecification request;
    private Response response;
    private final String methodSend;
    private final METHOD method;
    private final String url;
    private final URL URL;
    private final URI URI;
    private final String endpoint;
    private Object body;

    @ConstructorProperties({"method", "pathList"})
    public Request(METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException {
        this.request = given(getRequestSpec());
        this.methodSend = method.toString().toLowerCase();
        this.method = method;
        String path = getPath(pathList);
        this.request.basePath(path); // задаем базовый путь для запроса
        this.url = getFullPath();
        this.URL = new URL(this.url);
        this.URI = new URI(path);
        this.endpoint = this.method + " " + path;
        out.println(this.endpoint);
        out.println(getBaseUri());
        out.println(getBasePath());
        this.printFullPath();
        out.println(this.URI);
        out.println(this.URL);
    }

    @Description("Send request")
    private Response send() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invoke(this.request, this.methodSend, this.URL); // вызов метода с аргументами
    }

    @Description("Builder: get response")
    public Response response() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        this.request = this.request.contentType(ContentType.JSON); // header
        this.request = (this.body != null && (this.method == POST || this.method == PUT) ? this.request.body(this.body) : this.request) // body json
            .when();
        return this.send().andReturn();
    }

    @Description("Builder: set body request")
    public Request body(Object body) {
        this.body = body;
        return this;
    }

    @Description("Get query request")
    public QueryableRequestSpecification getQuery() {
        this.request.get();
        return SpecificationQuerier.query(this.request);
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
        out.println("Full PATH is: " + this.url);
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
        String endpoint = this.printEndpoint();
        String url = this.printUrl();
        METHOD method = this.printMethod();
        //METHOD methodStr = this.printMethodStr();
        out.println(endpoint);
        out.println(url);
        out.println(method);
        //out.println(methodStr);
    }

}
