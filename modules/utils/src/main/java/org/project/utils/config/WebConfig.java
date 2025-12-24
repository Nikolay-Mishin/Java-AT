package org.project.utils.config;

/**
 *
 */
public class WebConfig extends Config<WebBaseConfig> {
    /**
     *
     */
    protected static String key = "web";

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
    /**
     *
     * @return T
     * @param <T> extends BaseConfig
     */
    public static <T extends BaseConfig> T setConfig() {
        return setConfig(key());
    }
}
