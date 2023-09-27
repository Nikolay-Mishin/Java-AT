package core.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;

import static java.lang.System.out;

public class Instance<T> {

    public T instance;
    protected final Map<Class<T>, T> registry = new HashMap<>();

    public Instance(Class<T> t) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        this.instance = newInstance(t);
        this.register(t, this.instance);
    }

    public final T newInstance(Class<T> t) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return t.getConstructor().newInstance();
    }

    public void register(Class<T> t, T instance) {
        registry.put(t, instance);
    }

    public T getInstance(Class<T> t) {
        out.println(registry);
        return registry.get(t);
    }

    public T instantiate(Supplier<? extends T> supplier) {
        return supplier.get();
    }

}
