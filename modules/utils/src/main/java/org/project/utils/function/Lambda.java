package org.project.utils.function;

import java.io.Serializable;
import java.lang.reflect.Method;

import static org.project.utils.reflection.Reflection.getClazz;
import static org.project.utils.reflection.Reflection.lambdaMethod;
import static org.project.utils.reflection.Reflection.lambdaMethodName;

/**
 *
 */
public interface Lambda extends Serializable {
    /**
     *
     * @return Class
     * @throws ReflectiveOperationException throws
     */
    default Class<?> clazz() throws ReflectiveOperationException {
        return getClazz(method());
    }

    /**
     *
     * @return Method
     * @throws ReflectiveOperationException throws
     */
    default Method method() throws ReflectiveOperationException {
        return lambdaMethod(this);
    }

    /**
     *
     * @return String
     * @throws NoSuchMethodException throws
     */
    default String methodName() throws NoSuchMethodException {
        return lambdaMethodName(this);
    }

    /**
     * Invoke method of lambda expression.
     * <p>Получение и вызов метода с аргументами.
     * @param args Object[]
     * @return T
     * @param <T> T
     * @throws ReflectiveOperationException throws
     */
    @SuppressWarnings("unchecked")
    default <T> T invoke(Object... args) throws ReflectiveOperationException {
        return (T) method().invoke(this, args); // получение и вызов метода с аргументами
    }
}
