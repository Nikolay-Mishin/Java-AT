package utils.base;

import utils.exceptions.AssertException;
import utils.fs.JsonSchema;

import java.lang.reflect.InvocationTargetException;

import static java.lang.System.out;
import static utils.Helper.isNull;
import static utils.reflections.Reflection.invoke;

public class HashMap<K, V> {

    private final K[] keys;

    @SafeVarargs
    public HashMap(K... keys) {
        this.keys = keys;
    }

    @SafeVarargs
    public final java.util.HashMap<K, V> values(V... values) throws NullPointerException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return _values(null, null, values);
    }

    public final java.util.HashMap<K, V> values(JsonSchema jsonSchema) throws NullPointerException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return _values(jsonSchema, "object");
    }

    public final java.util.HashMap<K, V> values(JsonSchema jsonSchema, String type) throws NullPointerException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return _values(jsonSchema, type);
    }

    @SafeVarargs
    private java.util.HashMap<K, V> _values(JsonSchema jsonSchema, String type, V... values)
        throws NullPointerException, InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        java.util.HashMap<K, V> hashMap = new java.util.HashMap<>();
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

}
