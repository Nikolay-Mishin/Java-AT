package utils.reflections;

import jdk.jfr.Description;
import org.apache.commons.beanutils.BeanUtils;
import utils.exceptions.AssertException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;
import static org.apache.commons.beanutils.PropertyUtils.getProperty;
import static org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors;
import static utils.Helper.*;

public class Reflection {

    @Description("Get object property")
    private static Object _getProp(Object obj, String prop, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object _prop = getProperty(obj, prop);
        if (print) out.println(_prop);
        return _prop;
    }

    @Description("Get object property value of String")
    private static Object _getPropStr(Object obj, String prop, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object _prop = BeanUtils.getProperty(obj, prop);
        if (print) out.println(_prop);
        return _prop;
    }

    @Description("Get property descriptor by name")
    private static PropertyDescriptor _getPropDescriptor(List<PropertyDescriptor> descList, String name) {
        PropertyDescriptor[] _descList = descList.stream().filter(s -> s.getName().equals(name)).toArray(PropertyDescriptor[]::new);
        PropertyDescriptor desc = _descList.length > 0 ? _descList[0] : null;
        out.println(desc);
        return desc;
    }

    private static Class<?>[] _getTypes(Boolean getPrimitive, Object... args) {
        out.println(Arrays.toString(args));
        Class<?>[] argTypes = Arrays.stream(args)
            .map(arg -> getPrimitive ? getPrimitiveType(arg) : arg.getClass())
            .toArray(Class<?>[]::new);
        out.println(Arrays.toString(argTypes));
        return argTypes;
    }

    private static Class<?> _getGenericClass(Class<?> genericClass, int index) throws ClassNotFoundException {
        Class<?> clazz = getCallingClass(2);
        Class<?> actualClass = isInstance(clazz, genericClass) ? clazz : getCallingClass(3);
        Class<?> genericType = ReflectionUtils.getGenericParameterClass(actualClass, genericClass, index);
        out.println(genericType);
        return genericType;
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
        Class<?> clazz = isInstance(obj, List.class) ? List.class : _getClass(obj);
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

    public static Class<?> _getClass(Object obj) {
        Class<?> clazz = isClass(obj) ? (Class<?>) obj : obj.getClass();
        out.println(clazz);
        return clazz;
    }

    public static String getClassName(Object obj) {
        String name = _getClass(obj).getName();
        out.println(name);
        return name;
    }

    public static String getClassSimpleName(Object obj) {
        String name = _getClass(obj).getSimpleName();
        out.println(name);
        return name;
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

    public static Class<?> getCallingClass(int index) throws ClassNotFoundException {
        return Class.forName(getStackTraceEl(++index).getClassName());
    }

    public static Class<?> getCallingClass() throws ClassNotFoundException {
        return getCallingClass(1);
    }

    public static String getCallingClassName(int index) {
        return getStackTraceEl(++index).getClassName();
    }

    public static String getCallingClassName() {
        return getCallingClassName(1);
    }

    public static Class<?> getGenericClass(Class<?> genericClass, int index) throws ClassNotFoundException {
        return _getGenericClass(genericClass, index);
    }

    public static Class<?> getGenericClass(int index) throws ClassNotFoundException {
        return _getGenericClass(getCallingClass(1), index);
    }

    public static Class<?> getGenericClass() throws ClassNotFoundException {
        return _getGenericClass(getCallingClass(1), 0);
    }

    @Description("Get object property value of String")
    public static Object getPropStr(Object obj, String prop, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getPropStr(obj, prop, print);
    }

    @Description("Get object property value of String")
    public static Object getPropStr(Object obj, String prop) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getPropStr(obj, prop, false);
    }

    @Description("Get object property")
    public static Object getProp(Object obj, String prop, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getProp(obj, prop, print);
    }

    @Description("Get object property")
    public static Object getProp(Object obj, String prop) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getProp(obj, prop, false);
    }

    @Description("Get property descriptors")
    public static List<PropertyDescriptor> getPropDescriptors(Object obj) {
        PropertyDescriptor[] descList = getPropertyDescriptors(_getClass(obj));
        out.println(Arrays.toString(descList));
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
        out.println(сonstructor);
        return сonstructor;
    }

    public static <T> T newInstance(Class<?> clazz, Object... args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T instance = (T) getConstructor(clazz, args).newInstance(args);
        out.println(instance);
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
        Class<?> clazz = _getClass(obj);
        out.println(obj);
        out.println(Arrays.toString(args));
        Method _method = null;
        try {
            _method = clazz.getDeclaredMethod(method, getTypes(args));
        } catch (NoSuchMethodException e) {
            Throwable err = e.getCause();
            String errMsg = err.toString();
            Boolean isNoSuchMethod = errMsg.contains("NoSuchMethodException");
            out.println("catch getMethod");
            out.println(err);
            out.println(errMsg);
            out.println(isNoSuchMethod);
            if (isNoSuchMethod) _method = clazz.getDeclaredMethod(method, getPrimitiveTypes(args));
        }
        out.println(_method);
        new AssertException(_method).notNull();
        out.println(Arrays.toString(_method.getParameterTypes()));
        return _method;
    }

    @Description("Invoke method of object")
    public static <T> T invoke(Object obj, String method, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method methodWithArgs = getMethod(obj, method, args); // получение метода с аргументами
        return (T) methodWithArgs.invoke(obj, args); // вызов метода с аргументами
    }

    @Description("Invoke parse number method")
    public static <T> T invokeParse(Class<?> type, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (!isParseType(type)) return (T) value;
        Class<?> _type = type;
        String name = getClassSimpleName(type);
        type = getParseType(type);
        String methodPostfix = _type == type ? name : getClassSimpleName(type);
        String method = isInt(type) ? "parseInt" : ("parse" + methodPostfix);
        out.println(_type == type);
        out.println(method);
        return invoke(type, method, value); // вызов метода с аргументами
    }

}
