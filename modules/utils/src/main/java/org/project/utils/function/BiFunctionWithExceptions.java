package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @param <T>
 * @param <U>
 * @param <R>
 * @param <E>
 */
@FunctionalInterface
public interface BiFunctionWithExceptions<T, U, R, E extends Exception> extends Lambda {
    /**
     *
     * @param t T
     * @param u U
     * @return R
     * @throws E throws
     */
    R apply(T t, U u) throws E;

    /**
     *
     * @param after Function
     * @return BiFunction
     * @param <V> V
     */
    default <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> after) {
        requireNonNull(after);
        return (T t, U u) -> {
            try {
                return after.apply(apply(t, u));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
