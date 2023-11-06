package utils.base;

import utils.exceptions.AssertException;
import utils.fs.JsonSchema;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static java.lang.System.out;
import static utils.Helper.isNull;
import static utils.reflections.Reflection.invoke;

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

    public String[] keys() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return HashMap.keys(this);
    }

    public static String[] keys(Object obj) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return ((Set<String>) invoke(obj, "keySet")).toArray(String[]::new);
    }

}
