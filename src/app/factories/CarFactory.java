package app.factories;

import core.utils.Instance;

import java.lang.reflect.InvocationTargetException;

public class CarFactory<T> extends Instance<T> {

    public CarFactory(Class<T> t) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(t);
    }

}
