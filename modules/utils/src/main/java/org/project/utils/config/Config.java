package org.project.utils.config;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;

import static java.lang.System.*;
import static org.aeonbits.owner.ConfigFactory.create;
import static org.project.utils.Helper._equals;
import static org.project.utils.Helper.debug;
import static org.project.utils.config.WebBaseConfig.*;

import org.project.utils.base.HashMap;

public class Config {
    protected static HashMap<String, BaseConfig> map = new HashMap<>();
    protected static BaseConfig config = BASE_CONFIG;
    protected static int debugLvl = DEBUG_LEVEL;

    public static HashMap<String, BaseConfig> configs() {
        return map;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseConfig> T config(String k) {
        return (T) map.get(k);
    }

    public static <T extends BaseConfig> T setConfig(Class<T> clazz)
        throws InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        return setConfig(createConfig(clazz));
    }

    public static <T extends BaseConfig> T setConfig(String k, Class<T> clazz)
        throws InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        return config(k, createConfig(clazz));
    }

    public static <T extends BaseConfig> T setConfig(T config)
        throws InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        return config("config", config);
    }

    public static <T extends BaseConfig> T config(String k, BaseConfig config)
        throws InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        map.put(k, config);
        return config("config");
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
        debug("createConfig: " + configClass.getInterfaces()[0]);
        debug("config: " + baseConfigClass.getInterfaces()[0]);
        return (T) (_equals(configClass, baseConfigClass) ? baseConfig : (Config.config = debugLvl(config)));
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseConfig> T config(Class<T> clazz) {
        config = createConfig(clazz);
        //debug("setConfig: " + config);
        return (T) config;
    }

    public static <T extends BaseConfig> T createConfig(Class<T> clazz) {
        debug("createConfig: " + clazz);
        return debugLvl(create(clazz, getenv(), getProperties()));
    }

    public static int debugLvl() {
        return debugLvl;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseConfig> T debugLvl(int value) {
        return (T) debugLvl(config, value);
    }

    protected static <T extends BaseConfig> T debugLvl(T config) {
        debug("debugLevel: " + config.getDebugLevel());
        return debugLvl(config, Integer.parseInt(config.getDebugLevel()));
    }

    protected static <T extends BaseConfig> T debugLvl(T config, int value) {
        debugLvl = value;
        return config;
    }
}
