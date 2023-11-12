package utils.reflections;

import utils.Register;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Supplier;

import static java.lang.System.out;
import static utils.reflections.Reflection.newInstance;
import static utils.reflections.ReflectionUtils.getGenericParameterClass;

public class Instance<T> extends Register<Class<T>, T> {

    public static <T> T create(Class<T> clazz, Object... args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        T _instance = newInstance(clazz, args);
        registerMap(Instance.class, clazz, _instance);
        return _instance;
    }

    public static <T> T getInstance(Class<T> clazz) {
        T _instance = getRegister(Instance.class, clazz);
        out.println(Optional.ofNullable(_instance));
        return _instance;
    }

    public static <T> T instantiate(Supplier<? extends T> supplier) {
        return supplier.get();
    }

    public Class<?> getEntityClass() {
        out.println(getClass());
        return getGenericParameterClass(getClass(), Instance.class, 0);
    }

}
