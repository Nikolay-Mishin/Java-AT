package utils.reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;

import static java.lang.System.out;
import static utils.reflections.Reflection.newInstance;

public class Instance<T> {

    protected T instance;
    protected final Map<Class<T>, T> registry = new HashMap<>();

    public Instance(Class<T> t, Object... args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        this.instance = _newInstance(t, args);
        this._register(t, this.instance);
    }

    public Instance() {}

    public T instance() {
        return instance;
    }

    private T _newInstance(Class<T> t, Object... args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return newInstance(t, args);
    }

    private void _register(Class<T> t, T instance) {
        registry.put(t, instance);
    }

    public T getInstance(Class<T> t) {
        out.println(registry);
        return registry.get(t);
    }

    public T instantiate(Supplier<? extends T> supplier) {
        return supplier.get();
    }

    public Class<?> getEntityClass() {
        out.println(this.getClass());
        out.println(Instance.class);
        return ReflectionUtils.getGenericParameterClass(this.getClass(), Instance.class, 0);
    }

}
