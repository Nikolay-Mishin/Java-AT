package org.project.utils.base;

import static java.util.Comparator.comparing;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.isNull;
import static org.project.utils.Helper.newMap;
import static org.project.utils.Helper.streamMap;
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
    public final HashMap<K, V> values(V... values) throws NullPointerException, ReflectiveOperationException {
        return _values(null, null, values);
    }

    public HashMap<K, V> values(JsonSchema jsonSchema) throws NullPointerException, ReflectiveOperationException {
        return _values(jsonSchema, "object");
    }

    public HashMap<K, V> values(JsonSchema jsonSchema, String type) throws NullPointerException, ReflectiveOperationException {
        return _values(jsonSchema, type);
    }

    @SafeVarargs
    private HashMap<K, V> _values(JsonSchema jsonSchema, String type, V... values) throws NullPointerException, ReflectiveOperationException {
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

    public K[] keys(IntFunction<K[]> generator) throws ReflectiveOperationException {
        return keys(this, generator);
    }

    @SuppressWarnings("unchecked")
    public static <K> K[] keys(Object obj) throws ReflectiveOperationException {
        return (K[]) keys(obj, String[]::new);
    }

    @SuppressWarnings("unchecked")
    public static <K> K[] keys(Object obj, IntFunction<K[]> generator) throws ReflectiveOperationException {
        return ((Set<K>) invoke(obj, "keySet")).toArray(generator);
    }

    public static <T extends Map<K, V>, K, V> T sortByK(T map) throws ReflectiveOperationException {
        return sort(map, o -> o.getKey().toString());
    }

    public static <T extends Map<K, V>, K, V> T sortByV(T map) throws ReflectiveOperationException {
        return sort(map, o -> o.getValue().toString());
    }

    public static <T extends Map<K, V>, K extends Comparable<K>, V> T sort(T map) throws ReflectiveOperationException {
        return sort(map, Entry.comparingByKey());
    }

    public static <T extends Map<K, V>, K, V extends Comparable<V>> T sortV(T map) throws ReflectiveOperationException {
        return sort(map, Entry.comparingByValue());
    }

    public static <T extends Map<K, V>, K, V, R extends Comparable<R>> T sort(T map, Function<Entry<K, V>, R> keyExtractor) throws ReflectiveOperationException {
        return sort(map, comparing(keyExtractor));
    }

    public static <T extends Map<K, V>, K, V, R extends Comparable<R>> T sort(T map, Function<Entry<K, V>, R> keyExtractor, Supplier<T> factory) throws ReflectiveOperationException {
        return sort(map, comparing(keyExtractor), factory);
    }

    public static <T extends Map<K, V>, K, V> T sort(T map, Comparator<Entry<K, V>> comparator) throws ReflectiveOperationException {
        return (T) newMap(sorted(map, comparator));
    }

    public static <T extends Map<K, V>, K, V> T sort(T map, Comparator<Entry<K, V>> comparator, Supplier<T> factory) {
        return newMap(sorted(map, comparator), factory);
    }

    public static <T extends Map<K, V>, K, V> Stream<Entry<K, V>> sorted(T map, Comparator<Entry<K, V>> comparator) {
        return streamMap(map).sorted(comparator);
    }

}
