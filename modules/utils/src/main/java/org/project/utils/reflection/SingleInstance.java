package org.project.utils.reflection;

import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.notNull;
import static org.project.utils.reflection.Reflection.getCallingChildClass;
import static org.project.utils.reflection.Reflection.getField;
import static org.project.utils.reflection.Reflection.getGenericClass;
import static org.project.utils.reflection.Reflection.setField;

/**
 *
 * @param <T>
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
    public static <T extends SingleInstance<?>> T instance() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //debug("instanceCall: " + getCallingClass());
        debug("instanceChildCall: " + getCallingChildClass());
        try {
            debug("instanceGeneric: " + getGenericClass());
            return (T) getField(getGenericClass(), iField);
        }
        catch (IllegalArgumentException e) {return (T) i;}
    }

    /**
     *
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     * @throws NoSuchFieldException throws
     */
    public static <T extends SingleInstance<?>> T instance(Object... args)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        return _instance(getGenericClass(), args);
    }

    /**
     *
     * @param clazz Class T
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     * @throws ClassNotFoundException throws
     * @throws NoSuchFieldException throws
     */
    public static <T extends SingleInstance<?>> T instance(Class<T> clazz, Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        return _instance(clazz, args);
    }

    /**
     *
     * @param clazz Class T
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     * @throws ClassNotFoundException throws
     * @throws NoSuchFieldException throws
     */
    @SuppressWarnings("unchecked")
    protected static <T extends SingleInstance<?>> T _instance(Class<T> clazz, Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        debug("instance: " + clazz);
        T i = (T) getField(clazz, iField);
        debug("field.get: " + i);
        return notNull(i) ? i : (T) setField(clazz, iField, create(clazz, args));
    }

}
