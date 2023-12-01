package config;

import org.aeonbits.owner.Config;
import org.project.utils.config.WebBaseConfig;

import static org.project.utils.config.Config.*;

@Config.Sources({"classpath:${env}.properties"})
public interface WebConfig extends WebBaseConfig {

    WebConfig BASE_CONFIG = setConfig(WebConfig.class);

}
