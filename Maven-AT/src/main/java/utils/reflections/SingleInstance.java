package utils.reflections;

import java.lang.reflect.InvocationTargetException;

import static utils.Helper.notNull;
import static utils.reflections.Reflection.getGenericClass;

public class SingleInstance<T extends SingleInstance<?>> {

    protected static SingleInstance<? extends SingleInstance<?>> instance;

    public static <T extends SingleInstance<?>> T instance(Object... args)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return _instance(_getGenericClass(), args);
    }

    public static <T extends SingleInstance<?>> T instance(Class<T> clazz, Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return _instance(clazz, args);
    }

    protected static <T extends SingleInstance<?>> T _instance(Class<T> clazz, Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return (T) (notNull(instance) ? instance : (instance = new Instance<>(clazz, args).instance()));
    }

    protected static <T extends SingleInstance<?>> Class<T> _getGenericClass() throws ClassNotFoundException {
        return (Class<T>) getGenericClass();
    }

}
