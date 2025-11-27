package org.project.utils.function;

@FunctionalInterface
public interface ConsumerWithExceptions<T, E extends Exception> {
    void accept(T t) throws E;
}
