package org.project.utils.function;

@FunctionalInterface
public interface RunnableWithExceptions<E extends Exception> {
    void run() throws E;
}
