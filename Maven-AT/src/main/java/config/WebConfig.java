package config;

import org.aeonbits.owner.Config;
import static org.aeonbits.owner.ConfigFactory.*;

@Config.Sources({"classpath:${env}.properties"})
public interface WebConfig extends Config {

    WebConfig BASE_CONFIG = create(WebConfig.class, System.getenv(), System.getProperties());

    @Key("BASE_URL")
    String getBaseUrl();

    @Key("USER_LOGIN")
    String getUserLogin();

    @Key("USER_PASSWORD")
    String getUserPassword();

}
