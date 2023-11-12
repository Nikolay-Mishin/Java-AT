package utils.base;

import io.restassured.response.Response;
import utils.fs.JsonSchema;
import utils.reflections.SingleInstance;
import utils.tokens.AuthToken;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import static java.lang.System.out;

public class Auth extends SingleInstance<Auth> {

    protected final AuthToken token;

    @ConstructorProperties({"token"})
    public Auth(AuthToken token) {
        this.token = token;
    }

    public static Auth instance() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return SingleInstance.instance();
    }

    public static Auth instance(AuthToken token) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return SingleInstance.instance(token);
    }

    protected static AuthToken token() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return instance().token;
    }

    public static void refreshTokens(Response tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        token().refreshTokens(tokens);
    }

    public static void refreshTokens(JsonSchema tokens) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        token().refreshTokens(tokens);
    }

    public static Token getAccessToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return token().getAccessToken();
    }

    public static Token getRefreshToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return token().getRefreshToken();
    }

    public static Token getFileToken() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return token().getFileToken();
    }

    public static void printTokens() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        token().printTokens();
        out.println(getInstance(Auth.class));
    }

}
