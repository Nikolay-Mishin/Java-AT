package org.project.utils.config;

import java.lang.reflect.InvocationTargetException;

import static java.lang.System.*;
import static org.aeonbits.owner.ConfigFactory.create;
import static org.project.utils.Helper._equals;
import static org.project.utils.Helper.debug;
import static org.project.utils.config.WebBaseConfig.*;

import org.project.utils.base.HashMap;

public class Config {
    protected static HashMap<String, BaseConfig> _config = new HashMap<String, BaseConfig>();
    protected static BaseConfig config = BASE_CONFIG;
    protected static int debugLvl = DEBUG_LEVEL;

    public static HashMap<String, BaseConfig> configs() {
        return _config;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseConfig> T config(String k) {
        return (T) _config.get(k);
    }

    public static <T extends BaseConfig> HashMap<String, T> getConfig(T config)
        throws InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        return config("config", config);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseConfig> HashMap<String, T> config(String k, BaseConfig config)
        throws InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        _config.put(k, config);
        return (HashMap<String, T>) _config;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseConfig> T config() {
        return (T) config;
    }

    public static <T extends BaseConfig> T config(T config) {
        return compare(config, Config.config);
    }

    @SuppressWarnings("unchecked")
    protected static <T extends BaseConfig, C extends BaseConfig> T compare(T config, C baseConfig) {
        Class<? extends BaseConfig> configClass = config.getClass();
        Class<? extends BaseConfig> baseConfigClass = baseConfig.getClass();
        debug("getClass: " + configClass);
        debug("getClass: " + baseConfigClass);
        debug("setConfig: " + configClass.getInterfaces()[0]);
        debug("config: " + baseConfigClass.getInterfaces()[0]);
        return (T) (_equals(configClass, baseConfigClass) ? baseConfig : (Config.config = debugLvl(config)));
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseConfig> T config(Class<T> clazz) {
        debug("setConfig: " + clazz);
        config = create(clazz, getenv(), getProperties());
        return (T) debugLvl(config);
    }

    @SuppressWarnings("unchecked")
    protected static <T extends BaseConfig> T debugLvl(T config) {
        debug("debugLevel: " + config.getDebugLevel());
        return (T) debugLvl(Integer.parseInt(config.getDebugLevel()));
    }

    public static int debugLvl() {
        return debugLvl;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseConfig> T debugLvl(int value) {
        debugLvl = value;
        return (T) config;
    }
}
