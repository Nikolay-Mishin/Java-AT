package org.project.utils.factory;

import org.project.utils.reflection.Instance;

import java.lang.reflect.InvocationTargetException;

public class ModelFactory<T> extends Instance<T> {

    public ModelFactory(Class<T> clazz, Object... args)
        throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        create(clazz, args);
    }

}
