package org.project.utils.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import static org.project.utils.Helper.debug;
import static org.project.utils.auth.Auth.*;

import org.project.utils.reflection.SingleInstance;

public class TestAuth {

    public static void main(String[] args) throws IOException, URISyntaxException, ReflectiveOperationException {
        testAuth();
    }

    public static void testAuth()
        throws IOException, URISyntaxException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException
    {
        debug("testAuth");
        init("auth/token");
        printTokens();
        debug("instance: " + instance());
        debug("SingleInstance: " + SingleInstance.instance());
        debug("req: " + req());
        debug("auth: " + auth());
        debug("authUrl: " + auth().url());
        init("auth/tokens");
        debug("req: " + req());
        debug("auth: " + auth());
        debug("authUrl: " + auth().url());
    }

}
