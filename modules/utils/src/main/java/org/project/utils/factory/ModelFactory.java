package org.project.utils.factory;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import org.project.utils.reflection.Instance;

/**
 *
 * @param <T>
 */
public class ModelFactory<T> extends Instance<T> {
    /**
     *
     * @param clazz Class T
     * @param args Object[]
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     * @throws InvocationTargetException throws
     * @throws ClassNotFoundException throws
     */
    @ConstructorProperties({"clazz", "args"})
    public ModelFactory(Class<T> clazz, Object... args)
        throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        create(clazz, args);
    }
}
