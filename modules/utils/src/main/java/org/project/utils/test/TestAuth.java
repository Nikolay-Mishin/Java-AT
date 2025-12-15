package org.project.utils.test;

import java.util.Arrays;

import static org.project.utils.Helper.debug;
import static org.project.utils.auth.Auth.auth;
import static org.project.utils.auth.Auth.instance;
import static org.project.utils.auth.Auth.printTokens;
import static org.project.utils.auth.Auth.token;
import static org.project.utils.base.Register.printRegisterMap;
import static org.project.utils.constant.RequestConstants.METHOD.POST;

import org.project.utils.auth.Auth;
import org.project.utils.reflection.SingleInstance;

/**
 *
 */
public class TestAuth extends TestReq {
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
     */
    public static void init() {
        debug("TestAuth:init");
        TestReq.init();
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
        debug("accessToken: " + token().accessToken());
        debug("value: " + token().getAccessToken());
    }

    /**
     *
     * @param pathList Object[]
     * @throws Exception throws
     */
    public static void testAuth(Object... pathList) throws Exception {
        debug("testAuth: " + Arrays.toString(pathList));
        Auth.init(pathList);
        debug("instance: " + instance());
        debug("SingleInstance: " + SingleInstance.instance());
        debug("req: " + Auth.req());
        debug("auth: " + auth());
        debug("authUrl: " + auth().url());
    }

}
