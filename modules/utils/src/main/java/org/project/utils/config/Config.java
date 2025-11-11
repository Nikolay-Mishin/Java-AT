package org.project.utils.config;

import static java.lang.System.*;

import java.util.*;

import org.aeonbits.owner.*;

import static org.project.utils.config.WebBaseConfig.*;
import static org.project.utils.Helper.*;

import org.project.utils.base.HashMap;

public class Config {
    protected static Factory f = f();
    protected static HashMap<String, BaseConfig> map = new HashMap<>();
    protected static String env = ENV;
    protected static int debugLvl = DEBUG_LEVEL;

    public static HashMap<String, BaseConfig> configs() {
        return map;
    }

    public static <T extends BaseConfig> T config() {
        return config("config");
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseConfig> T config(String k) {
        return (T) map.get(k);
    }

    public static <T extends BaseConfig> T config(Class<T> clazz) {
        return config(createConfig(clazz));
    }

    public static <T extends BaseConfig> T config(String k, Class<T> clazz) {
        return config(k, createConfig(clazz));
    }

    public static <T extends BaseConfig> T config(T config) {
        return compare(config);
    }

    public static <T extends BaseConfig> T config(String k, T config) {
        return compare(k, config);
    }

    public static <T extends BaseConfig> T compare(T config) {
        return compare("config", init(config));
    }

    public static <T extends BaseConfig> T compare(String k, T config) {
        Class<? extends BaseConfig> configClass = config.getClass();
        Class<? extends BaseConfig> prevConfigClass;
        boolean equals = false;
        T v = config(k);
        if (notNull(v)) {
            prevConfigClass = v.getClass();
            equals = _equals(configClass, prevConfigClass);
            debug(prevConfigClass.getInterfaces()[0]);
            debug(configClass.getInterfaces()[0]);
        }
        if (!equals) map.put(k, config);
        return config;
    }

    public static <T extends BaseConfig> T createConfig(Class<T> clazz) {
        debug("createConfig: " + clazz);
        //return init(create(clazz, getenv(), getProperties()));
        return init(getOrCreate(clazz, getenv(), getProperties()));
    }

    public static <T extends BaseConfig> T init(T config) {
        env(config);
        debugLvl(config);
        set("env", env);
        return config;
    }

    public static String env() {
        return env;
    }

    public static <T extends BaseConfig> T env(String value) {
        return env(config(), value);
    }

    protected static <T extends BaseConfig> T env(T config) {
        String env = config.getEnv();
        debug("env: " + env);
        return env(config, env);
    }

    protected static <T extends BaseConfig> T env(T config, String value) {
        env = value;
        return config;
    }
    public static int debugLvl() {
        return debugLvl;
    }

    public static <T extends BaseConfig> T debugLvl(int value) {
        return debugLvl(config(), value);
    }

    protected static <T extends BaseConfig> T debugLvl(T config) {
        int debugLvl = config.getDebugLevel();
        debug("debugLevel: " + debugLvl);
        return debugLvl(config, debugLvl);
    }

    protected static <T extends BaseConfig> T debugLvl(T config, int value) {
        debugLvl = value;
        return config;
    }

    public static Properties get() {
        return ConfigFactory.getProperties();
    }

    public static String get(String k) {
        return ConfigFactory.getProperty(k);
    }

    public static void set(Properties props) {
        ConfigFactory.setProperties(props);
    }

    public static String set(String k, String v) {
        return ConfigFactory.setProperty(k, v);
    }

    public static void clear(String k) {
        ConfigFactory.clearProperty(k);
    }

    public static Factory getF() {
        return f;
    }

    public static Factory f() {
        return f = ConfigFactory.newInstance();
    }

    public static Factory f(Factory factory) {
        return f = factory;
    }

    public static <T extends BaseConfig> T getOrCreate(Class<? extends T> clazz, Map<?, ?>... imports) {
        return ConfigCache.getOrCreate(clazz, imports);
    }

    public static <T extends BaseConfig> T getOrCreateF(Class<? extends T> clazz, Map<?, ?>... imports) {
        return ConfigCache.getOrCreate(f, clazz, imports);
    }

    public static <T extends BaseConfig> T getOrCreate(Factory factory, Class<? extends T> clazz, Map<?, ?>... imports) {
        return ConfigCache.getOrCreate(factory, clazz, imports);
    }

    public static Set<Object> list() {
        return ConfigCache.list();
    }

    public static <T extends BaseConfig> T getCache(Object k) {
        return ConfigCache.get(k);
    }

    public static <T extends BaseConfig> T add(Object k, T config) {
        return ConfigCache.add(k, config);
    }

    public static void clear() {
        ConfigCache.clear();
    }

    public static <T extends BaseConfig> T remove(Object k) {
        return ConfigCache.remove(k);
    }
}
