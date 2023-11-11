package utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;
import static utils.Helper.notNull;
import static utils.base.HashMap.keys;

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

    public V getRegister(K key) {
        return register.get(key);
    }

    public void printRegister() {
        out.println(register);
    }

    protected static <K, V> Register<K, V> registerMap(Class<?> clazz, K key, V value) {
        Register<K, V> registerValue = getRegister(clazz);
        Register<K, V> register = notNull(registerValue) ? registerValue : new Register<>();
        register.register(key, value);
        registerMap.put(clazz, register);
        return register;
    }

    public static Map<Class<?>, Register<?, ?>> getRegisterMap() {
        return registerMap;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Register<K, V> getRegister(Class<?> key) {
        return (Register<K, V>) registerMap.get(key);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> V getRegister(Class<?> clazz, K key) {
        return (V) getRegister(clazz).getRegister(key);
    }

    public static void printRegisterMap() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        out.println(registerMap);
        for (Class<?> key : keys(registerMap, Class<?>[]::new)) {
            getRegister(key).printRegister();
        }
    }

}
