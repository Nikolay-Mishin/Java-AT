package utils;

import jdk.jfr.Description;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.PropertyUtils;

import static java.lang.System.out;
import static org.apache.commons.beanutils.BeanUtils.getProperty;

public class Reflection {

    @Description("Get object property")
    private static Object _getProp(Object obj, String prop, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object _prop = PropertyUtils.getProperty(obj, prop);
        if (print) out.println(_prop);
        return _prop;
    }

    @Description("Get object property value of String")
    private static Object _getPropStr(Object obj, String prop, boolean print) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object _prop = getProperty(obj, prop);
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

}
