package org.project.utils.config;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.*;

import static org.project.utils.config.Config.*;

@Sources({"classpath:dev.properties"})
public interface BaseConfig extends Config {
    BaseConfig BASE_CONFIG = config(BaseConfig.class);
    int DEBUG_LEVEL = debugLvl();

    @Key("BASE_CONFIG")
    BaseConfig getConfig();

    @Key("DEBUG_LEVEL")
    String getDebugLevel();
}
