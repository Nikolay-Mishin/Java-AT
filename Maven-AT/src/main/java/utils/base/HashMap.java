package utils.base;

import utils.exceptions.AssertException;

import static java.lang.System.out;

public class HashMap<K, V> {

    private final K[] keys;

    @SafeVarargs
    public HashMap(K... keys) {
        this.keys = keys;
    }

    @SafeVarargs
    public final java.util.HashMap<K, V> values(V... values) throws NullPointerException {
        java.util.HashMap<K, V> hashMap = new java.util.HashMap<>();
        new AssertException(keys.length)._equals(values.length);
        for (int i = 0; i < keys.length; i++) {
            hashMap.put(keys[i], values[i]);
        }
        out.println(hashMap);
        return hashMap;
    }

}
