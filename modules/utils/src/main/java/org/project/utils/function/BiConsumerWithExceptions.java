package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface BiConsumerWithExceptions<T, U, E extends Exception> {
    void accept(T t, U u) throws E;

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
