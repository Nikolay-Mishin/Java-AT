package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.BiConsumer;

/**
 *
 * @param <T> T
 * @param <U> U
 * @param <E> E
 */
@FunctionalInterface
public interface BiConsumerWithExceptions<T, U, E extends Exception> {
    /**
     *
     * @param t T
     * @param u U
     * @throws E throws
     */
    void accept(T t, U u) throws E;

    /**
     *
     * @param after BiConsumer
     * @return BiConsumer
     */
    default BiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> after) {
        requireNonNull(after);
        return (l, r) -> {
            try {
                accept(l, r);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            after.accept(l, r);
        };
    }
}
