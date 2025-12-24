package org.project.utils.config;

/**
 *
 */
public class TestConfig extends Config<TestBaseConfig> {
    /**
     *
     */
    protected static String key = "test";

    /**
     *
     * @return String
     */
    public static String key() {
        return key;
    }
    /**
     *
     * @return T
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T extends TestBaseConfig> T getConfig() {
        return (T) config();
    }
    /**
     *
     * @return TestBaseConfig
     */
    public static TestBaseConfig config() {
        return config(key);
    }
    /**
     *
     * @param clazz Class T
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T config(Class<T> clazz) {return config(key, clazz);}
    /**
     *
     * @param config TestBaseConfig
     * @return TestBaseConfig
     */
    public static TestBaseConfig config(TestBaseConfig config) {
        return config(key, config);
    }
    /**
     *
     * @return T
     * @param <T> extends BaseConfig
     */
    public static <T extends BaseConfig> T setConfig() {
        return setConfig(key());
    }
}
