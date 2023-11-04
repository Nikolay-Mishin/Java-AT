package utils.reflections;

import java.lang.reflect.InvocationTargetException;

import static utils.Helper.notNull;
import static utils.reflections.Reflection.getGenericClass;

public class SingleInstance<T extends SingleInstance<?>> {

    protected static SingleInstance<? extends SingleInstance<?>> instance;

    public static SingleInstance<? extends SingleInstance<?>> instance()
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return _instance(_getGenericClass());
    }

    public static SingleInstance<? extends SingleInstance<?>> instance(Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        return _instance(_getGenericClass(), args);
    }

    public static SingleInstance<? extends SingleInstance<?>> instance(Class<? extends SingleInstance<?>> clazz)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return _instance(clazz);
    }

    public static SingleInstance<? extends SingleInstance<?>> instance(Class<? extends SingleInstance<?>> clazz, Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return _instance(clazz, args);
    }

    protected static SingleInstance<? extends SingleInstance<?>> _instance(Class<? extends SingleInstance<?>> clazz, Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return notNull(instance) ? instance : new Instance<>(clazz, args).instance();
    }

    protected static Class<? extends SingleInstance<?>> _getGenericClass() throws ClassNotFoundException {
        return (Class<? extends SingleInstance<?>>) getGenericClass();
    }

}
