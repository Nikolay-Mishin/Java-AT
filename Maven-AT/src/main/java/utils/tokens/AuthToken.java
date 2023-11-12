package utils.tokens;

import io.restassured.response.Response;
import utils.base.Token;
import utils.fs.JsonSchema;

import java.lang.reflect.InvocationTargetException;

public class AuthToken extends Token {

    public AuthToken(Response tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        super(tokens);
    }

    public AuthToken(JsonSchema tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        super(tokens);
    }

    public Token getAccessToken() {
        return getToken("access");
    }

    public Token getRefreshToken() {
        return getToken("refresh");
    }

    public Token getFileToken() {
        return getToken("file");
    }

    public void printTokens() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        super.printTokens();
        getAccessToken().print();
        getRefreshToken().print();
        getFileToken().print();
    }

}
