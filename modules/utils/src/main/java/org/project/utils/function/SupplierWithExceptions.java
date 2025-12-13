package org.project.utils.function;

/**
 *
 * @param <T>
 * @param <E>
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
