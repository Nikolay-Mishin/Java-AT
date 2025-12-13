package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

/**
 *
 * @param <T>
 * @param <E>
 */
@FunctionalInterface
public interface ConsumerVoidWithExceptions<T, E extends Exception> {
    /**
     *
     * @throws E throws
     */
    void accept() throws E;

    /**
     *
     * @param after Consumer
     * @return Consumer
     */
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
