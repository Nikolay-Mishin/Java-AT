package org.project.utils.base;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;
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

    public Map<K, V> getRegister() {
        return register;
    }

    public V register(K key) {
        return register.get(key);
    }

    public void printRegister() {
        out.println(register);
    }

    protected static <K, V> Register<K, V> registerMap(Class<?> clazz, K key, V value) throws ClassNotFoundException {
        out.println("registerMap");
        Class<?> t1 = getGenericClass();
        out.println("Generic#1: " + t1);
        Class<?> t2 = t1;
        try {
            t2 = getGenericClass(1);
        } catch (Exception ignored) {}
        out.println("Generic#2: " + t2);
        Register<K, V> registerValue = getRegisterMap(clazz);
        Register<K, V> register = notNull(registerValue) ? registerValue : new Register<>();
        register.register(key, value);
        registerMap.put(clazz, register);
        return register;
    }

    public static Map<Class<?>, Register<?, ?>> getRegisterMap() {
        return registerMap;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Register<K, V> getRegisterMap(Class<?> key) throws ClassNotFoundException {
        return (Register<K, V>) registerMap.get(key);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> V getRegister(Class<?> clazz, K key) throws ClassNotFoundException {
        return (V) getRegisterMap(clazz).register(key);
    }

    public static <K, V> V getRegister(K key) throws ClassNotFoundException {
        out.println("getRegisterMap");
        Class<?> t1 = getGenericClass();
        out.println("Generic#1: " + t1);
        Class<?> t2 = t1;
        try {
            t2 = getGenericClass(1);
            out.println("Generic#2: " + t2);
            return getRegister(t2, key);
        } catch (Exception e) {
            out.println(e.toString());
        }
        return null;
    }

    public static void printRegisterMap() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        out.println(registerMap);
        for (Class<?> key : keys(registerMap, Class<?>[]::new)) {
            getRegisterMap(key).printRegister();
        }
    }

}
