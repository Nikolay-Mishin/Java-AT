package org.project.utils.function;

/**
 *
 * @param <E> E
 */
@FunctionalInterface
public interface RunnableWithExceptions<E extends Exception> {
    /**
     *
     * @throws E throws
     */
    void run() throws E;
}
