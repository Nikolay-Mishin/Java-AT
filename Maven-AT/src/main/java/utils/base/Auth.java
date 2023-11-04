package utils.base;

import utils.reflections.SingleInstance;

public class Auth extends SingleInstance<Auth> {

    public Auth() {
        //Auth.instance();
    }

    public static Auth instance() throws ClassNotFoundException {
        return (Auth) SingleInstance.instance();
    }

}
