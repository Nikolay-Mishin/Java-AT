package org.project.utils.function;

@FunctionalInterface
public interface FunctionWithExceptions<T, R, E extends Exception> {
    R apply(T t) throws E;
}
