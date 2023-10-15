package utils;

import jdk.jfr.Description;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    @Description("Get object property")
    private static Method _getMethod(Object obj, String method, boolean print, Class<?>... args) throws NoSuchMethodException {
        out.println(Arrays.toString(args));
        Method _method = obj.getClass().getDeclaredMethod(method, args);
        out.println(Arrays.toString(_method.getParameterTypes()));
        if (print) out.println(_method);
        return _method;
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

    @Description("Get object property")
    public static Method getMethod(Object obj, String method, boolean print, Class<?>... args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getMethod(obj, method, print, args);
    }

    @Description("Get object property")
    public static Method getMethod(Object obj, String method, Class<?>... args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _getMethod(obj, method, false, args);
    }

}
