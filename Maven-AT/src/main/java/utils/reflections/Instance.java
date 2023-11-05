package utils.reflections;

import utils.Register;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import static java.lang.System.out;
import static utils.reflections.Reflection.newInstance;
import static utils.reflections.ReflectionUtils.getGenericParameterClass;

public class Instance<T> extends Register<Class<T>, T> {

    protected T instance;

    public Instance(Class<T> t, Object... args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        register(t, instance = _newInstance(t, args));
    }

    public T instance() {
        return instance;
    }

    protected T _newInstance(Class<T> t, Object... args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return newInstance(t, args);
    }

    public T getInstance(Class<T> t) {
        return getRegister(t);
    }

    public T instantiate(Supplier<? extends T> supplier) {
        return supplier.get();
    }

    public Class<?> getEntityClass() {
        out.println(getClass());
        return getGenericParameterClass(getClass(), Instance.class, 0);
    }

}
