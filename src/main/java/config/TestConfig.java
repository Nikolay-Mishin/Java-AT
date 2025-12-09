package config;

import static org.aeonbits.owner.Config.Sources;

import static org.project.utils.config.TestConfig.config;

import org.project.utils.config.TestBaseConfig;

@Sources({"classpath:test.properties"})
public interface TestConfig extends TestBaseConfig {
    TestConfig BASE_CONFIG = config(TestConfig.class);
}
