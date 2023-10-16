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
import java.lang.reflect.Method;

import static config.ApiConfig.getRequestSpec;
import static io.restassured.RestAssured.given;
import static java.lang.System.out;
import static utils.Reflection.*;
import static utils.constant.RequestConstants.METHOD.*;

public class Request {

    private final RequestSpecification spec;
    private RequestSpecification request;
    private Response response;
    private String methodSend;
    private final METHOD method;
    private final String url;
    private final String endpoint;
    private Object body;

    @ConstructorProperties({"method", "pathList"})
    public Request(METHOD method, String... pathList) {
        this.spec = getRequestSpec();
        this.request = given(this.spec);
        this.methodSend = method.toString().toLowerCase();
        this.method = method;
        this.url = getUrl(pathList);
        this.endpoint = this.method + " " + this.url;
        out.println(this.endpoint);
    }

    @Description("Generate url path")
    public static String getUrl(String... pathList) {
        return String.join("/", pathList);
    }

    @Description("Builder: get response")
    public Response response() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        this.request = this.request.contentType(ContentType.JSON); // header
        this.request = (this.body != null && (this.method == POST || this.method == PUT) ? this.request.body(this.body) : this.request) // body json
            .when();
        return this.send().andReturn();
    }

    @Description("Send request")
    private Response send() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // получение и вызов метода отправки запроса без аргументов
        Method _method = getMethod(this.request, this.methodSend);
        this.request.basePath(this.url); // задаем базовый путь для запроса
        out.println(getBaseUri());
        out.println(getBasePath());
        this.response = (Response) _method.invoke(this.request);
        out.println(this.response);

        // тестовое получение и вызов метода с аргументами
        Method methodTest = getMethod(this, "printMessage", "hello");
        methodTest.invoke(this, "hello");

        // получение и вызов метода отправки запроса с аргументами
        Method methodWithArgs = getMethod(this.request, this.methodSend, this.url);
        this.response = (Response) methodWithArgs.invoke(this.request, this.url);
        out.println(this.response);

        return this.response;
    }

    public void printMessage(String message) {
        out.println("you invoked me with the message: " + message);
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
        String retrievePath = getFullPath();
        out.println("Full PATH is: " + retrievePath);
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
