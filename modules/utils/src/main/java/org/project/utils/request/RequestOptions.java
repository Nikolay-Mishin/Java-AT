package org.project.utils.request;

import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.Map;

import io.restassured.http.*;
import io.restassured.specification.*;
import jdk.jfr.Description;

import org.project.utils.fs.FS;

public class RequestOptions<T extends RequestOptions<T>> extends org.project.utils.request.Response<T> {
    protected String baseUrl;
    protected String url;
    protected URI URI;
    protected String path;
    protected String endpoint;

    @Description("Builder: get headers")
    public static Headers getHeaders(RequestSpecification req) {
        if (req instanceof FilterableRequestSpecification) {
            return ((FilterableRequestSpecification) req).getHeaders();
        }
        return new Headers();
    }

    @Description("Builder: get spec")
    public RequestSpecification spec() {
        return request;
    }

    @Description("Get url")
    public String url() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return url;
    }

    @Description("Get uri")
    public URI uri() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return URI;
    }

    @Description("Get path")
    public String path() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return path;
    }

    @Description("Get endpoint")
    public String endpoint() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return endpoint;
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

    @Description("Builder: set url request")
    public T url(Object... pathList) throws MalformedURLException, URISyntaxException {
        path(pathList);
        url = fullPath();
        URL = new URL(url);
        URI = new URI(baseUri());
        return (T) this;
    }

    @Description("Set base uri")
    public T uri(String uri) throws MalformedURLException, URISyntaxException {
        return uri(uri, path);
    }

    @Description("Set base uri")
    public T uri(String uri, Object... pathList) throws MalformedURLException, URISyntaxException {
        request.baseUri(uri);
        url(pathList);
        return (T) this;
    }

    @Description("Builder: set path request")
    public T path(Object... pathList) {
        request.basePath(FS.path(pathList)); // задаем базовый путь для запроса
        path = basePath();
        return (T) this;
    }

    @Description("Builder: set endpoint")
    public T endpoint(Object... pathList) throws MalformedURLException, URISyntaxException {
        url(baseUrl, pathList);
        return (T) this;
    }

    @Description("Builder: get auth spec")
    public AuthenticationSpecification auth() {
        return request.auth();
    }

    @Description("Builder: set spec")
    public RequestSpecification spec(RequestSpecification spec) {
        return request.spec(spec);
    }

    @Description("Builder: set content type")
    public T contentType(ContentType contentType) {
        request.contentType(contentType);
        return (T) this;
    }

    @Description("Builder: set content type")
    public T contentType(String contentType) {
        request.contentType(contentType);
        return (T) this;
    }

    @Description("Builder: no content type")
    public T noContentType() {
        request.noContentType();
        return (T) this;
    }

    @Description("Builder: set accept")
    public T accept(ContentType contentType) {
        request.accept(contentType);
        return (T) this;
    }

    @Description("Builder: set accept")
    public T accept(String accept) {
        request.accept(accept);
        return (T) this;
    }

    @Description("Builder: get headers")
    public Headers getHeaders() {
        return getHeaders(request);
    }

    @Description("Builder: set headers")
    public T headers(Headers headers) {
        request.headers(headers);
        return (T) this;
    }

    @Description("Builder: set headers")
    public T headers(Map<String, ?> map) {
        request.headers(map);
        return (T) this;
    }

    @Description("Builder: set headers")
    public T headers(Header... headers) {
        return headers(new Headers());
    }

    @Description("Builder: set headers")
    public T headers(String s, Object o, Object... objects) {
        request.headers(s, o, objects);
        return (T) this;
    }

    @Description("Builder: set header")
    public T header(Header header) {
        request.header(header);
        return (T) this;
    }

    @Description("Builder: set header")
    public T header(String name, String value) {
        request.header(new Header(name, value));
        return (T) this;
    }

    @Description("Builder: set header")
    public T header(String s, Object o, Object... objects) {
        request.header(s, o, objects);
        return (T) this;
    }

    @Description("Builder: set sessionId")
    public T sessionId(String s) {
        request.sessionId(s);
        return (T) this;
    }

    @Description("Builder: set sessionId")
    public T sessionId(String s, String s1) {
        request.sessionId(s, s1);
        return (T) this;
    }

    @Description("Builder: set port")
    public T port(int port) {
        request.port(port);
        return (T) this;
    }
}
