package org.project.utils.config;

import static org.aeonbits.owner.Config.*;

import org.aeonbits.owner.Config;

import static org.project.utils.config.Config.*;

@LoadPolicy(LoadType.MERGE)
@Sources({
    //"system:properties", // -DpropertyName=propertyValue
    //"classpath:${env}.properties",
    "classpath:dev.properties"
})
public interface BaseConfig extends Config {
    BaseConfig BASE_CONFIG = config(BaseConfig.class);
    String ENV = BASE_CONFIG.getEnv();
    int DEBUG_LEVEL = debugLvl();

    @Key("BASE_CONFIG")
    BaseConfig getConfig();

    @Key("ENV")
    String getEnv();

    @Key("DEBUG_LEVEL")
    int getDebugLevel();

    @Key("JAVA_VER")
    String getJavaVer();

    @Key("JAVA_HOME")
    String getJavaHome();
}
