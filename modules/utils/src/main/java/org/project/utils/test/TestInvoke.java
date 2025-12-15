package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.table;
import static org.project.utils.config.Config.configs;
import static org.project.utils.reflection.Reflection.getClazz;
import static org.project.utils.reflection.Reflection.getField;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.config.TestBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestInvoke<T extends TestBaseConfig> extends TestFS<T> {
    /**
     *
     */
    protected static WebDriver driver;
    /**
     *
     */
    protected static String fsClass;
    /**
     *
     */
    protected static String fsField;
    /**
     *
     */
    protected static String fsMethod;
    /**
     *
     */
    protected static String fsMethodName;

    /**
     *
     */
    @ConstructorProperties({})
    public TestInvoke() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("TestInvoke:init");
        fsClass = c.getFs();
        fsField = c.getFsField();
        fsMethod = c.getFsMethod();
        fsMethodName = c.getFsMethodName();
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testInvoke() throws Exception {
        debug("testInvoke");
        getClazz(fsClass, "::");
        getClazz(fsField, "::");
        getClazz(fsMethod, "::");
        debug(getField(fsField));
        debug(getField(fsClass));

        Requests req = new Requests();
        req.init("baseUrl");
        req.post().printFullPath();
        req.uri(uri);
        req.post().printFullPath();
        req.endpoint("id");
        req.post().printFullPath();
        req.endpoint(1);
        req.post().printFullPath();

        //debug(invoke(fsClass, fsMethodName));
        debug(invoke(fsMethod));
    }

    /**
     *
     * @return String
     */
    public static String invokeName() {
        debug("invokeName");
        debug(table(configs()));
        return "invokeName";
    }

}
