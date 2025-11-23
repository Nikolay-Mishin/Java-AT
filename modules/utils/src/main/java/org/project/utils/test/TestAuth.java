package org.project.utils.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static org.project.utils.Helper.debug;
import static org.project.utils.auth.Auth.init;
import static org.project.utils.fs.Attributes.printAttrs;
import static org.project.utils.fs.File.delete;
import static org.project.utils.fs.File.pathStr;

public class TestAuth extends App {

    public static void main(String[] args)
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException
    {
        testAuth();
    }

    public static void testAuth()
        throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException
    {
        init("auth", "token");
    }

}
