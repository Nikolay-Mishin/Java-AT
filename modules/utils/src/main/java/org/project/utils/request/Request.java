package org.project.utils.request;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.Map;

import static io.restassured.http.ContentType.JSON;

import io.restassured.http.*;
import io.restassured.response.Response;
import io.restassured.specification.*;
import jdk.jfr.Description;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.ApiConfig.*;
import static org.project.utils.constant.RequestConstants.METHOD.*;
import static org.project.utils.fs.FS.path;
import static org.project.utils.reflection.Reflection.*;

import org.project.utils.constant.RequestConstants.METHOD;

public class Request {

    private RequestSpecification request;
    private final METHOD method;
    private final String methodSend;
    private final String path;
    private final String url;
    private final URL URL;
    private final URI URI;
    private final String endpoint;
    private Object body;

    @ConstructorProperties({})
    public Request() {
        request = null;
        method = null;
        methodSend = null;
        path = null;
        url = null;
        URL = null;
        URI = null;
        endpoint = null;
    }

    @ConstructorProperties({"method", "pathList"})
    public Request(METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException {
        //request = givenRequestSpec();
        request = getRequestSpec();
        this.method = method;
        methodSend = method.toString().toLowerCase();
        path = path(pathList);
        request.basePath(path); // задаем базовый путь для запроса
        url = fullPath();
        URL = new URL(url);
        URI = new URI(path);
        endpoint = this.method + " " + path;
        debug(this.endpoint);
        debug(baseUri());
        debug(basePath());
        printFullPath();
        debug(URI);
        debug(URL);
    }

    @Description("Send request")
    protected Response send() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invoke(request, methodSend, URL); // вызов метода с аргументами
    }

    @Description("Builder: set body request")
    public Request body(Object body) {
        this.body = body;
        return this;
    }

    @Description("Builder: get response")
    public Response response() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        contentType(JSON); // header
        request = (body != null && (method == POST || method == PUT) ? request.body(body) : request) // body json
            .when();
        return send().andReturn();
    }

    @Description("Builder: set content type")
    public Request contentType(ContentType contentType) {
        request = request.contentType(contentType);
        return this;
    }

    @Description("Builder: set content type")
    public Request contentType(String contentType) {
        request = request.contentType(contentType);
        return this;
    }

    @Description("Builder: no content type")
    public Request noContentType() {
        request = request.noContentType();
        return this;
    }

    @Description("Builder: set headers")
    public Request headers(Headers headers) {
        request = request.headers(headers);
        return this;
    }

    @Description("Builder: set headers")
    public Request headers(Map<String, ?> map) {
        request = request.headers(map);
        return this;
    }

    @Description("Builder: set headers")
    public Request headers(String s, Object o, Object... objects) {
        request = request.headers(s, o, objects);
        return this;
    }

    @Description("Builder: set header")
    public Request header(Header header) {
        request = request.header(header);
        return this;
    }

    @Description("Builder: set header")
    public Request header(String s, Object o, Object... objects) {
        request = request.header(s, o, objects);
        return this;
    }

    @Description("Get query request")
    public QueryableRequestSpecification query() {
        request.get();
        return SpecificationQuerier.query(request);
    }

    @Description("Get base path")
    public String baseUri() {
        return query().getBaseUri();
    }

    @Description("Get base path")
    public String basePath() {
        return query().getBasePath();
    }

    @Description("Get full path")
    public String fullPath() {
        return query().getURI();
    }

    @Description("Print full path")
    public void printFullPath() {
        debug("Full PATH is: " + url);
    }

    @Description("Get endpoint")
    public String endpoint() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return (String) getProp(this, "endpoint");
    }

    @Description("Get url")
    public String url() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
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
        debug(endpoint);
        debug(url);
        debug(method);
        //debug(methodStr);
    }

}
