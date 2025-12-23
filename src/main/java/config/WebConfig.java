package config;

import static org.aeonbits.owner.Config.LoadPolicy;
import static org.aeonbits.owner.Config.LoadType.MERGE;
import static org.aeonbits.owner.Config.Sources;

import static org.project.utils.config.WebConfig.config;
import static org.project.utils.config.WebConfig.debugLvl;

import org.project.utils.config.WebBaseConfig;

@LoadPolicy(MERGE)
@Sources({
    //"system:properties", // -DpropertyName=propertyValue
    //"system:env", // environment variables
    "${props.stand}",
    "${props.web}"
})
public interface WebConfig extends WebBaseConfig {
    WebConfig BASE_CONFIG = config(WebConfig.class);
    int DEBUG_LEVEL = debugLvl();
}
