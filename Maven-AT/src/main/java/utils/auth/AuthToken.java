package utils.auth;

import io.restassured.response.Response;
import utils.json.JsonSchema;

import java.lang.reflect.InvocationTargetException;

public class AuthToken extends Token {

    public AuthToken(Response tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        super(tokens);
    }

    public AuthToken(JsonSchema tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        super(tokens);
    }

    public Token getAccessToken() throws ClassNotFoundException {
        return getToken("access");
    }

    public Token getRefreshToken() throws ClassNotFoundException {
        return getToken("refresh");
    }

    public Token getFileToken() throws ClassNotFoundException {
        return getToken("file");
    }

    public void printTokens() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        super.printTokens();
        getAccessToken().print();
        getRefreshToken().print();
        getFileToken().print();
    }

}
