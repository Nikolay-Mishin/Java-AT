package org.project.utils.config;

import static org.aeonbits.owner.Config.LoadPolicy;
import static org.aeonbits.owner.Config.LoadType.MERGE;
import static org.aeonbits.owner.Config.Sources;

import org.aeonbits.owner.Config;

import static org.project.utils.config.Config.config;
import static org.project.utils.config.Config.debugLvl;
import static org.project.utils.config.Config.env;

@LoadPolicy(MERGE)
@Sources({"${props.utils.dev}"})
public interface BaseConfig extends Config {
    BaseConfig BASE_CONFIG = config(BaseConfig.class);
    String ENV = env();
    int DEBUG_LEVEL = debugLvl();

    @Key("BASE_CONFIG")
    BaseConfig getBaseConfig();

    @Key("ENV")
    String getEnv();

    @Key("DEBUG_LEVEL")
    int getDebugLevel();

    @Key("JAVA_VER")
    String getJavaVer();

    @Key("JAVA_HOME")
    String getJavaHome();
}
