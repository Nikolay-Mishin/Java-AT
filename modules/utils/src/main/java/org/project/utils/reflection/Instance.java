package org.project.utils.reflection;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static org.project.utils.Helper.debug;
import static org.project.utils.reflection.ReflectionUtils.getGenericParameterClass;

import org.project.utils.base.Register;
import org.project.utils.function.SupplierWithExceptions;

/**
 *
 * @param <T> T
 */
public class Instance<T> extends Register<Class<T>, T> {

    /**
     *
     * @param clazz Class T
     * @param args Object[]
     * @return T
     * @param <T> T
     */
    public static <T> T create(Class<T> clazz, Object... args) {
        return create(clazz, () -> Reflection.instance(clazz, args));
    }

    /**
     *
     * @param clazz Class T
     * @param args Map {Class, Object}
     * @return T
     * @param <T> T
     */
    public static <T> T create(Class<T> clazz, Map<Class<?>, Object> args) {
        return create(clazz, () -> Reflection.instance(clazz, args));
    }

    /**
     *
     * @param clazz Class T
     * @param cb Supplier {T}
     * @return T
     * @param <T> T
     * @param <E> extends ReflectiveOperationException
     */
    public static <T, E extends ReflectiveOperationException> T create(Class<T> clazz, SupplierWithExceptions<T, E> cb) {
        try {
            T _instance = cb.get();
            registerMap(Instance.class, clazz, _instance);
            return _instance;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
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
