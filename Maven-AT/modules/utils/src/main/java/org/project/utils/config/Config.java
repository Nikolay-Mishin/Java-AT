package org.project.utils.config;

import static java.lang.System.*;
import static org.aeonbits.owner.ConfigFactory.create;
import static org.project.utils.Helper.notEquals;
import static org.project.utils.config.WebBaseConfig.BASE_CONFIG;
import static org.project.utils.config.WebBaseConfig.DEBUG_LEVEL;

public class Config {

    protected static WebBaseConfig config = BASE_CONFIG;
    protected static int debugLvl = DEBUG_LEVEL;

    public static WebBaseConfig config() {
        return config;
    }

    public static WebBaseConfig setConfig(WebBaseConfig webConfig) {
        out.println(webConfig.getDebugLevel());
        return notEquals(webConfig.getClass(), config.getClass()) ? config = setDebugLvl(webConfig) : config;
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebBaseConfig> T setConfig(Class<T> clazz) {
        out.println("setConfig: " + clazz);
        config = create(clazz, getenv(), getProperties());
        out.println(config.getDebugLevel());
        return (T) setDebugLvl(config);
    }

    protected static WebBaseConfig setDebugLvl(WebBaseConfig config) {
        out.println(config.getDebugLevel());
        return setDebugLvl(Integer.parseInt(config.getDebugLevel()));
    }

    public static int debugLvl() {
        return debugLvl;
    }

    public static WebBaseConfig setDebugLvl(int value) {
        debugLvl = value;
        return config;
    }

}
