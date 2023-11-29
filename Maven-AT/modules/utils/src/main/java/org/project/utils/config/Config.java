package org.project.utils.config;

import static java.lang.System.*;
import static org.aeonbits.owner.ConfigFactory.create;
import static org.project.utils.Helper.notEquals;
import static org.project.utils.config.WebBaseConfig.BASE_CONFIG;

public class Config {

    protected static WebBaseConfig config = BASE_CONFIG;

    public static WebBaseConfig setConfig(WebBaseConfig webConfig) {
        out.println(webConfig.getClass());
        out.println(config.getClass());
        return notEquals(webConfig.getClass(), config.getClass()) ? (config = webConfig) : config;
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebBaseConfig> T setConfig(Class<T> clazz) {
        out.println("setConfig: " + clazz);
        return (T) (config = create(clazz, getenv(), getProperties()));
    }

    public static WebBaseConfig config() {
        return config;
    }

}
