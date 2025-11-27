package org.project.utils.function;

@FunctionalInterface
public interface BiFunctionWithExceptions<T, U, R, E extends Exception> {
    R apply(T t, U u) throws E;
}
