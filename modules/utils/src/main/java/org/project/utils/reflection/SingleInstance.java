package org.project.utils.reflection;

import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.notNull;
import static org.project.utils.reflection.Reflection.getCallingChildClass;
import static org.project.utils.reflection.Reflection.getField;
import static org.project.utils.reflection.Reflection.getGenericClass;
import static org.project.utils.reflection.Reflection.setField;

public class SingleInstance<T> extends Instance<T> {
    protected static String iField = "i";
    protected static SingleInstance<? extends SingleInstance<?>> i;

    public static <T extends SingleInstance<?>> T instance() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //debug("instanceCall: " + getCallingClass());
        debug("instanceChildCall: " + getCallingChildClass());
        try {
            debug("instanceGeneric: " + getGenericClass());
            return (T) getField(getGenericClass(), iField);
        }
        catch (IllegalArgumentException e) {return (T) i;}
    }

    public static <T extends SingleInstance<?>> T instance(Object... args)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        return _instance(getGenericClass(), args);
    }

    public static <T extends SingleInstance<?>> T instance(Class<T> clazz, Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        return _instance(clazz, args);
    }

    @SuppressWarnings("unchecked")
    protected static <T extends SingleInstance<?>> T _instance(Class<T> clazz, Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        debug("instance: " + clazz);
        T i = (T) getField(clazz, iField);
        debug("field.get: " + i);
        return notNull(i) ? i : (T) setField(clazz, iField, create(clazz, args));
    }

}
