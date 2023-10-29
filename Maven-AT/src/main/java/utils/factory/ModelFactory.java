package utils.factory;

import utils.Instance;

import java.lang.reflect.InvocationTargetException;

public class ModelFactory<T> extends Instance<T> {

    public ModelFactory(Class<T> t) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(t);
    }

}
