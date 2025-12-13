package org.project.utils.config;

/**
 *
 */
public class WebConfig extends Config {
    /**
     *
     */
    protected static String key = "web";

    /**
     *
     * @return T
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T extends WebBaseConfig> T getConfig() {
        return (T) config();
    }
    /**
     *
     * @return WebBaseConfig
     */
    public static WebBaseConfig config() {
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
     * @param config WebBaseConfig
     * @return WebBaseConfig
     */
    public static WebBaseConfig config(WebBaseConfig config) {
        return config(key, config);
    }
}
