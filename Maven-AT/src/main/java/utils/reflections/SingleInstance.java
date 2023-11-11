package utils.reflections;

import java.lang.reflect.InvocationTargetException;

import static utils.Helper.notNull;
import static utils.reflections.Reflection.getGenericClass;

public class SingleInstance<T extends SingleInstance<?>> {

    protected static SingleInstance<? extends SingleInstance<?>> instance;

    public static <T extends SingleInstance<?>> T instance(Object... args)
        throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return _instance(getGenericClass(), args);
    }

    public static <T extends SingleInstance<?>> T instance(Class<T> clazz, Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        return _instance(clazz, args);
    }

    @SuppressWarnings("unchecked")
    protected static <T extends SingleInstance<?>> T _instance(Class<T> clazz, Object... args)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        T _instance = (T) (notNull(instance) ? instance : (instance = new Instance<>(clazz, args).instance()));
        Instance.instance(clazz);
        return _instance;
    }

}
