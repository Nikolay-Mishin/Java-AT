package org.project.utils.config;

public class WinConfig extends Config {
    protected static String key = "win";

    public static WebBaseConfig config() {
        return config(key);
    }
    public static <T extends BaseConfig> T config(Class<T> clazz) {return config(key, clazz);}
    public static WebBaseConfig config(WebBaseConfig config) {
        return config(key, config);
    }
}
