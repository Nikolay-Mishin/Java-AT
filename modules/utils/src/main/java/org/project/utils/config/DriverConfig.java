package org.project.utils.config;

/**
 *
 */
public class DriverConfig extends Config<DriverBaseConfig> {
    /**
     *
     */
    protected static String key = "win";

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
    public static <T extends DriverBaseConfig> T getConfig() {
        return (T) config();
    }
    /**
     *
     * @return DriverBaseConfig
     */
    public static DriverBaseConfig config() {
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
     * @param config DriverBaseConfig
     * @return DriverBaseConfig
     */
    public static DriverBaseConfig config(DriverBaseConfig config) {
        return config(key, config);
    }
}
