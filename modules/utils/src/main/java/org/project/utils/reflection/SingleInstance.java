package org.project.utils.reflection;

import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.*;
import static org.project.utils.reflection.Reflection.*;

public class SingleInstance<T> extends Instance<T> {

    protected static SingleInstance<?> instance;

    public static <T extends SingleInstance<?>> T instance() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //debug("instanceChildCall: " + getCallingChildClass());
        //debug("generic: " + getGenericClass());
        try {return (T) getField(getGenericClass(), "instance");}
        catch (IllegalArgumentException e) {return (T) instance;}
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
        T instance = (T) getField(clazz, "instance");
        debug("field.get: " + instance);
        //return (T) (notNull(instance) ? instance : (instance = create(clazz, args)));
        return notNull(instance) ? instance : (T) setField(clazz, "instance", create(clazz, args));
    }

}
