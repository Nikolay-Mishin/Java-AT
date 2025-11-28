package org.project.utils.function;

@FunctionalInterface
public interface IntSupplierWithExceptions<E extends Exception> {
    int getAsInt() throws E;
}
