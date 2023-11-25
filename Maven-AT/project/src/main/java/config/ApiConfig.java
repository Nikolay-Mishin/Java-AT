package config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static config.WebConfig.BASE_CONFIG;

public class ApiConfig {

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
            .setBaseUri(BASE_CONFIG.getBaseUrl())
            .addFilter(new AllureRestAssured())
            .build();
    }

}
