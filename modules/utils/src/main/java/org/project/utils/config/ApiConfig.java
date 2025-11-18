package org.project.utils.config;

import java.beans.ConstructorProperties;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import static io.restassured.RestAssured.given;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.project.utils.constant.RequestConstants;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.WebBaseConfig.BASE_CONFIG;
import static org.project.utils.fs.FS.path;

public class ApiConfig extends RequestSpecBuilder {

    @ConstructorProperties({})
    public ApiConfig() {
        config();
    }

    public static RequestSpecification getRequestSpec() {
        return given(new RequestSpecBuilder()
            .setBaseUri(BASE_CONFIG.getBaseUrl())
            .addFilter(new AllureRestAssured())
            .build());
    }

    public RequestSpecification get() {
        return given(this
            .addFilter(new AllureRestAssured())
            .build());
    }

    public ApiConfig config() {
        return config(BASE_CONFIG);
    }

    public ApiConfig config(WebBaseConfig config) {
        return baseUri(config.getBaseUrl());
    }

    public ApiConfig baseUri(String uri) {
        return (ApiConfig) setBaseUri(uri);
    }

    public ApiConfig contentType(ContentType contentType) {
        return (ApiConfig) setContentType(contentType);
    }

    public ApiConfig contentType(String contentType) {
        return (ApiConfig) setContentType(contentType);
    }

    public ApiConfig headers(Map<String, String> headers) {
        return (ApiConfig) addHeaders(headers);
    }

    public ApiConfig header(String name, String value) {
        return (ApiConfig) addHeader(name, value);
    }
}
