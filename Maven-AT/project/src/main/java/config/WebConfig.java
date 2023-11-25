package config;

import org.aeonbits.owner.Config;
import utils.config.IConfig;

import static utils.config.Config.setConfig;

@Config.Sources({"classpath:${env}.properties"})
public interface WebConfig extends IConfig {

    WebConfig BASE_CONFIG = setConfig(WebConfig.class);

}
