package utils.base;

import utils.reflections.SingleInstance;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

public class Auth extends SingleInstance<Auth> {

    private final Token token;

    @ConstructorProperties({"token"})
    public Auth(Token token) {
        this.token = token;
    }

    public static Auth instance() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return (Auth) SingleInstance.instance();
    }

    public static Auth instance(Token token) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return (Auth) SingleInstance.instance(token);
    }

    public static void refreshTokens(Token token) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        instance().token.refreshTokens(token);
    }

    public static String getToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().token.getToken();
    }

    public static String getRefreshToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().token.getRefreshToken();
    }

    public static String getFileToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().token.getFileToken();
    }

}
