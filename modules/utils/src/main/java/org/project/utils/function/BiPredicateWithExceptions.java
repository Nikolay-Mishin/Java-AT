package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.function.BiPredicate;

@FunctionalInterface
public interface BiPredicateWithExceptions<T, U, E extends Exception> {
    boolean test(T t, U u) throws E;

    default BiPredicate<T, U> and(BiPredicate<? super T, ? super U> other) {
        requireNonNull(other);
        return (T t, U u) -> {
            try {
                return test(t, u) && other.test(t, u);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    default BiPredicate<T, U> negate() {
        return (T t, U u) -> {
            try {
                return !test(t, u);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    default BiPredicate<T, U> or(BiPredicate<? super T, ? super U> other) {
        requireNonNull(other);
        return (T t, U u) -> {
            try {
                return test(t, u) || other.test(t, u);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
