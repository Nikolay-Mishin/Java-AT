package org.project.utils.config;

import static java.lang.System.getenv;
import static java.lang.System.getProperties;
import static java.lang.System.getProperty;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Factory;

import static org.project.utils.Helper._equals;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.isNull;
import static org.project.utils.Helper.notNull;
import static org.project.utils.base.Properties.setProps;
import static org.project.utils.exception.UtilException.tryCatchNoArgs;
import static org.project.utils.reflection.Reflection.getCallingClass;
import static org.project.utils.reflection.Reflection.getGenericClass;

import org.project.utils.base.HashMap;
import org.project.utils.function.SupplierWithExceptions;

/**
 *
 */
public class Config<T extends BaseConfig> implements BaseConfig {
    /**
     *
     */
    protected static String key = "config";
    /**
     *
     */
    protected static String envKey = "env";
    /**
     *
     */
    protected static HashMap<String, BaseConfig> map = new HashMap<>();
    /**
     *
     */
    protected static String env = ENV;
    /**
     *
     */
    protected static int debugLvl = DEBUG_LEVEL;
    /**
     *
     */
    protected static Factory f = f();

    /**
     *
     * @return String
     */
    public static String key() {
        return key;
    }

    /**
     *
     * @return HashMap
     */
    public static HashMap<String, BaseConfig> configs() {
        return map;
    }

    /**
     *
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T config() {
        return config(key);
    }

    /**
     *
     * @param k String
     * @return T
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseConfig> T config(String k) {
        return (T) map.get(k);
    }

    /**
     *
     * @param clazz Class T
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T config(Class<T> clazz) {
        return config(createConfig(clazz));
    }

    /**
     *
     * @param k String
     * @param clazz Class T
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T config(String k, Class<T> clazz) {
        return config(k, createConfig(clazz));
    }

    /**
     *
     * @return T
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseConfig> T setConfig() {
        return config((T) createConfig());
    }

    /**
     *
     * @param k String
     * @return T
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseConfig> T setConfig(String k) {
        return config(k, (T) createConfig());
    }

    /**
     *
     * @param config T
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T config(T config) {
        return compare(config);
    }

    /**
     *
     * @param k String
     * @param config T
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T config(String k, T config) {
        return compare(k, config);
    }

    /**
     *
     * @param config T
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T compare(T config) {
        return compare(key, config);
    }

    /**
     *
     * @param k String
     * @param config T
     * @return T
     * @param <T> T
     */
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

    /**
     *
     * @return Class T
     * @param <T> extends BaseConfig
     */
    public static <T extends BaseConfig> T createConfig() {
        /*try {
            Class<T> clazz = getGenericClass();
            debug("getGenericClass: " + clazz);
            //return clazz;
        } catch (Exception e) {
            debug("getGenericClass:catch");
            e.printStackTrace();
        }*/
        return createConfig(() -> getCallingClass(c -> !c.isInterface()));
    }

    /**
     *
     * @param clazz Class T
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T createConfig(Class<T> clazz) {
        return createConfig(() -> clazz);
    }

    /**
     *
     * @param cb SupplierWithExceptions {Class T, E}
     * @return T
     * @param <T> T
     * @param <E> extends ClassNotFoundException
     */
    public static <T extends BaseConfig, E extends ClassNotFoundException> T createConfig(SupplierWithExceptions<Class<T>, E> cb) {
        try {
            setProps();
            Class<T> clazz = cb.get();
            debug("createConfig: " + clazz);
            //return init(getOrCreate(clazz, getenv(), setProperties()));
            return init(getOrCreate(clazz));
        } catch (ClassNotFoundException e) {
            debug("createConfig:catch");
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param config T
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T init(T config) {
        //debug("config: " + config);
        env(config);
        debugLvl(config);
        return config;
    }

    /**
     *
     * @return String
     */
    public static String env() {
        return env;
    }

    /**
     *
     * @param config T
     * @return String
     * @param <T> T
     */
    protected static <T extends BaseConfig> String env(T config) {
        String env = getProperty("env");
        return tryCatchNoArgs(() -> env(notNull(env) ? env : config.getEnv()), e -> env);
    }

    /**
     *
     * @param value String
     * @return String
     */
    protected static String env(String value) {
        debug("setEnv: " + value);
        return isNull(value) ? env : (env = set(envKey, value));
    }

    /**
     *
     * @return int
     */
    public static int debugLvl() {
        return debugLvl;
    }

    /**
     *
     * @param config T
     * @return int
     * @param <T> T
     */
    protected static <T extends BaseConfig> int debugLvl(T config) {
        return tryCatchNoArgs(() -> debugLvl(config.getDebugLevel()), e -> debugLvl);
    }

    /**
     *
     * @param value int
     * @return int
     */
    protected static int debugLvl(int value) {
        debug("setDebugLevel: " + value);
        return debugLvl = value;
    }

    /**
     *
     * @return Properties
     */
    public static Properties get() {
        return ConfigFactory.getProperties();
    }

    /**
     *
     * @param k String
     * @return String
     */
    public static String get(String k) {
        return ConfigFactory.getProperty(k);
    }

    /**
     *
     * @param props Properties
     */
    public static void set(Properties props) {
        ConfigFactory.setProperties(props);
    }

    /**
     *
     * @param k String
     * @param v String
     * @return String
     */
    public static String set(String k, String v) {
        ConfigFactory.setProperty(k, v);
        return get(k);
    }

    /**
     *
     * @param k String
     */
    public static void clear(String k) {
        ConfigFactory.clearProperty(k);
    }

    /**
     *
     * @return Factory
     */
    public static Factory getF() {
        return f;
    }

    /**
     *
     * @return Factory
     */
    public static Factory f() {
        return f = ConfigFactory.newInstance();
    }

    /**
     *
     * @param factory Factory
     * @return Factory
     */
    public static Factory f(Factory factory) {
        return f = factory;
    }

    /**
     *
     * @param clazz Class extends T
     * @param imports Map
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T getOrCreate(Class<? extends T> clazz, Map<?, ?>... imports) {
        return ConfigCache.getOrCreate(clazz, imports);
    }

    /**
     *
     * @param clazz Class extends T
     * @param imports Map
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T getOrCreateF(Class<? extends T> clazz, Map<?, ?>... imports) {
        return ConfigCache.getOrCreate(f, clazz, imports);
    }

    /**
     *
     * @param factory Factory
     * @param clazz Class extends T
     * @param imports Map
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T getOrCreate(Factory factory, Class<? extends T> clazz, Map<?, ?>... imports) {
        return ConfigCache.getOrCreate(factory, clazz, imports);
    }

    /**
     *
     * @return Set
     */
    public static Set<Object> list() {
        return ConfigCache.list();
    }

    /**
     *
     * @param k Object
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T getCache(Object k) {
        return ConfigCache.get(k);
    }

    /**
     *
     * @param k Object
     * @param config T
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T add(Object k, T config) {
        return ConfigCache.add(k, config);
    }

    /**
     *
     */
    public static void clear() {
        ConfigCache.clear();
    }

    /**
     *
     * @param k Object
     * @return T
     * @param <T> T
     */
    public static <T extends BaseConfig> T remove(Object k) {
        return ConfigCache.remove(k);
    }

    /**
     *
     */
    public static void printEnvList() {
        printEnvList(config());
    }

    /**
     *
     * @param config T
     * @param <T> T
     */
    public static <T extends BaseConfig> void printEnvList(T config) {
        printEnv(config);
        printProps();
    }

    /**
     *
     */
    public static void printEnvListWithSys() {
        printEnvList();
        printList();
    }

    /**
     *
     * @param config T
     * @param <T> T
     */
    public static <T extends BaseConfig> void printEnvListWithSys(T config) {
        printEnvList(config);
        printList();
    }

    /**
     *
     */
    public static void printEnv() {
        printEnv(config());
    }

    /**
     *
     * @param config T
     * @param <T> T
     */
    public static <T extends BaseConfig> void printEnv(T config) {
        String env = config.getEnv();
        debug("env: " + env);
        debug("javaVer: " + config.getJavaVer());
        debug("javaHome: " + config.getJavaHome());
    }

    /**
     *
     */
    public static void printProps() {
        debug("prop: " + get(envKey));
        debug("props: " + get());
        debug("arg: " + getProperty(envKey));
    }

    /**
     *
     */
    public static void printList() {
        debug("getenv: " + getenv());
        debug("getProperties: " + getProperties());
    }

    /**
     * @return BaseConfig
     */
    @Override
    public BaseConfig getBaseConfig() {
        return null;
    }

    /**
     * @return String
     */
    @Override
    public String getEnv() {
        return "";
    }

    /**
     * @return int
     */
    @Override
    public int getDebugLevel() {
        return 0;
    }

    /**
     * @return String
     */
    @Override
    public String getJavaVer() {
        return "";
    }

    /**
     * @return String
     */
    @Override
    public String getJavaHome() {
        return "";
    }

    /**
     * app
     *
     * @return long
     */
    @Override
    public long getSleep() {
        return 0;
    }

    /**
     * app
     *
     * @return long
     */
    @Override
    public long getTimeout() {
        return 0;
    }
}
