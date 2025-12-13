package org.project.utils.auth;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import io.restassured.response.Response;

import org.project.utils.json.JsonSchema;

/**
 *
 */
public class AuthToken extends Token {

    /**
     *
     * @param tokens Response
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"tokens"})
    public AuthToken(Response tokens) throws ReflectiveOperationException {
        super(tokens);
    }

    /**
     *
     * @param tokens JsonSchema
     * @throws ReflectiveOperationException throws
     */
    @ConstructorProperties({"tokens"})
    public AuthToken(JsonSchema tokens) throws ReflectiveOperationException {
        super(tokens);
    }

    /**
     *
     * @param pathList Object[]
     * @throws ReflectiveOperationException throws
     * @throws IOException throws
     */
    @ConstructorProperties({"pathList"})
    public AuthToken(Object... pathList) throws ReflectiveOperationException, IOException {
        super(pathList);
    }

    /**
     *
     * @return String
     * @throws ClassNotFoundException throws
     */
    public String getAccessToken() throws ClassNotFoundException {
        return accessToken().value;
    }

    /**
     *
     * @return String
     * @throws ClassNotFoundException throws
     */
    public String getRefreshToken() throws ClassNotFoundException {
        return refreshToken().value;
    }

    /**
     *
     * @return String
     * @throws ClassNotFoundException throws
     */
    public String getFileToken() throws ClassNotFoundException {
        return fileToken().value;
    }

    /**
     *
     * @return Token
     * @throws ClassNotFoundException throws
     */
    public Token accessToken() throws ClassNotFoundException {
        return token("access");
    }

    /**
     *
     * @return Token
     * @throws ClassNotFoundException throws
     */
    public Token refreshToken() throws ClassNotFoundException {
        return token("refresh");
    }

    /**
     *
     * @return Token
     * @throws ClassNotFoundException throws
     */
    public Token fileToken() throws ClassNotFoundException {
        return token("file");
    }

    /**
     *
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws IllegalAccessException throws
     * @throws ClassNotFoundException throws
     */
    public void printTokens() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        super.printTokens();
        accessToken().print();
        refreshToken().print();
        fileToken().print();
    }

}
