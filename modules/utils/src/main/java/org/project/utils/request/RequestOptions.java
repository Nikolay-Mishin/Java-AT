package org.project.utils.request;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import jdk.jfr.Description;

import static org.project.utils.Helper.toMap;
import static org.project.utils.config.WebConfig.config;

import org.project.utils.config.WebBaseConfig;
import org.project.utils.fs.FS;
import org.project.utils.json.JsonSchema;

public class RequestOptions extends org.project.utils.request.Response {
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

    @Description("Builder: get headers")
    public static List<Header> getHeaders(Headers headers) {
        return headers.asList();
    }

    @Description("Builder: get spec")
    public RequestSpecification spec() {
        return request;
    }

    @Description("Get baseUrl")
    public String baseUrl() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return baseUrl;
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

    @Description("Builder: set baseUrl request")
    public <R extends RequestOptions> R baseUrl(Object... pathList) throws MalformedURLException, URISyntaxException {
        url(pathList);
        baseUrl = basePath();
        return (R) this;
    }

    @Description("Builder: set url request")
    public <R extends RequestOptions> R url(Object... pathList) throws MalformedURLException, URISyntaxException {
        path(pathList);
        url = fullPath();
        URL = new URL(url);
        URI = new URI(baseUri());
        return (R) this;
    }

    @Description("Set base uri")
    public <R extends RequestOptions> R uri(String uri) throws MalformedURLException, URISyntaxException {
        return uri(uri, path);
    }

    @Description("Set base uri")
    public <R extends RequestOptions> R uri(String uri, Object... pathList) throws MalformedURLException, URISyntaxException {
        request.baseUri(uri);
        url(pathList);
        return (R) this;
    }

    @Description("Builder: set path request")
    public <R extends RequestOptions> R path(Object... pathList) {
        request.basePath(FS.path(pathList)); // задаем базовый путь для запроса
        path = basePath();
        return (R) this;
    }

    @Description("Builder: set endpoint")
    public <R extends RequestOptions> R endpoint(Object... pathList) throws MalformedURLException, URISyntaxException {
        url(baseUrl, pathList);
        return (R) this;
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
    public <R extends RequestOptions> R contentType(ContentType contentType) {
        request.contentType(contentType);
        return (R) this;
    }

    @Description("Builder: set content type")
    public <R extends RequestOptions> R contentType(String contentType) {
        request.contentType(contentType);
        return (R) this;
    }

    @Description("Builder: no content type")
    public <R extends RequestOptions> R noContentType() {
        request.noContentType();
        return (R) this;
    }

    @Description("Builder: set accept")
    public <R extends RequestOptions> R accept(ContentType contentType) {
        request.accept(contentType);
        return (R) this;
    }

    @Description("Builder: set accept")
    public <R extends RequestOptions> R accept(String accept) {
        request.accept(accept);
        return (R) this;
    }

    @Description("Builder: get headers")
    public Headers getHeaders() {
        return getHeaders(request);
    }

    @Description("Builder: get headers")
    public List<Header> getHeadersList() {
        return getHeaders(request).asList();
    }

    @Description("Builder: set headers")
    public <R extends RequestOptions> R headers(Headers headers) {
        request.headers(headers);
        return (R) this;
    }

    @Description("Builder: set headers")
    public <R extends RequestOptions> R headers(Map<String, ?> map) {
        request.headers(map);
        return (R) this;
    }

    @Description("Builder: set headers")
    public <R extends RequestOptions> R headers(Header... headers) {
        return headers(new Headers(headers));
    }

    @Description("Builder: set headers")
    public <R extends RequestOptions> R headers(String s, Object o, Object... objects) {
        request.headers(s, o, objects);
        return (R) this;
    }

    @Description("Builder: set headers")
    public <R extends RequestOptions> R setHeaders() {
        return headers(config());
    }

    @Description("Builder: set headers")
    public <R extends RequestOptions> R headers(WebBaseConfig config) {
        return headers(config.getHeaders());
    }

    @Description("Builder: set headers")
    public <R extends RequestOptions> R headers(String json) {
        return headers(new JsonSchema(json));
    }

    @Description("Builder: set headers")
    public <R extends RequestOptions> R headers(JsonSchema json) {
        return headers(toMap(new JsonSchema(config().getBaseHeaders()).toMap(), json.toMap()));
    }

    @Description("Builder: set header")
    public <R extends RequestOptions> R header(Header header) {
        request.header(header);
        return (R) this;
    }

    @Description("Builder: set header")
    public <R extends RequestOptions> R header(String name, String value) {
        request.header(new Header(name, value));
        return (R) this;
    }

    @Description("Builder: set header")
    public <R extends RequestOptions> R header(String s, Object o, Object... objects) {
        request.header(s, o, objects);
        return (R) this;
    }

    @Description("Builder: set sessionId")
    public <R extends RequestOptions> R sessionId(String s) {
        request.sessionId(s);
        return (R) this;
    }

    @Description("Builder: set sessionId")
    public <R extends RequestOptions> R sessionId(String s, String s1) {
        request.sessionId(s, s1);
        return (R) this;
    }

    @Description("Builder: set port")
    public <R extends RequestOptions> R port(int port) {
        request.port(port);
        return (R) this;
    }
}
