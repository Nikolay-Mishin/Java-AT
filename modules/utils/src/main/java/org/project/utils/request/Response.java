package org.project.utils.request;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import jdk.jfr.Description;

import static org.project.utils.Helper.notNull;
import static org.project.utils.constant.RequestConstants.METHOD.PATCH;
import static org.project.utils.constant.RequestConstants.METHOD.POST;
import static org.project.utils.constant.RequestConstants.METHOD.PUT;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.constant.RequestConstants.METHOD;

public class Response {
    protected RequestSpecification request;
    protected METHOD method;
    protected String methodSend;
    protected URL URL;
    protected Object body;
    protected io.restassured.response.Response response;

    @Description("Builder: get response body")
    public ResponseBody<?> body() throws ReflectiveOperationException {
        return getResponse().body();
    }

    @Description("Builder: set request body")
    public <R extends Response> R body(Object body) {
        this.body = body;
        request.body(body);
        return (R) this;
    }

    @Description("Builder: get response")
    public io.restassured.response.Response response() throws ReflectiveOperationException {
        return response(body);
    }

    @Description("Builder: get response")
    public io.restassured.response.Response response(Object body) throws ReflectiveOperationException {
        if (body != null && (method == POST || method == PUT || method == PATCH)) body(body); // body json
        return response = send().andReturn();
    }

    @Description("Send request")
    protected io.restassured.response.Response send() throws ReflectiveOperationException {
        return invoke(request.when(), methodSend, URL); // вызов метода с аргументами
    }

    @Description("Send request")
    protected io.restassured.response.Response send(RequestSpecification request)
        throws ReflectiveOperationException {
        return invoke(request.when(), methodSend, URL); // вызов метода с аргументами
    }

    @Description("Builder: get response")
    public io.restassured.response.Response getResponse() throws ReflectiveOperationException {
        return notNull(response) ? response : response();
    }

    @Description("Response: get statusCode")
    public int statusCode() throws ReflectiveOperationException {
        return getResponse().statusCode();
    }

    @Description("Response: get response as String")
    public String string() throws ReflectiveOperationException {
        return getResponse().asString();
    }

    @Description("Response: get response as PrettyString")
    public String pretty() throws ReflectiveOperationException {
        return getResponse().asPrettyString();
    }

    @Description("Response: get response as InputStream")
    public InputStream stream() throws ReflectiveOperationException {
        return response().asInputStream();
    }

    @Description("Response: get response as ByteArray")
    public byte[] bytes() throws ReflectiveOperationException {
        return response().asByteArray();
    }

    @Description("Response: get contentType")
    public String contentType() throws ReflectiveOperationException {
        return getResponse().contentType();
    }

    @Description("Response: get headers")
    public Headers headers() throws ReflectiveOperationException {
        return getResponse().headers();
    }

    @Description("Response: get sessionId")
    public String sessionId() throws ReflectiveOperationException {
        return getResponse().sessionId();
    }

    @Description("Response: get cookies")
    public Map<String, String> cookies() throws ReflectiveOperationException {
        return getResponse().cookies();
    }

    @Description("Response: get detailedCookies")
    public Cookies detailedCookies() throws ReflectiveOperationException {
        return getResponse().detailedCookies();
    }
}
