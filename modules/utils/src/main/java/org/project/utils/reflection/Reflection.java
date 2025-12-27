package org.project.utils.reflection;

import static java.lang.invoke.MethodType.fromMethodDescriptorString;
import static java.lang.reflect.Array.newInstance;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.file.Path;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.apache.commons.beanutils.PropertyUtils.getProperty;
import static org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors;
import static org.apache.commons.beanutils.PropertyUtils.setProperty;

import org.apache.commons.beanutils.BeanUtils;

import static org.project.utils.Helper.arrayList;
import static org.project.utils.Helper.concatTo;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.isClass;
import static org.project.utils.Helper.isInt;
import static org.project.utils.Helper.isList;
import static org.project.utils.Helper.isNull;
import static org.project.utils.Helper.isParseType;
import static org.project.utils.Helper.map;
import static org.project.utils.Helper.notNull;
import static org.project.utils.exception.UtilException.tryCatchNoArgs;
import static org.project.utils.exception.UtilException.tryNoArgsWithPrintMsg;
import static org.project.utils.reflection.ReflectionUtils.getGenericParameterClass;

import org.project.utils.exception.AssertException;
import org.project.utils.function.BiFunctionWithExceptions;
import org.project.utils.function.FunctionWithExceptions;
import org.project.utils.function.TriFunctionWithExceptions;

/**
 *
 */
public class Reflection {

    /**
     * Get object property
     * @param obj Object
     * @param prop String
     * @param print boolean
     * @return Object
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    private static Object _getProp(Object obj, String prop, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object _prop = getProperty(obj, prop);
        if (print) debug(_prop);
        return _prop;
    }

    /**
     * Get object property value of String
     * @param obj Object
     * @param prop String
     * @param print boolean
     * @return Object
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    private static Object _getPropStr(Object obj, String prop, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object _prop = BeanUtils.getProperty(obj, prop);
        if (print) debug(_prop);
        return _prop;
    }

    /**
     * Set object property
     * @param obj Object
     * @param prop String
     * @param value Object
     * @param print boolean
     * @return Object
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    private static Object _setProp(Object obj, String prop, Object value, boolean print)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        setProperty(obj, prop, value);
        return _getProp(obj, prop, print);
    }

    /**
     * Set object property value of String
     * @param obj Object
     * @param prop String
     * @param value Object
     * @param print boolean
     * @return Object
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    private static Object _setPropStr(Object obj, String prop, Object value, boolean print)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        BeanUtils.setProperty(obj, prop, value);
        return _getPropStr(obj, prop, print);
    }

    /**
     * Get property descriptor by name
     * @param descList List {PropertyDescriptor}
     * @param name String
     * @return PropertyDescriptor
     */
    private static PropertyDescriptor _getPropDescriptor(List<PropertyDescriptor> descList, String name) {
        PropertyDescriptor[] _descList = descList.stream().filter(s -> s.getName().equals(name)).toArray(PropertyDescriptor[]::new);
        PropertyDescriptor desc = _descList.length > 0 ? _descList[0] : null;
        debug(desc);
        return desc;
    }

    /**
     *
     * @param getPrimitive Boolean
     * @param args Object[]
     * @return Class[]
     */
    private static Class<?>[] _getTypes(Boolean getPrimitive, Object... args) {
        debug(Arrays.toString(args));
        Class<?>[] argTypes = map(args, Class<?>[]::new, arg -> {
            Class<?> clazz = getClazz(arg);
            if (clazz.getName().equals("sun.nio.fs.WindowsPath")) clazz = Path.class;
            return getPrimitive ? getPrimitiveType(arg) : !(arg instanceof Proxy) ? clazz : getInterface(clazz);
        });
        debug(Arrays.toString(argTypes));
        return argTypes;
    }

    /**
     *
     * @param o Object
     * @return Class[]
     */
    public static Class<?>[] getInterfaces(Object o) {
        return getClazz(o).getInterfaces();
    }

    /**
     *
     * @param o Object
     * @return Class[]
     */
    public static Class<?> getInterface(Object o) {
        return getInterface(o, 0);
    }

    /**
     *
     * @param o Object
     * @param i int
     * @return Class[]
     */
    public static Class<?> getInterface(Object o, int i) {
        return getInterfaces(o)[i];
    }

    /**
     *
     * @param type Class
     * @return Class
     */
    public static Class<?> getParseType(Class<?> type) {
        String name = getClassSimpleName(type);
        return switch (name) {
            case ("boolean") -> Boolean.class;
            case ("int") -> Integer.class;
            case ("float") -> Float.class;
            case ("long") -> Long.class;
            case ("double") -> Double.class;
            case ("short") -> Short.class;
            case ("byte") -> Byte.class;
            default -> type;
        };
    }

    /**
     *
     * @param obj Object[]
     * @return Class
     */
    public static Class<?> getPrimitiveType(Object obj) {
        Class<?> clazz = isList(obj) ? List.class : getClazz(obj);
        String name = getClassSimpleName(clazz);
        return switch (name) {
            case ("Boolean") -> boolean.class;
            case ("Integer") -> int.class;
            case ("Float") -> float.class;
            case ("Long") -> long.class;
            case ("Double") -> double.class;
            case ("Short") -> short.class;
            case ("Byte") -> byte.class;
            default -> clazz;
        };
    }

    /**
     * Get ClassName of parse
     * @param className String
     * @return String
     */
    public static String parseClassName(String className) {
        return parseClassName(className, "::", "$1,$2");
    }

    /**
     * Get ClassName of parse
     * @param className String
     * @param sep String
     * @return String
     */
    public static String parseClassName(String className, String sep) {
        return parseClassName(className, sep, "$1,$2");
    }

    /**
     * Get ClassName of parse
     * @param className String
     * @param sep String
     * @param replace String
     * @return String
     */
    public static String parseClassName(String className, String sep, String replace) {
        String parsedClassName = className.replaceAll("(.+)" + sep + "(.+)", replace);
        debug("parseClassName: " + className.replaceAll("(.+)" + sep + "(.+)", "$1,$2"));
        return parsedClassName;
    }

    /**
     *
     * @param obj Object
     * @return Class
     */
    public static Class<?> getClazz(Object obj) {
        Class<?> clazz = isClass(obj) ? (Class<?>) obj : obj.getClass();
        debug(clazz);
        return clazz;
    }

    /**
     *
     * @param method Method
     * @return Class
     */
    public static Class<?> getClazz(Method method) {
        return method.getDeclaringClass();
    }

    /**
     * get class from lambda expression
     * @param lambda Serializable
     * @return String
     * @throws ClassNotFoundException throws
     */
    public static Class<?> getLambdaClazz(Serializable lambda) throws ClassNotFoundException {
        return getClazz(getLambdaClassName(lambda));
    }

    /**
     *
     * @param lambda Serializable
     * @return String
     */
    public static String getLambdaClassName(Serializable lambda) {
        return lambda(lambda, SerializedLambda::getImplClass).replace("/", ".");
    }

    /**
     *
     * @param name String
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClazz(String name) throws ClassNotFoundException {
        return (Class<T>) Class.forName(name);
    }

    /**
     *
     * @param name String
     * @param subClass Class S
     * @return Class T
     * @param <T> T
     * @param <S> S
     * @throws ClassNotFoundException throws
     */
    @SuppressWarnings("unchecked")
    public static <T extends S, S> Class<T> asSubClass(String name, Class<S> subClass) throws ClassNotFoundException {
        return (Class<T>) getClazz(name).asSubclass(subClass);
    }

    /**
     * Get Class by className with parse
     * @param className String
     * @param sep String
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> Class<T> getClazz(String className, String sep) throws ClassNotFoundException {
        return getClazz(parseClassName(className, sep, "$1"));
    }

    /**
     * Get Class by className with parse
     * @param className String
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> Class<T> getClassParse(String className) throws ClassNotFoundException {
        return getClazz(className, "::");
    }

    /**
     *
     * @param index int
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> Class<T> getClazz(int index) throws ClassNotFoundException {
        return getClazz(getClassName(index + 1));
    }

    /**
     *
     * @param stackTraceEl StackTraceElement
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> Class<T> getClazz(StackTraceElement stackTraceEl) throws ClassNotFoundException {
        return getClazz(getClassName(stackTraceEl));
    }

    /**
     *
     * @param obj Object
     * @return String
     */
    public static String getClassName(Object obj) {
        String name = getClazz(obj).getName();
        debug(name);
        return name;
    }

    /**
     *
     * @param className String
     * @return String
     * @throws ClassNotFoundException throws
     */
    public static String getClassName(String className) throws ClassNotFoundException {
        return getClassName(getClazz(className));
    }

    /**
     *
     * @param index int
     * @return String
     */
    public static String getClassName(int index) {
        return getClassName(getStackTraceEl(index + 1));
    }

    /**
     *
     * @param stackTraceEl StackTraceElement
     * @return String
     */
    public static String getClassName(StackTraceElement stackTraceEl) {
        return stackTraceEl.getClassName();
    }

    /**
     *
     * @param obj Object
     * @return String
     */
    public static String getClassSimpleName(Object obj) {
        String name = getClazz(obj).getSimpleName();
        debug(name);
        return name;
    }

    /**
     *
     * @param className String
     * @return String String
     * @throws ClassNotFoundException throws
     */
    public static String getClassSimpleName(String className) throws ClassNotFoundException {
        return getClassSimpleName(getClazz(className));
    }

    /**
     *
     * @param index int
     * @return String
     * @throws ClassNotFoundException throws
     */
    public static String getClassSimpleName(int index) throws ClassNotFoundException {
        return getClassSimpleName(getStackTraceEl(index + 1));
    }

    /**
     *
     * @param stackTraceEl StackTraceElement
     * @return String
     * @throws ClassNotFoundException throws
     */
    public static String getClassSimpleName(StackTraceElement stackTraceEl) throws ClassNotFoundException {
        return getClassSimpleName(getClazz(stackTraceEl));
    }

    /**
     *
     * @return StackTraceElement[]
     */
    public static StackTraceElement[] getStackTrace() {
        return new RuntimeException().getStackTrace();
    }

    /**
     *
     * @param index int
     * @return StackTraceElement
     */
    public static StackTraceElement getStackTraceEl(int index) {
        return getStackTrace()[index + 2];
    }

    /**
     *
     * @return StackTraceElement
     */
    public static StackTraceElement getStackTraceEl() {
        return getStackTraceEl(1);
    }

    /**
     *
     * @param index int
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    //@SuppressWarnings("unchecked")
    public static <T> Class<T> getCallingClass(int index) throws ClassNotFoundException {
        //debug("getClassName: " + getClassName(index + 1));
        //debug("getClazz: " + getClazz(index + 1));
        //return (Class<T>) Class.forName(getStackTraceEl(++index).getClassName());
        return getClazz(++index);
    }

    /**
     *
     * @param cb Function {Class, Boolean}
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getCallingClass(Function<Class<?>, Boolean> cb) throws ClassNotFoundException {
        int i = 0;
        Class<?> c = getCallingClass(i);
        while (cb.apply(c)) c = getCallingClass(++i);
        return (Class<T>) c;
    }

    /**
     *
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> Class<T> getCallingClass() throws ClassNotFoundException {
        return getCallingClass(1);
    }

    /**
     *
     * @param index int
     * @param rec Function {Class {T}, Boolean}
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> Class<T> getCallingClass(int index, Function<Class<T>, Boolean> rec) throws ClassNotFoundException {
        return getCallingClass(index, rec, c -> c);
    }

    /**
     *
     * @param index int
     * @param rec Function {Class {T}, Boolean}
     * @param cb Function {Class {T}, Class {T}}
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> Class<T> getCallingClass(int index, Function<Class<T>, Boolean> rec, Function<Class<T>, Class<T>> cb) throws ClassNotFoundException {
        return getCallingClassRec(index + 2, rec, cb);
    }

    /**
     *
     * @param index int
     * @param rec Function {Class {T}, Boolean}
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> String getCallingClassName(int index, Function<Class<T>, Boolean> rec) throws ClassNotFoundException {
        return getCallingClassRec(index + 2, rec, Class::getName);
    }

    /**
     *
     * @param index int
     * @param rec Function {Class {T}, Boolean}
     * @param cb Function {String, String}
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> String getCallingClassName(int index, Function<Class<T>, Boolean> rec, Function<String, String> cb) throws ClassNotFoundException {
        return getCallingClassRec(index + 2, rec, c -> cb.apply(c.getName()));
    }

    /**
     *
     * @param index int
     * @param rec Function {Class {T}, Boolean}
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> String getCallingClassSimpleName(int index, Function<Class<T>, Boolean> rec) throws ClassNotFoundException
    {
        return getCallingClassRec(index + 2, rec, Class::getSimpleName);
    }

    /**
     *
     * @param index int
     * @param rec Function {Class {T}, Boolean}
     * @param cb Function {String, String}
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> String getCallingClassSimpleName(int index, Function<Class<T>, Boolean> rec, Function<String, String> cb) throws ClassNotFoundException
    {
        return getCallingClassRec(index + 2, rec, c -> cb.apply(c.getSimpleName()));
    }

    /**
     *
     * @param index int
     * @param rec Function {Class {T}, Boolean}
     * @param cb Function {Class {T}, R}
     * @return Class T
     * @param <T> T
     * @param <R> R
     * @throws ClassNotFoundException throws
     */
    public static <T, R> R getCallingClassRec(int index, Function<Class<T>, Boolean> rec, Function<Class<T>, R> cb) throws ClassNotFoundException {
        Class<T> clazz = getCallingClass(index);
        boolean isExtends = rec.apply(clazz);

        while (isExtends) {
            Class<T> next = getCallingClass(++index);
            isExtends = rec.apply(next);
            if (isExtends) clazz = next;
        }

        debug("recClass: " + clazz);

        return cb.apply(clazz);
    }

    /**
     *
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> Class<T> getCallingChildClass() throws ClassNotFoundException {
        return getCallingClass(2);
    }

    /**
     *
     * @param index int
     * @return String
     */
    public static String getCallingClassName(int index) {
        return getStackTraceEl(++index).getClassName();
    }

    /**
     *
     * @return String
     */
    public static String getCallingClassName() {
        return getCallingClassName(1);
    }

    /**
     *
     * @return String
     */
    public static String getCallingChildClassName() {
        return getCallingClassName(2);
    }

    /**
     *
     * @param index int
     * @return String
     * @throws ClassNotFoundException throws
     */
    public static String getCallingClassSimpleName(int index) throws ClassNotFoundException {
        return getClassSimpleName(++index);
    }

    /**
     *
     * @return String
     * @throws ClassNotFoundException throws
     */
    public static String getCallingClassSimpleName() throws ClassNotFoundException {
        return getClassSimpleName(1);
    }

    /**
     *
     * @return String
     * @throws ClassNotFoundException throws
     */
    public static String getCallingChildClassSimpleName() throws ClassNotFoundException {
        return getCallingClassSimpleName(2);
    }

    /**
     *
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> Class<T> getGenericClass() throws ClassNotFoundException {
        return getGenericClass(0);
    }

    /**
     *
     * @param index int
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> Class<T> getGenericClass(int index) throws ClassNotFoundException {
        return getGenericClass(index, 0);
    }

    /**
     *
     * @param index int
     * @param traceDepth int
     * @return Class T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> Class<T> getGenericClass(int index, int traceDepth) throws ClassNotFoundException {
        Class<?> genericClass = getCallingClass(traceDepth);
        Class<?> actualClass = getCallingClass(++traceDepth);
        try {
            if (isExtends(genericClass, Reflection.class)) return getGenericClass(index, ++traceDepth);
            else {
                while (actualClass == genericClass) actualClass = getCallingClass(++traceDepth);
                while (actualClass != genericClass) {
                    Class<?> clazz = getCallingClass(++traceDepth);
                    if (!isExtends(clazz, actualClass)) break;
                    actualClass = clazz;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        new AssertException(actualClass).notNull();
        return getGenericParameterClass(actualClass, genericClass, index);
    }

    /**
     *
     * @param actual Class R
     * @return Class T
     * @param <T> T
     * @param <R> R
     */
    public static <T, R> Class<R> getGenericClass(Class<T> actual) {
        return getGenericClass(actual, 0);
    }

    /**
     *
     * @param actual Class T
     * @param index int
     * @return Class R
     * @param <T> T
     * @param <R> R
     */
    public static <T, R> Class<R> getGenericClass(Class<T> actual, int index) {
        return getGenericParameterClass(actual, actual.getSuperclass(), index);
    }

    /**
     *
     * @return String
     * @throws ClassNotFoundException throws
     */
    public static String getGenericClassName() throws ClassNotFoundException {
        return getClassName(getGenericClass());
    }

    /**
     *
     * @param index int
     * @return String
     * @throws ClassNotFoundException throws
     */
    public static String getGenericClassName(int index) throws ClassNotFoundException {
        return getClassName(getGenericClass(index));
    }

    /**
     *
     * @param genericClass Class T
     * @param index int
     * @return String
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> String getGenericClassName(Class<T> genericClass, int index) throws ClassNotFoundException {
        return getClassName(getGenericClass(genericClass, index));
    }

    /**
     *
     * @param subClass SubClass
     * @param superClass SuperClass
     * @return boolean
     */
    public static boolean isExtends(Class<?> subClass, Class<?> superClass) {
        return notNull(subClass) && notNull(superClass) && superClass.isAssignableFrom(subClass);
    }

    /**
     * Get object property
     * @param obj Object
     * @param prop String
     * @param print boolean
     * @return Object
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public static Object getProp(Object obj, String prop, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getProp(obj, prop, print);
    }

    /**
     * Get object property
     * @param obj Object
     * @param prop String
     * @return Object
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public static Object getProp(Object obj, String prop) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getProp(obj, prop, false);
    }

    /**
     * Get object property value of String
     * @param obj Object
     * @param prop String
     * @param print boolean
     * @return Object
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public static Object getPropStr(Object obj, String prop, boolean print)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        return _getPropStr(obj, prop, print);
    }

    /**
     * Get object property value of String
     * @param obj Object
     * @param prop String
     * @return Object
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public static Object getPropStr(Object obj, String prop) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getPropStr(obj, prop, false);
    }

    /**
     * Set object property
     * @param obj Object
     * @param prop String
     * @param value Object
     * @param print boolean
     * @return Object
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public static Object setProp(Object obj, String prop, Object value, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _setProp(obj, prop, value, print);
    }

    /**
     * Set object property
     * @param obj Object
     * @param prop String
     * @param value Object
     * @return Object
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public static Object setProp(Object obj, String prop, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _setProp(obj, prop, value, false);
    }

    /**
     * Set object property value of String
     * @param obj Object
     * @param prop String
     * @param value Object
     * @param print boolean
     * @return Object
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public static Object setPropStr(Object obj, String prop, Object value, boolean print)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        return _setPropStr(obj, prop, value, print);
    }

    /**
     * Set object property value of String
     * @param obj Object
     * @param prop String
     * @param value Object
     * @return Object
     * @throws InvocationTargetException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchMethodException throws
     */
    public static Object setPropStr(Object obj, String prop, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _setPropStr(obj, prop, value, false);
    }

    /**
     * "Get property descriptors by name
     * @param obj Object
     * @return List {PropertyDescriptor}
     */
    public static List<PropertyDescriptor> getPropDescriptors(Object obj) {
        PropertyDescriptor[] descList = getPropertyDescriptors(getClazz(obj));
        debug(Arrays.toString(descList));
        return List.of(descList);
    }

    /**
     * "Get property descriptor by name
     * @param descList List {PropertyDescriptor}
     * @param name String
     * @return PropertyDescriptor
     */
    public static PropertyDescriptor getPropDescriptor(List<PropertyDescriptor> descList, String name) {
        return _getPropDescriptor(descList, name);
    }

    /**
     * "Get property descriptor by name
     * @param obj Object
     * @param name String
     * @return PropertyDescriptor
     */
    public static PropertyDescriptor getPropDescriptor(Object obj, String name) {
        return _getPropDescriptor(getPropDescriptors(obj), name);
    }

    /**
     * @param clazz Class T
     * @return TypeVariable {Class {T}}[]
     * @param <T> T
     */
    public static <T> TypeVariable<Class<T>>[] types(Class<T> clazz) {
        return clazz.getTypeParameters();
    }

    /**
     * @param clazz Class T
     * @return TypeVariable {Class {T}}
     * @param <T> T
     */
    public static <T> TypeVariable<Class<T>> type(Class<T> clazz) {
        return type(clazz, 0);
    }

    /**
     * @param clazz Class T
     * @param i int
     * @return TypeVariable {Class {T}}
     * @param <T> T
     */
    public static <T> TypeVariable<Class<T>> type(Class<T> clazz, int i) {
        return type(i, () -> types(clazz));
    }

    /**
     * @param i int
     * @param cb Supplier {T[]}
     * @return T
     * @param <T> extends Type
     */
    public static <T extends Type> T type(int i, Supplier<T[]> cb) {
        T[] types = cb.get();
        return notNull(types) && types.length > 0 && i < types.length ? types[i] : null;
    }

    /**
     * @param clazz Class T
     * @return String
     * @param <T> T
     */
    public static <T> String typeName(Class<T> clazz) {
        return typeName(clazz, 0);
    }

    /**
     * @param clazz Class T
     * @param i int
     * @return String
     * @param <T> T
     */
    public static <T> String typeName(Class<T> clazz, int i) {
        TypeVariable<Class<T>> type = type(clazz, i);
        return notNull(type) ? type.getTypeName() : null;
    }

    /**
     * @param clazz Class T
     * @return Type[]
     * @param <T> T
     */
    public static <T> Type[] typeBounds(Class<T> clazz) {
        return typeBounds(clazz, 0);
    }

    /**
     * @param clazz Class T
     * @param i int
     * @return Type[]
     * @param <T> T
     */
    public static <T> Type[] typeBounds(Class<T> clazz, int i) {
        TypeVariable<Class<T>> type = type(clazz, i);
        return notNull(type) ? type.getBounds() : null;
    }

    /**
     * @param clazz Class T
     * @return Type[]
     * @param <T> T
     */
    public static <T> Type typeBound(Class<T> clazz) {
        return typeBound(clazz, 0);
    }

    /**
     * @param clazz Class T
     * @param i int
     * @return Type[]
     * @param <T> T
     */
    public static <T> Type typeBound(Class<T> clazz, int i) {
        return typeBound(clazz, i, 0);
    }

    /**
     * @param clazz Class T
     * @param i int
     * @param b int
     * @return Type[]
     * @param <T> T
     */
    public static <T> Type typeBound(Class<T> clazz, int i, int b) {
        return type(b, () -> typeBounds(clazz, i));
    }

    /**
     * @param clazz Class T
     * @return Annotation[]
     * @param <T> T
     */
    public static <T> Annotation[] typeAnnotations(Class<T> clazz) {
        return typeAnnotations(clazz, 0);
    }

    /**
     * @param clazz Class T
     * @param i int
     * @return Annotation[]
     * @param <T> T
     */
    public static <T> Annotation[] typeAnnotations(Class<T> clazz, int i) {
        return type(clazz, i).getAnnotations();
    }

    /**
     * @param type Type
     * @return Type
     */
    public static Type rawType(Type type) {
        return type instanceof ParameterizedType ? ((ParameterizedType) type).getRawType() : type;
    }

    /**
     * @param type Type
     * @return Type
     */
    public static Type typeArg(ParameterizedType type) {
        return typeArg(type, 0);
    }

    /**
     * @param type Type
     * @param i int
     * @return Type
     */
    public static Type typeArg(ParameterizedType type, int i) {
        return type(i, type::getActualTypeArguments);
    }

    /**
     *
     * @param clazz Class T
     * @param args Object[]
     * @return Constructor T
     * @param <T> T
     * @throws NoSuchMethodException throws
     */
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Object... args) throws NoSuchMethodException {
        debug("getDeclaredConstructor");
        return getMethod(clazz, c -> clazz.getConstructor(getTypes(args)));
    }

    /**
     *
     * @param clazz Class T
     * @param args Object[]
     * @return Constructor T
     * @param <T> T
     * @throws NoSuchMethodException throws
     */
    public static <T> Constructor<T> getPrimitiveConstructor(Class<T> clazz, Object... args) throws NoSuchMethodException {
        debug("getPrimitiveConstructor");
        return getMethod(clazz, c -> clazz.getConstructor(getPrimitiveTypes(args)));
    }

    /**
     *
     * @param clazz Class T
     * @param types Class[]
     * @return Constructor T
     * @param <T> T
     * @throws NoSuchMethodException throws
     */
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... types) throws NoSuchMethodException {
        debug("getConstructor");
        return getMethod(clazz, c -> clazz.getConstructor(types));
    }

    /**
     *
     * @param clazz Class T
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws NoSuchMethodException throws
     * @throws InvocationTargetException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    public static <T> T instance(Class<T> clazz, Object... args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        debug("instance: args");
        debug("constructors: " + Arrays.toString(clazz.getConstructors()));
        return instance(tryCatchNoArgs(() -> getConstructor(clazz, args), e -> getPrimitiveConstructor(clazz, args)), args);
    }

    /**
     *
     * @param clazz Class T
     * @param args Map {Class, Object}
     * @return T
     * @param <T> T
     * @throws NoSuchMethodException throws
     * @throws InvocationTargetException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    public static <T> T instance(Class<T> clazz, Map<Class<?>, Object> args)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
    {
        debug("instance: map");
        debug("constructors: " + Arrays.toString(clazz.getConstructors()));
        return instance(getConstructor(clazz, args.keySet().toArray(Class[]::new)), args.values().toArray());
    }

    /**
     *
     * @param constructor Constructor T
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws NoSuchMethodException throws
     * @throws InvocationTargetException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    public static <T> T instance(Constructor<T> constructor, Object... args)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
    {
        debug("newInstance");
        T instance = constructor.newInstance(args);
        debug(instance);
        return instance;
    }

    /**
     *
     * @param clazz Class
     * @param size int
     * @return T[]
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] arrInstance(Class<?> clazz, int size) {
        T[] instance = (T[]) newInstance(clazz, size);
        debug(instance);
        return instance;
    }

    /**
     *
     * @param a T[]
     * @param size int
     * @return T[]
     * @param <T> T
     */
    public static <T> T[] arrInstance(T[] a, int size) {
        return arrInstance(getType(a), size);
    }

    /**
     * Get descriptor of class
     * @param c {@code final} Class
     * @return String
     */
    public static String getDescriptorForClass(final Class<?> c) {
        if (c.isPrimitive()) {
            if (c == byte.class)
                return "B";
            if (c == char.class)
                return "C";
            if (c == double.class)
                return "D";
            if (c == float.class)
                return "F";
            if (c == int.class)
                return "I";
            if (c == long.class)
                return "J";
            if (c == short.class)
                return "S";
            if (c == boolean.class)
                return "Z";
            if (c == void.class)
                return "V";
            throw new RuntimeException("Unrecognized primitive " + c);
        }
        if (c.isArray()) return c.getName().replace('.', '/');
        return ('L' + c.getName() + ';').replace('.', '/');
    }

    /**
     *
     * @param m Method
     * @return String
     */
    static String getMethodDescriptor(Method m) {
        StringBuilder s = new StringBuilder("(");
        for (final Class<?> c: m.getParameterTypes())
            s.append(getDescriptorForClass(c));
        s.append(')');
        return s + getDescriptorForClass(m.getReturnType());
    }

    /**
     * Get classLoader of object
     * @param obj Object
     * @return ClassLoader
     * @throws NullPointerException throws
     */
    public static ClassLoader getClassLoader(Object obj) throws NullPointerException {
        return getClazz(obj).getClassLoader();
    }

    /**
     * Get descriptor of lambda object
     * @param obj Object
     * @return String
     * @throws NullPointerException throws
     */
    public static String getDescriptor(Object obj) throws NullPointerException {
        return getClazz(obj).descriptorString();
    }

    /**
     * Get descriptor of lambda expression
     * @param sl SerializedLambda
     * @return String
     * @throws NullPointerException throws
     */
    public static String getDescriptor(SerializedLambda sl) throws NullPointerException {
        return sl.getImplMethodSignature();
    }

    /**
     *
     * @param o Object
     * @return Class
     */
    public static Class<?> getType(Object o) {
        return getClazz(o).getComponentType();
    }

    /**
     *
     * @param args Object[]
     * @return Class[]
     */
    public static Class<?>[] getTypes(Object... args) {
        return _getTypes(false, args);
    }

    /**
     *
     * @param args Object[]
     * @return Class[]
     */
    public static Class<?>[] getPrimitiveTypes(Object... args) {
        return _getTypes(true, args);
    }

    /**
     * Get types of object
     * @param obj Object
     * @param descriptor String
     * @return Class[]
     * @throws NullPointerException throws
     */
    public static Class<?>[] getTypes(Object obj, String descriptor) throws NullPointerException {
        return fromMethodDescriptorString(descriptor, getClassLoader(obj)).parameterArray();
    }

    /**
     * Get method name of lambda expression
     * @param sl SerializedLambda
     * @return String
     * @throws NullPointerException throws
     */
    public static String getMethodName(SerializedLambda sl) throws NullPointerException {
        return sl.getImplMethodName();
    }

    /**
     * Get method of object
     * @param obj Object
     * @param method String
     * @param args Object[]
     * @return Method
     * @throws NoSuchMethodException throws
     */
    public static Method getDeclaredMethod(Object obj, String method, Object... args) throws NoSuchMethodException {
        debug("getDeclaredMethod");
        //debug("declaredMethods: " + Arrays.toString(getClazz(obj).getDeclaredMethods()));
        return getMethod(obj, clazz -> clazz.getDeclaredMethod(method, getTypes(args)));
    }

    /**
     * Get method of object
     * @param obj Object
     * @param method String
     * @param args Object[]
     * @return Method
     * @throws NoSuchMethodException throws
     */
    public static Method getDeclaredPrimitiveMethod(Object obj, String method, Object... args) throws NoSuchMethodException {
        debug("getDeclaredPrimitiveMethod");
        return getMethod(obj, clazz -> clazz.getDeclaredMethod(method, getPrimitiveTypes(args)));
    }

    /**
     * Get method of object
     * @param obj Object
     * @param method String
     * @param args Object[]
     * @return Method
     * @throws NoSuchMethodException throws
     */
    public static Method getSuperMethod(Object obj, String method, Object... args) throws NoSuchMethodException {
        debug("getSuperMethod");
        //debug("methods: " + Arrays.toString(getClazz(obj).getMethods()));
        return getMethod(obj, clazz -> clazz.getMethod(method, getTypes(args)));
    }

    /**
     * Get method of object
     * @param obj Object
     * @param method String
     * @param args Object[]
     * @return Method
     * @throws NoSuchMethodException throws
     */
    public static Method getPrimitiveMethod(Object obj, String method, Object... args) throws NoSuchMethodException {
        debug("getPrimitiveMethod");
        return getMethod(obj, clazz -> clazz.getMethod(method, getPrimitiveTypes(args)));
    }

    /**
     * Get method of object
     * @param obj Object
     * @param cb FunctionWithExceptions {Class, M, E}
     * @return Method
     * @param <M> extends Executable
     * @param <E> extends Exception
     * @throws E throws
     */
    public static <M extends Executable, E extends Exception> M getMethod(Object obj, FunctionWithExceptions<Class<?>, M, E> cb) throws E {
        M _method = cb.apply(getClazz(obj));
        new AssertException(_method).notNull();
        debug(Arrays.toString(_method.getParameterTypes()));
        return _method;
    }

    /**
     * Get method of object
     * @param obj Object
     * @param method String
     * @param args Object[]
     * @return Method
     * @throws ReflectiveOperationException throws
     * @throws NullPointerException throws
     */
    public static Method getMethod(Object obj, String method, Object... args) throws ReflectiveOperationException, NullPointerException {
        /*Method _method = tryCatchNoArgs(() -> tryCatchNoArgs(() -> getDeclaredMethod(obj, method, args), e -> getDeclaredPrimitiveMethod(obj, method, args)),
            e -> tryCatchNoArgs(() -> getSuperMethod(obj, method, args), _e -> getPrimitiveMethod(obj, method, args)));*/
        Method _method = getInvoke(obj, c -> tryCatchNoArgs(() -> getDeclaredMethod(c, method, args), e -> getDeclaredPrimitiveMethod(c, method, args)));
        debug(_method);
        new AssertException(_method).notNull();
        debug(Arrays.toString(_method.getParameterTypes()));
        return _method;
    }

    /**
     * Get method of class
     * @param clazz Class
     * @param method String
     * @param args Object[]
     * @return Method
     * @throws NoSuchMethodException throws
     * @throws NullPointerException throws
     */
    public static Method getMethod(Class<?> clazz, String method, Class<?>... args) throws NoSuchMethodException, NullPointerException {
        return clazz.getDeclaredMethod(method, args);
    }

    /**
     * Get method of object
     * @param obj Object
     * @param method String
     * @param args Object[]
     * @return Method
     * @throws NoSuchMethodException throws
     * @throws NullPointerException throws
     */
    public static Method getMethod(Object obj, String method, Class<?>... args) throws NoSuchMethodException, NullPointerException {
        return getMethod(getClazz(obj), method, args);
    }

    /**
     * Get method of object
     * @param obj Object
     * @param method String
     * @param descriptor String
     * @return Method
     * @throws NoSuchMethodException throws
     * @throws NullPointerException throws
     */
    public static Method getMethod(Object obj, String method, String descriptor) throws NoSuchMethodException, NullPointerException {
        return getMethod(obj, method, getTypes(obj, descriptor));
    }

    /**
     * Get method of object
     * @param obj Object
     * @param method String
     * @param sl SerializedLambda
     * @return Method
     * @throws NoSuchMethodException throws
     * @throws NullPointerException throws
     */
    public static Method getMethod(Object obj, String method, SerializedLambda sl) throws NoSuchMethodException, NullPointerException {
        return getMethod(obj, method, getDescriptor(sl));
    }

    /**
     * Get method of object
     * @param obj Object
     * @param sl SerializedLambda
     * @return Method
     * @throws NoSuchMethodException throws
     * @throws NullPointerException throws
     */
    public static Method getMethod(Object obj, SerializedLambda sl) throws NoSuchMethodException, NullPointerException {
        return getMethod(obj, getMethodName(sl), getDescriptor(sl));
    }

    /**
     * get method name from lambda expression
     *
     * @param lambda Serializable
     * @return String
     */
    public static String lambdaMethodName(Serializable lambda) {
        return lambda(lambda, SerializedLambda::getImplMethodName);
    }

    /**
     * get method from lambda expression
     *
     * @param lambda Serializable
     * @return String
     * @throws ReflectiveOperationException throws
     */
    public static Method lambdaMethod(Serializable lambda) throws ReflectiveOperationException {
        return lambda(lambda, sl -> getMethod(getLambdaClazz(lambda), sl));
    }

    /**
     *
     * @param obj Object
     * @param lambda Serializable
     * @return Method
     * @throws ReflectiveOperationException throws
     */
    public static Method lambdaMethod(Object obj, Serializable lambda) throws ReflectiveOperationException {
        return lambda(lambda, sl -> getMethod(obj, sl));
    }

    /**
     *
     * @param lambda Serializable
     * @param cb FunctionWithExceptions
     * @return String
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <R, E extends Exception> R lambda(Serializable lambda, FunctionWithExceptions<SerializedLambda, R, E> cb) throws E {
        try {
            Method m = getMethod(lambda, "writeReplace");
            m.setAccessible(true);
            return cb.apply((SerializedLambda) m.invoke(lambda));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get method or field from class or superclass
     * @param obj Object
     * @param cb FunctionWithExceptions {Class, R, E}
     * @return R
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <R, E extends ReflectiveOperationException> R getInvoke(Object obj, FunctionWithExceptions<Class<?>, R, E> cb) throws E {
        return getInvoke(getClazz(obj), cb);
    }

    /**
     * Get method or field from class or superclass
     * @param clazz Class
     * @param cb FunctionWithExceptions {Class, R, E}
     * @return R
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    public static <R, E extends ReflectiveOperationException> R getInvoke(Class<?> clazz, FunctionWithExceptions<Class<?>, R, E> cb) throws E {
        return getInvoke(clazz, cb, (c, e) -> {
            Class<?> superClass = c.getSuperclass();
            if (isNull(superClass)) throw e;
            else {
                return getInvoke(superClass, cb);
            }
        });
    }

    /**
     * Get method or field from class or superclass
     * @param clazz Class
     * @param cb FunctionWithExceptions {Class, R, E}
     * @param catchCb BiFunctionWithExceptions {Class, E, R, E}
     * @return R
     * @param <R> R
     * @param <E> E
     * @throws E throws
     */
    @SuppressWarnings("unchecked")
    public static <R, E extends ReflectiveOperationException> R getInvoke
    (Class<?> clazz, FunctionWithExceptions<Class<?>, R, E> cb, BiFunctionWithExceptions<Class<?>, E, R, E> catchCb) throws E {
        try { return cb.apply(clazz); }
        catch (ReflectiveOperationException e) { return catchCb.apply(clazz, (E) e); }
    }

    /**
     * Get method or field by className
     * @param className String
     * @return Object[]
     * @throws ClassNotFoundException throws
     */
    public static Object[] getInvoke(String className) throws ClassNotFoundException {
        List<Object> list = arrayList(parseClassName(className, "::").split(","));
        list.set(0, getClazz(list.get(0).toString()));
        return list.toArray();
    }

    /**
     * Get method or field by className
     * @param className String
     * @param cb TriFunctionWithExceptions {Class, String, T[], R, E}
     * @param args T[]
     * @return R
     * @param <T> T
     * @param <R> R
     * @param <E> E
     * @throws E throws
     * @throws ReflectiveOperationException throws
     */
    @SafeVarargs
    public static <T, R, E extends Exception> R invoke(String className, TriFunctionWithExceptions<Class<?>, String, T[], R, E> cb, T... args)
        throws E, ReflectiveOperationException {
        return tryNoArgsWithPrintMsg("wrong number of arguments", () -> cb.invoke(concatTo(getInvoke(className), args)));
    }

    /**
     * Get method or field by className
     * @param className String
     * @param cb BiFunctionWithExceptions {Class, String, R, E}
     * @return R
     * @param <R> R
     * @param <E> extends Exception
     * @throws ReflectiveOperationException throws
     * @throws E throws
     */
    public static <R, E extends Exception> R invoke(String className, BiFunctionWithExceptions<Class<?>, String, R, E> cb) throws ReflectiveOperationException, E {
        return tryNoArgsWithPrintMsg("wrong number of arguments", () -> cb.invoke(getInvoke(className)));
    }

    /**
     * Invoke method of className with parse
     * @param className String
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T> T invoke(String className, Object... args) throws ReflectiveOperationException {
        return invoke(className, Reflection::invoke, args);
    }

    /**
     * Invoke method of className
     * @param className String
     * @param method String
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T> T invoke(String className, String method, Object... args) throws ReflectiveOperationException {
        return invoke(getClazz(className), method, args); // вызов метода с аргументами
    }

    /**
     * Invoke method of lambda expression
     * @param lambda Serializable
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeLambda(Serializable lambda, Object... args) throws ReflectiveOperationException {
        return (T) lambdaMethod(lambda).invoke(lambda, args); // получение и вызов метода с аргументами
    }

    /**
     * Invoke method of object
     * @param obj Object
     * @param method String
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object obj, String method, Object... args) throws ReflectiveOperationException {
        return (T) getMethod(obj, method, args).invoke(obj, args); // получение и вызов метода с аргументами
    }

    /**
     * Invoke parse number method
     * @param type Class
     * @param value Object
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeParse(Class<?> type, Object value) throws ReflectiveOperationException {
        if (!isParseType(type)) return (T) value;
        Class<?> _type = type;
        String name = getClassSimpleName(type);
        type = getParseType(type);
        String methodPostfix = _type == type ? name : getClassSimpleName(type);
        String method = isInt(type) ? "parseInt" : ("parse" + methodPostfix);
        debug(_type == type);
        debug(method);
        return invoke(type, method, value); // вызов метода с аргументами
    }

    /**
     *
     * @param obj Object
     * @return Field[]
     */
    public static Field[] fields(Object obj) {
        return getClazz(obj).getDeclaredFields();
    }

    /**
     * See:
     * <p><a href="https://stackoverflow.com/questions/13400075/reflection-generic-get-field-value">Reflection generic get field value</a>.
     * <p><a href="https://www.geeksforgeeks.org/reflection-in-java">Reflection in Java</a>.
     * <p><a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredField-java.lang.String-">getDeclaredField</a>.
     * @param obj Object
     * @param func BiFunction {Field, T, R}
     * @param arg T
     * @return T
     * @param <T> T
     * @param <R> R
     */
    public static <T, R> T fields(Object obj, BiFunction<Field, T, R> func, T arg) {
        for (Field field : fields(obj)) {
            boolean make = makeAccessible(field, obj);
            func.apply(field, arg);
            makeUnAccessible(field, make);
        }
        return arg;
    }

    /**
     * Get field
     * @param className String
     * @return Field
     * @throws ReflectiveOperationException throws
     */
    public static Field field(String className) throws ReflectiveOperationException {
        return invoke(className, Reflection::field);
    }

    /**
     * Get field
     * @param obj Object
     * @param name String
     * @return Field
     * @throws NoSuchFieldException throws
     */
    public static Field field(Object obj, String name) throws NoSuchFieldException {
        return field(getClazz(obj), name);
    }

    /**
     * Get field
     * <p>Если есть менеджер безопасности, это не сработает. Вам нужно обернуть вызов {@link Field#setAccessible(boolean) setAccessible} и {@link Class#getDeclaredField} в {@link PrivilegedAction} и запустить его через {@code java.security.AccessController#doPrivileged(PrivilegedAction, AccessControlContext, Permission...) doPrivileged}
     * @param clazz Class
     * @param name, String
     * @return Field
     * @throws NoSuchFieldException throws
     */
    public static Field field(Class<?> clazz, String name) throws NoSuchFieldException {
        return getInvoke(clazz, c -> c.getDeclaredField(name));
    }

    /**
     * Get field value
     * @param className String
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T> T getField(String className) throws ReflectiveOperationException {
        return invoke(className, Reflection::getField);
    }

    /**
     * Get field value
     * @param obj Object
     * @param name String
     * @return T
     * @param <T> T
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    @SuppressWarnings("unchecked")
    public static <T> T getField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException {
        return field(obj, name, field -> {
            try {return (T) field.get(obj);}
            catch (IllegalAccessException e) {throw new RuntimeException(e);}
        });
    }

    /**
     * Set field value
     * @param className String
     * @param value T
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T> T setField(String className, T value) throws ReflectiveOperationException {
        return invoke(className, Reflection::setField, value);
    }

    /**
     * Set field value
     * @param obj Object
     * @param name String
     * @param value T
     * @return R
     * @param <T> T
     * @param <R> R
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    @SuppressWarnings("unchecked")
    public static <T, R> R setField(Object obj, String name, T value) throws NoSuchFieldException, IllegalAccessException {
        return field(obj, name, field -> {
            try {
                field.set(obj, value);
                return (R) field.get(obj);
            } catch (IllegalAccessException e) {throw new RuntimeException(e);}
        });
    }

    /**
     * Set field value
     * @param obj Object
     * @param name String
     * @param cb Function {Field, Object}
     * @return Object
     * @param <T> T
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    public static <T> T field(Object obj, String name, Function<Field, T> cb) throws NoSuchFieldException, IllegalAccessException {
        Field field = field(obj, name);
        boolean make = makeAccessible(field, obj);
        debug("field: " + field);
        T value = cb.apply(field);
        makeUnAccessible(field, make);
        return value;
    }

    /**
     * Field set accessible to true
     * @param field Field
     * @param obj Object
     * @return boolean
     */
    public static boolean makeAccessible(Field field, Object obj) {
        boolean make = !canAccess(field, obj);
        if (make) field.setAccessible(true);
        return make;
    }

    /**
     * Field set accessible to true
     * @param field Field
     * @return boolean
     */
    public static boolean makeAccessible(Field field) {
        boolean make = !isPublic(field);
        if (!isPublic(field)) field.setAccessible(true);
        return make;
    }

    /**
     * Field set accessible to false
     * @param field Field
     * @param make boolean
     */
    public static void makeUnAccessible(Field field, boolean make) {
        if (make) field.setAccessible(false);
    }

    /**
     * Field set accessible to false
     * @param field Field
     */
    public static void makeUnAccessible(Field field) {
        field.setAccessible(false);
    }

    /**
     * Field is static
     * @param field Field
     * @return boolean
     */
    public static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers()) || Modifier.isStatic(field.getDeclaringClass().getModifiers());
    }

    /**
     * Field is public
     * @param field Field
     * @return boolean
     */
    public static boolean isPublic(Field field) {
        return Modifier.isPublic(field.getModifiers()) || Modifier.isPublic(field.getDeclaringClass().getModifiers());
    }

    /**
     * Field is protected
     * @param field Field
     * @return boolean
     */
    public static boolean isProtected(Field field) {
        return Modifier.isProtected(field.getModifiers()) || Modifier.isProtected(field.getDeclaringClass().getModifiers());
    }

    /**
     * Field is private
     * @param field Field
     * @return boolean
     */
    public static boolean isPrivate(Field field) {
        return Modifier.isPrivate(field.getModifiers()) || Modifier.isPrivate(field.getDeclaringClass().getModifiers());
    }

    /**
     * Field is accessible by object
     * @param field Field
     * @param obj Object
     * @return boolean
     */
    public static boolean canAccess(Field field, Object obj) {
        return field.canAccess(isStatic(field) ? null : obj);
    }

}
