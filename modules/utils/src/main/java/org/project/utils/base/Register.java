package org.project.utils.base;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.notNull;
import static org.project.utils.base.HashMap.keys;
import static org.project.utils.reflection.Reflection.getGenericClass;

public class Register<K, V> {

    protected static final Map<Class<?>, Register<?, ?>> registerMap = new HashMap<>();
    protected final Map<K, V> register = new HashMap<>();

    protected V register(K key, V value) {
        register.put(key, value);
        return value;
    }

    public Map<K, V> register() {
        return register;
    }

    public V register(K key) {
        return register.get(key);
    }

    public void printRegister() {
        debug(register);
    }

    protected static <K, V> Register<K, V> registerMap(Class<?> clazz, K key, V value) throws ClassNotFoundException {
        debug("registerMap");
        Class<?> t1 = getGenericClass();
        debug("Generic#1: " + t1);
        Class<?> t2 = t1;
        try {
            t2 = getGenericClass(1);
        } catch (Exception ignored) {}
        debug("Generic#2: " + t2);
        Register<K, V> registerValue = registerMap(clazz);
        Register<K, V> register = notNull(registerValue) ? registerValue : new Register<>();
        register.register(key, value);
        registerMap.put(clazz, register);
        return register;
    }

    public static Map<Class<?>, Register<?, ?>> registerMap() {
        return registerMap;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Register<K, V> registerMap(Class<?> key) {
        return (Register<K, V>) registerMap.get(key);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> V register(Class<?> clazz, K key) throws ClassNotFoundException {
        return (V) registerMap(clazz).register(key);
    }

    public static <K, V> V getRegister(K key) throws ClassNotFoundException {
        debug("registerMap");
        Class<?> t1 = getGenericClass();
        debug("Generic#1: " + t1);
        Class<?> t2 = t1;
        try {
            t2 = getGenericClass(1);
            debug("Generic#2: " + t2);
            return register(t2, key);
        } catch (Exception e) {
            debug(e.toString());
        }
        return null;
    }

    public static void printRegisterMap() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        debug(registerMap);
        for (Class<?> key : keys(registerMap, Class<?>[]::new)) {
            registerMap(key).printRegister();
        }
    }

}
