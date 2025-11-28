package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

@FunctionalInterface
public interface ConsumerVoidWithExceptions<T, E extends Exception> {
    void accept() throws E;

    default Consumer<T> andThen(Consumer<? super T> after) {
        requireNonNull(after);
        return (T t) -> {
            try {
                accept();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            after.accept(t); };
    }
}
