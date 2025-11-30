package org.project.utils.function;

import jdk.jfr.Description;

import java.io.Serializable;
import java.lang.reflect.Method;

import static org.project.utils.reflection.Reflection.*;

public interface Lambda extends Serializable {
    default Class<?> clazz() throws ReflectiveOperationException {
        return getClazz(method());
    }

    default Method method() throws ReflectiveOperationException {
        return lambdaMethod(this);
    }

    default String methodName() throws NoSuchMethodException {
        return lambdaMethodName(this);
    }

    @Description("Invoke method of lambda expression")
    @SuppressWarnings("unchecked")
    default <T> T invoke(Object... args) throws ReflectiveOperationException {
        return (T) method().invoke(this, args); // получение и вызов метода с аргументами
    }
}
