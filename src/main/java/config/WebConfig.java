package config;

import org.aeonbits.owner.Config.*;

import static org.project.utils.config.WebConfig.*;

import org.project.utils.config.WebBaseConfig;

//@Sources({"classpath:${env}.properties"})
@Sources({"classpath:stand.properties"})
public interface WebConfig extends WebBaseConfig {
    WebConfig BASE_CONFIG = config(WebConfig.class);
    int DEBUG_LEVEL = debugLvl();
}
