package org.project.utils.reflection;

import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.notNull;
import static org.project.utils.reflection.Reflection.getGenericClass;

public class SingleInstance<T> extends Instance<T> {

    protected static SingleInstance<?> instance;

    public static <T extends SingleInstance<?>> T instance(Object... args)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return _instance(getGenericClass(), args);
    }

    public static <T extends SingleInstance<?>> T instance(Class<T> clazz, Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        return _instance(clazz, args);
    }

    @SuppressWarnings("unchecked")
    protected static <T extends SingleInstance<?>> T _instance(Class<T> clazz, Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (T) (notNull(instance) ? instance : (instance = create(clazz, args)));
    }

}
