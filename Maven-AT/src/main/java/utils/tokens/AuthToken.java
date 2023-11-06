package utils.tokens;

import io.restassured.response.Response;
import utils.base.Token;
import utils.fs.JsonSchema;

import java.lang.reflect.InvocationTargetException;

import static java.lang.System.out;

public class AuthToken extends Token {

    public AuthToken(Response tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        super(tokens);
    }

    public AuthToken(JsonSchema tokens) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        super(tokens);
    }

    public String getAccessToken() {
        return getToken("access");
    }

    public String getRefreshToken() {
        return getToken("refresh");
    }

    public String getFileToken() {
        return getToken("file");
    }

    public void printTokens() {
        super.printTokens();
        out.println(getAccessToken());
        out.println(getRefreshToken());
        out.println(getFileToken());
    }

}
