package org.project.utils.request;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
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

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.toMap;
import static org.project.utils.config.WebConfig.config;

import org.project.utils.config.WebBaseConfig;
import org.project.utils.fs.FS;
import org.project.utils.json.JsonSchema;

/**
 *
 */
public class RequestOptions extends org.project.utils.request.Response {
    /**
     *
     */
    protected String baseUrl;
    /**
     *
     */
    protected String url;
    /**
     *
     */
    protected URI URI;
    /**
     *
     */
    protected String path;
    /**
     *
     */
    protected String endpoint;

    /**
     * Builder: get headers
     * @param req RequestSpecification
     * @return Headers
     */
    public static Headers getHeaders(RequestSpecification req) {
        if (req instanceof FilterableRequestSpecification) {
            return ((FilterableRequestSpecification) req).getHeaders();
        }
        return new Headers();
    }

    /**
     * Builder: get headers
     * @param headers Headers
     * @return List {Header}
     */
    public static List<Header> getHeaders(Headers headers) {
        return headers.asList();
    }

    /**
     * Builder: get spec
     * @return RequestSpecification
     */
    public RequestSpecification spec() {
        return request;
    }

    /**
     * Get baseUrl
     * @return String
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public String baseUrl() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return baseUrl;
    }

    /**
     * Get url
     * @return String
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public String url() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return url;
    }

    /**
     * Get uri
     * @return URI
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public URI uri() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return URI;
    }

    /**
     * Get path
     * @return String
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public String path() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return path;
    }

    /**
     * Get endpoint
     * @return String
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public String endpoint() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return endpoint;
    }

    /**
     * Get query request
     * @return QueryableRequestSpecification
     */
    public QueryableRequestSpecification query() {
        request.get();
        return SpecificationQuerier.query(request);
    }

    /**
     * Set query request
     * @param args Object[]
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R query(Object... args) {
        Map<String, ?> map = JsonSchema.toMap(args);
        debug("query: " + map);
        request.queryParams(map);
        return (R) this;
    }

    /**
     * Set query request
     * @param map Map {String, ?}
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R query(Map<String, ?> map) {
        debug("query: " + map);
        request.queryParams(map);
        return (R) this;
    }

    /**
     * Set query request
     * @param s String
     * @param objects Object[]
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R query(String s, Object... objects) {
        request.queryParam(s, objects);
        return (R) this;
    }

    /**
     * Set query request
     * @param s String
     * @param c Collection
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R query(String s, Collection<?> c) {
        request.queryParam(s, c);
        return (R) this;
    }

    /**
     * Set query request
     * @param s String
     * @param o Object
     * @param objects Object[]
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R query(String s, Object o, Object... objects) {
        request.queryParams(s, o, objects);
        return (R) this;
    }
    /**
     * Get base path
     * @return String
     */
    public String baseUri() {
        return query().getBaseUri();
    }

    /**
     * Get base path
     * @return String
     */
    public String basePath() {
        return query().getBasePath();
    }

    /**
     * Get full path
     * @return String
     */
    public String fullPath() {
        return query().getURI();
    }

    /**
     * Builder: set baseUrl request
     * @param pathList Object[]
     * @return R
     * @param <R> extends RequestOptions
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R baseUrl(Object... pathList) throws MalformedURLException, URISyntaxException {
        url(pathList);
        baseUrl = basePath();
        return (R) this;
    }

    /**
     * Builder: set url request
     * @param pathList Object[]
     * @return R
     * @param <R> extends RequestOptions
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R url(Object... pathList) throws MalformedURLException, URISyntaxException {
        path(pathList);
        url = fullPath();
        URL = new URL(url);
        URI = new URI(baseUri());
        return (R) this;
    }

    /**
     * Set base uri
     * @param uri String
     * @return R
     * @param <R> extends RequestOptions
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     */
    public <R extends RequestOptions> R uri(String uri) throws MalformedURLException, URISyntaxException {
        return uri(uri, path);
    }

    /**
     * Set base uri
     * @param uri String
     * @param pathList Object[]
     * @return R
     * @param <R> extends RequestOptions
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R uri(String uri, Object... pathList) throws MalformedURLException, URISyntaxException {
        request.baseUri(uri);
        url(pathList);
        return (R) this;
    }

    /**
     * Builder: set path request
     * @param pathList Object[]
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R path(Object... pathList) {
        request.basePath(FS.path(pathList)); // задаем базовый путь для запроса
        path = basePath();
        return (R) this;
    }

    /**
     * Builder: set endpoint
     * @param pathList Object[]
     * @return R
     * @param <R> extends RequestOptions
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R endpoint(Object... pathList) throws MalformedURLException, URISyntaxException {
        url(baseUrl, pathList);
        return (R) this;
    }

    /**
     * Builder: get auth spec
     * @return AuthenticationSpecification
     */
    public AuthenticationSpecification auth() {
        return request.auth();
    }

    /**
     * Builder: set spec
     * @param spec RequestSpecification
     * @return RequestSpecification
     */
    public RequestSpecification spec(RequestSpecification spec) {
        return request.spec(spec);
    }

    /**
     * Builder: set content type
     * @param contentType ContentType
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R contentType(ContentType contentType) {
        request.contentType(contentType);
        return (R) this;
    }

    /**
     * Builder: set content type
     * @param contentType String
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R contentType(String contentType) {
        request.contentType(contentType);
        return (R) this;
    }

    /**
     * Builder: no content type
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R noContentType() {
        request.noContentType();
        return (R) this;
    }

    /**
     * Builder: set accept
     * @param contentType ContentType
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R accept(ContentType contentType) {
        request.accept(contentType);
        return (R) this;
    }

    /**
     * Builder: set accept
     * @param accept String
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R accept(String accept) {
        request.accept(accept);
        return (R) this;
    }

    /**
     * Builder: get headers
     * @return Headers
     */
    public Headers getHeaders() {
        return getHeaders(request);
    }

    /**
     * Builder: get headers
     * @return List {Header}
     */
    public List<Header> getHeadersList() {
        return getHeaders(request).asList();
    }

    /**
     * Builder: set headers
     * @param headers Headers
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R headers(Headers headers) {
        request.headers(headers);
        return (R) this;
    }

    /**
     * Builder: set headers
     * @param map Map {String, ?}
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R headers(Map<String, ?> map) {
        request.headers(map);
        return (R) this;
    }

    /**
     * Builder: set headers
     * @param headers Header[]
     * @return R
     * @param <R> extends RequestOptions
     */
    public <R extends RequestOptions> R headers(Header... headers) {
        return headers(new Headers(headers));
    }

    /**
     * Builder: set headers
     * @param s String
     * @param o Object
     * @param objects Object[]
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R headers(String s, Object o, Object... objects) {
        request.headers(s, o, objects);
        return (R) this;
    }

    /**
     * Builder: set headers
     * @return R
     * @param <R> extends RequestOptions
     */
    public <R extends RequestOptions> R setHeaders() {
        return headers(config());
    }

    /**
     * Builder: set headers
     * @param config WebBaseConfig
     * @return R
     * @param <R> extends RequestOptions
     */
    public <R extends RequestOptions> R headers(WebBaseConfig config) {
        return headers(config.getHeaders());
    }

    /**
     * Builder: set headers
     * @param json String
     * @return R
     * @param <R> extends RequestOptions
     */
    public <R extends RequestOptions> R headers(String json) {
        return headers(new JsonSchema(json));
    }

    /**
     * Builder: set headers
     * @param json JsonSchema
     * @return R
     * @param <R> extends RequestOptions
     */
    public <R extends RequestOptions> R headers(JsonSchema json) {
        return headers(toMap(new JsonSchema(config().getBaseHeaders()).toMap(), json.toMap()));
    }

    /**
     * Builder: set header
     * @param header Header
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R header(Header header) {
        request.header(header);
        return (R) this;
    }

    /**
     * Builder: set header
     * @param name String
     * @param value String
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R header(String name, String value) {
        request.header(new Header(name, value));
        return (R) this;
    }

    /**
     * Builder: set header
     * @param s String
     * @param o Object
     * @param objects Object[]
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R header(String s, Object o, Object... objects) {
        request.header(s, o, objects);
        return (R) this;
    }

    /**
     * Builder: set sessionId
     * @param s String
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R sessionId(String s) {
        request.sessionId(s);
        return (R) this;
    }

    /**
     * Builder: set sessionId
     * @param s String
     * @param s1 String
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R sessionId(String s, String s1) {
        request.sessionId(s, s1);
        return (R) this;
    }

    /**
     * Builder: set port
     * @param port int
     * @return R
     * @param <R> extends RequestOptions
     */
    @SuppressWarnings("unchecked")
    public <R extends RequestOptions> R port(int port) {
        request.port(port);
        return (R) this;
    }
}
