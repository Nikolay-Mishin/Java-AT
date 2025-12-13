package org.project.utils.function;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * @param <T>
 * @param <E>
 */
@FunctionalInterface
public interface PredicateWithExceptions<T, E extends Exception> {
    /**
     *
     * @param t T
     * @return boolean
     * @throws E throws
     */
    boolean test(T t) throws E;

    /**
     *
     * @param other Predicate
     * @return Predicate
     */
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

    /**
     *
     * @return Predicate
     */
    default Predicate<T> negate() {
        return (t) -> {
            try {
                return !test(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     *
     * @param other Predicate
     * @return Predicate
     */
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

    /**
     *
     * @param targetRef Object
     * @return Predicate
     * @param <T> T
     */
    static <T> Predicate<T> isEqual(Object targetRef) {
        return targetRef == null ? Objects::isNull : targetRef::equals;
    }

    /**
     *
     * @param target Predicate
     * @return Predicate
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    static <T> Predicate<T> not(Predicate<? super T> target) {
        requireNonNull(target);
        return (Predicate<T>)target.negate();
    }
}
