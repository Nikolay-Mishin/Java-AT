package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.IntPredicate;

@FunctionalInterface
public interface IntPredicateWithExceptions<E extends Exception> {
    boolean test(int value) throws E;

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

    default IntPredicate negate() {
        return (value) -> {
            try {
                return !test(value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

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
