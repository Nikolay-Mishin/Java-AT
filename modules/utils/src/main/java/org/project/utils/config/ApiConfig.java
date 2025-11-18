package org.project.utils.config;

import java.beans.ConstructorProperties;

import static io.restassured.RestAssured.given;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static org.project.utils.config.WebBaseConfig.BASE_CONFIG;

public class ApiConfig extends RequestSpecBuilder {

    @ConstructorProperties({})
    public ApiConfig() {
        config();
    }

    @ConstructorProperties({"config"})
    public ApiConfig(WebBaseConfig config) {
        config(config);
    }

    public static RequestSpecification getRequestSpec() {
        return given(new RequestSpecBuilder()
            .setBaseUri(BASE_CONFIG.getBaseUrl())
            .addFilter(new AllureRestAssured())
            .build());
    }

    public ApiConfig config() {
        return config(BASE_CONFIG);
    }

    public ApiConfig config(WebBaseConfig config) {
        return (ApiConfig) setBaseUri(config.getBaseUrl());
    }

    public RequestSpecification get() {
        return given(this
            .addFilter(new AllureRestAssured())
            .build());
    }
}
