package config;

import org.aeonbits.owner.Config;
import static org.aeonbits.owner.ConfigFactory.*;

@Config.Sources({"classpath:${env}.properties"})
public interface WebConfig extends Config {

    WebConfig BASE_CONFIG = create(WebConfig.class, System.getenv(), System.getProperties());

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

    //tokens
    @Key("TOKEN_KEYS")
    String getTokenKeys();

}
