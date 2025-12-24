package org.project.utils.test;

import java.beans.ConstructorProperties;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.table;
import static org.project.utils.config.Config.configs;
import static org.project.utils.reflection.Reflection.getClazz;
import static org.project.utils.reflection.Reflection.getField;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.test.step.Requests;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestInvoke<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestFS<T, W, D> {
    /**
     *
     */
    protected static String testInvoke;
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
    public TestInvoke() {
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
