package org.project.utils.config;

import static org.aeonbits.owner.Config.LoadPolicy;
import static org.aeonbits.owner.Config.LoadType.MERGE;
import static org.aeonbits.owner.Config.Sources;

import org.aeonbits.owner.Config;

import static org.project.utils.config.Config.config;
import static org.project.utils.config.Config.debugLvl;
import static org.project.utils.config.Config.env;

/**
 *
 */
@LoadPolicy(MERGE)
@Sources({"${props.utils.dev}"})
public interface BaseConfig extends Config {
    /**
     *
     */
    BaseConfig BASE_CONFIG = config(BaseConfig.class);
    /**
     *
     */
    String ENV = env();
    /**
     *
     */
    int DEBUG_LEVEL = debugLvl();

    /**
     *
     * @return BaseConfig
     */
    @Key("BASE_CONFIG")
    BaseConfig getBaseConfig();

    /**
     *
     * @return String
     */
    @Key("ENV")
    String getEnv();

    /**
     *
     * @return int
     */
    @Key("DEBUG_LEVEL")
    int getDebugLevel();

    /**
     *
     * @return String
     */
    @Key("JAVA_VER")
    String getJavaVer();

    /**
     *
     * @return String
     */
    @Key("JAVA_HOME")
    String getJavaHome();
}
