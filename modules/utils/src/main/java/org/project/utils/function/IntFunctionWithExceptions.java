package org.project.utils.function;

/**
 *
 * @param <R> R
 * @param <E> E
 */
@FunctionalInterface
public interface IntFunctionWithExceptions<R, E extends Exception> {
    /**
     *
     * @param value int
     * @return R
     * @throws E throws
     */
    R apply(int value) throws E;
}
