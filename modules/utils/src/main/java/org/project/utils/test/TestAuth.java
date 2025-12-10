package org.project.utils.test;

import java.util.Arrays;

import static org.project.utils.Helper.debug;
import static org.project.utils.auth.Auth.auth;
import static org.project.utils.auth.Auth.init;
import static org.project.utils.auth.Auth.instance;
import static org.project.utils.auth.Auth.printTokens;
import static org.project.utils.auth.Auth.req;
import static org.project.utils.auth.Auth.token;
import static org.project.utils.base.Register.printRegisterMap;

import org.project.utils.reflection.SingleInstance;

public class TestAuth extends TestApi {
    protected static String auth;
    protected static String authTest;

    public TestAuth() {
        auth = c.getAuth();
        authTest = c.getAuthTest();
    }

    public static void main(String[] args) throws Exception {
        testAuth();
    }

    public static void testAuth() throws Exception {
        testAuth(auth);
        req().uri(uri);
        testAuth(authTest);

        printTokens();
        printRegisterMap();

        debug("token: " + token());
        debug("accessToken: " + token().accessToken());
        debug("value: " + token().getAccessToken());
    }

    public static void testAuth(Object... pathList) throws Exception {
        debug("testAuth: " + Arrays.toString(pathList));
        init(pathList);
        debug("instance: " + instance());
        debug("SingleInstance: " + SingleInstance.instance());
        debug("req: " + req());
        debug("auth: " + auth());
        debug("authUrl: " + auth().url());
    }

}
