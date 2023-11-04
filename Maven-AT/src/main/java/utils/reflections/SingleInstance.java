package utils.reflections;

import java.lang.reflect.InvocationTargetException;

import static java.lang.System.out;
import static utils.Helper.isInstance;
import static utils.Helper.notNull;
import static utils.reflections.Reflection.getCallingClass;
import static utils.reflections.ReflectionUtils.getGenericParameterClass;

public class SingleInstance<T extends SingleInstance<?>> {

    protected static SingleInstance<? extends SingleInstance<?>> instance;

    public static SingleInstance<? extends SingleInstance<?>> instance() throws ClassNotFoundException {
        Class<?> clazz = getCallingClass();
        Class<?> genericClass = SingleInstance.class;
        Class<?> actualClass = isInstance(clazz, genericClass) ? clazz : getCallingClass(1);
        out.println(getGenericParameterClass(actualClass, genericClass, 0));
        return instance;
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

}
