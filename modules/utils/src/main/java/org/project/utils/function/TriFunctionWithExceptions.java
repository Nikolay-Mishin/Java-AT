package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import org.apache.commons.lang3.function.TriFunction;

/**
 *
 * @param <T> T
 * @param <U> U
 * @param <V> V
 * @param <R> R
 * @param <E> E
 */
@FunctionalInterface
public interface TriFunctionWithExceptions<T, U, V, R, E extends Exception> extends Lambda {
    /**
     *
     * @param t T
     * @param u U
     * @param v V
     * @return R
     * @throws E throws
     */
    R apply(T t, U u, V v) throws E;

    /**
     *
     * @param after Function
     * @return TriFunction
     * @param <W> W
     */
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
