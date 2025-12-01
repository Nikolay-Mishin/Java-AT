package org.project.utils.base;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.IntFunction;

import static org.project.utils.Helper.*;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.exception.AssertException;
import org.project.utils.json.JsonSchema;

public class HashMap<K, V> extends java.util.HashMap<K, V> implements Map<K, V> {
    private final K[] keys;

    @SafeVarargs
    public HashMap(K... keys) {
        this.keys = keys;
    }

    @SuppressWarnings("unchecked")
    public HashMap(Set<K> keys) {
        this.keys = (K[]) keys.toArray();
    }

    @SuppressWarnings("unchecked")
    public HashMap<K, V> values(List<V> values) throws ReflectiveOperationException {
        return values((V[]) values.toArray());
    }

    @SafeVarargs
    public final HashMap<K, V> values(V... values)
        throws NullPointerException, ReflectiveOperationException {
        return _values(null, null, values);
    }

    public HashMap<K, V> values(JsonSchema jsonSchema)
        throws NullPointerException, ReflectiveOperationException {
        return _values(jsonSchema, "object");
    }

    public HashMap<K, V> values(JsonSchema jsonSchema, String type)
        throws NullPointerException, ReflectiveOperationException {
        return _values(jsonSchema, type);
    }

    @SafeVarargs
    private HashMap<K, V> _values(JsonSchema jsonSchema, String type, V... values)
        throws NullPointerException, ReflectiveOperationException {
        HashMap<K, V> hashMap = new HashMap<>();
        boolean valuesNotJson = isNull(jsonSchema);
        int valuesLength = valuesNotJson ? values.length : keys.length;
        new AssertException(keys.length)._equals(valuesLength);
        for (int i = 0; i < keys.length; i++) {
            K key = keys[i];
            V value = valuesNotJson ? values[i] : invoke(jsonSchema, "get", key, type);
            hashMap.put(key, value);
        }
        debug(hashMap);
        return hashMap;
    }

    @SuppressWarnings("unchecked")
    public K[] keys() throws ReflectiveOperationException {
        return (K[]) keys(this, String[]::new);
    }

    public K[] keys(IntFunction<K[]> generator)
        throws ReflectiveOperationException {
        return keys(this, generator);
    }

    @SuppressWarnings("unchecked")
    public static <K> K[] keys(Object obj) throws ReflectiveOperationException {
        return (K[]) keys(obj, String[]::new);
    }

    @SuppressWarnings("unchecked")
    public static <K> K[] keys(Object obj, IntFunction<K[]> generator)
        throws ReflectiveOperationException {
        return ((Set<K>) invoke(obj, "keySet")).toArray(generator);
    }
}
