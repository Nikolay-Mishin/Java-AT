package org.project.utils.function;

public interface TriFunctionWithExceptions<T, U, V, R, E extends Exception> {
    R apply(T t, U u, V v) throws E;
}
