package org.project.utils.config;

public class TestConfig extends Config {
    protected static String key = "test";

    public static <T extends TestBaseConfig> T getConfig() {
        return (T) config();
    }
    public static TestBaseConfig config() {
        return config(key);
    }
    public static <T extends BaseConfig> T config(Class<T> clazz) {return config(key, clazz);}
    public static TestBaseConfig config(TestBaseConfig config) {
        return config(key, config);
    }
}
