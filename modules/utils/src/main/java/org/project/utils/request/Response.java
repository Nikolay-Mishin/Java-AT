package org.project.utils.request;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import static org.project.utils.Helper.notNull;
import static org.project.utils.constant.RequestConstants.METHOD.PATCH;
import static org.project.utils.constant.RequestConstants.METHOD.POST;
import static org.project.utils.constant.RequestConstants.METHOD.PUT;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.constant.RequestConstants.METHOD;

/**
 *
 */
public class Response {
    /**
     *
     */
    protected RequestSpecification request;
    /**
     *
     */
    protected METHOD method;
    /**
     *
     */
    protected String methodSend;
    /**
     *
     */
    protected URL URL;
    /**
     *
     */
    protected Object body;
    /**
     *
     */
    protected io.restassured.response.Response response;

    /**
     * Builder: get response body
     * @return ResponseBody
     * @throws ReflectiveOperationException throws
     */
    public ResponseBody<?> body() throws ReflectiveOperationException {
        return getResponse().body();
    }

    /**
     * Builder: set request body
     * @param body Object
     * @return R
     * @param <R> R
     */
    @SuppressWarnings("unchecked")
    public <R extends Response> R body(Object body) {
        this.body = body;
        request.body(body);
        return (R) this;
    }

    /**
     * Builder: get response
     * @return Response
     * @throws ReflectiveOperationException throws
     */
    public io.restassured.response.Response response() throws ReflectiveOperationException {
        return response(body);
    }

    /**
     * Builder: get response
     * @param body Object
     * @return Response
     * @throws ReflectiveOperationException throws
     */
    public io.restassured.response.Response response(Object body) throws ReflectiveOperationException {
        if (body != null && (method == POST || method == PUT || method == PATCH)) body(body); // body json
        return response = send().andReturn();
    }

    /**
     * Send request
     * @return Response
     * @throws ReflectiveOperationException throws
     */
    protected io.restassured.response.Response send() throws ReflectiveOperationException {
        return invoke(request.when(), methodSend, URL); // вызов метода с аргументами
    }

    /**
     * Send request
     * @param request RequestSpecification
     * @return Response
     * @throws ReflectiveOperationException throws
     */
    protected io.restassured.response.Response send(RequestSpecification request)
        throws ReflectiveOperationException {
        return invoke(request.when(), methodSend, URL); // вызов метода с аргументами
    }

    /**
     * Builder: get response
     * @return Response
     * @throws ReflectiveOperationException throws
     */
    public io.restassured.response.Response getResponse() throws ReflectiveOperationException {
        return notNull(response) ? response : response();
    }

    /**
     * Response: get statusCode
     * @return int
     * @throws ReflectiveOperationException throws
     */
    public int statusCode() throws ReflectiveOperationException {
        return getResponse().statusCode();
    }

    /**
     * Response: get response as String
     * @return String
     * @throws ReflectiveOperationException throws
     */
    public String string() throws ReflectiveOperationException {
        return getResponse().asString();
    }

    /**
     * Response: get response as PrettyString
     * @return String
     * @throws ReflectiveOperationException throws
     */
    public String pretty() throws ReflectiveOperationException {
        return getResponse().asPrettyString();
    }

    /**
     * Response: get response as InputStream
     * @return InputStream
     * @throws ReflectiveOperationException throws
     */
    public InputStream stream() throws ReflectiveOperationException {
        return response().asInputStream();
    }

    /**
     * Response: get response as ByteArray
     * @return byte[]
     * @throws ReflectiveOperationException throws
     */
    public byte[] bytes() throws ReflectiveOperationException {
        return response().asByteArray();
    }

    /**
     * Response: get contentType
     * @return String
     * @throws ReflectiveOperationException throws
     */
    public String contentType() throws ReflectiveOperationException {
        return getResponse().contentType();
    }

    /**
     * Response: get headers
     * @return Headers
     * @throws ReflectiveOperationException throws
     */
    public Headers headers() throws ReflectiveOperationException {
        return getResponse().headers();
    }

    /**
     * Response: get sessionId
     * @return String
     * @throws ReflectiveOperationException throws
     */
    public String sessionId() throws ReflectiveOperationException {
        return getResponse().sessionId();
    }

    /**
     * Response: get cookies
     * @return Map {String, String}
     * @throws ReflectiveOperationException throws
     */
    public Map<String, String> cookies() throws ReflectiveOperationException {
        return getResponse().cookies();
    }

    /**
     * Response: get detailedCookies
     * @return Cookies
     * @throws ReflectiveOperationException throws
     */
    public Cookies detailedCookies() throws ReflectiveOperationException {
        return getResponse().detailedCookies();
    }
}
