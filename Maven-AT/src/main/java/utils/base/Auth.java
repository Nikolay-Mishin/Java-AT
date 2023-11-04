package utils.base;

import utils.reflections.SingleInstance;

import java.lang.reflect.InvocationTargetException;

public class Auth extends SingleInstance<Auth> {

    private String token;
    private String fileToken;
    private String refreshToken;

    public Auth(String token, String fileToken, String refreshToken) {
        this.token = token;
        this.fileToken = fileToken;
        this.refreshToken = refreshToken;
    }

    public static Auth instance() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return (Auth) SingleInstance.instance();
    }

}
