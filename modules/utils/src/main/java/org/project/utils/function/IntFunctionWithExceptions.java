package org.project.utils.function;

@FunctionalInterface
public interface IntFunctionWithExceptions<R, E extends Exception> {
    R apply(int value) throws E;
}
