package org.project.utils.function;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

@FunctionalInterface
public interface FunctionNoArgsWithExceptions<T, R, E extends Exception> {
    R apply() throws E;

    default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
        requireNonNull(before);
        return (V v) -> {
            try {
                //return apply(before.apply(v));
                return (R) before.apply(v);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
        requireNonNull(after);
        return (T t) -> {
            try {
                return after.apply(apply());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    static <T> Function<T, T> identity() {
        return t -> t;
    }
}
