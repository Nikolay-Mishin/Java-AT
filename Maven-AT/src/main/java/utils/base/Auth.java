package utils.base;

import utils.reflections.SingleInstance;

import java.lang.reflect.InvocationTargetException;

public class Auth extends SingleInstance<Auth> {

    public Auth() {
        //Auth.instance();
    }

    public static Auth instance() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return (Auth) SingleInstance.instance();
    }

}
