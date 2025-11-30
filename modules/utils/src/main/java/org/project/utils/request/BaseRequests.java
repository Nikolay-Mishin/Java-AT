package org.project.utils.request;

import java.lang.reflect.InvocationTargetException;
import java.net.*;

import io.restassured.response.Response;
import jdk.jfr.Description;

import static org.project.utils.Helper.debug;
import static org.project.utils.constant.RequestConstants.METHOD.*;

public class BaseRequests<T> {
    protected String baseUrl;
    protected Request post = new Request(POST);
    protected Request get = new Request(GET);
    protected Request put = new Request(PUT);
    protected Request delete = new Request(DELETE);

    public BaseRequests(String baseUrl) throws MalformedURLException, URISyntaxException {
        init(baseUrl);
    }

    @Description("Init baseUrl")
    public <R extends BaseRequests<T>> R init(String baseUrl) throws MalformedURLException, URISyntaxException {
        debug("setBaseUrl: " + baseUrl);
        baseUrl(baseUrl);
        post.baseUrl(baseUrl);
        get.baseUrl(baseUrl);
        put.baseUrl(baseUrl);
        delete.baseUrl(baseUrl);
        return (R) this;
    }

    @Description("Set uri")
    public <R extends BaseRequests<T>> R uri(String uri) throws MalformedURLException, URISyntaxException {
        debug("setUri: " + uri);
        post.uri(uri);
        get.uri(uri);
        put.uri(uri);
        delete.uri(uri);
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
