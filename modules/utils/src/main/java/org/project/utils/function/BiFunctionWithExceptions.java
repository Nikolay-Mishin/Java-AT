package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface BiFunctionWithExceptions<T, U, R, E extends Exception> extends Lambda {
    R apply(T t, U u) throws E;

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
