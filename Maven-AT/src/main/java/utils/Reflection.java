package utils;

import jdk.jfr.Description;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.beanutils.BeanUtils;

import static java.lang.System.out;
import static org.apache.commons.beanutils.PropertyUtils.getProperty;

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

    @Description("Get method of object")
    public static Method getMethod(Object obj, String method, Object... args) throws NoSuchMethodException {
        out.println(obj);
        out.println(Arrays.toString(args));
        Class<?>[] argTypes = Arrays.stream(args)
            .map(arg -> arg.getClass())
            .toArray(Class<?>[]::new);
        out.println(Arrays.toString(argTypes));
        Method _method = obj.getClass().getDeclaredMethod(method, argTypes);
        out.println(_method);
        out.println(Arrays.toString(_method.getParameterTypes()));
        return _method;
    }

    @Description("Invoke method of object")
    public static <T> T invoke(Object obj, String method, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method methodWithArgs = getMethod(obj, method, args); // получение метода с аргументами
        return (T) methodWithArgs.invoke(obj, args); // вызов метода с аргументами
    }

}
