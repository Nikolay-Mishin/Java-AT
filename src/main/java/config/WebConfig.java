package config;

import org.aeonbits.owner.Config.*;

import static org.project.utils.config.WebConfig.*;

import org.project.utils.config.WebBaseConfig;

@LoadPolicy(LoadType.MERGE)
@Sources({
    //"system:properties", // -DpropertyName=propertyValue
    //"system:env", // environment variables
    "classpath:stand.properties",
    "classpath:dev.properties"
})
public interface WebConfig extends WebBaseConfig {
    WebConfig BASE_CONFIG = config(WebConfig.class);
    int DEBUG_LEVEL = debugLvl();
}
