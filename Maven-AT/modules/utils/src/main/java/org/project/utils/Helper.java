package org.project.utils;

import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static java.lang.String.join;
import static java.lang.System.out;
import static java.text.MessageFormat.format;
import static org.project.utils.config.Config.debugLvl;

public class Helper {

    public static void debug(Object msg) {
        if (debugLvl() > 0) out.println(msg);
    }

    public static void debug(String msg, int debugLvl) {
        if (debugLvl() >= debugLvl) debug(msg);
    }

    public static void debug(String msg, String... args) {
        if (debugLvl() > 0) debug(format("{0} {1}", msg, join(", ", toArray(args, String[]::new))));
    }

    public static void debug(String msg, int debugLvl, String... args) {
        if (debugLvl() >= debugLvl) debug(msg, args);
    }

    public static <A> A[] toArray(A[] array, IntFunction<A[]> generator) {
        return toArray(Arrays.stream(array), generator);
    }

    public static <A> A[] toArray(Stream<A> array, IntFunction<A[]> generator) {
        return array.toArray(generator);
    }

    public static <A, T> T[] toArray(A[] array, IntFunction<T[]> generator, Function<A, A> mapper) {
        return Arrays.stream(array)
            .map(mapper)
            .toArray(generator);
    }

    private static Boolean is(Object type, Class<?> clazz) {
        return type == clazz;
    }

    public static Charset defaultCharset() {
        return Charset.defaultCharset();
    }

    public static void PrintDefaultCharset() {
        out.println(defaultCharset());
    }

    public static boolean isNull(Object value) {
        return value == null;
    }

    public static boolean notNull(Object value) {
        return value != null;
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

    public static Boolean isList(Object obj) {
        return isInstance(obj, List.class);
    }

    public static Boolean isArrayList(Object obj) {
        return isInstance(obj, ArrayList.class);
    }

    public static Boolean isArray(Object obj) {
        return isInstance(obj, Array.class);
    }

    public static Boolean isParseType(Object type) {
        return isBool(type) || isInt(type) || isFloat(type)  || isLong(type) || isDouble(type) || isShort(type) || isByte(type);
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

    public static <T> T[] removeLast(T[] arr) {
        return remove(arr, arr.length - 1);
    }

    public static <T> T[] removeFirst(T[] arr) {
        return remove(arr, 0);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] remove(T[] arr, int index) {
        return (T[]) ArrayUtils.remove(arr, index);
    }
}
