package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

@FunctionalInterface
public interface FunctionWithExceptions<T, R, E extends Exception> {
    R apply(T t) throws E;

    default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
        requireNonNull(before);
        return (V v) -> {
            try {
                return apply(before.apply(v));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
        requireNonNull(after);
        return (T t) -> {
            try {
                return after.apply(apply(t));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    default <V> FunctionWithExceptions<T, V, E> andThen(FunctionWithExceptions<? super R, ? extends V, E> after) {
        requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    default Function<T, R> andThen() {
        return (T t) -> {
            try {
                return apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    static <T> Function<T, T> identity() {
        return t -> t;
    }
}
