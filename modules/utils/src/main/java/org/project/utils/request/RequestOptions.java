package org.project.utils.request;

import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.Map;

import io.restassured.http.*;
import io.restassured.specification.*;
import jdk.jfr.Description;

import org.project.utils.fs.FS;

public class RequestOptions extends org.project.utils.request.Response {
    protected String url;
    protected URI URI;
    protected String path;
    protected String endpoint;

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
    public RequestOptions url(Object... pathList) throws MalformedURLException, URISyntaxException {
        path(pathList);
        url = fullPath();
        URL = new URL(url);
        URI = new URI(baseUri());
        return this;
    }

    @Description("Set base uri")
    public RequestOptions uri(String uri) throws MalformedURLException, URISyntaxException {
        return uri(uri, path);
    }

    @Description("Set base uri")
    public RequestOptions uri(String uri, Object... pathList) throws MalformedURLException, URISyntaxException {
        request.baseUri(uri);
        url(pathList);
        return this;
    }

    @Description("Builder: set path request")
    public RequestOptions path(Object... pathList) {
        request.basePath(FS.path(pathList)); // задаем базовый путь для запроса
        path = basePath();
        return this;
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
    public RequestOptions contentType(ContentType contentType) {
        request.contentType(contentType);
        return this;
    }

    @Description("Builder: set content type")
    public RequestOptions contentType(String contentType) {
        request.contentType(contentType);
        return this;
    }

    @Description("Builder: no content type")
    public RequestOptions noContentType() {
        request.noContentType();
        return this;
    }

    @Description("Builder: set accept")
    public RequestOptions accept(ContentType contentType) {
        request.accept(contentType);
        return this;
    }

    @Description("Builder: set accept")
    public RequestOptions accept(String accept) {
        request.accept(accept);
        return this;
    }

    @Description("Builder: set headers")
    public RequestOptions headers(Headers headers) {
        request.headers(headers);
        return this;
    }

    @Description("Builder: set headers")
    public RequestOptions headers(Map<String, ?> map) {
        request.headers(map);
        return this;
    }

    @Description("Builder: set headers")
    public RequestOptions headers(String s, Object o, Object... objects) {
        request.headers(s, o, objects);
        return this;
    }

    @Description("Builder: set header")
    public RequestOptions header(Header header) {
        request.header(header);
        return this;
    }

    @Description("Builder: set header")
    public RequestOptions header(String s, Object o, Object... objects) {
        request.header(s, o, objects);
        return this;
    }

    @Description("Builder: set sessionId")
    public RequestOptions sessionId(String s) {
        request.sessionId(s);
        return this;
    }

    @Description("Builder: set sessionId")
    public RequestOptions sessionId(String s, String s1) {
        request.sessionId(s, s1);
        return this;
    }

    @Description("Builder: set port")
    public RequestOptions port(int port) {
        request.port(port);
        return this;
    }
}
