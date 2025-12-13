package org.project.utils.request;

import java.beans.ConstructorProperties;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;

import io.restassured.response.Response;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.notNull;
import static org.project.utils.constant.RequestConstants.METHOD.DELETE;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.constant.RequestConstants.METHOD.PATCH;
import static org.project.utils.constant.RequestConstants.METHOD.POST;
import static org.project.utils.constant.RequestConstants.METHOD.PUT;
import static org.project.utils.fs.File.path;
import static org.project.utils.reflection.Reflection.getField;

import org.project.utils.constant.RequestConstants.METHOD;
import org.project.utils.constant.RequestConstants.METHOD_LOWER_CASE;
import org.project.utils.function.FunctionWithExceptions;

/**
 *
 * @param <T>
 */
public class BaseRequests<T> {
    /**
     *
     */
    protected String baseUrl;
    /**
     *
     */
    protected Request get;
    /**
     *
     */
    protected Request post;
    /**
     *
     */
    protected Request put;
    /**
     *
     */
    protected Request patch;
    /**
     *
     */
    protected Request delete;

    /**
     *
     * @param pathList Object[]
     * @throws Exception throws
     */
    @ConstructorProperties({"pathList"})
    public BaseRequests(Object... pathList) throws Exception {
        baseUrl(pathList);
        //init(pathList);
    }

    /**
     * Init baseUrl
     * @param pathList Object[]
     * @return R
     * @param <R> R
     * @throws Exception throws
     */
    public <R extends BaseRequests<T>> R init(Object... pathList) throws Exception {
        debug("setBaseUrl: " + Arrays.toString(pathList));
        baseUrl(pathList);
        return init(req -> req.baseUrl(pathList));
    }

    /**
     * Set uri
     * @param uri String
     * @return R
     * @param <R> R
     * @throws Exception throws
     */
    public <R extends BaseRequests<T>> R uri(String uri) throws Exception {
        debug("setUri: " + uri);
        return init(req -> req.uri(uri));
    }

    /**
     * Set endpoint
     * @param pathList Object[]
     * @return R
     * @param <R> R
     * @throws Exception throws
     */
    public <R extends BaseRequests<T>> R endpoint(Object... pathList) throws Exception {
        debug("setEndpoint: " + Arrays.toString(pathList));
        return init(req -> req.endpoint(pathList));
    }

    /**
     * Init with cb
     * @param cb FunctionWithExceptions
     * @return R
     * @param <R> R
     * @param <V> V
     * @param <E> E
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     * @throws E throws
     */
    @SuppressWarnings("unchecked")
    public <R extends BaseRequests<T>, V extends RequestOptions, E extends Exception> R init(FunctionWithExceptions<Request, V, E> cb)
        throws MalformedURLException, URISyntaxException, NoSuchFieldException, IllegalAccessException, E {
        for (METHOD method : METHOD.values())
            cb.apply(getOrCreate(method));
        return (R) this;
    }

    /**
     * Get baseUrl
     * @return String
     */
    public String baseUrl() {
        return baseUrl;
    }

    /**
     * Set baseUrl
     * @param pathList Object[]
     * @return String
     */
    public String baseUrl(Object... pathList) {
        return baseUrl(path(pathList));
    }

    /**
     * Set baseUrl
     * @param baseUrl String
     * @return String
     */
    public String baseUrl(String baseUrl) {
        return this.baseUrl = baseUrl;
    }

    /**
     * Get or set request
     * @param method METHOD
     * @return Request
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     */
    public Request getOrCreate(METHOD method) throws NoSuchFieldException, IllegalAccessException, MalformedURLException, URISyntaxException {
        return req(req(method.toString().toLowerCase()), method);
    }

    /**
     * Get request
     * @param req Request
     * @param method METHOD
     * @return Request
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     */
    public Request req(Request req, METHOD method) throws NoSuchFieldException, IllegalAccessException, MalformedURLException, URISyntaxException {
        return notNull(req) ? req : req(method);
    }

    /**
     * Get request
     * @param method METHOD_LOWER_CASE
     * @return Request
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    public Request req(METHOD_LOWER_CASE method) throws NoSuchFieldException, IllegalAccessException {
        return req(method.toString());
    }

    /**
     * Get request
     * @param method String
     * @return Request
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    public Request req(String method) throws NoSuchFieldException, IllegalAccessException {
        return (Request) getField(this, method);
    }

    /**
     * Set request
     * @param method METHOD
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     */
    public Request req(METHOD method) throws MalformedURLException, URISyntaxException {
        return new Request(method, baseUrl);
    }

    /**
     * Get GET request
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    public Request get() throws MalformedURLException, URISyntaxException, NoSuchFieldException, IllegalAccessException {
        return req(get, GET);
    }

    /**
     * Set GET request
     * @param req Request
     * @return Request
     */
    public Request get(Request req) {
        return get = req;
    }

    /**
     * Get POST request
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    public Request post() throws MalformedURLException, URISyntaxException, NoSuchFieldException, IllegalAccessException {
        return req(post, POST);
    }

    /**
     * Set POST request
     * @param req Request
     * @return Request
     */
    public Request post(Request req) {
        return post = req;
    }

    /**
     * Get PUT request
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    public Request patch() throws MalformedURLException, URISyntaxException, NoSuchFieldException, IllegalAccessException {
        return req(patch, PATCH);
    }

    /**
     * Set PATCH request
     * @param req Request
     * @return Request
     */
    public Request patch(Request req) {
        return patch = req;
    }

    /**
     * Get PUT request
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    public Request put() throws MalformedURLException, URISyntaxException, NoSuchFieldException, IllegalAccessException {
        return req(put, PUT);
    }

    /**
     * Set PUT request
     * @param req Request
     * @return Request
     */
    public Request put(Request req) {
        return put = req;
    }

    /**
     * Get DELETE request
     * @return Request
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    public Request delete() throws MalformedURLException, URISyntaxException, NoSuchFieldException, IllegalAccessException {
        return req(delete, DELETE);
    }

    /**
     * Set DELETE request
     * @param req Request
     * @return Request
     */
    public Request delete(Request req) {
        return delete = req;
    }

    /**
     * Find object by ID
     * @param id Long
     * @return Response
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public Response get(Long id) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return get().endpoint(id).response();
    }

    /**
     * Add a new object
     * @param model T
     * @return Response
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public Response post(T model) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return post().response(model);
    }

    /**
     * Update object
     * @param model T
     * @return Response
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public Response put(T model) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return put().response(model);
    }

    /**
     * Patch object
     * @param model T
     * @return Response
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public Response patch(T model) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return patch().response(model);
    }

    /**
     * Delete object
     * @param id Long
     * @return Response
     * @throws MalformedURLException throws
     * @throws URISyntaxException throws
     * @throws ReflectiveOperationException throws
     */
    public Response delete(Long id) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return delete().endpoint(id).response();
    }

}
