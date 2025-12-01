package org.project.utils;

import static java.lang.String.join;
import static java.lang.System.*;
import static java.text.MessageFormat.format;
import static java.util.Arrays.stream;

import java.lang.reflect.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableList.copyOf;
import static org.testng.collections.Lists.newArrayList;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.*;

import static org.project.utils.config.Config.debugLvl;
import static org.project.utils.reflection.Reflection.*;

public class Helper {

    public static void debug(Object msg) {
        if (debugLvl() > 0) out.println(msg);
    }

    public static void debug(String msg, int debugLvl) {
        if (debugLvl() >= debugLvl) debug(msg);
    }

    public static void debug(String msg, String... args) {
        if (debugLvl() > 0) debug(format("{0} {1}", msg, join(", ", args)));
    }

    public static void debug(String msg, int debugLvl, String... args) {
        if (debugLvl() >= debugLvl) debug(msg, args);
    }

    @SafeVarargs
    public static <T> List<List<T>> table(T[]... row) {
        return toList(row, List[]::new, Helper::arrayList);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> List<List<V>> toTable(Map<K, V> map) {
        return toList(map.keySet(), List[]::new, (K k) -> (List<V>) newArrayList(k, map.get(k)));
    }

    public static <A> List<A> arrayList(A[] array) {
        return new ArrayList<>(List.of(array));
    }

    @SafeVarargs
    public static <T> List<T> newArrayList(T... array) {
        return arrayList(array);
    }

    public static <A, T> T[] newArray(A[] array) {
        return array.length == 1 ? (T[]) new Object[]{array} : toArray(array);
    }

    public static <A, T> T[] toArray(A[] array) {
        return (T[]) stream(array).toArray();
    }

    public static <A, T> T[] toArray(A[] array, IntFunction<T[]> generator) {
        return stream(array).toArray(generator);
    }

    public static <A> List<A> toList(A[] array) {
        return stream(array).toList();
    }

    public static <A, T> List<T> toList(Set<A> set, IntFunction<T[]> generator, Function<A, T> mapper) {
        return toList(map(set, generator, mapper));
    }

    public static <A, T> List<T> toList(A[] array, IntFunction<T[]> generator, Function<A, T> mapper) {
        return toList(map(array, generator, mapper));
    }

    public static <A, T> List<T> toList(List<A> list, IntFunction<T[]> generator, Function<A, T> mapper) {
        return toList(map(list, generator, mapper));
    }

    public static <A, T> List<T> toList(Stream<A> stream, IntFunction<T[]> generator, Function<A, T> mapper) {
        return toList(map(stream, generator, mapper));
    }

    public static <T, I> List<T> toList(Iterable<I> iterable) {
        return toList(iterable.iterator());
    }

    //public static <I> List<I> toList(Iterator<I> iterator) {
    //public static <T, I> ImmutableList<T> toList(Iterator<I> iterator) {
    public static <T, I> List<T> toList(Iterator<I> iterator) {
        //return arrayList(iterator);
        //return copyOf(iterator);
        return IteratorUtils.toList(iterator);
    }

    public static <T, I> List<T> toList(Iterable<I> iterable, Predicate<T> filter) {
        return toList(iterable.iterator(), filter);
    }

    public static <T, I> List<T> toList(Iterator<I> iterator, Predicate<T> filter) {
        //return ((List<T>) toList(iterator)).stream().filter(filter).toList();
        return filter(toList(iterator), filter);
    }

    public static <A, T> T[] map(Set<A> set, IntFunction<T[]> generator, Function<A, T> mapper) {
        return map((A[]) set.toArray(), generator, mapper);
    }

    public static <A, T> T[] map(A[] array, IntFunction<T[]> generator, Function<A, T> mapper) {
        return map(stream(array), generator, mapper);
    }

    public static <A, T> T[] map(List<A> list, IntFunction<T[]> generator, Function<A, T> mapper) {
        return map(list.stream(), generator, mapper);
    }

    public static <A, T> T[] map(Stream<A> stream, IntFunction<T[]> generator, Function<A, T> mapper) {
        return stream
            .map(mapper)
            .toArray(generator);
    }

    public static <A> List<A> filter(A[] array, Predicate<A> filter) {
        return filter(stream(array), filter);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> filter) {
        return filter(list.stream(), filter);
    }

    public static <T> List<T> filter(Stream<T> stream, Predicate<T> filter) {
        return stream
            .filter(filter)
            .toList();
    }

    public static <A> A[] concat(A[] a, A[] b) {
        return Stream.concat(stream(a), stream(b)).toArray(size -> arrInstance(a, size));
    }

    public static <A, B> Object[] concatTo(A[] a, B[] b) {
        return concat(toArray(a, Object[]::new), new Object[]{b});
    }

    public static <A> A[] concatUtils(A[] a, A[] b) {
        return (A[]) ArrayUtils.addAll(a, b);
    }

    public static <A> A[] concatAll(A[] a, A... array) {
        return org.apache.commons.lang3.ArrayUtils.addAll(a, array);
    }

    @SafeVarargs
    public static <T> Collection<T> addAll(Collection<T> c, T @NotNull ... elements) {
        Collections.addAll(c, elements);
        return c;
    }

    private static Boolean is(Object type, Class<?> clazz) {
        return type == clazz || isInstance(type, clazz);
    }

    public static Charset defaultCharset() {
        return Charset.defaultCharset();
    }

    public static void PrintDefaultCharset() {
        debug(defaultCharset());
    }

    public static boolean isEmpty(Object value) {
        return _equals(value, "");
    }

    public static boolean isNull(Object value) {
        return value == null || isEmpty(value);
    }

    public static boolean notNull(Object value) {
        return !isNull(value);
    }

    public static boolean _equals(@Nullable Object a, @Nullable Object b) {
        return Objects.equals(a, b);
    }

    public static boolean notEquals(@Nullable Object a, @Nullable Object b) {
        return !_equals(a, b);
    }

    public static Boolean isClass(Object obj) {
        return obj instanceof Class<?>;
    }

    public static Boolean isInstance(Object obj, Class<?> clazz) {
        return clazz.isInstance(obj);
    }

    public static Boolean isArray(Object obj) {
        return isInstance(obj, Array.class) || getClazz(obj).isArray();
    }

    public static Boolean isList(Object obj) {
        return isInstance(obj, List.class);
    }

    public static Boolean isArrayList(Object obj) {
        return isInstance(obj, ArrayList.class);
    }

    public static Boolean isParseType(Object type) {
        return isString(type) || isBool(type) || isInt(type) || isFloat(type)  || isLong(type) || isDouble(type) || isShort(type) || isByte(type);
    }

    public static Boolean isString(Object type) {
        return isInstance(type, String.class);
    }

    public static Boolean isBool(Object type) {
        return is(type, boolean.class) || is(type, Boolean.class);
    }

    public static Boolean isInt(Object type) {
        return is(type, int.class) || is(type, Integer.class);
    }

    public static Boolean isFloat(Object type) {
        return is(type, float.class) || is(type, Float.class);
    }

    public static Boolean isLong(Object type) {
        return is(type, long.class) || is(type, Long.class);
    }

    public static Boolean isDouble(Object type) {
        return is(type, double.class) || is(type, Double.class);
    }

    public static Boolean isShort(Object type) {
        return is(type, short.class) || is(type, Short.class);
    }

    public static Boolean isByte(Object type) {
        return is(type, byte.class) || is(type, Byte.class);
    }

    public static String toUpperCaseFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String toLowerCaseFirst(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static <T> T last(T[] arr) {
        return arr[arr.length - 1];
    }

    public static String last(String s, String split) {
        //return s.substring(s.lastIndexOf(split) + 1);
        return last(s.split(split));
    }

    public static <T> T[] push(T[] arr, T item) {
        T[] tmp = Arrays.copyOf(arr, arr.length + 1);
        tmp[tmp.length - 1] = item;
        return tmp;
    }

    public static <T> T[] shift(T[] arr) {
        return remove(arr, 0);
    }

    public static <T> T[] shiftSkip(T[] arr) {
        return skip(arr, 1);
    }

    public static <T> T[] shiftList(T[] arr) {
        return pop(rotate(arr, -1));
    }

    public static <T> T[] pop(T[] arr) {
        return remove(arr, arr.length - 1);
    }

    public static <T> T[] popCopy(T[] arr) {
        return Arrays.copyOf(arr, arr.length - 1);
    }

    public static <T> T[] popSkip(T[] arr) {
        return skip(rotate(arr, 1), 1);
    }

    public static <T> T[] skip(T[] arr, int n) {
        return (T[]) stream(arr).skip(n).toArray();
    }

    public static <T> T[] rotate(T[] arr, int distance) {
        //List<T> list = asList(arr); // меняет исходный массив (работает как ссылка)
        List<T> list = arrayList(arr);
        Collections.rotate(list, distance);
        return (T[]) list.toArray();
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] remove(T[] arr, int index) {
        return (T[]) ArrayUtils.remove(arr, index);
    }

    public static <T, E> ArrayList<T> addEntries(ArrayList<T> entries, E @NotNull ... elements) {
        entries.add((T) addAll(new ArrayList<>(), elements));
        return entries;
    }

    public static <K, V> Map<K, V> putEntries(Map<K, V> entries, K key, V value) {
        entries.put(key, value);
        return entries;
    }

    public static Map<String, Object> entries(Object obj) {
        return entries(obj, new HashMap<>(), (field, entries) -> {
            try {return (HashMap<String, Object>) putEntries(entries, fieldName(field), field.get(obj));}
            catch (IllegalAccessException e) {throw new RuntimeException(e);}
        });
    }

    public static List<Object[]> entriesList(Object obj) {
        return entries(obj, new ArrayList<>(), (field, entries) -> {
            try {return addEntries(entries, fieldName(field), field.get(obj));}
            catch (IllegalAccessException e) {throw new RuntimeException(e);}
        });
    }

    public static Object[] entriesArray(Object obj) {
        return entriesList(obj).toArray();
    }

    public static <T> T entries(Object obj, T entries, BiFunction<Field, T, T> func) {
        return fields(obj, func, entries);
    }

    public static String fieldName(Field field) {
        return field.toString().replaceAll("(^.*)(\\.)([^\\.]+)$", "$3");
    }

    public static Set<String> keys(Object obj) throws IllegalAccessException {
        return keys(entries(obj));
    }

    public static Collection<Object> values(Object obj) throws IllegalAccessException {
        return values(entries(obj));
    }

    public static Object[] keysArray(Object obj) {
        return keysArray(entries(obj));
    }

    public static Object[] valuesArray(Object obj) {
        return valuesArray(entries(obj));
    }

    public static List<String> keysList(Object obj) {
        return keysList(entries(obj));
    }

    public static List<Object> valuesList(Object obj) {
        return valuesList(entries(obj));
    }

    public static Set<String> keys(Map<String, Object> entriesMap) throws IllegalAccessException {
        return entriesMap.keySet();
    }

    public static Collection<Object> values(Map<String, Object> entriesMap) throws IllegalAccessException {
        return entriesMap.values();
    }

    public static Object[] keysArray(Map<String, Object> entriesMap) {
        return entriesMap.keySet().toArray();
    }

    public static Object[] valuesArray(Map<String, Object> entriesMap) {
        return entriesMap.values().toArray();
    }

    public static List<String> keysList(Map<String, Object> entriesMap) {
        return entriesMap.keySet().stream().toList();
    }

    public static List<Object> valuesList(Map<String, Object> entriesMap) {
        return entriesMap.values().stream().toList();
    }

    public static String sb(Object... args) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) sb.append(arg);
        return sb.toString();
    }

    public static String sf(String str, Object... args) {
        return String.format(str, args);
    }
}
