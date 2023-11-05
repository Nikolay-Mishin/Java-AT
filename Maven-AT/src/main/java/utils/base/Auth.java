package utils.base;

import io.restassured.response.Response;
import utils.fs.JsonSchema;
import utils.reflections.SingleInstance;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import static java.lang.System.out;

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

    private static Token token() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().token;
    }

    public static void refreshTokens(Response tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        token().refreshTokens(tokens);
    }

    public static void refreshTokens(JsonSchema tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        token().refreshTokens(tokens);
    }

    public static String getToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return token().getToken("access");
    }

    public static String getRefreshToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return token().getToken("refresh");
    }

    public static String getFileToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return token().getToken("file");
    }

    public static void printTokens() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        token().printTokens();
        out.println(getToken());
        out.println(getRefreshToken());
        out.println(getFileToken());
    }

}
