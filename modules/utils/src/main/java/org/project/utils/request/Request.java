package org.project.utils.request;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.Map;

import static io.restassured.http.ContentType.*;

import io.restassured.http.*;
import io.restassured.response.Response;
import io.restassured.specification.*;
import jdk.jfr.Description;

import static org.project.utils.Helper.debug;
import static org.project.utils.constant.RequestConstants.METHOD.*;
import static org.project.utils.reflection.Reflection.*;

import org.project.utils.config.ApiConfig;
import org.project.utils.constant.RequestConstants.METHOD;
import org.project.utils.fs.FS;

public class Request {
    private RequestSpecification request;
    private METHOD method;
    private String methodSend;
    private String url;
    private URL URL;
    private URI URI;
    private String path;
    private String endpoint;
    private Object body;

    @ConstructorProperties({"method", "pathList"})
    public Request(METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException {
        init(method, pathList);
    }

    public Request init(METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException {
        return init(new ApiConfig(), method, pathList);
    }

    public Request init(ApiConfig apiConfig, METHOD method, Object... pathList) throws MalformedURLException, URISyntaxException {
        request = apiConfig.get();
        this.method = method;
        methodSend = method.toString().toLowerCase();
        url(pathList); // задаем базовый путь для запроса
        endpoint = this.method + " " + path;
        contentType(JSON); // header
        debug(endpoint);
        debug(URL);
        debug(URI);
        debug(path);
        printFullPath();
        return this;
    }

    @Description("Builder: set url request")
    public Request url(Object... pathList) throws MalformedURLException, URISyntaxException {
        path(pathList);
        url = fullPath();
        URL = new URL(url);
        URI = new URI(baseUri());
        return this;
    }

    @Description("Set base uri")
    public Request uri(String uri) throws MalformedURLException, URISyntaxException {
        request.baseUri(uri);
        URI = new URI(baseUri());
        return this;
    }

    @Description("Builder: set path request")
    public Request path(Object... pathList) {
        request.basePath(FS.path(pathList)); // задаем базовый путь для запроса
        path = basePath();
        return this;
    }

    @Description("Builder: get response")
    public Response response() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //request = (body != null && (method == POST || method == PUT) ? request.body(body) : request).when(); // body json
        if (body != null && (method == POST || method == PUT)) body(body);
        return send().andReturn();
    }

    @Description("Builder: set body request")
    public Request body(Object body) {
        this.body = body;
        request.body(body);
        return this;
    }

    @Description("Send request")
    protected Response send() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invoke(request.when(), methodSend, URL); // вызов метода с аргументами
    }

    @Description("Send request")
    protected Response send(RequestSpecification request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invoke(request.when(), methodSend, URL); // вызов метода с аргументами
    }

    @Description("Builder: get spec")
    public RequestSpecification spec() {
        return request;
    }

    @Description("Builder: set spec")
    public RequestSpecification spec(RequestSpecification spec) {
        return request.spec(spec);
    }

    @Description("Builder: set content type")
    public Request contentType(ContentType contentType) {
        request.contentType(contentType);
        return this;
    }

    @Description("Builder: set content type")
    public Request contentType(String contentType) {
        request.contentType(contentType);
        return this;
    }

    @Description("Builder: no content type")
    public Request noContentType() {
        request.noContentType();
        return this;
    }

    @Description("Builder: set accept")
    public Request accept(ContentType contentType) {
        request.accept(contentType);
        return this;
    }

    @Description("Builder: set accept")
    public Request accept(String accept) {
        request.accept(accept);
        return this;
    }

    @Description("Builder: set headers")
    public Request headers(Headers headers) {
        request.headers(headers);
        return this;
    }

    @Description("Builder: set headers")
    public Request headers(Map<String, ?> map) {
        request.headers(map);
        return this;
    }

    @Description("Builder: set headers")
    public Request headers(String s, Object o, Object... objects) {
        request.headers(s, o, objects);
        return this;
    }

    @Description("Builder: set header")
    public Request header(Header header) {
        request.header(header);
        return this;
    }

    @Description("Builder: set header")
    public Request header(String s, Object o, Object... objects) {
        request.header(s, o, objects);
        return this;
    }

    @Description("Builder: set sessionId")
    public Request sessionId(String s) {
        request.sessionId(s);
        return this;
    }

    @Description("Builder: set sessionId")
    public Request sessionId(String s, String s1) {
        request.sessionId(s, s1);
        return this;
    }

    @Description("Builder: get auth spec")
    public AuthenticationSpecification auth() {
        return request.auth();
    }

    @Description("Builder: get auth spec")
    public Request port(int port) {
        request.port(port);
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
        debug(printEndpoint());
        debug(printUrl());
        debug(printMethod());
        //debug(printMethodStr());
    }

}
