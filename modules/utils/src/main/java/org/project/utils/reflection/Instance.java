package org.project.utils.reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Supplier;

import static org.project.utils.Helper.debug;
import static org.project.utils.reflection.ReflectionUtils.getGenericParameterClass;

import org.project.utils.base.Register;

/**
 *
 * @param <T>
 */
public class Instance<T> extends Register<Class<T>, T> {

    /**
     *
     * @param clazz Class T
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     * @throws InvocationTargetException throws
     * @throws ClassNotFoundException throws
     */
    public static <T> T create(Class<T> clazz, Object... args)
        throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        T _instance = Reflection.instance(clazz, args);
        registerMap(Instance.class, clazz, _instance);
        return _instance;
    }

    /**
     *
     * @param clazz Class T
     * @return T
     * @param <T> T
     * @throws ClassNotFoundException throws
     */
    public static <T> T instance(Class<T> clazz) throws ClassNotFoundException {
        T _instance = register(Instance.class, clazz);
        debug("instance");
        debug(Optional.ofNullable(_instance));
        T _instance2 = getRegister(clazz);
        debug(Optional.ofNullable(_instance2));
        return _instance;
    }

    /**
     *
     * @param supplier Supplier
     * @return T
     * @param <T> T
     */
    public static <T> T instantiate(Supplier<? extends T> supplier) {
        return supplier.get();
    }

    /**
     *
     * @return Class
     */
    public Class<?> entityClass() {
        debug(getClass());
        return getGenericParameterClass(getClass(), Instance.class, 0);
    }

}
