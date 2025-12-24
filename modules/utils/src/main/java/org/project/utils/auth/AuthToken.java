package org.project.utils.auth;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import io.restassured.response.Response;

import org.project.utils.json.JsonSchema;

import static org.project.utils.Helper.debug;
import static org.project.utils.auth.Auth.printTokens;

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
     * @return Token
     * @throws ClassNotFoundException throws
     */
    public Token accessToken() throws ClassNotFoundException {
        debug("accessToken");
        try {
            printTokens();
            printRegisterMap();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
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
     * @return String
     * @throws ClassNotFoundException throws
     */
    public String getAccessToken() throws ClassNotFoundException {
        debug("getAccessToken: " + accessToken());
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
