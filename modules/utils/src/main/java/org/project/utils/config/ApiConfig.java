package org.project.utils.config;

import static io.restassured.RestAssured.given;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static org.project.utils.config.WebConfig.config;
import static org.project.utils.constant.ContentType.getAccept;
import static org.project.utils.constant.ContentType.getContentType;

/**
 *
 */
public class ApiConfig extends RequestSpecBuilder {

    /**
     *
     * @return RequestSpecification
     */
    public static RequestSpecification requestSpec() {
        return requestSpec(config());
    }

    /**
     *
     * @param config WebBaseConfig
     * @return RequestSpecification
     */
    public static RequestSpecification requestSpec(WebBaseConfig config) {
        return requestSpec(new RequestSpecBuilder(), config);
    }

    /**
     *
     * @param builder RequestSpecBuilder
     * @param config WebBaseConfig
     * @return RequestSpecification
     */
    public static RequestSpecification requestSpec(RequestSpecBuilder builder, WebBaseConfig config) {
        return given(builder
            .setBaseUri(config.getBaseApi())
            .setAccept(getAccept())
            .setContentType(getContentType())
            .addFilter(new AllureRestAssured())
            .build());
    }

    /**
     *
     * @return RequestSpecification
     */
    public RequestSpecification get() {
        return get(config());
    }

    /**
     *
     * @param config WebBaseConfig
     * @return RequestSpecification
     */
    public RequestSpecification get(WebBaseConfig config) {
        return requestSpec(this, config);
    }

}
