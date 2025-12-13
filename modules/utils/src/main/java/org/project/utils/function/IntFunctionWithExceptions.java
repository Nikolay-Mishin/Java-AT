package org.project.utils.function;

/**
 *
 * @param <R>
 * @param <E>
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
