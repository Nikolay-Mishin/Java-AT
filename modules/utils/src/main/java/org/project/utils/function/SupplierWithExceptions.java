package org.project.utils.function;

@FunctionalInterface
public interface SupplierWithExceptions<T, E extends Exception> {
    T get() throws E;
}
