package org.project.utils.config;

import static io.restassured.RestAssured.given;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static org.project.utils.config.WebConfig.config;
import static org.project.utils.constant.ContentType.getAccept;
import static org.project.utils.constant.ContentType.getContentType;

public class ApiConfig extends RequestSpecBuilder {

    public static RequestSpecification requestSpec() {
        return requestSpec(config());
    }

    public static RequestSpecification requestSpec(WebBaseConfig config) {
        return requestSpec(new RequestSpecBuilder(), config);
    }

    public static RequestSpecification requestSpec(RequestSpecBuilder builder, WebBaseConfig config) {
        return given(builder
            .setBaseUri(config.getBaseUrl())
            .setAccept(getAccept())
            .setContentType(getContentType())
            .addFilter(new AllureRestAssured())
            .build());
    }

    public RequestSpecification get() {
        return get(config());
    }

    public RequestSpecification get(WebBaseConfig config) {
        return requestSpec(this, config);
    }

}
