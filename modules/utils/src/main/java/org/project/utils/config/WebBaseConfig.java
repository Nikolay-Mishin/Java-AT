package org.project.utils.config;

import static org.aeonbits.owner.Config.LoadPolicy;
import static org.aeonbits.owner.Config.LoadType.MERGE;
import static org.aeonbits.owner.Config.Sources;

import io.restassured.http.ContentType;

import static org.project.utils.config.WebConfig.config;

/**
 *
 */
@LoadPolicy(MERGE)
@Sources({
    "${props.env}",
    "${props.utils.web}",
    "${props.utils.dev}"
})
public interface WebBaseConfig extends BaseConfig {
    /**
     *
     */
    WebBaseConfig BASE_CONFIG = config(WebBaseConfig.class);

    /**
     *
     * @return String
     */
    //at
    @Key("HOST")
    String getHost();

    /**
     *
     * @return String
     */
    @Key("BASE_URL")
    String getBaseUrl();

    /**
     *
     * @return String
     */
    @Key("ROOT_API")
    String getRootApi();

    /**
     *
     * @return String
     */
    @Key("BASE_API")
    String getBaseApi();

    /**
     *
     * @return String
     */
    @Key("USER_LOGIN")
    String getUserLogin();

    /**
     *
     * @return String
     */
    @Key("USER_PASSWORD")
    String getUserPassword();

    /**
     *
     * @return String
     */
    //db
    @Key("DB_URL")
    String getDbUrl();

    /**
     *
     * @return String
     */
    @Key("DB_USER_NAME")
    String getDbLogin();

    /**
     *
     * @return String
     */
    @Key("DB_USER_PASS")
    String getDbPassword();

    /**
     *
     * @return String
     */
    //resources
    @Key("JSON_ROOT")
    String getJsonRoot();

    /**
     *
     * @return String
     */
    @Key("JSON_SCHEMA_ROOT")
    String getJsonSchemaRoot();

    /**
     *
     * @return String
     */
    @Key("POJO_ROOT")
    String getPojoRoot();

    /**
     *
     * @return String
     */
    @Key("TARGET_PACKAGE")
    String getTargetPackage();

    /**
     *
     * @return String
     */
    @Key("TARGET_JSON_PACKAGE")
    String getTargetJsonPackage();

    /**
     *
     * @return String
     */
    //tokens
    @Key("TOKEN_KEYS")
    String getTokenKeys();

    /**
     *
     * @return ContentType
     */
    //headers
    @Key("CONTENT_TYPE")
    ContentType getContentType();

    /**
     *
     * @return ContentType
     */
    @Key("ACCEPT")
    ContentType getAccept();

    /**
     *
     * @return String
     */
    @Key("USER_AGENT")
    String getUserAgent();

    /**
     *
     * @return String
     */
    @Key("USER_AGENT_POSTMAN")
    String getUserAgentPostman();

    /**
     *
     * @return String
     */
    @Key("BASE_HEADERS")
    String getBaseHeaders();

    /**
     *
     * @return String
     */
    @Key("HEADERS")
    String getHeaders();

    //TestWeb
    @Key("endpoint")
    String getEndpoint();

    @Key("token.key")
    String getTokenKey();

    @Key("token")
    String getToken();

    @Key("project.id")
    int getProjectId();

}
