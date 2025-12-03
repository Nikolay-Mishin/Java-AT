package org.project.utils.request;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;

import io.restassured.response.Response;
import jdk.jfr.Description;

import static org.project.utils.Helper.debug;
import static org.project.utils.constant.RequestConstants.METHOD.DELETE;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.project.utils.constant.RequestConstants.METHOD.PATCH;
import static org.project.utils.constant.RequestConstants.METHOD.POST;
import static org.project.utils.constant.RequestConstants.METHOD.PUT;
import static org.project.utils.fs.File.path;
import static org.project.utils.reflection.Reflection.getField;

import org.project.utils.constant.RequestConstants.METHOD_LOWER_CASE;
import org.project.utils.function.FunctionWithExceptions;

public class BaseRequests<T> {
    protected String baseUrl;
    protected Request post = new Request(POST);
    protected Request get = new Request(GET);
    protected Request put = new Request(PUT);
    protected Request patch = new Request(PATCH);
    protected Request delete = new Request(DELETE);

    public BaseRequests(Object... pathList) throws Exception {
        init(pathList);
    }

    @Description("Init baseUrl")
    public <R extends BaseRequests<T>> R init(Object... pathList) throws Exception {
        debug("setBaseUrl: " + Arrays.toString(pathList));
        baseUrl(pathList);
        return init(req -> req.baseUrl(pathList));
    }

    @Description("Set uri")
    public <R extends BaseRequests<T>> R uri(String uri) throws Exception {
        debug("setUri: " + uri);
        return init(req -> req.uri(uri));
    }

    @Description("Set endpoint")
    public <R extends BaseRequests<T>> R endpoint(Object... pathList) throws Exception {
        debug("setEndpoint: " + Arrays.toString(pathList));
        return init(req -> req.endpoint(pathList));
    }

    @Description("Init with cb")
    public <R extends BaseRequests<T>, V extends RequestOptions, E extends Exception> R init(FunctionWithExceptions<Request, V, E> cb)
        throws URISyntaxException, NoSuchFieldException, IllegalAccessException, E {
        for (METHOD_LOWER_CASE method : METHOD_LOWER_CASE.values())
            cb.apply((Request) getField(this, method.toString()));
        return (R) this;
    }

    @Description("Get baseUrl")
    public String baseUrl() {
        return baseUrl;
    }

    @Description("Set baseUrl")
    public String baseUrl(Object... pathList) {
        return baseUrl(path(pathList));
    }

    @Description("Set baseUrl")
    public String baseUrl(String baseUrl) {
        return this.baseUrl = baseUrl;
    }

    @Description("Get POST request")
    public Request post() {
        return post;
    }

    @Description("Set POST request")
    public Request post(Request req) {
        return post = req;
    }

    @Description("Get GET request")
    public Request get() {
        return get;
    }

    @Description("Set GET request")
    public Request get(Request req) {
        return get = req;
    }

    @Description("Get PUT request")
    public Request patch() {
        return patch;
    }

    @Description("Set PATCH request")
    public Request patch(Request req) {
        return patch = req;
    }

    @Description("Get PUT request")
    public Request put() {
        return put;
    }

    @Description("Set PUT request")
    public Request put(Request req) {
        return put = req;
    }

    @Description("Get DELETE request")
    public Request delete() {
        return delete;
    }

    @Description("Set DELETE request")
    public Request delete(Request req) {
        return delete = req;
    }

    @Description("Add a new object")
    public Response post(T model) throws ReflectiveOperationException {
        return post.response(model);
    }

    @Description("Find object by ID")
    public Response get(Long id) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return get.endpoint(id).response();
    }

    @Description("Update object")
    public Response put(T model) throws ReflectiveOperationException {
        return put.response(model);
    }

    @Description("Patch object")
    public Response patch(T model) throws ReflectiveOperationException {
        return patch.response(model);
    }

    @Description("Delete object")
    public Response delete(Long id) throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        return delete.endpoint(id).response();
    }

}
