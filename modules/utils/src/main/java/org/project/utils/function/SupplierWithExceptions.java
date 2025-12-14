package org.project.utils.function;

/**
 *
 * @param <T> T
 * @param <E> E
 */
@FunctionalInterface
public interface SupplierWithExceptions<T, E extends Exception> {
    /**
     *
     * @return T
     * @throws E throws
     */
    T get() throws E;
}
