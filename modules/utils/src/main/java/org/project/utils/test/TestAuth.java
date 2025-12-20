package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.project.utils.Helper.debug;
import static org.project.utils.auth.Auth.accessToken;
import static org.project.utils.auth.Auth.auth;
import static org.project.utils.auth.Auth.getAccessToken;
import static org.project.utils.auth.Auth.getAuth;
import static org.project.utils.auth.Auth.printTokens;
import static org.project.utils.auth.Auth.token;
import static org.project.utils.constant.RequestConstants.METHOD.POST;

import org.project.utils.auth.Auth;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.reflection.SingleInstance;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestAuth<T extends TestBaseConfig> extends TestReq<T> {
    /**
     *
     */
    protected static String auth;
    /**
     *
     */
    protected static String authTest;

    /**
     *
     * @throws NoSuchFieldException throws
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    @ConstructorProperties({})
    public TestAuth() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("TestAuth:init");
        auth = c.getAuth();
        authTest = c.getAuthTest();
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testAuth() throws Exception {
        testAuth(auth);
        Auth.req().uri(POST, uri);
        testAuth(authTest);

        printTokens();
        printRegisterMap();

        debug("token: " + token());
        debug("accessToken: " + accessToken());
        debug("value: " + getAccessToken());
    }

    /**
     *
     * @param pathList Object[]
     * @throws Exception throws
     */
    public static void testAuth(Object... pathList) throws Exception {
        debug("testAuth: " + Arrays.toString(pathList));
        Auth.init(pathList);
        debug("instance: " + Auth.instance());
        debug("SingleInstance: " + SingleInstance.instance());
        debug("req: " + Auth.req());
        debug("auth: " + getAuth());
        debug("authUrl: " + getAuth().url());
    }

}
