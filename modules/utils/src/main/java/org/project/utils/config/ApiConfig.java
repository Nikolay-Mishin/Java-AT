package org.project.utils.config;

import java.util.Map;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiConfig extends Config {
    public static RequestSpecification givenRequestSpec() {
        return given(getRequestSpec());
    }

    public static RequestSpecification getRequestSpec() {
        return requestSpec()
            .addFilter(new AllureRestAssured())
            .build();
    }

    public static RequestSpecBuilder requestSpecUri(String uri) {
        return new RequestSpecBuilder().setBaseUri(uri);
    }

    public static RequestSpecBuilder requestSpec() {
        return requestSpec(WebConfig.config());
    }

    public static RequestSpecBuilder requestSpec(WebBaseConfig config) {
        return requestSpecUri(config.getBaseUrl());
    }

    public static RequestSpecBuilder requestSpec(ContentType contentType) {
        return requestSpec().setContentType(contentType);
    }

    public static RequestSpecBuilder requestSpec(String contentType) {
        return requestSpec().setContentType(contentType);
    }

    public static RequestSpecBuilder requestSpec(Map<String, String> headers) {
        return requestSpec().addHeaders(headers);
    }

    public static RequestSpecBuilder requestSpec(String headerName, String headerValue) {
        return requestSpec().addHeader(headerName, headerValue);
    }

    public static RequestSpecBuilder requestSpec(ContentType contentType, Map<String, String> headers) {
        return requestSpec(contentType).addHeaders(headers);
    }

    public static RequestSpecBuilder requestSpec(String contentType, Map<String, String> headers) {
        return requestSpec(contentType).addHeaders(headers);
    }

    public static RequestSpecBuilder requestSpec(ContentType contentType, String headerName, String headerValue) {
        return requestSpec(contentType).addHeader(headerName, headerValue);
    }

    public static RequestSpecBuilder requestSpec(String contentType, String headerName, String headerValue) {
        return requestSpec(contentType).addHeader(headerName, headerValue);
    }

    public static RequestSpecBuilder requestSpec(WebBaseConfig config, ContentType contentType) {
        return requestSpec(config).setContentType(contentType);
    }

    public static RequestSpecBuilder requestSpec(WebBaseConfig config, String contentType) {
        return requestSpec(config).setContentType(contentType);
    }

    public static RequestSpecBuilder requestSpec(WebBaseConfig config, Map<String, String> headers) {
        return requestSpec(config).addHeaders(headers);
    }

    public static RequestSpecBuilder requestSpec(WebBaseConfig config, String headerName, String headerValue) {
        return requestSpec(config).addHeader(headerName, headerValue);
    }

    public static RequestSpecBuilder requestSpec(WebBaseConfig config, ContentType contentType, Map<String, String> headers) {
        return requestSpec(config, contentType).addHeaders(headers);
    }

    public static RequestSpecBuilder requestSpec(WebBaseConfig config, String contentType, Map<String, String> headers) {
        return requestSpec(config, contentType).addHeaders(headers);
    }

    public static RequestSpecBuilder requestSpec(WebBaseConfig config, ContentType contentType, String headerName, String headerValue) {
        return requestSpec(config, contentType).addHeader(headerName, headerValue);
    }

    public static RequestSpecBuilder requestSpec(WebBaseConfig config, String contentType, String headerName, String headerValue) {
        return requestSpec(config, contentType).addHeader(headerName, headerValue);
    }
}
