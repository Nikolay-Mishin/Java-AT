package org.project.utils.reflection;

import org.project.utils.base.Register;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Supplier;

import static org.project.utils.Helper.debug;
import static org.project.utils.reflection.ReflectionUtils.getGenericParameterClass;

public class Instance<T> extends Register<Class<T>, T> {

    public static <T> T create(Class<T> clazz, Object... args)
        throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        T _instance = Reflection.instance(clazz, args);
        registerMap(Instance.class, clazz, _instance);
        return _instance;
    }

    public static <T> T instance(Class<T> clazz) throws ClassNotFoundException {
        T _instance = register(Instance.class, clazz);
        debug("instance");
        debug(Optional.ofNullable(_instance));
        T _instance2 = getRegister(clazz);
        debug(Optional.ofNullable(_instance2));
        return _instance;
    }

    public static <T> T instantiate(Supplier<? extends T> supplier) {
        return supplier.get();
    }

    public Class<?> entityClass() {
        debug(getClass());
        return getGenericParameterClass(getClass(), Instance.class, 0);
    }

}
