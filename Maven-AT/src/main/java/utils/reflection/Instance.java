package utils.reflection;

import utils.base.Register;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Supplier;

import static java.lang.System.out;
import static utils.reflection.Reflection.newInstance;
import static utils.reflection.ReflectionUtils.getGenericParameterClass;

public class Instance<T> extends Register<Class<T>, T> {

    public static <T> T create(Class<T> clazz, Object... args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        T _instance = newInstance(clazz, args);
        registerMap(Instance.class, clazz, _instance);
        return _instance;
    }

    public static <T> T getInstance(Class<T> clazz) throws ClassNotFoundException {
        T _instance = getRegister(Instance.class, clazz);
        out.println("getInstance");
        out.println(Optional.ofNullable(_instance));
        T _instance2 = getRegister(clazz);
        out.println(Optional.ofNullable(_instance2));
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
