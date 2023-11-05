package utils;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

public class Register<K, V> {

    protected final Map<K, V> register = new HashMap<>();

    public Register(K key, V value) {
        register(key, value);
    }

    public Register() {}

    protected void register(K key, V value) {
        register.put(key, value);
    }

    public V getRegister(K key) {
        return register.get(key);
    }

    public void printRegister() {
        out.println(register);
    }

}
