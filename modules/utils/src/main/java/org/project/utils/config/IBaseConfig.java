package org.project.utils.config;

import static org.aeonbits.owner.Config.LoadPolicy;
import static org.aeonbits.owner.Config.LoadType.MERGE;
import static org.aeonbits.owner.Config.Sources;

import static org.project.utils.config.TestConfig.config;

/**
 *
 */
@LoadPolicy(MERGE)
@Sources({
    "${props.env}",
    "${props.utils.dev}"
})
public interface IBaseConfig extends TestBaseConfig {
    /**
     *
     */
    IBaseConfig BASE_CONFIG = config(IBaseConfig.class);
}
