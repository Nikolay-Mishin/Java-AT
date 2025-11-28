package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import org.apache.commons.lang3.function.TriFunction;

@FunctionalInterface
public interface TriFunctionWithExceptions<T, U, V, R, E extends Exception> {
    R apply(T t, U u, V v) throws E;

    default <W> TriFunction<T, U, V, W> andThen(Function<? super R, ? extends W> after) {
        requireNonNull(after);
        return (t, u, v) -> {
            try {
                return after.apply(apply(t, u, v));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
