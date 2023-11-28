package org.project.utils.base;

import org.project.utils.exception.AssertException;
import org.project.utils.reflection.Reflection;
import org.project.utils.json.JsonSchema;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.function.IntFunction;

import static java.lang.System.out;
import static org.project.utils.Helper.isNull;
import static org.project.utils.reflection.Reflection.invoke;

public class HashMap<K, V> extends java.util.HashMap<K, V> {

    private final K[] keys;

    @SafeVarargs
    public HashMap(K... keys) {
        this.keys = keys;
    }

    @SafeVarargs
    public final HashMap<K, V> values(V... values) throws NullPointerException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return _values(null, null, values);
    }

    public HashMap<K, V> values(JsonSchema jsonSchema) throws NullPointerException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return _values(jsonSchema, "object");
    }

    public HashMap<K, V> values(JsonSchema jsonSchema, String type) throws NullPointerException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return _values(jsonSchema, type);
    }

    @SafeVarargs
    private HashMap<K, V> _values(JsonSchema jsonSchema, String type, V... values)
        throws NullPointerException, InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        HashMap<K, V> hashMap = new HashMap<>();
        boolean valuesNotJson = isNull(jsonSchema);
        int valuesLength = valuesNotJson ? values.length : keys.length;
        new AssertException(keys.length)._equals(valuesLength);
        for (int i = 0; i < keys.length; i++) {
            K key = keys[i];
            V value = valuesNotJson ? values[i] : invoke(jsonSchema, "get", key, type);
            hashMap.put(key, value);
        }
        out.println(hashMap);
        return hashMap;
    }

    public K[] keys(IntFunction<K[]> generator) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return HashMap.keys(this, generator);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] keys(Object obj, IntFunction<T[]> generator) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return ((Set<T>) invoke(obj, "keySet")).toArray(generator);
    }

}
