package org.project.utils.config;

import static org.aeonbits.owner.Config.*;

import org.aeonbits.owner.Config;

import static org.project.utils.config.Config.*;

/*@LoadPolicy(LoadType.MERGE)
@Sources({
    "system:properties", // -DpropertyName=propertyValue,
    //"classpath:stand.properties",
    "classpath:org.project.utils.dev.properties"
})*/
@Sources({"classpath:org.project.utils.dev.properties"})
public interface BaseConfig extends Config {
    BaseConfig BASE_CONFIG = config(BaseConfig.class);
    String ENV = env();
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
