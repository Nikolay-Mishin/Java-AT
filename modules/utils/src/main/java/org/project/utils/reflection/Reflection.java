package org.project.utils.reflection;

import static java.lang.invoke.MethodType.fromMethodDescriptorString;
import static java.lang.reflect.Array.newInstance;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.apache.commons.beanutils.PropertyUtils.*;

import org.apache.commons.beanutils.BeanUtils;

import jdk.jfr.Description;

import static org.project.utils.Helper.*;
import static org.project.utils.exception.UtilException.*;

import org.project.utils.exception.AssertException;
import org.project.utils.function.BiFunctionWithExceptions;
import org.project.utils.function.FunctionWithExceptions;
import org.project.utils.function.TriFunctionWithExceptions;

public class Reflection {

    @Description("Get object property")
    private static Object _getProp(Object obj, String prop, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object _prop = getProperty(obj, prop);
        if (print) debug(_prop);
        return _prop;
    }

    @Description("Get object property value of String")
    private static Object _getPropStr(Object obj, String prop, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object _prop = BeanUtils.getProperty(obj, prop);
        if (print) debug(_prop);
        return _prop;
    }

    @Description("Set object property")
    private static Object _setProp(Object obj, String prop, Object value, boolean print)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        setProperty(obj, prop, value);
        return _getProp(obj, prop, print);
    }

    @Description("Set object property value of String")
    private static Object _setPropStr(Object obj, String prop, Object value, boolean print)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        BeanUtils.setProperty(obj, prop, value);
        return _getPropStr(obj, prop, print);
    }

    @Description("Get property descriptor by name")
    private static PropertyDescriptor _getPropDescriptor(List<PropertyDescriptor> descList, String name) {
        PropertyDescriptor[] _descList = descList.stream().filter(s -> s.getName().equals(name)).toArray(PropertyDescriptor[]::new);
        PropertyDescriptor desc = _descList.length > 0 ? _descList[0] : null;
        debug(desc);
        return desc;
    }

    private static Class<?>[] _getTypes(Boolean getPrimitive, Object... args) {
        debug(Arrays.toString(args));
        Class<?>[] argTypes = map(args, Class<?>[]::new, arg -> getPrimitive ? getPrimitiveType(arg) : arg.getClass());
        debug(Arrays.toString(argTypes));
        return argTypes;
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> _getGenericClass(Class<T> genericClass, int index) throws ClassNotFoundException {
        int traceDepth = 0;
        Class<?> clazz = getCallingClass(traceDepth);
        while (clazz != genericClass) clazz = getCallingClass(++traceDepth);
        Class<?> actualClass = getCallingClass(++traceDepth);
        while (actualClass == genericClass) actualClass = getCallingClass(++traceDepth);
        new AssertException(actualClass).notNull();
        return (Class<T>) ReflectionUtils.getGenericParameterClass(actualClass, genericClass, index);
    }

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

    @Description("Get ClassName of parse")
    public static String parseClassName(String className) {
        return parseClassName(className, "::", "$1,$2");
    }

    @Description("Get ClassName of parse")
    public static String parseClassName(String className, String sep) {
        return parseClassName(className, sep, "$1,$2");
    }

    @Description("Get ClassName of parse")
    public static String parseClassName(String className, String sep, String replace) {
        String parsedClassName = className.replaceAll("(.+)" + sep + "(.+)", replace);
        debug("parseClassName: " + className.replaceAll("(.+)" + sep + "(.+)", "$1,$2"));
        return parsedClassName;
    }

    public static Class<?> getClazz(Object obj) {
        Class<?> clazz = isClass(obj) ? (Class<?>) obj : obj.getClass();
        debug(clazz);
        return clazz;
    }

    public static Class<?> getClazz(Method method) {
        return method.getDeclaringClass();
    }

    /**
     * get class from lambda expression
     *
     * @param lambda
     * @return String
     */
    public static Class<?> getClazz(Serializable lambda) throws ClassNotFoundException {
        return getClazz(getClassName(lambda));
    }

    public static String getClassName(Serializable lambda) {
        return lambda(lambda, SerializedLambda::getImplClass).replace("/", ".");
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClazz(String name) throws ClassNotFoundException {
        return (Class<T>) Class.forName(name);
    }

    @SuppressWarnings("unchecked")
    public static <T extends S, S> Class<T> asSubClass(String name, Class<S> subClass) throws ClassNotFoundException {
        return (Class<T>) getClazz(name).asSubclass(subClass);
    }

    @Description("Get Class by className with parse")
    public static <T> Class<T> getClazz(String className, String sep) throws ClassNotFoundException {
        return getClazz(parseClassName(className, sep, "$1"));
    }

    @Description("Get Class by className with parse")
    public static <T> Class<T> getClassParse(String className) throws ClassNotFoundException {
        return getClazz(className, "::");
    }

    public static <T> Class<T> getClazz(int index) throws ClassNotFoundException {
        return getClazz(getClassName(index + 1));
    }

    public static <T> Class<T> getClazz(StackTraceElement stackTraceEl) throws ClassNotFoundException {
        return getClazz(getClassName(stackTraceEl));
    }

    public static String getClassName(Object obj) {
        String name = getClazz(obj).getName();
        debug(name);
        return name;
    }

    public static String getClassSimpleName(Object obj) {
        String name = getClazz(obj).getSimpleName();
        debug(name);
        return name;
    }

    public static String getClassName(int index) {
        return getClassName(getStackTraceEl(index + 1));
    }

    public static String getClassName(StackTraceElement stackTraceEl) {
        return stackTraceEl.getClassName();
    }

    public static StackTraceElement[] getStackTrace() {
        return new RuntimeException().getStackTrace();
    }

    public static StackTraceElement getStackTraceEl(int index) {
        return getStackTrace()[index + 2];
    }

    public static StackTraceElement getStackTraceEl() {
        return getStackTraceEl(1);
    }

    //@SuppressWarnings("unchecked")
    public static <T> Class<T> getCallingClass(int index) throws ClassNotFoundException {
        //debug("getClassName: " + getClassName(index + 1));
        //debug("getClazz: " + getClazz(index + 1));
        //return (Class<T>) Class.forName(getStackTraceEl(++index).getClassName());
        return getClazz(++index);
    }

    public static <T> Class<T> getCallingClass() throws ClassNotFoundException {
        return getCallingClass(1);
    }

    public static <T> Class<T> getCallingChildClass() throws ClassNotFoundException {
        return getCallingClass(2);
    }

    public static String getCallingClassName(int index) {
        return getStackTraceEl(++index).getClassName();
    }

    public static String getCallingClassName() {
        return getCallingClassName(1);
    }

    public static String getCallingChildClassName() {
        return getCallingClassName(2);
    }

    public static String getCallingClassSimpleName() throws ClassNotFoundException {
        return getClassSimpleName(getCallingClass(1));
    }

    public static String getCallingChildClassSimpleName() throws ClassNotFoundException {
        return getClassSimpleName(getCallingClass(2));
    }

    public static <T> Class<T> getGenericClass() throws ClassNotFoundException {
        return _getGenericClass(getCallingClass(1), 0);
    }

    public static <T> Class<T> getGenericClass(int index) throws ClassNotFoundException {
        return _getGenericClass(getCallingClass(1), index);
    }

    public static <T> Class<T> getGenericClass(Class<T> genericClass, int index) throws ClassNotFoundException {
        return _getGenericClass(genericClass, index);
    }

    public static <T> Class<T> getGenericChildClass() throws ClassNotFoundException {
        return _getGenericClass(getCallingChildClass(), 0);
    }

    public static <T> Class<T> getGenericChildClass(int index) throws ClassNotFoundException {
        return _getGenericClass(getCallingChildClass(), index);
    }

    public static String getGenericClassName() throws ClassNotFoundException {
        return getClassName(getGenericClass());
    }

    public static String getGenericClassName(int index) throws ClassNotFoundException {
        return getClassName(getGenericClass(index));
    }

    public static <T> String getGenericClassName(Class<T> genericClass, int index) throws ClassNotFoundException {
        return getClassName(getGenericClass(genericClass, index));
    }

    public static String getGenericChildClassName() throws ClassNotFoundException {
        return getClassName(getGenericChildClass());
    }

    public static String getGenericChildClassName(int index) throws ClassNotFoundException {
        return getClassName(getGenericChildClass(index));
    }

    @Description("Get object property")
    public static Object getProp(Object obj, String prop, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getProp(obj, prop, print);
    }

    @Description("Get object property")
    public static Object getProp(Object obj, String prop) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getProp(obj, prop, false);
    }

    @Description("Get object property value of String")
    public static Object getPropStr(Object obj, String prop, boolean print)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        return _getPropStr(obj, prop, print);
    }

    @Description("Get object property value of String")
    public static Object getPropStr(Object obj, String prop) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getPropStr(obj, prop, false);
    }

    @Description("Set object property")
    public static Object setProp(Object obj, String prop, Object value, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _setProp(obj, prop, value, print);
    }

    @Description("Set object property")
    public static Object setProp(Object obj, String prop, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _setProp(obj, prop, value, false);
    }

    @Description("Set object property value of String")
    public static Object setPropStr(Object obj, String prop, Object value, boolean print)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        return _setPropStr(obj, prop, value, print);
    }

    @Description("Set object property value of String")
    public static Object setPropStr(Object obj, String prop, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _setPropStr(obj, prop, value, false);
    }

    @Description("Get property descriptors")
    public static List<PropertyDescriptor> getPropDescriptors(Object obj) {
        PropertyDescriptor[] descList = getPropertyDescriptors(getClazz(obj));
        debug(Arrays.toString(descList));
        return List.of(descList);
    }

    @Description("Get property descriptor by name")
    public static PropertyDescriptor getPropDescriptor(List<PropertyDescriptor> descList, String name) {
        return _getPropDescriptor(descList, name);
    }

    @Description("Get property descriptor by name")
    public static PropertyDescriptor getPropDescriptor(Object obj, String name) {
        return _getPropDescriptor(getPropDescriptors(obj), name);
    }

    public static Constructor<?> getConstructor(Class<?> clazz, Object... args) throws NoSuchMethodException {
        Constructor<?> сonstructor = clazz.getConstructor(getPrimitiveTypes(args));
        debug(сonstructor);
        return сonstructor;
    }

    @SuppressWarnings("unchecked")
    public static <T> T instance(Class<?> clazz, Object... args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T instance = (T) getConstructor(clazz, args).newInstance(args);
        debug(instance);
        return instance;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] arrInstance(Class<?> clazz, int size) {
        T[] instance = (T[]) newInstance(clazz, size);
        debug(instance);
        return instance;
    }

    public static <T> T[] arrInstance(T[] a, int size) {
        return arrInstance(getType(a), size);
    }

    @Description("Get descriptor of class")
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

    static String getMethodDescriptor(Method m) {
        String s = "(";
        for (final Class<?> c: m.getParameterTypes())
            s += getDescriptorForClass(c);
        s += ')';
        return s + getDescriptorForClass(m.getReturnType());
    }

    @Description("Get classLoader of object")
    public static ClassLoader getClassLoader(Object obj) throws NullPointerException {
        return getClazz(obj).getClassLoader();
    }

    @Description("Get descriptor of lambda object")
    public static String getDescriptor(Object obj) throws NullPointerException {
        return getClazz(obj).descriptorString();
    }

    @Description("Get descriptor of lambda expression")
    public static String getDescriptor(SerializedLambda sl) throws NullPointerException {
        return sl.getImplMethodSignature();
    }

    public static Class<?> getType(Object o) {
        return getClazz(o).getComponentType();
    }

    public static Class<?>[] getTypes(Object... args) {
        return _getTypes(false, args);
    }

    public static Class<?>[] getPrimitiveTypes(Object... args) {
        return _getTypes(true, args);
    }

    @Description("Get types of object")
    public static Class<?>[] getTypes(Object obj, String descriptor) throws NullPointerException {
        return fromMethodDescriptorString(descriptor, getClassLoader(obj)).parameterArray();
    }

    @Description("Get method name of lambda expression")
    public static String getMethodName(SerializedLambda sl) throws NullPointerException {
        return sl.getImplMethodName();
    }

    @Description("Get method of object")
    public static Method getDeclaredMethod(Object obj, String method, Object... args) throws NoSuchMethodException {
        debug("getDeclaredMethod");
        //debug("declaredMethods: " + Arrays.toString(getClazz(obj).getDeclaredMethods()));
        return getMethod(obj, clazz -> clazz.getDeclaredMethod(method, getTypes(args)));
    }

    @Description("Get method of object")
    public static Method getDeclaredPrimitiveMethod(Object obj, String method, Object... args) throws NoSuchMethodException {
        debug("getDeclaredPrimitiveMethod");
        return getMethod(obj, clazz -> clazz.getDeclaredMethod(method, getPrimitiveTypes(args)));
    }

    @Description("Get method of object")
    public static Method getSuperMethod(Object obj, String method, Object... args) throws NoSuchMethodException {
        debug("getSuperMethod");
        //debug("methods: " + Arrays.toString(getClazz(obj).getMethods()));
        return getMethod(obj, clazz -> clazz.getMethod(method, getTypes(args)));
    }

    @Description("Get method of object")
    public static Method getPrimitiveMethod(Object obj, String method, Object... args) throws NoSuchMethodException {
        debug("getPrimitiveMethod");
        return getMethod(obj, clazz -> clazz.getMethod(method, getPrimitiveTypes(args)));
    }

    @Description("Get method of object")
    public static <E extends Exception> Method getMethod(Object obj, FunctionWithExceptions<Class<?>, Method, E> cb) throws E {
        Method _method = cb.apply(getClazz(obj));
        new AssertException(_method).notNull();
        debug(Arrays.toString(_method.getParameterTypes()));
        return _method;
    }

    @Description("Get method of object")
    public static Method getMethod(Object obj, String method, Object... args) throws ReflectiveOperationException, NullPointerException {
        Method _method = tryCatchNoArgs(() -> tryCatchNoArgs(() -> getDeclaredMethod(obj, method, args), e -> getDeclaredPrimitiveMethod(obj, method, args)),
            e -> tryCatchNoArgs(() -> getSuperMethod(obj, method, args), _e -> getPrimitiveMethod(obj, method, args)));
        //Method _method = getInvoke(obj, c -> tryCatchNoArgs(() -> getDeclaredMethod(c, method, args), e -> getDeclaredPrimitiveMethod(c, method, args)));
        debug(_method);
        new AssertException(_method).notNull();
        debug(Arrays.toString(_method.getParameterTypes()));
        return _method;
    }

    @Description("Get method of class")
    public static Method getMethod(Class<?> clazz, String method, Class<?>... args) throws NoSuchMethodException, NullPointerException {
        return clazz.getDeclaredMethod(method, args);
    }

    @Description("Get method of object")
    public static Method getMethod(Object obj, String method, Class<?>... args) throws NoSuchMethodException, NullPointerException {
        return getMethod(getClazz(obj), method, args);
    }

    @Description("Get method of object")
    public static Method getMethod(Object obj, String method, String descriptor) throws NoSuchMethodException, NullPointerException {
        return getMethod(obj, method, getTypes(obj, descriptor));
    }

    @Description("Get method of object")
    public static Method getMethod(Object obj, String method, SerializedLambda sl) throws NoSuchMethodException, NullPointerException {
        return getMethod(obj, method, getDescriptor(sl));
    }

    @Description("Get method of object")
    public static Method getMethod(Object obj, SerializedLambda sl) throws NoSuchMethodException, NullPointerException {
        return getMethod(obj, getMethodName(sl), getDescriptor(sl));
    }

    /**
     * get method name from lambda expression
     *
     * @param lambda
     * @return String
     */
    public static String lambdaMethodName(Serializable lambda) {
        return lambda(lambda, SerializedLambda::getImplMethodName);
    }

    /**
     * get method from lambda expression
     *
     * @param lambda
     * @return String
     */
    public static Method lambdaMethod(Serializable lambda) throws ReflectiveOperationException {
        return lambda(lambda, sl -> getMethod(getClazz(lambda), sl));
    }

    public static Method lambdaMethod(Object obj, Serializable lambda) throws ReflectiveOperationException {
        return lambda(lambda, sl -> getMethod(obj, sl));
    }

    /**
     * get method from lambda expression
     *
     * @param lambda
     * @param cb
     * @return String
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

    @Description("Get method or field from class or superclass")
    public static <R, E extends ReflectiveOperationException> R getInvoke(Object obj, FunctionWithExceptions<Class<?>, R, E> cb) throws E {
        return getInvoke(getClazz(obj), cb);
    }

    //Если есть менеджер безопасности, это не сработает. Вам нужно обернуть вызов setAccessible и getDeclaredField в PriviledgedAction и запустить его через java.security.AccessController.doPrivileged(...)
    @Description("Get method or field from class or superclass")
    public static <R, E extends ReflectiveOperationException> R getInvoke(Class<?> clazz, FunctionWithExceptions<Class<?>, R, E> cb) throws E {
        return getInvoke(clazz, cb, (c, e) -> {
            Class<?> superClass = c.getSuperclass();
            if (isNull(superClass)) throw e;
            else {
                debug("getSuperClass");
                return getInvoke(superClass, cb);
            }
        });
    }

    //Если есть менеджер безопасности, это не сработает. Вам нужно обернуть вызов setAccessible и getDeclaredField в PriviledgedAction и запустить его через java.security.AccessController.doPrivileged(...)
    @Description("Get method or field from class or superclass")
    public static <R, E extends ReflectiveOperationException> R getInvoke
    (Class<?> clazz, FunctionWithExceptions<Class<?>, R, E> cb, BiFunctionWithExceptions<Class<?>, E, R, E> catchCb) throws E {
        try { return cb.apply(clazz); }
        catch (ReflectiveOperationException e) { return catchCb.apply(clazz, (E) e); }
    }

    @Description("Get method or field by className")
    public static Object[] getInvoke(String className) throws ClassNotFoundException {
        List<Object> list = arrayList(parseClassName(className, "::").split(","));
        list.set(0, getClazz(list.get(0).toString()));
        return list.toArray();
    }

    @SafeVarargs
    @Description("Get method or field by className")
    public static <T, R, E extends Exception> R invoke(String className, TriFunctionWithExceptions<Class<?>, String, T[], R, E> cb, T... args)
        throws E, ReflectiveOperationException {
        return tryNoArgsWithPrintMsg("wrong number of arguments", () -> cb.invoke(concatTo(getInvoke(className), args)));
    }

    @Description("Get method or field by className")
    public static <R, E extends Exception> R invoke(String className, BiFunctionWithExceptions<Class<?>, String, R, E> cb) throws ReflectiveOperationException, E {
        return tryNoArgsWithPrintMsg("wrong number of arguments", () -> cb.invoke(getInvoke(className)));
    }

    @Description("Invoke method of className with parse")
    public static <T> T invoke(String className, Object... args) throws ReflectiveOperationException {
        return invoke(className, Reflection::invoke, args);
    }

    @Description("Invoke method of className")
    public static <T> T invoke(String className, String method, Object... args) throws ReflectiveOperationException {
        return invoke(getClazz(className), method, args); // вызов метода с аргументами
    }

    @Description("Invoke method of lambda expression")
    @SuppressWarnings("unchecked")
    public static <T> T invokeLambda(Serializable lambda, Object... args) throws ReflectiveOperationException {
        return (T) lambdaMethod(lambda).invoke(lambda, args); // получение и вызов метода с аргументами
    }

    @Description("Invoke method of object")
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object obj, String method, Object... args) throws ReflectiveOperationException {
        return (T) getMethod(obj, method, args).invoke(obj, args); // получение и вызов метода с аргументами
    }

    @Description("Invoke parse number method")
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

    public static Field[] fields(Object obj) {
        return getClazz(obj).getDeclaredFields();
    }

    // See:
    //  - https://stackoverflow.com/questions/13400075/reflection-generic-get-field-value
    //  - https://www.geeksforgeeks.org/reflection-in-java
    //  - https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredField-java.lang.String-
    public static <T, R> T fields(Object obj, BiFunction<Field, T, R> func, T arg) {
        for (Field field : fields(obj)) {
            boolean make = makeAccessible(field, obj);
            func.apply(field, arg);
            makeUnAccessible(field, make);
        }
        return arg;
    }

    @Description("Get field")
    public static Field field(String className) throws ReflectiveOperationException {
        return invoke(className, Reflection::field);
    }

    @Description("Get field")
    public static Field field(Object obj, String name) throws NoSuchFieldException {
        return field(getClazz(obj), name);
    }

    //Если есть менеджер безопасности, это не сработает. Вам нужно обернуть вызов setAccessible и getDeclaredField в PriviledgedAction и запустить его через java.security.AccessController.doPrivileged(...)
    @Description("Get field")
    public static Field field(Class<?> clazz, String name) throws NoSuchFieldException {
        return getInvoke(clazz, c -> c.getDeclaredField(name));
    }

    @Description("Get field value")
    public static Object getField(String className) throws ReflectiveOperationException {
        return invoke(className, Reflection::getField);
    }

    @Description("Get field value")
    public static Object getField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException {
        return field(obj, name, field -> {
            try {return field.get(obj);}
            catch (IllegalAccessException e) {throw new RuntimeException(e);}
        });
    }

    @Description("Set field value")
    public static Object setField(String className, Object value) throws ReflectiveOperationException {
        return invoke(className, Reflection::setField, value);
    }

    @Description("Set field value")
    public static Object setField(Object obj, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        return field(obj, name, field -> {
            try {
                field.set(obj, value);
                return field.get(obj);
            } catch (IllegalAccessException e) {throw new RuntimeException(e);}
        });
    }

    @Description("Set field value")
    public static Object field(Object obj, String name, Function<Field, Object> cb) throws NoSuchFieldException, IllegalAccessException {
        Field field = field(obj, name);
        boolean make = makeAccessible(field, obj);
        debug("field: " + field);
        Object value = cb.apply(field);
        makeUnAccessible(field, make);
        return value;
    }

    @Description("Field set accessible to true")
    public static boolean makeAccessible(Field field, Object obj) {
        boolean make = !canAccess(field, obj);
        if (make) field.setAccessible(true);
        return make;
    }

    @Description("Field set accessible to true")
    public static boolean makeAccessible(Field field) {
        boolean make = !isPublic(field);
        if (!isPublic(field)) field.setAccessible(true);
        return make;
    }

    @Description("Field set accessible to false")
    public static void makeUnAccessible(Field field, boolean make) {
        if (make) field.setAccessible(false);
    }

    @Description("Field set accessible to false")
    public static void makeUnAccessible(Field field) {
        field.setAccessible(false);
    }

    @Description("Field is static")
    public static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers()) || Modifier.isStatic(field.getDeclaringClass().getModifiers());
    }

    @Description("Field is public")
    public static boolean isPublic(Field field) {
        return Modifier.isPublic(field.getModifiers()) || Modifier.isPublic(field.getDeclaringClass().getModifiers());
    }

    @Description("Field is protected")
    public static boolean isProtected(Field field) {
        return Modifier.isProtected(field.getModifiers()) || Modifier.isProtected(field.getDeclaringClass().getModifiers());
    }

    @Description("Field is private")
    public static boolean isPrivate(Field field) {
        return Modifier.isPrivate(field.getModifiers()) || Modifier.isPrivate(field.getDeclaringClass().getModifiers());
    }

    @Description("Field is accessible by object")
    public static boolean canAccess(Field field, Object obj) {
        return field.canAccess(isStatic(field) ? null : obj);
    }

}
