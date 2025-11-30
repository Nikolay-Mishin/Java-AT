package org.project.utils.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.project.utils.Helper.debug;
import static org.project.utils.auth.Auth.*;

import org.project.utils.reflection.SingleInstance;

public class TestAuth {

    public static void main(String[] args) throws IOException, URISyntaxException, ReflectiveOperationException {
        testAuth();
    }

    public static void testAuth() throws IOException, URISyntaxException, ReflectiveOperationException {
        testAuth("auth/token");
        req().uri("https://googlechromelabs.github.io/");
        testAuth("auth/tokens");
    }

    public static void testAuth(Object... pathList) throws IOException, URISyntaxException, ReflectiveOperationException {
        debug("testAuth: " + Arrays.toString(pathList));
        init(pathList);
        printTokens();
        debug("instance: " + instance());
        debug("SingleInstance: " + SingleInstance.instance());
        debug("req: " + req());
        debug("auth: " + auth());
        debug("authUrl: " + auth().url());
    }

}
