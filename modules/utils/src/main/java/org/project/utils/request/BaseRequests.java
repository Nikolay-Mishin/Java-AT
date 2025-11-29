package org.project.utils.request;

import java.lang.reflect.InvocationTargetException;
import java.net.*;

import io.restassured.response.Response;
import jdk.jfr.Description;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.notNull;
import static org.project.utils.constant.RequestConstants.METHOD.*;

public class BaseRequests<T> {
    protected String baseUrl;
    protected Request post;
    protected Request get;
    protected Request put;
    protected Request delete;

    public BaseRequests(String baseUrl) throws MalformedURLException, URISyntaxException {
        init(baseUrl);
    }

    @Description("Get baseUrl")
    public <R extends BaseRequests<T>> R init(String baseUrl) throws MalformedURLException, URISyntaxException {
        debug("setBaseUrl: " + baseUrl);
        baseUrl(baseUrl);
        post = notNull(post) ? post.baseUrl(baseUrl) : new Request(POST, baseUrl);
        get = notNull(get) ? get.baseUrl(baseUrl) : new Request(GET, baseUrl);
        put = notNull(put) ? put.baseUrl(baseUrl) : new Request(PUT, baseUrl);
        delete = notNull(delete) ? delete.baseUrl(baseUrl) : new Request(DELETE, baseUrl);
        return (R) this;
    }

    @Description("Get baseUrl")
    public String baseUrl() {
        return baseUrl;
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
    public Response post(T model) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return post.body(model).response();
    }

    @Description("Find object by ID")
    public Response get(Long id) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return get.endpoint(id).response();
    }

    @Description("Update object")
    public Response put(T model) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return put.body(model).response();
    }

    @Description("Delete object")
    public Response delete(Long id) throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return delete.endpoint(id).response();
    }

}
