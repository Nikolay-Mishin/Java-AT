package org.project.utils;

import static java.lang.System.out;
import static java.nio.charset.Charset.defaultCharset;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.text.MessageFormat.format;
import static java.util.Arrays.stream;
import static java.util.Map.Entry;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableList.copyOf;

import com.google.common.collect.ImmutableList;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.testng.collections.Lists;

import static org.project.utils.config.Config.debugLvl;
import static org.project.utils.base.HashMap.getVComp;
import static org.project.utils.reflection.Reflection.arrInstance;
import static org.project.utils.reflection.Reflection.fields;
import static org.project.utils.reflection.Reflection.getClazz;

/**
 *
 */
public class Helper {

    /**
     *
     * @param msg Object
     */
    public static void debug(Object msg) {
        if (debugLvl() > 0) out.println(msg);
    }

    /**
     *
     * @param msg String
     * @param debugLvl int
     */
    public static void debug(String msg, int debugLvl) {
        if (debugLvl() >= debugLvl) debug(msg);
    }

    /**
     *
     * @param msg String
     * @param args  String[]
     */
    public static void debug(String msg, String... args) {
        if (debugLvl() > 0) debug(format("{0} {1}", msg, join(", ", args)));
    }

    /**
     *
     * @param msg String
     * @param debugLvl int
     * @param args String[]
     */
    public static void debug(String msg, int debugLvl, String... args) {
        if (debugLvl() >= debugLvl) debug(msg, args);
    }

    /**
     *
     * @param table String
     * @return List {List {T}}
     */
    @SuppressWarnings("unchecked")
    public static List<List<String>> table(String table) {
        return toList(trim(table.split(";")), List[]::new, row -> arrayList(trim(row.split(","))));
    }

    /**
     *
     * @param table T[]
     * @return List {List {T}}
     * @param <T> T
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T> List<List<T>> table(T[]... table) {
        return toList(table, List[]::new, Helper::arrayList);
    }

    /**
     *
     * @param map Map {K, V}
     * @return List {List {V}}
     * @param <K> K
     * @param <V> V
     */
    @SuppressWarnings("unchecked")
    public static <K, V> List<List<V>> table(Map<K, V> map) {
        return toList(map.keySet(), List[]::new, (K k) -> (List<V>) newArrayList(k, map.get(k)));
    }

    /**
     *
     * @param array A[]
     * @return List {A}
     * @param <A> A
     */
    public static <A> List<A> arrayList(A[] array) {
        return new ArrayList<>(List.of(array));
    }

    /**
     *
     * @param array T[]
     * @return List {T}
     * @param <T> T
     */
    @SafeVarargs
    public static <T> List<T> newArrayList(T... array) {
        return arrayList(array);
    }

    /**
     *
     * @param array A[]
     * @return T[]
     * @param <A> A
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <A, T> T[] newArray(A[] array) {
        return array.length == 1 ? (T[]) new Object[]{array} : toArray(array);
    }

    /**
     *
     * @param array A[]
     * @return T[]
     * @param <A> A
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <A, T> T[] toArray(A[] array) {
        return (T[]) stream(array).toArray();
    }

    /**
     *
     * @param array A[]
     * @param generator IntFunction {T[]}
     * @return T[]
     * @param <A> A
     * @param <T> T
     */
    public static <A, T> T[] toArray(A[] array, IntFunction<T[]> generator) {
        return stream(array).toArray(generator);
    }

    /**
     *
     * @param set Set {A}
     * @return A[]
     * @param <A> A
     */
    @SuppressWarnings("unchecked")
    public static <A> A[] toArray(Set<A> set) {
        return (A[]) set.toArray();
    }

    /**
     *
     * @param array A[]
     * @return List {A}
     * @param <A> A
     */
    public static <A> List<A> toList(A[] array) {
        return stream(array).toList();
    }

    /**
     *
     * @param set Set {A}
     * @param generator IntFunction {T[]}
     * @param mapper Function {A, T}
     * @return List {T}
     * @param <A> A
     * @param <T> T
     */
    public static <A, T> List<T> toList(Set<A> set, IntFunction<T[]> generator, Function<A, T> mapper) {
        return toList(map(set, generator, mapper));
    }

    /**
     *
     * @param array A[]
     * @param generator IntFunction {T[]}
     * @param mapper Function {A, T}
     * @return List {T}
     * @param <A> A
     * @param <T> T
     */
    public static <A, T> List<T> toList(A[] array, IntFunction<T[]> generator, Function<A, T> mapper) {
        return toList(map(array, generator, mapper));
    }

    /**
     *
     * @param list List {A}
     * @param generator IntFunction {T[]}
     * @param mapper Function {A, T}
     * @return List {T}
     * @param <A> A
     * @param <T> T
     */
    public static <A, T> List<T> toList(List<A> list, IntFunction<T[]> generator, Function<A, T> mapper) {
        return toList(map(list, generator, mapper));
    }

    /**
     *
     * @param stream Stream {A}
     * @param generator IntFunction {T[]}
     * @param mapper Function {A, T}
     * @return List {T}
     * @param <A> A
     * @param <T> T
     */
    public static <A, T> List<T> toList(Stream<A> stream, IntFunction<T[]> generator, Function<A, T> mapper) {
        return toList(map(stream, generator, mapper));
    }

    /**
     *
     * @param iterable Iterable {I}
     * @return List {T}
     * @param <T> T
     * @param <I> I
     */
    public static <T, I> List<T> toList(Iterable<I> iterable) {
        return toList(iterable.iterator());
    }

    /**
     *
     * @param iterator Iterator {I}
     * @return List {T}
     * @param <T> T
     * @param <I> I
     */
    @SuppressWarnings("unchecked")
    public static <T, I> List<T> toList(Iterator<I> iterator) {
        return IteratorUtils.toList(iterator);
    }

    /**
     *
     * @param iterator Iterator {T}
     * @return List {T}
     * @param <T> T
     */
    public static <T> List<T> newList(Iterator<T> iterator) {
        return Lists.newArrayList(iterator);
    }

    /**
     *
     * @param iterator Iterator {T}
     * @return List {T}
     * @param <T> T
     */
   public static <T> ImmutableList<T> copyList(Iterator<T> iterator) {
        return copyOf(iterator);
    }

    /**
     *
     * @param iterable Iterable {I}
     * @param filter Predicate {T}
     * @return List {T}
     * @param <T> T
     * @param <I> I
     */
    public static <T, I> List<T> toList(Iterable<I> iterable, Predicate<T> filter) {
        return toList(iterable.iterator(), filter);
    }

    /**
     *
     * @param iterator Iterator {I}
     * @param filter Predicate {T}
     * @return List {T}
     * @param <T> T
     * @param <I> I
     */
    public static <T, I> List<T> toList(Iterator<I> iterator, Predicate<T> filter) {
        //return ((List<T>) toList(iterator)).stream().filter(filter).toList();
        return filter(toList(iterator), filter);
    }

    /**
     *
     * @param set Set {A}
     * @param action Consumer {A}
     * @param <A> A
     */
    public static <A> void forEach(Set<A> set, Consumer<A> action) {
        forEach(toArray(set), action);
    }

    /**
     *
     * @param array A[]
     * @param action Consumer {A}
     * @param <A> A
     */
    public static <A> void forEach(A[] array, Consumer<A> action) {
        forEach(stream(array), action);
    }

    /**
     *
     * @param list List {A}
     * @param action Consumer {A}
     * @param <A> A
     */
    public static <A> void forEach(List<A> list, Consumer<A> action) {
        forEach(list.stream(), action);
    }

    /**
     *
     * @param stream Stream {A}
     * @param action Consumer {A}
     * @param <A> A
     */
    public static <A> void forEach(Stream<A> stream, Consumer<A> action) {
        stream.forEach(action);
    }

    /**
     *
     * @param set Set {A}
     * @param generator IntFunction {T[]}
     * @param mapper Function {A, T}
     * @return T[]
     * @param <A> A
     * @param <T> T
     */
    public static <A, T> T[] map(Set<A> set, IntFunction<T[]> generator, Function<A, T> mapper) {
        return map(toArray(set), generator, mapper);
    }

    /**
     *
     * @param array A[]
     * @param generator IntFunction {T[]}
     * @param mapper Function {A, T}
     * @return T[]
     * @param <A> A
     * @param <T> T
     */
    public static <A, T> T[] map(A[] array, IntFunction<T[]> generator, Function<A, T> mapper) {
        return map(stream(array), generator, mapper);
    }

    /**
     *
     * @param list List {A}
     * @param generator IntFunction {T[]}
     * @param mapper Function {A, T}
     * @return T[]
     * @param <A> A
     * @param <T> T
     */
    public static <A, T> T[] map(List<A> list, IntFunction<T[]> generator, Function<A, T> mapper) {
        return map(list.stream(), generator, mapper);
    }

    /**
     *
     * @param stream Stream {A}
     * @param generator IntFunction {T[]}
     * @param mapper Function {A, T}
     * @return T[]
     * @param <A> A
     * @param <T> T
     */
    public static <A, T> T[] map(Stream<A> stream, IntFunction<T[]> generator, Function<A, T> mapper) {
        return stream
            .map(mapper)
            .toArray(generator);
    }

    /**
     *
     * @param array A[]
     * @param filter Predicate {A}
     * @return List {A}
     * @param <A> A
     */
    public static <A> List<A> filter(A[] array, Predicate<A> filter) {
        return filter(stream(array), filter);
    }

    /**
     *
     * @param list List {T}
     * @param filter Predicate {T}
     * @return List {T}
     * @param <T> T
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> filter) {
        return filter(list.stream(), filter);
    }

    /**
     *
     * @param stream Stream {T}
     * @param filter Predicate {T}
     * @return List {T}
     * @param <T> T
     */
    public static <T> List<T> filter(Stream<T> stream, Predicate<T> filter) {
        return stream
            .filter(filter)
            .toList();
    }

    /**
     *
     * @param a A[]
     * @param b A[]
     * @return A[]
     * @param <A> A
     */
    public static <A> A[] concat(A[] a, A[] b) {
        return Stream.concat(stream(a), stream(b)).toArray(size -> arrInstance(a, size));
    }

    /**
     *
     * @param a A[]
     * @param b B[]
     * @return Object[]
     * @param <A> A
     * @param <B> B
     */
    public static <A, B> Object[] concatTo(A[] a, B[] b) {
        return concat(toArray(a, Object[]::new), new Object[]{b});
    }

    /**
     *
     * @param a A[]
     * @param b A[]
     * @return A[]
     * @param <A> A
     */
    @SuppressWarnings("unchecked")
    public static <A> A[] concatUtils(A[] a, A[] b) {
        return (A[]) ArrayUtils.addAll(a, b);
    }

    /**
     *
     * @param a A[]
     * @param array A[]
     * @return A[]
     * @param <A> A
     */
    @SafeVarargs
    public static <A> A[] concatAll(A[] a, A... array) {
        return org.apache.commons.lang3.ArrayUtils.addAll(a, array);
    }

    /**
     *
     * @param map1 M
     * @param map2 M
     * @param <K> K
     * @param <V> V
     * @param <M> extends Map {K, V}
     */
    public static <K, V, M extends Map<K, V>> void merge(M map1, M map2) {
        merge(map1, map2, (v1, v2) -> v2);
    }

    /**
     *
     * @param map1 M
     * @param map2 M
     * @param merge BiFunction {V, V, V}
     * @param <K> K
     * @param <V> V
     * @param <M> extends Map {K, V}
     */
    public static <K, V, M extends Map<K, V>> void merge(M map1, M map2, BiFunction<V, V, V> merge) {
        map2.forEach((k, v) -> map1.merge(k, v, merge));
    }

    /**
     *
     * @param map M
     * @return Stream {Entry {K, V}}
     * @param <K> K
     * @param <V> V
     * @param <M> extends Map {K, V}
     */
    public static <K, V, M extends Map<K, V>> Stream<Entry<K, V>> streamMap(M map) {
        return map.entrySet().stream();
    }

    /**
     *
     * @param map1 M
     * @param map2 M
     * @return Stream {Entry {K, V}}
     * @param <K> K
     * @param <V> V
     * @param <M> extends Map {K, V}
     */
    public static <K, V, M extends Map<K, V>> Stream<Entry<K, V>> concat(M map1, M map2) {
        return Stream.concat(streamMap(map1), streamMap(map2));
    }

    /**
     *
     * @param map1 M
     * @param map2 M
     * @return M
     * @param <K> K
     * @param <V> V
     * @param <M> extends Map {K, V}
     */
    public static <K, V, M extends Map<K, V>> M toMap(M map1, M map2) {
        return toMap(map1, map2, (v1, v2) -> v2);
    }

    /**
     *
     * @param map1 M
     * @param map2 M
     * @param merge BinaryOperator {V}
     * @return M
     * @param <K> K
     * @param <V> V
     * @param <M> extends Map {K, V}
     */
    @SuppressWarnings("unchecked")
    public static <K, V, M extends Map<K, V>> M toMap(M map1, M map2, BinaryOperator<V> merge) {
        return (M) toMap(concat(map1, map2), merge);
    }

    /**
     *
     * @param map Stream {Entry {K, V}}
     * @return Map {K, V}
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Map<K, V> toMap(Stream<Entry<K, V>> map) {
        return toMap(map, Entry::getKey, Entry::getValue);
    }

    /**
     *
     * @param map Stream {Entry {K, V}}
     * @return LinkedHashMap {K, V}
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> LinkedHashMap<K, V> newMap(Stream<Entry<K, V>> map) {
        return newMap(map, LinkedHashMap::new);
    }

    /**
     *
     * @param map Stream {Entry {K, V}}
     * @param factory Supplier {M}
     * @return M
     * @param <K> K
     * @param <V> V
     * @param <M> extends Map {K, V}
     */
    public static <K, V, M extends Map<K, V>> M newMap(Stream<Entry<K, V>> map, Supplier<M> factory) {
        //return toMap(map, Entry::getKey, Entry::getValue, (v1, v2) -> v1, factory);
        return toMap(map, Entry::getKey, getVComp(), (v1, v2) -> v1, factory);
    }

    /**
     *
     * @param map Stream {Entry {K, V}}
     * @param merge BinaryOperator {V}
     * @return Map {K, V}
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Map<K, V> toMap(Stream<Entry<K, V>> map, BinaryOperator<V> merge) {
        return toMap(map, Entry::getKey, Entry::getValue, merge);
    }

    /**
     *
     * @param map Stream {Entry {K, V}}
     * @param merge BinaryOperator {V}
     * @param factory Supplier {M}
     * @return M
     * @param <K> K
     * @param <V> V
     * @param <M> extends Map {K, V}
     */
    public static <K, V, M extends Map<K, V>> M toMap(Stream<Entry<K, V>> map, BinaryOperator<V> merge, Supplier<M> factory) {
        return toMap(map, Entry::getKey, Entry::getValue, merge, factory);
    }

    /**
     *
     * @param map Stream {T}
     * @param keys Function {T, K}
     * @param values Function {T, V}
     * @return M
     * @param <T> extends Entry {K, V}
     * @param <K> K
     * @param <V> V
     * @param <M> extends Map {K, V}
     */
    @SuppressWarnings("unchecked")
    public static <T extends Entry<K, V>, K, V, M extends Map<K, V>> M toMap(Stream<T> map, Function<T, K> keys, Function<T, V> values) {
        return (M) map.collect(Collectors.toMap(keys, values));
    }

    /**
     *
     * @param map Stream {T}
     * @param keys Function {T, K}
     * @param values Function {T, V}
     * @param merge BinaryOperator {V}
     * @return M
     * @param <T> extends Entry {K, V}
     * @param <K> K
     * @param <V> V
     * @param <M> extends Map {K, V}
     */
    @SuppressWarnings("unchecked")
    public static <T extends Entry<K, V>, K, V, M extends Map<K, V>> M toMap(Stream<T> map, Function<T, K> keys, Function<T, V> values, BinaryOperator<V> merge) {
        return (M) map.collect(Collectors.toMap(keys, values, merge));
    }

    /**
     *
     * @param map Stream {T}
     * @param keys Function {T, K}
     * @param values Function {T, V}
     * @param merge BinaryOperator {V}
     * @param factory Supplier {M}
     * @return M
     * @param <T> extends Entry {K, V}
     * @param <K> K
     * @param <V> V
     * @param <M> extends Map {K, V}
     */
    public static <T extends Entry<K, V>, K, V, M extends Map<K, V>> M toMap(Stream<T> map, Function<T, K> keys, Function<T, V> values, BinaryOperator<V> merge, Supplier<M> factory)
    {
        return map.collect(Collectors.toMap(keys, values, merge, factory));
    }

    /**
     *
     * @param c Collection {T}
     * @param elements T[]
     * @return Collection {T}
     * @param <T> T
     */
    @SafeVarargs
    public static <T> Collection<T> addAll(Collection<T> c, T @NotNull ... elements) {
        Collections.addAll(c, elements);
        return c;
    }

    /**
     *
     * @param type Object
     * @param clazz Class
     * @return Boolean
     */
    private static Boolean is(Object type, Class<?> clazz) {
        return type == clazz || isInstance(type, clazz);
    }

    /**
     *
     */
    public static void printDefaultCharset() {
        debug(defaultCharset());
    }

    /**
     *
     * @param value String
     * @return ByteBuffer
     */
    public static ByteBuffer encode(String value) {
        return UTF_8.encode(value);
    }

    /**
     *
     * @param buf ByteBuffer
     * @return String
     */
    public static String decode(ByteBuffer buf) {
        return defaultCharset().decode(buf.flip()).toString();
    }

    /**
     *
     * @param value Object
     * @return boolean
     */
    public static boolean isEmpty(Object value) {
        return _equals(value, "");
    }

    /**
     *
     * @param value Object
     * @return boolean
     */
    public static boolean isNull(Object value) {
        return value == null || isEmpty(value);
    }

    /**
     *
     * @param value Object
     * @return boolean
     */
    public static boolean notNull(Object value) {
        return !isNull(value);
    }

    /**
     *
     * @param a Object
     * @param b Object
     * @return boolean
     */
    public static boolean _equals(@Nullable Object a, @Nullable Object b) {
        return Objects.equals(a, b);
    }

    /**
     *
     * @param a Object
     * @param b Object
     * @return boolean
     */
    public static boolean notEquals(@Nullable Object a, @Nullable Object b) {
        return !_equals(a, b);
    }

    /**
     *
     * @param obj Object
     * @return Boolean
     */
    public static Boolean isClass(Object obj) {
        return obj instanceof Class<?>;
    }

    /**
     *
     * @param obj Object
     * @param clazz Class
     * @return Boolean
     */
    public static Boolean isInstance(Object obj, Class<?> clazz) {
        return clazz.isInstance(obj);
    }

    /**
     *
     * @param obj Object
     * @return Boolean
     */
    public static Boolean isArray(Object obj) {
        return isInstance(obj, Array.class) || getClazz(obj).isArray();
    }

    /**
     *
     * @param obj Object
     * @return Boolean
     */
    public static Boolean isList(Object obj) {
        return isInstance(obj, List.class);
    }

    /**
     *
     * @param obj Object
     * @return Boolean
     */
    public static Boolean isArrayList(Object obj) {
        return isInstance(obj, ArrayList.class);
    }

    /**
     *
     * @param type Object
     * @return Boolean
     */
    public static Boolean isParseType(Object type) {
        return isString(type) || isBool(type) || isInt(type) || isFloat(type)  || isLong(type) || isDouble(type) || isShort(type) || isByte(type);
    }

    /**
     *
     * @param type Object
     * @return Boolean
     */
    public static Boolean isString(Object type) {
        return isInstance(type, String.class);
    }

    /**
     *
     * @param type Object
     * @return Boolean
     */
    public static Boolean isBool(Object type) {
        return is(type, boolean.class) || is(type, Boolean.class);
    }

    /**
     *
     * @param type Object
     * @return Boolean
     */
    public static Boolean isInt(Object type) {
        return is(type, int.class) || is(type, Integer.class);
    }

    /**
     *
     * @param type Object
     * @return Boolean
     */
    public static Boolean isFloat(Object type) {
        return is(type, float.class) || is(type, Float.class);
    }

    /**
     *
     * @param type Object
     * @return Boolean
     */
    public static Boolean isLong(Object type) {
        return is(type, long.class) || is(type, Long.class);
    }

    /**
     *
     * @param type Object
     * @return Boolean
     */
    public static Boolean isDouble(Object type) {
        return is(type, double.class) || is(type, Double.class);
    }

    /**
     *
     * @param type Object
     * @return Boolean
     */
    public static Boolean isShort(Object type) {
        return is(type, short.class) || is(type, Short.class);
    }

    /**
     *
     * @param type Object
     * @return Boolean
     */
    public static Boolean isByte(Object type) {
        return is(type, byte.class) || is(type, Byte.class);
    }

    /**
     *
     * @param str String
     * @return String
     */
    public static String toUpperCaseFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     *
     * @param str String
     * @return String
     */
    public static String toLowerCaseFirst(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     *
     * @param arr T[]
     * @return T
     * @param <T> T
     */
    public static <T> T last(T[] arr) {
        return arr[arr.length - 1];
    }

    /**
     *
     * @param arr String[]
     * @param sep String
     * @return T
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T> T lastTrim(T[] arr, String sep) {
        return (T) last(arr, trim(last(arr).toString(), sep));
    }

    /**
     *
     * @param arr T[]
     * @param v T
     * @return T
     * @param <T> T
     */
    public static <T> T last(T[] arr, T v) {
        return arr[arr.length - 1] = v;
    }

    /**
     *
     * @param s String
     * @return String
     */
    public static String last(String s) {
        return last(s, "/");
    }

    /**
     *
     * @param s String
     * @param split String
     * @return String
     */
    public static String last(String s, String split) {
        //return s.substring(s.lastIndexOf(split) + 1);
        return last(s.split(split));
    }

    /**
     *
     * @param s String
     * @param sep String
     * @return String
     */
    public static String trim(String s, String sep) {
        return s.replaceAll(sep, "");
    }

    /**
     *
     * @param arr String[]
     * @return String[]
     */
    public static String[] trim(String[] arr) {
        return map(arr, String[]::new, String::trim);
    }

    /**
     *
     * @param s String
     * @param sep String
     * @return String[]
     */
    public static String[] split(String s, String sep) {
        return trim(s.split(sep));
    }

    /**
     *
     * @param arr T[]
     * @param item T
     * @return T[]
     * @param <T> T
     */
    public static <T> T[] push(T[] arr, T item) {
        T[] tmp = Arrays.copyOf(arr, arr.length + 1);
        tmp[tmp.length - 1] = item;
        return tmp;
    }

    /**
     *
     * @param arr T[]
     * @return T[]
     * @param <T> T
     */
    public static <T> T[] shift(T[] arr) {
        return remove(arr, 0);
    }

    /**
     *
     * @param arr T[]
     * @return T[]
     * @param <T> T
     */
    public static <T> T[] shiftSkip(T[] arr) {
        return skip(arr, 1);
    }

    /**
     *
     * @param arr T[]
     * @return T[]
     * @param <T> T
     */
    public static <T> T[] shiftList(T[] arr) {
        return pop(rotate(arr, -1));
    }

    /**
     *
     * @param arr T[]
     * @return T[]
     * @param <T> T
     */
    public static <T> T[] pop(T[] arr) {
        return remove(arr, arr.length - 1);
    }

    /**
     *
     * @param arr T[]
     * @return T[]
     * @param <T> T
     */
    public static <T> T[] popCopy(T[] arr) {
        return Arrays.copyOf(arr, arr.length - 1);
    }

    /**
     *
     * @param arr T[]
     * @return T[]
     * @param <T> T
     */
    public static <T> T[] popSkip(T[] arr) {
        return skip(rotate(arr, 1), 1);
    }

    /**
     *
     * @param arr T[]
     * @param n int
     * @return T[]
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] skip(T[] arr, int n) {
        return (T[]) stream(arr).skip(n).toArray();
    }

    /**
     *
     * @param arr T[]
     * @param distance int
     * @return T[]
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] rotate(T[] arr, int distance) {
        //List<T> list = asList(arr); // меняет исходный массив (работает как ссылка)
        List<T> list = arrayList(arr);
        Collections.rotate(list, distance);
        return (T[]) list.toArray();
    }

    /**
     *
     * @param arr T[]
     * @param index int
     * @return T[]
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] remove(T[] arr, int index) {
        return (T[]) ArrayUtils.remove(arr, index);
    }

    /**
     *
     * @param entries ArrayList {T}
     * @param elements E[]
     * @return ArrayList {T}
     * @param <T> T
     * @param <E> E
     */
    @SuppressWarnings("unchecked")
    public static <T, E> ArrayList<T> addEntries(ArrayList<T> entries, E @NotNull ... elements) {
        entries.add((T) addAll(new ArrayList<>(), elements));
        return entries;
    }

    /**
     *
     * @param entries Map {K, V}
     * @param key K
     * @param value V
     * @return Map {K, V}
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> Map<K, V> putEntries(Map<K, V> entries, K key, V value) {
        entries.put(key, value);
        return entries;
    }

    /**
     *
     * @param obj Object
     * @return Map {K, V}
     */
    public static Map<String, Object> entries(Object obj) {
        return entries(obj, new HashMap<>(), (field, entries) -> {
            try {return (HashMap<String, Object>) putEntries(entries, fieldName(field), field.get(obj));}
            catch (IllegalAccessException e) {throw new RuntimeException(e);}
        });
    }

    /**
     *
     * @param obj Object
     * @return List {Object[]}
     */
    public static List<Object[]> entriesList(Object obj) {
        return entries(obj, new ArrayList<>(), (field, entries) -> {
            try {return addEntries(entries, fieldName(field), field.get(obj));}
            catch (IllegalAccessException e) {throw new RuntimeException(e);}
        });
    }

    /**
     *
     * @param obj Object
     * @return Object[]
     */
    public static Object[] entriesArray(Object obj) {
        return entriesList(obj).toArray();
    }

    /**
     *
     * @param obj Object
     * @param entries T
     * @param func BiFunction {Field, T, T}
     * @return T
     * @param <T> T
     */
    public static <T> T entries(Object obj, T entries, BiFunction<Field, T, T> func) {
        return fields(obj, func, entries);
    }

    /**
     *
     * @param field Field
     * @return String
     */
    public static String fieldName(Field field) {
        return field.toString().replaceAll("(^.*)(\\.)([^.]+)$", "$3");
    }

    /**
     *
     * @param obj Object
     * @return Set {String}
     * @throws IllegalAccessException throws
     */
    public static Set<String> keys(Object obj) throws IllegalAccessException {
        return keys(entries(obj));
    }

    /**
     *
     * @param obj Object
     * @return Collection {Object}
     * @throws IllegalAccessException throws
     */
    public static Collection<Object> values(Object obj) throws IllegalAccessException {
        return values(entries(obj));
    }

    /**
     *
     * @param obj Object
     * @return Object[]
     */
    public static Object[] keysArray(Object obj) {
        return keysArray(entries(obj));
    }

    /**
     *
     * @param obj Object
     * @return Object[]
     */
    public static Object[] valuesArray(Object obj) {
        return valuesArray(entries(obj));
    }

    /**
     *
     * @param obj Object
     * @return List {String}
     */
    public static List<String> keysList(Object obj) {
        return keysList(entries(obj));
    }

    /**
     *
     * @param obj Object
     * @return List {Object}
     */
    public static List<Object> valuesList(Object obj) {
        return valuesList(entries(obj));
    }

    /**
     *
     * @param entriesMap Map {String, Object}
     * @return Set {String}
     * @throws IllegalAccessException throws
     */
    public static Set<String> keys(Map<String, Object> entriesMap) throws IllegalAccessException {
        return entriesMap.keySet();
    }

    /**
     *
     * @param entriesMap Map {String, Object}
     * @return Collection {Object}
     * @throws IllegalAccessException throws
     */
    public static Collection<Object> values(Map<String, Object> entriesMap) throws IllegalAccessException {
        return entriesMap.values();
    }

    /**
     *
     * @param entriesMap Map {String, Object}
     * @return Object[]
     */
    public static Object[] keysArray(Map<String, Object> entriesMap) {
        return entriesMap.keySet().toArray();
    }

    /**
     *
     * @param entriesMap Map {String, Object}
     * @return Object[]
     */
    public static Object[] valuesArray(Map<String, Object> entriesMap) {
        return entriesMap.values().toArray();
    }

    /**
     *
     * @param entriesMap Map {String, Object}
     * @return List {String}
     */
    public static List<String> keysList(Map<String, Object> entriesMap) {
        return entriesMap.keySet().stream().toList();
    }

    /**
     *
     * @param entriesMap Map {String, Object}
     * @return List {Object}
     */
    public static List<Object> valuesList(Map<String, Object> entriesMap) {
        return entriesMap.values().stream().toList();
    }

    /**
     *
     * @param args String[]
     * @return String
     */
    public static String join(String... args) {
        return join("", args);
    }

    /**
     *
     * @param sep String
     * @param args String[]
     * @return String
     */
    public static String join(String sep, String... args) {
        return String.join(sep, args);
    }

    /**
     *
     * @param args T[]
     * @return String
     * @param <T> T
     */
    @SafeVarargs
    public static <T> String join(T... args) {
        return join("", args);
    }

    /**
     *
     * @param sep String
     * @param args T[]
     * @return String
     * @param <T> T
     */
    @SafeVarargs
    public static <T> String join(String sep, T... args) {
        return join(sep, toArray(args, String[]::new));
    }

    /**
     *
     * @param args List {T}
     * @return String
     * @param <T> T
     */
    public static <T> String join(List<T> args) {
        return join("", args);
    }

    /**
     *
     * @param sep String
     * @param args List {T}
     * @return String
     * @param <T> T
     */
    public static <T> String join(String sep, List<T> args) {
        return join(sep, map(args.toArray(), String[]::new, Object::toString));
    }

    /**
     *
     * @param args Object[]
     * @return String
     */
    public static String sb(Object... args) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) sb.append(arg);
        return sb.toString();
    }

    /**
     *
     * @param str String
     * @param args Object[]
     * @return String
     */
    public static String sf(String str, Object... args) {
        return String.format(str, args);
    }

}
