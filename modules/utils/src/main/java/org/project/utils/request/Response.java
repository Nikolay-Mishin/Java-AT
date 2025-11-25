package org.project.utils.request;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;

import io.restassured.http.*;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import jdk.jfr.Description;

import static org.project.utils.Helper.notNull;
import static org.project.utils.constant.RequestConstants.METHOD.*;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.constant.RequestConstants.METHOD;

public class Response<T extends Response<T>> {
    protected RequestSpecification request;
    protected METHOD method;
    protected String methodSend;
    protected URL URL;
    protected Object body;
    protected io.restassured.response.Response response;

    @Description("Builder: get response body")
    public ResponseBody body() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return getResponse().body();
    }

    @Description("Builder: set request body")
    public T body(Object body) {
        this.body = body;
        request.body(body);
        return (T) this;
    }

    @Description("Builder: get response")
    public io.restassured.response.Response response() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return response(body);
    }

    @Description("Builder: get response")
    public io.restassured.response.Response response(Object body) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //request = (body != null && (method == POST || method == PUT) ? request.body(body) : request).when(); // body json
        if (body != null && (method == POST || method == PUT)) body(body);
        return response = send().andReturn();
    }

    @Description("Send request")
    protected io.restassured.response.Response send() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invoke(request.when(), methodSend, URL); // вызов метода с аргументами
    }

    @Description("Send request")
    protected io.restassured.response.Response send(RequestSpecification request)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        return invoke(request.when(), methodSend, URL); // вызов метода с аргументами
    }

    @Description("Builder: get response")
    public io.restassured.response.Response getResponse() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return notNull(response) ? response : response();
    }

    @Description("Response: get statusCode")
    public int statusCode() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return getResponse().statusCode();
    }

    @Description("Response: get response as String")
    public String string() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return getResponse().asString();
    }

    @Description("Response: get response as PrettyString")
    public String pretty() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return getResponse().asPrettyString();
    }

    @Description("Response: get response as InputStream")
    public InputStream stream() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return response().asInputStream();
    }

    @Description("Response: get response as ByteArray")
    public byte[] bytes() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return response().asByteArray();
    }

    @Description("Response: get contentType")
    public String contentType() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return getResponse().contentType();
    }

    @Description("Response: get headers")
    public Headers headers() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return getResponse().headers();
    }

    @Description("Response: get sessionId")
    public String sessionId() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return getResponse().sessionId();
    }

    @Description("Response: get cookies")
    public Map<String, String> cookies() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return getResponse().cookies();
    }

    @Description("Response: get detailedCookies")
    public Cookies detailedCookies() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return getResponse().detailedCookies();
    }
}
