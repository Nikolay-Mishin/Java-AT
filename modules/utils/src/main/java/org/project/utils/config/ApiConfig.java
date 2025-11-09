package org.project.utils.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class ApiConfig extends Config {
    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
            .setBaseUri(WebConfig.config().getBaseUrl())
            .addFilter(new AllureRestAssured())
            .build();
    }
}
