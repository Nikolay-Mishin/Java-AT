package org.project.utils.config;

public class DriverConfig extends Config {
    protected static String key = "win";

    public static <T extends DriverBaseConfig> T getConfig() {
        return (T) config();
    }
    public static DriverBaseConfig config() {
        return config(key);
    }
    public static <T extends BaseConfig> T config(Class<T> clazz) {return config(key, clazz);}
    public static DriverBaseConfig config(DriverBaseConfig config) {
        return config(key, config);
    }
}
