package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jdk.jfr.Description;
import utils.constant.RequestConstants.METHOD;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import static config.ApiConfig.getRequestSpec;
import static io.restassured.RestAssured.given;
import static java.lang.System.out;
import static utils.Reflection.getPropStr;
import static utils.Reflection.getProp;
import static utils.constant.RequestConstants.METHOD.*;

public class Request {

    private final RequestSpecification spec;
    private final METHOD method;
    private final String url;
    private final String endpoint;
    private Object body;

    @ConstructorProperties("")
    public Request(METHOD method, String... pathList) {
        this.spec = getRequestSpec();
        this.method = method;
        this.url = getUrl(pathList);
        this.endpoint = this.method + " " + this.url;
    }

    @Description("Generate url path")
    public static String getUrl(String... pathList) {
        return String.join("/", pathList);
    }

    @Description("Base request")
    public Response getResponse() {
        RequestSpecification request = given(this.spec)
            .contentType(ContentType.JSON); // header
        return (this.body != null && (this.method == POST || this.method == PUT) ? request.body(body) : request) // body json
            .when()
            .post(this.url) // endpoint
            .andReturn();
    }

    @Description("Builder: set body request")
    public Request setBody(Object body) {
        this.body = body;
        return this;
    }

    @Description("Get endpoint")
    public String getEndpoint() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (String) getProp(this, "endpoint");
    }

    @Description("Get method")
    public METHOD getMethod() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (METHOD) getProp(this, "method");
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
        getPropStr(this, "method", true);
        return (METHOD) getProp(this, "method", true);
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
        out.println(endpoint);
        out.println(url);
        out.println(method);
    }

}
