package utils;

import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Helper {

    private static Boolean is(Object type, Class<?> clazz) {
        return type == clazz;
    }

    public static Charset getDefaultCharset() {
        return Charset.defaultCharset();
    }

    public static void DefaultCharsetPrinter() {
        out.println(getDefaultCharset());
    }

    public static boolean isNull(Object value) {
        return value == null;
    }

    public static boolean notNull(Object value) {
        return value != null;
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
