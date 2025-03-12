package org.project.utils.config;

import static org.project.utils.config.WebBaseConfig.*;

public class WebConfig extends Config {
    protected static WebBaseConfig config = BASE_CONFIG;

    public static WebBaseConfig config() {
        return config;
    }
}
