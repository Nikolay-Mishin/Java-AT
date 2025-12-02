package org.project.utils.config;

import io.restassured.http.ContentType;

import static org.aeonbits.owner.Config.*;

import static org.project.utils.config.WebConfig.*;

//@Sources({"classpath:dev.properties"})
@Sources({"file:src/main/resources/dev.properties"})
public interface WebBaseConfig extends BaseConfig {
    WebBaseConfig BASE_CONFIG = config(WebBaseConfig.class);

    //at
    @Key("HOST")
    String getHost();

    //at
    @Key("ORIGIN")
    String getOrigin();

    //at
    @Key("BASE_URL")
    String getBaseUrl();

    @Key("USER_LOGIN")
    String getUserLogin();

    @Key("USER_PASSWORD")
    String getUserPassword();

    //db
    @Key("DB_URL")
    String getDbUrl();

    @Key("DB_USER_NAME")
    String getDbLogin();

    @Key("DB_USER_PASS")
    String getDbPassword();

    //resources
    @Key("JSON_ROOT")
    String getJsonRoot();

    @Key("JSON_SCHEMA_ROOT")
    String getJsonSchemaRoot();

    @Key("POJO_ROOT")
    String getPojoRoot();

    @Key("TARGET_PACKAGE")
    String getTargetPackage();

    @Key("TARGET_JSON_PACKAGE")
    String getTargetJsonPackage();

    //tokens
    @Key("TOKEN_KEYS")
    String getTokenKeys();

    //headers
    @Key("CONTENT_TYPE")
    ContentType getContentType();

    @Key("ACCEPT")
    ContentType getAccept();

    @Key("USER_AGENT")
    String getUserAgent();

    @Key("USER_AGENT_POSTMAN")
    String getUserAgentPostman();

    @Key("BASE_HEADERS")
    String getBaseHeaders();

    @Key("HEADERS")
    String getHeaders();
}
