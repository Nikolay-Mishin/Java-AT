package org.project.utils.reflection;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;

import static org.apache.commons.beanutils.PropertyUtils.*;

import org.apache.commons.beanutils.BeanUtils;

import jdk.jfr.Description;

import static org.project.utils.Helper.*;

import org.project.utils.exception.AssertException;

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

    @Description("Get method of object")
    private static Method _getMethod(String get, Object obj, String method, Object... args) throws NoSuchMethodException {
        Class<?> clazz = getClass(obj);
        Method _method;
        boolean declared = Objects.equals(get, "declared");
        get = declared ? "getDeclaredMethod" : "getMethod";
        debug(get);
        try {
            _method = _getMethod(declared, clazz, method, getTypes(args));
        } catch (NoSuchMethodException e) {
            _method = _getMethod(declared, clazz, method, getPrimitiveTypes(args));
        }
        new AssertException(_method).notNull();
        debug(Arrays.toString(_method.getParameterTypes()));
        return _method;
    }

    @Description("Get method of object")
    private static Method _getMethod(boolean declared, Class<?> clazz, String method, Class<?>... types) throws NoSuchMethodException {
        return declared ? clazz.getDeclaredMethod(method, types) : clazz.getMethod(method, types);
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
        Class<?> clazz = isInstance(obj, List.class) ? List.class : getClass(obj);
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

    public static Class<?> getClass(Object obj) {
        Class<?> clazz = isClass(obj) ? (Class<?>) obj : obj.getClass();
        debug(clazz);
        return clazz;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(String name) throws ClassNotFoundException {
        return (Class<T>) Class.forName(name);
    }

    @SuppressWarnings("unchecked")
    public static <T extends S, S> Class<T> asSubClass(String name, Class<S> subClass) throws ClassNotFoundException {
        return (Class<T>) getClass(name).asSubclass(subClass);
    }

    public static <T> Class<T> getClass(int index) throws ClassNotFoundException {
        return getClass(getClassName(index + 1));
    }

    public static <T> Class<T> getClass(StackTraceElement stackTraceEl) throws ClassNotFoundException {
        return getClass(getClassName(stackTraceEl));
    }

    public static String getClassName(Object obj) {
        String name = getClass(obj).getName();
        debug(name);
        return name;
    }

    public static String getClassSimpleName(Object obj) {
        String name = getClass(obj).getSimpleName();
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
        //debug("getClass: " + getClass(index + 1));
        //return (Class<T>) Class.forName(getStackTraceEl(++index).getClassName());
        return getClass(++index);
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
        PropertyDescriptor[] descList = getPropertyDescriptors(getClass(obj));
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

    public static Class<?>[] getTypes(Object... args) {
        return _getTypes(false, args);
    }

    public static Class<?>[] getPrimitiveTypes(Object... args) {
        return _getTypes(true, args);
    }

    @Description("Get method of object")
    public static Method getMethod(Object obj, String method, Object... args) throws NoSuchMethodException, NullPointerException {
        Method _method;
        try {
            _method = _getMethod("declared", obj, method, args);
        } catch (NoSuchMethodException e) {
            _method = _getMethod("all", obj, method, args);
        }
        debug(_method);
        new AssertException(_method).notNull();
        debug(Arrays.toString(_method.getParameterTypes()));
        return _method;
    }

    @Description("Invoke method of className")
    public static <T> T invoke(String className, String method, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        return invoke(getClass(className), method, args); // вызов метода с аргументами
    }

    @Description("Invoke method of object")
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Object obj, String method, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method methodWithArgs = getMethod(obj, method, args); // получение метода с аргументами
        return (T) methodWithArgs.invoke(obj, args); // вызов метода с аргументами
    }

    @Description("Invoke parse number method")
    @SuppressWarnings("unchecked")
    public static <T> T invokeParse(Class<?> type, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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

    @Description("Get field")
    public static Field field(Object obj, String name) throws NoSuchFieldException {
        return field(getClass(obj), name);
    }

    //Если есть менеджер безопасности, это не сработает. Вам нужно обернуть вызов setAccessible и getDeclaredField в PriviledgedAction и запустить его через java.security.AccessController.doPrivileged(...)
    @Description("Get field")
    public static Field field(Class<?> clazz, String name) throws NoSuchFieldException {
        try {return clazz.getDeclaredField(name);}
        catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass == null) throw e;
            else return field(superClass, name);
        }
    }

    @Description("Get field value")
    public static Object getField(Object obj, String name) throws NoSuchFieldException, IllegalAccessException {
        return field(obj, name, field -> {
            try {return field.get(obj);}
            catch (IllegalAccessException e) {throw new RuntimeException(e);}
        });
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
        boolean makeAccessible = makeAccessible(field, obj);
        debug("field: " + field);
        Object value = cb.apply(field);
        makeUnAccessible(field, makeAccessible);
        return value;
    }

    @Description("Field set accessible to true")
    public static boolean makeAccessible(Field field, Object obj) {
        boolean makeAccessible = !canAccess(field, obj);
        if (makeAccessible) field.setAccessible(true);
        return makeAccessible;
    }

    @Description("Field set accessible to true")
    public static boolean makeAccessible(Field field) {
        boolean makeAccessible = !isPublic(field);
        if (!isPublic(field)) field.setAccessible(true);
        return makeAccessible;
    }

    @Description("Field set accessible to false")
    public static void makeUnAccessible(Field field, boolean makeAccessible) {
        if (makeAccessible) field.setAccessible(false);
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
