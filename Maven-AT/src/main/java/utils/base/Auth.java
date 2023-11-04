package utils.base;

import utils.reflections.SingleInstance;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

public class Auth extends SingleInstance<Auth> {

    private String token;
    private String fileToken;
    private String refreshToken;

    @ConstructorProperties({"token", "fileToken", "refreshToken"})
    public Auth(String token, String fileToken, String refreshToken) {
        this.token = token;
        this.fileToken = fileToken;
        this.refreshToken = refreshToken;
    }

    public static Auth instance() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return (Auth) SingleInstance.instance();
    }

    public static String getToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().token;
    }

    public static String getFileToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().fileToken;
    }

    public static String getRefreshToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().refreshToken;
    }

    public static String setToken(String token) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().token = token;
    }

    public static String setFileToken(String fileToken) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().fileToken = fileToken;
    }

    public static String setRefreshToken(String refreshToken) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().refreshToken = refreshToken;
    }

}
