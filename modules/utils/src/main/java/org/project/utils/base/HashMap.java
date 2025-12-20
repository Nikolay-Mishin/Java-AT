package org.project.utils.base;

import static java.util.Comparator.comparing;

import java.beans.ConstructorProperties;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.entries;
import static org.project.utils.Helper.isNull;
import static org.project.utils.Helper.newMap;
import static org.project.utils.Helper.notNull;
import static org.project.utils.Helper.streamMap;
import static org.project.utils.reflection.Instance.create;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.exception.AssertException;
import org.project.utils.json.JsonSchema;

/**
 *
 * @param <K> K
 * @param <V> V
 */
public class HashMap<K, V> extends java.util.LinkedHashMap<K, V> implements Map<K, V> {
    /**
     *
     */
    private final K[] keys;

    /**
     *
     * @param keys K[]
     */
    @SafeVarargs
    @ConstructorProperties({"keys"})
    public HashMap(K... keys) {
        this.keys = keys;
    }

    /**
     *
     * @param keys Set {K}
     */
    @SuppressWarnings("unchecked")
    @ConstructorProperties({"keys"})
    public HashMap(Set<K> keys) {
        this.keys = (K[]) keys.toArray();
    }

    /**
     *
     * @param values List {V}
     * @return HashMap {K, V}
     * @throws ReflectiveOperationException throws
     */
    @SuppressWarnings("unchecked")
    public HashMap<K, V> values(List<V> values) throws ReflectiveOperationException {
        return values((V[]) values.toArray());
    }

    /**
     *
     * @param values V[]
     * @return HashMap {K, V}
     * @throws NullPointerException throws
     * @throws ReflectiveOperationException throws
     */
    @SafeVarargs
    public final HashMap<K, V> values(V... values) throws NullPointerException, ReflectiveOperationException {
        return _values(null, null, values);
    }

    /**
     *
     * @param jsonSchema JsonSchema
     * @return HashMap {K, V}
     * @throws NullPointerException throws
     * @throws ReflectiveOperationException throws
     */
    public HashMap<K, V> values(JsonSchema jsonSchema) throws NullPointerException, ReflectiveOperationException {
        return _values(jsonSchema, "object");
    }

    /**
     *
     * @param jsonSchema JsonSchema
     * @param type String
     * @return HashMap {K, V}
     * @throws NullPointerException throws
     * @throws ReflectiveOperationException throws
     */
    public HashMap<K, V> values(JsonSchema jsonSchema, String type) throws NullPointerException, ReflectiveOperationException {
        return _values(jsonSchema, type);
    }

    /**
     *
     * @param jsonSchema JsonSchema
     * @param type String
     * @param values V[]
     * @return HashMap {K, V}
     * @throws NullPointerException throws
     * @throws ReflectiveOperationException throws
     */
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
        debug("hashMap: " + hashMap);
        //debug("sortByK: " + sortByK(hashMap));
        return hashMap;
    }

    /**
     *
     * @return K[]
     * @throws ReflectiveOperationException throws
     */
    @SuppressWarnings("unchecked")
    public K[] keys() throws ReflectiveOperationException {
        return (K[]) keys(this, String[]::new);
    }

    /**
     *
     * @param generator IntFunction {K[]}
     * @return K[]
     * @throws ReflectiveOperationException throws
     */
    public K[] keys(IntFunction<K[]> generator) throws ReflectiveOperationException {
        return keys(this, generator);
    }

    /**
     *
     * @param obj Object
     * @return K[]
     * @param <K> K
     * @throws ReflectiveOperationException throws
     */
    @SuppressWarnings("unchecked")
    public static <K> K[] keys(Object obj) throws ReflectiveOperationException {
        return (K[]) keys(obj, String[]::new);
    }

    /**
     *
     * @param obj Object
     * @param generator IntFunction {K[]}
     * @return K[]
     * @param <K> K
     * @throws ReflectiveOperationException throws
     */
    @SuppressWarnings("unchecked")
    public static <K> K[] keys(Object obj, IntFunction<K[]> generator) throws ReflectiveOperationException {
        return ((Set<K>) invoke(obj, "keySet")).toArray(generator);
    }

    /**
     *
     * @return Function {K, String}
     * @param <K> K
     */
    public static <K> Function<K, String> getMapComp() {
        return Object::toString;
    }

    /**
     *
     * @return Function {Entry {K, V}, String}
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Function<Entry<K, V>, String> getKComp() {
        return o -> o.getKey().toString();
    }

    /**
     *
     * @return Function {Entry {K, V}, String}
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Function<Entry<K, V>, String> getVCompStr() {
        return o -> o.getValue().toString();
    }

    /**
     *
     * @return Function {Entry {K, V}, V}
     * @param <K> K
     * @param <V> V
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Function<Entry<K, V>, V> getVComp() {
        return o -> {
            V v = o.getValue();
            return notNull(v) ? v : (V) "";
        };
    }

    /**
     *
     * @return Comparator {K}
     * @param <K> K
     */
    public static <K> Comparator<K> mapComp() {
        return comparing(getMapComp());
    }

    /**
     *
     * @return Comparator {Entry {K, V}}
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Comparator<Entry<K, V>> kComp() {
        return comparing(getKComp());
    }

    /**
     *
     * @return Comparator {Entry {K, V}}
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Comparator<Entry<K, V>> vComp() {
        return comparing(getVCompStr());
    }

    /**
     *
     * @param map T
     * @return T
     * @param <T> T
     * @param <K> K
     * @param <V> V
     * @throws ReflectiveOperationException throws
     */
    public static <T extends Map<K, V>, K, V> T sortByK(T map) throws ReflectiveOperationException {
        return sort(map, getKComp());
    }

    /**
     *
     * @param map T
     * @return T
     * @param <T> T
     * @param <K> K
     * @param <V> V
     * @throws ReflectiveOperationException throws
     */
    public static <T extends Map<K, V>, K, V> T sortByV(T map) throws ReflectiveOperationException {
        return sort(map, getVCompStr());
    }

    /**
     *
     * @param set Set {T}
     * @return List {T}
     * @param <T> T
     */
    public static <T> List<T> sort(Set<T> set) {
        return sortedStream(set).toList();
    }

    /**
     *
     * @param set Set {T}
     * @return Stream {T}
     * @param <T> T
     */
    public static <T> Stream<T> sortedStream(Set<T> set) {
        return set.stream().sorted();
    }

    /**
     *
     * @param map T
     * @return T
     * @param <T> T
     * @param <K> K
     * @param <V> V
     * @throws ReflectiveOperationException throws
     */
    public static <T extends Map<K, V>, K extends Comparable<K>, V> T sort(T map) throws ReflectiveOperationException {
        return sort(map, Entry.comparingByKey());
    }

    /**
     *
     * @param map T
     * @return T
     * @param <T> T
     * @param <K> K
     * @param <V> V
     * @throws ReflectiveOperationException throws
     */
    public static <T extends Map<K, V>, K, V extends Comparable<V>> T sortV(T map) throws ReflectiveOperationException {
        return sort(map, Entry.comparingByValue());
    }

    /**
     *
     * @param map T
     * @param keyExtractor Function
     * @return T
     * @param <T> T
     * @param <K> K
     * @param <V> V
     * @param <R> R
     * @throws ReflectiveOperationException throws
     */
    public static <T extends Map<K, V>, K, V, R extends Comparable<R>> T sort(T map, Function<Entry<K, V>, R> keyExtractor) throws ReflectiveOperationException {
        return sort(map, comparing(keyExtractor));
    }

    /**
     *
     * @param map T
     * @param keyExtractor Function
     * @param factory Supplier
     * @return T
     * @param <T> T
     * @param <K> K
     * @param <V> V
     * @param <R> R
     * @throws ReflectiveOperationException throws
     */
    public static <T extends Map<K, V>, K, V, R extends Comparable<R>> T sort(T map, Function<Entry<K, V>, R> keyExtractor, Supplier<T> factory) throws ReflectiveOperationException {
        return sort(map, comparing(keyExtractor), factory);
    }

    /**
     *
     * @param map T
     * @param comparator Comparator
     * @return T
     * @param <T> T
     * @param <K> K
     * @param <V> V
     * @throws ReflectiveOperationException throws
     */
    @SuppressWarnings("unchecked")
    public static <T extends Map<K, V>, K, V> T sort(T map, Comparator<Entry<K, V>> comparator) throws ReflectiveOperationException {
        return (T) newMap(sorted(map, comparator));
    }

    /**
     *
     * @param map T
     * @param comparator Comparator
     * @param factory Supplier
     * @return T
     * @param <T> T
     * @param <K> K
     * @param <V> V
     */
    public static <T extends Map<K, V>, K, V> T sort(T map, Comparator<Entry<K, V>> comparator, Supplier<T> factory) {
        return newMap(sorted(map, comparator), factory);
    }

    /**
     *
     * @param map T
     * @param comparator Comparator
     * @return Stream
     * @param <T> T
     * @param <K> K
     * @param <V> V
     */
    public static <T extends Map<K, V>, K, V> Stream<Entry<K, V>> sorted(T map, Comparator<Entry<K, V>> comparator) {
        return streamMap(map).sorted(comparator);
    }

    /**
     *
     * @param o T
     * @param cb BiConsumer {String, Object}
     * @param <T> T
     */
    public static <T> void forEach(T o, BiConsumer<String, Object> cb) {
        entries(o).forEach(cb);
    }

    /**
     *
     * @param o T
     * @param cb BiConsumer {String, Object}
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T> void forEachSort(T o, BiConsumer<String, Object> cb) throws ReflectiveOperationException {
        sort(entries(o)).forEach(cb);
    }

    /**
     *
     * @param map T
     * @param cb BiConsumer {String, Object}
     * @param <T> T extends Map {K, V}
     * @param <K> K extends Comparable {K}
     * @param <V> V
     * @throws ReflectiveOperationException throws
     */
    public static <T extends Map<K, V>, K extends Comparable<K>, V> void forEachMap(T map, BiConsumer<K, V> cb) throws ReflectiveOperationException {
        sort(map).forEach(cb);
    }

    /**
     *
     * @return Set
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Set<Entry<K, V>> newTreeSet() {
        return newTreeSet(kComp());
    }

    /**
     *
     * @param comp Comparator
     * @return Set
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Set<Entry<K, V>> newTreeSet(Comparator<Entry<K, V>> comp) {
        return new TreeSet<>(comp);
    }

    /**
     *
     * @param arg Set
     * @return Set
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Set<Entry<K, V>> newTreeSet(Set<Entry<K, V>> arg) {
        /*Set<Entry<K, V>> sortedSet = new TreeSet<>(new Comparator<>() {
            @Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o1.getKey().toString().compareTo(o2.getKey().toString());
            }
        });*/
        Set<Entry<K, V>> sortedSet = new TreeSet<>(kComp());
        sortedSet.addAll(arg);
        return sortedSet;
    }

    /**
     *
     * @return Map
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Map<K, V> newTreeMap() {
        return newTreeMap(mapComp());
    }

    /**
     *
     * @param comp Comparator
     * @return Map
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Map<K, V> newTreeMap(Comparator<K> comp) {
        return new TreeMap<>(comp);
    }

    /**
     *
     * @param arg Map
     * @return Map
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Map<K, V> newTreeMap(Map<K, V> arg) {
        Map<K, V> sortedMap = new TreeMap<>(mapComp());
        sortedMap.putAll(arg);
        return sortedMap;
    }

    /**
     *
     * @param tree T
     * @param cb Function
     * @return T
     * @param <T> T
     * @param <R> R
     */
    public static <T, R> T newTree(T tree, Function<T, R> cb) {
        cb.apply(tree);
        return tree;
    }

    /**
     *
     * @param tree T
     * @param arg Object
     * @param method String
     * @return T
     * @param <K> K
     * @param <V> V
     * @param <T> Collection
     * @throws ReflectiveOperationException throws
     */
    public static <K, V, T extends Collection<Entry<K, V>>> T newTree(T tree, Object arg, String method) throws ReflectiveOperationException {
        return invoke(tree, method, arg);
    }

    /**
     *
     * @param clazz Class {T}
     * @param arg Object
     * @param comparator Comparator
     * @param method String
     * @return T
     * @param <K> K
     * @param <V> V
     * @param <T> Collection
     * @throws ReflectiveOperationException throws
     */
    public static <K, V, T extends Collection<Entry<K, V>>> T newTree(Class<T> clazz, Object arg, Comparator<Entry<K, V>> comparator, String method) throws ReflectiveOperationException {
        boolean setComp = notNull(comparator);
        T tree = create(clazz, !setComp ? arg : comparator);
        if (setComp) invoke(tree, method, arg);
        return tree;
    }

}
