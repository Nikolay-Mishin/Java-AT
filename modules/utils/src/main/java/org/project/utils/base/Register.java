package org.project.utils.base;

import java.util.HashMap;
import java.util.Map;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.notNull;
import static org.project.utils.base.HashMap.keys;
import static org.project.utils.reflection.Reflection.getGenericClass;

/**
 *
 * @param <K> K
 * @param <V> V
 */
public class Register<K, V> {
    /**
     *
     */
    protected static final Map<Class<?>, Register<?, ?>> registerMap = new HashMap<>();
    /**
     *
     */
    protected final Map<K, V> register = new HashMap<>();

    /**
     *
     * @param key K
     * @param value V
     * @return V
     */
    protected V register(K key, V value) {
        register.put(key, value);
        return value;
    }

    /**
     *
     * @return Map {K, V}
     */
    public Map<K, V> register() {
        return register;
    }

    /**
     *
     * @param key K
     * @return V
     */
    public V register(K key) {
        return register.get(key);
    }

    /**
     *
     */
    public void printRegister() {
        debug(register);
    }

    /**
     *
     * @param clazz Class
     * @param key K
     * @param value V
     * @return Register {K, V}
     * @param <K> K
     * @param <V> V
     * @throws ClassNotFoundException throws
     */
    protected static <K, V> Register<K, V> registerMap(Class<?> clazz, K key, V value) throws ClassNotFoundException {
        debug("registerMap");
        Class<?> t1 = getGenericClass();
        Class<?> t2 = getGenericClass(1);
        debug("Generic#1: " + t1);
        debug("Generic#2: " + t2);
        Register<K, V> registerValue = registerMap(clazz);
        Register<K, V> register = notNull(registerValue) ? registerValue : new Register<>();
        register.register(key, value);
        registerMap.put(clazz, register);
        return register;
    }

    /**
     *
     * @return Map {Class {?}, Register {?, ?}}
     */
    public static Map<Class<?>, Register<?, ?>> registerMap() {
        return registerMap;
    }

    /**
     *
     * @param key Class {K}
     * @return Register {K, V}
     * @param <K> K
     * @param <V> V
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Register<K, V> registerMap(Class<?> key) {
        return (Register<K, V>) registerMap.get(key);
    }

    /**
     *
     * @param clazz Class
     * @param key K
     * @return V
     * @param <K> K
     * @param <V> V
     * @throws ClassNotFoundException throws
     */
    @SuppressWarnings("unchecked")
    public static <K, V> V register(Class<?> clazz, K key) {
        return (V) registerMap(clazz).register(key);
    }

    /**
     *
     * @param key K
     * @return V
     * @param <K> K
     * @param <V> V
     * @throws ClassNotFoundException throws
     */
    public static <K, V> V getRegister(K key) throws ClassNotFoundException {
        debug("registerMap: " + key);
        Class<?> t1 = getGenericClass();
        Class<?> t2 = getGenericClass(1);
        debug("Generic#1: " + t1);
        debug("Generic#2: " + t2);
        return register(t2, key);
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     */
    public static void printRegisterMap() throws ReflectiveOperationException {
        debug(registerMap);
        for (Class<?> key : keys(registerMap, Class<?>[]::new)) {
            registerMap(key).printRegister();
        }
    }

}
