package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.IntConsumer;

@FunctionalInterface
public interface IntConsumerWithExceptions<E extends Exception> {
    void accept(int value) throws E;

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
