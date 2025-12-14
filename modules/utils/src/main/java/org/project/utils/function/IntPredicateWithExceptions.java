package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.IntPredicate;

/**
 *
 * @param <E> E
 */
@FunctionalInterface
public interface IntPredicateWithExceptions<E extends Exception> {
    /**
     *
     * @param value int
     * @return boolean
     * @throws E throws
     */
    boolean test(int value) throws E;

    /**
     *
     * @param other IntPredicate
     * @return IntPredicate
     */
    default IntPredicate and(IntPredicate other) {
        requireNonNull(other);
        return (value) -> {
            try {
                return test(value) && other.test(value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     *
     * @return IntPredicate
     */
    default IntPredicate negate() {
        return (value) -> {
            try {
                return !test(value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     *
     * @param other IntPredicate
     * @return IntPredicate
     */
    default IntPredicate or(IntPredicate other) {
        requireNonNull(other);
        return (value) -> {
            try {
                return test(value) || other.test(value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
