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
    public static <T extends SingleInstance<?>> T getInstance() {
        try {
            //debug("instanceCall: " + getCallingClass());
            debug("instanceChildCall: " + getCallingChildClass());
            debug("instanceGeneric: " + getGenericClass());
            return getField(getGenericClass(), iField);
        } catch (IllegalArgumentException e) {
            return (T) i;
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T extends SingleInstance<?>> T instance(Object... args) {
        try {
            debug("getGenericClass: " + getGenericClass());
            return instance(getGenericClass(), args);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param args Map {Class, Object}
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T extends SingleInstance<?>> T instance(Map<Class<?>, Object> args) {
        try {
            debug("getGenericClass: " + getGenericClass());
            return instance(getGenericClass(), args);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param clazz Class T
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    public static <T extends SingleInstance<?>> T instance(Class<T> clazz, Object... args) {
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
    public static <T extends SingleInstance<?>> T instance(Class<T> clazz, Map<Class<?>, Object> args) {
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
    public static <T extends SingleInstance<?>> T setInstance(T obj) {
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
    protected static <T extends SingleInstance<?>> T instance(Class<T> clazz, T value) {
        try {
            debug("instance: " + clazz);
            T i = getField(clazz, iField);
            debug("field.get: " + i);
            T _i = notNull(i) ? i : setField(clazz, iField, value);
            debug("field.set: " + _i);
            return _i;
        } catch (IllegalAccessException | NoSuchFieldException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

}
