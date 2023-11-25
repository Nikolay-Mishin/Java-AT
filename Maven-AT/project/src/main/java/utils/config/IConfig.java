package utils.config;

import org.aeonbits.owner.Config;

import static utils.config.Config.setConfig;

@Config.Sources({"classpath:${env}.properties"})
public interface IConfig extends Config {

    IConfig BASE_CONFIG = setConfig(IConfig.class);

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
    @Key("JSON_SCHEMA_ROOT")
    String getJsonSchemaRoot();

    @Key("POJO_ROOT")
    String getPojoRoot();

    @Key("TARGET_PACKAGE")
    String getTargetPackage();

    //tokens
    @Key("TOKEN_KEYS")
    String getTokenKeys();

}
