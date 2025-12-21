package org.project.utils.reflection;

import java.util.Map;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.notNull;
import static org.project.utils.reflection.Reflection.getCallingChildClass;
import static org.project.utils.reflection.Reflection.getClazz;
import static org.project.utils.reflection.Reflection.getField;
import static org.project.utils.reflection.Reflection.getGenericClass;
import static org.project.utils.reflection.Reflection.setField;

/**
 *
 * @param <T> T
 */
public class SingleInstance<T> extends Instance<T> {
    /**
     *
     */
    protected static String iField = "i";
    /**
     *
     */
    protected static SingleInstance<? extends SingleInstance<?>> i;

    /**
     *
     * @return T
     * @param <T> T
     * @throws ClassNotFoundException throws
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    @SuppressWarnings("unchecked")
    public static <T extends SingleInstance<?>> T getInstance() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //debug("instanceCall: " + getCallingClass());
        debug("instanceChildCall: " + getCallingChildClass());
        try {
            debug("instanceGeneric: " + getGenericClass());
            return getField(getGenericClass(), iField);
        }
        catch (IllegalArgumentException e) {return (T) i;}
    }

    /**
     *
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T extends SingleInstance<?>> T instance(Object... args) throws ReflectiveOperationException {
        debug("getGenericClass: " + getGenericClass());
        return instance(getGenericClass(), args);
    }

    /**
     *
     * @param args Map {Class, Object}
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T extends SingleInstance<?>> T instance(Map<Class<?>, Object> args) throws ReflectiveOperationException {
        debug("getGenericClass: " + getGenericClass());
        return instance(getGenericClass(), args);
    }

    /**
     *
     * @param clazz Class T
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T extends SingleInstance<?>> T instance(Class<T> clazz, Object... args) throws ReflectiveOperationException {
        return instance(clazz, create(clazz, args));
    }

    /**
     *
     * @param clazz Class T
     * @param args Map {Class, Object}
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T extends SingleInstance<?>> T instance(Class<T> clazz, Map<Class<?>, Object> args) throws ReflectiveOperationException {
        return instance(clazz, create(clazz, args));
    }

    /**
     *
     * @param obj T
     * @return T
     * @param <T> T
     * @throws IllegalAccessException throws
     * @throws NoSuchFieldException throws
     */
    @SuppressWarnings("unchecked")
    public static <T extends SingleInstance<?>> T setInstance(T obj) throws IllegalAccessException, NoSuchFieldException {
        debug("setInstance: " + obj);
        return instance((Class<T>) getClazz(obj), obj);
    }

    /**
     *
     * @param clazz Class T
     * @param value T
     * @return T
     * @param <T> T
     * @throws IllegalAccessException throws
     * @throws NoSuchFieldException throws
     */
    protected static <T extends SingleInstance<?>> T instance(Class<T> clazz, T value) throws IllegalAccessException, NoSuchFieldException {
        debug("instance: " + clazz);
        T i = getField(clazz, iField);
        debug("field.get: " + i);
        return notNull(i) ? i : setField(clazz, iField, value);
    }

}
