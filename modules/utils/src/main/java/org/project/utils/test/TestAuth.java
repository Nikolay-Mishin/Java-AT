package org.project.utils.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.debug;
import static org.project.utils.auth.Auth.*;

import org.project.utils.reflection.SingleInstance;

public class TestAuth {

    public static void main(String[] args)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        testAuth();
    }

    public static void testAuth()
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException
    {
        new Step();
        init("auth/token");
        debug("instance: " + instance());
        debug("Auth: " + auth());
        debug("SingleInstance: " + SingleInstance.instance());
        printTokens();
    }

}
