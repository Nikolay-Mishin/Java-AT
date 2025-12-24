package org.project.utils.config;

import static org.aeonbits.owner.Config.LoadPolicy;
import static org.aeonbits.owner.Config.LoadType.MERGE;
import static org.aeonbits.owner.Config.Sources;

import io.restassured.http.ContentType;

import static org.project.utils.config.WebConfig.config;
import static org.project.utils.config.WebConfig.setConfig;

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
    //WebBaseConfig BASE_CONFIG = config(WebBaseConfig.class);
    WebBaseConfig BASE_CONFIG = setConfig();

    /**
     * web
     * @return String
     */
    @Key("BASE_HOST")
    String getBaseHost();

    /**
     * web
     * @return String
     */
    @Key("HOST")
    String getHost();

    /**
     * web
     * @return String
     */
    @Key("HOST_URL")
    String getHostUrl();

    /**
     * web
     * @return String
     */
    @Key("BASE_URL")
    String getBaseUrl();

    /**
     * web
     * @return String
     */
    @Key("ROOT_API")
    String getRootApi();

    /**
     * web
     * @return String
     */
    @Key("BASE_API")
    String getBaseApi();

    /**
     * web
     * @return String
     */
    @Key("BASE_HOST_API")
    String getBaseHostApi();

    /**
     * web
     * @return String
     */
    @Key("USER_LOGIN")
    String getUserLogin();

    /**
     * web
     * @return String
     */
    @Key("USER_PASSWORD")
    String getUserPassword();

    /**
     * db
     * @return String
     */
    @Key("DB_URL")
    String getDbUrl();

    /**
     * db
     * @return String
     */
    @Key("DB_USER_NAME")
    String getDbLogin();

    /**
     * db
     * @return String
     */
    @Key("DB_USER_PASS")
    String getDbPassword();

    /**
     * resources
     * @return String
     */
    @Key("JSON_ROOT")
    String getJsonRoot();

    /**
     * resources
     * @return String
     */
    @Key("JSON_SCHEMA_ROOT")
    String getJsonSchemaRoot();

    /**
     * resources
     * @return String
     */
    @Key("POJO_ROOT")
    String getPojoRoot();

    /**
     * resources
     * @return String
     */
    @Key("TARGET_PACKAGE")
    String getTargetPackage();

    /**
     * resources
     * @return String
     */
    @Key("TARGET_JSON_PACKAGE")
    String getTargetJsonPackage();

    /**
     * auth
     * @return String
     */
    @Key("auth.type")
    String getAuthType();

    /**
     * auth
     * @return String
     */
    @Key("endpoint.auth")
    String getEndpointAuth();

    /**
     * auth
     * @return String
     */
    @Key("username.key")
    String getUsernameKey();

    /**
     * auth
     * @return String
     */
    @Key("password.key")
    String getPasswordKey();

    /**
     * tokens
     * @return String
     */
    @Key("token.access.key")
    String getAccessTokenKey();

    /**
     * tokens
     * @return String
     */
    @Key("token.refresh.key")
    String getRefreshTokenKey();

    /**
     * tokens
     * @return String
     */
    @Key("token.file.key")
    String getFileTokenKey();

    /**
     * tokens
     * @return String
     */
    @Key("token.key")
    String getTokenKey();

    /**
     * tokens
     * @return String
     */
    @Key("token")
    String getToken();

    /**
     * tokens
     * @return String
     */
    @Key("TOKEN_KEYS")
    String getTokenKeys();

    /**
     * headers
     * @return ContentType
     */
    @Key("CONTENT_TYPE")
    ContentType getContentType();

    /**
     * headers
     * @return ContentType
     */
    @Key("ACCEPT")
    ContentType getAccept();

    /**
     * headers
     * @return String
     */
    @Key("USER_AGENT")
    String getUserAgent();

    /**
     * headers
     * @return String
     */
    @Key("USER_AGENT_POSTMAN")
    String getUserAgentPostman();

    /**
     * headers
     * @return String
     */
    @Key("BASE_HEADERS")
    String getBaseHeaders();

    /**
     * headers
     * @return String
     */
    @Key("HEADERS")
    String getHeaders();

}
