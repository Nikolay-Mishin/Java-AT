package config;

import static org.aeonbits.owner.Config.Sources;

import static org.project.utils.config.TestConfig.setConfig;

import org.project.utils.config.TestBaseConfig;

@Sources({"${props.test}"})
public interface TestConfig extends TestBaseConfig {
    TestConfig BASE_CONFIG = setConfig();
}
