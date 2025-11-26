package org.project.utils.config;

public class WebConfig extends Config {
    protected static String key = "web";

    public static WebBaseConfig config() {
        return config(key);
    }
    public static <T extends BaseConfig> T config(Class<T> clazz) {return config(key, clazz);}
    public static WebBaseConfig config(WebBaseConfig config) {
        return config(key, config);
    }
}
