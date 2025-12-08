package org.project.utils.config;

import static org.aeonbits.owner.Config.LoadPolicy;
import static org.aeonbits.owner.Config.LoadType.MERGE;
import static org.aeonbits.owner.Config.Sources;

import io.restassured.http.ContentType;

import static org.project.utils.config.WebConfig.config;

@LoadPolicy(MERGE)
//@Sources({"${props.web}"})
@Sources({"classpath:org.project.utils.web.properties"})
public interface WebBaseConfig extends BaseConfig {
    WebBaseConfig BASE_CONFIG = config(WebBaseConfig.class);

    //at
    @Key("HOST")
    String getHost();

    @Key("ORIGIN")
    String getOrigin();

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
