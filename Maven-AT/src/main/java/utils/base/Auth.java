package utils.base;

import utils.reflections.SingleInstance;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

public class Auth extends SingleInstance<Auth> {

    private Token token;

    @ConstructorProperties({"token", "fileToken", "refreshToken"})
    public Auth(Token token) {
        this.token = token;
    }

    public static void refreshTokens(Token token) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        instance().token.refreshTokens(token);
    }

    public static Auth instance() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return (Auth) SingleInstance.instance();
    }

    public static String getToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().token.getToken();
    }

    public static String getFileToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().token.getFileToken();
    }

    public static String getRefreshToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().token.getRefreshToken();
    }

}
