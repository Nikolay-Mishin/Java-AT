package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.IntConsumer;

/**
 *
 * @param <E>
 */
@FunctionalInterface
public interface IntConsumerWithExceptions<E extends Exception> {
    /**
     *
     * @param value int
     * @throws E throws
     */
    void accept(int value) throws E;

    /**
     *
     * @param after IntConsumer
     * @return IntConsumer
     */
    default IntConsumer andThen(IntConsumer after) {
        requireNonNull(after);
        return (int t) -> {
            try {
                accept(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            after.accept(t); };
    }
}
