package org.project.utils.config;

import static java.lang.System.*;
import static org.aeonbits.owner.ConfigFactory.create;
import static org.project.utils.Helper._equals;
import static org.project.utils.Helper.debug;
import static org.project.utils.config.WebBaseConfig.BASE_CONFIG;
import static org.project.utils.config.WebBaseConfig.DEBUG_LEVEL;

public class Config {

    protected static WebBaseConfig config = BASE_CONFIG;
    protected static int debugLvl = DEBUG_LEVEL;

    public static WebBaseConfig config() {
        return config;
    }

    public static WebBaseConfig config(WebBaseConfig webConfig) {
        debug("getClass: " + webConfig.getClass());
        debug("getClass: " + config.getClass());
        debug("setConfig: " + webConfig.getClass().getInterfaces()[0]);
        debug("config: " + config.getClass().getInterfaces()[0]);
        return _equals(webConfig.getClass(), config.getClass()) ? config : (config = debugLvl(webConfig));
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebBaseConfig> T config(Class<T> clazz) {
        debug("setConfig: " + clazz);
        config = create(clazz, getenv(), getProperties());
        return (T) debugLvl(config);
    }

    protected static WebBaseConfig debugLvl(WebBaseConfig config) {
        debug("debugLevel: " + config.getDebugLevel());
        return debugLvl(Integer.parseInt(config.getDebugLevel()));
    }

    public static int debugLvl() {
        return debugLvl;
    }

    public static WebBaseConfig debugLvl(int value) {
        debugLvl = value;
        return config;
    }

}
