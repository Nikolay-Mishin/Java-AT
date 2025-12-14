package org.project.utils.function;

/**
 *
 * @param <E> E
 */
@FunctionalInterface
public interface IntSupplierWithExceptions<E extends Exception> {
    /**
     *
     * @return int
     * @throws E throws
     */
    int getAsInt() throws E;
}
