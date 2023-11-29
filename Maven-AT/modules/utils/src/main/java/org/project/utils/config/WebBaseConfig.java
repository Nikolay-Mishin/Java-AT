package org.project.utils.config;

import org.aeonbits.owner.Config;

import static org.project.utils.config.Config.setConfig;

@Config.Sources({"classpath:dev.properties"})
public interface WebBaseConfig extends Config {

    WebBaseConfig BASE_CONFIG = setConfig(WebBaseConfig.class);

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

}
