package config;

import org.aeonbits.owner.Config;
import org.project.utils.config.IConfig;

import static org.project.utils.config.Config.setConfig;

@Config.Sources({"classpath:${env}.properties"})
public interface WebConfig extends IConfig {

    WebConfig BASE_CONFIG = setConfig(WebConfig.class);

}
