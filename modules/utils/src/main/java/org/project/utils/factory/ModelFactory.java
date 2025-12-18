package org.project.utils.factory;

import java.beans.ConstructorProperties;

import org.project.utils.reflection.Instance;

/**
 *
 * @param <T> T
 */
public class ModelFactory<T> extends Instance<T> {
    /**
     *
     * @param clazz Class T
     * @param args Object[]
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"clazz", "args"})
    public ModelFactory(Class<T> clazz, Object... args) throws ReflectiveOperationException {
        create(clazz, args);
    }
}
