package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

/**
 *
 * @param <T>
 * @param <R>
 * @param <E>
 */
@FunctionalInterface
public interface FunctionWithExceptions<T, R, E extends Exception> {
    /**
     *
     * @param t T
     * @return R
     * @throws E throws
     */
    R apply(T t) throws E;

    /**
     *
     * @param before Function
     * @return Function
     * @param <V> V
     */
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

    /**
     *
     * @param after Function
     * @return Function
     * @param <V> V
     */
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

    /**
     *
     * @param after FunctionWithExceptions
     * @return FunctionWithExceptions
     * @param <V> V
     */
    default <V> FunctionWithExceptions<T, V, E> andThen(FunctionWithExceptions<? super R, ? extends V, E> after) {
        requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    /**
     *
     * @return Function
     */
    default Function<T, R> andThen() {
        return (T t) -> {
            try {
                return apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     *
     * @return Function
     * @param <T> T
     */
    static <T> Function<T, T> identity() {
        return t -> t;
    }
}
