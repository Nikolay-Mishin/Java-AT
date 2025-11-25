package org.project.utils.config;

import java.beans.ConstructorProperties;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.RestAssured.given;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static org.project.utils.config.WebBaseConfig.BASE_CONFIG;

public class ApiConfig extends RequestSpecBuilder {
    protected WebBaseConfig config;

    @ConstructorProperties({})
    public ApiConfig() {
        config();
    }

    @ConstructorProperties({"config"})
    public ApiConfig(WebBaseConfig config) {
        config(config);
    }

    public static RequestSpecification requestSpec() {
        return requestSpec(new RequestSpecBuilder(), BASE_CONFIG);
    }

    public static RequestSpecification requestSpec(WebBaseConfig config) {
        return requestSpec(new RequestSpecBuilder(), config);
    }

    public static RequestSpecification requestSpec(RequestSpecBuilder builder, WebBaseConfig config) {
        return given(builder
            .setBaseUri(config.getBaseUrl())
            .setContentType(JSON)
            .addFilter(new AllureRestAssured())
            .build());
    }

    public ApiConfig config() {
        return config(BASE_CONFIG);
    }

    public ApiConfig config(WebBaseConfig config) {
        this.config = config;
        return this;
    }

    public RequestSpecification get() {
        return get(config);
    }

    public RequestSpecification get(WebBaseConfig config) {
        return requestSpec(this, config);
    }

}
