package org.project.utils.function;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T> T
 * @param <R> R
 * @param <E> E
 */
@FunctionalInterface
public interface FunctionNoArgsWithExceptions<T, R, E extends Exception> {
    /**
     *
     * @return R
     * @throws E throws
     */
    R apply() throws E;

    /**
     *
     * @param before Function
     * @return Function
     * @param <V> V
     */
    @SuppressWarnings("unchecked")
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
                return after.apply(apply());
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
