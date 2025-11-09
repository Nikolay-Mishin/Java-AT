package org.project.utils.config;

public class WebConfig extends Config {
    public static WebBaseConfig config() {
        return config("web");
    }
    public static WebBaseConfig config(WebBaseConfig config) {
        return config("web", config);
    }
}
