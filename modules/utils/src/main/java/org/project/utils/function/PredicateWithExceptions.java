package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface PredicateWithExceptions<T, E extends Exception> {
    boolean test(T t) throws E;

    default Predicate<T> and(Predicate<? super T> other) {
        requireNonNull(other);
        return (t) -> {
            try {
                return test(t) && other.test(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    default Predicate<T> negate() {
        return (t) -> {
            try {
                return !test(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    default Predicate<T> or(Predicate<? super T> other) {
        requireNonNull(other);
        return (t) -> {
            try {
                return test(t) || other.test(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
            ? Objects::isNull
            : object -> targetRef.equals(object);
    }

    @SuppressWarnings("unchecked")
    static <T> Predicate<T> not(Predicate<? super T> target) {
        requireNonNull(target);
        return (Predicate<T>)target.negate();
    }
}
