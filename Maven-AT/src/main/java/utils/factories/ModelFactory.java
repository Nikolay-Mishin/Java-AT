package utils.factories;

import utils.reflections.Instance;

import java.lang.reflect.InvocationTargetException;

public class ModelFactory<T> extends Instance<T> {

    public ModelFactory(Class<T> t, Object... args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(t, args);
    }

}
