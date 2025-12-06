package org.project.utils.test;

import org.openqa.selenium.WebDriver;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.table;
import static org.project.utils.config.Config.configs;
import static org.project.utils.reflection.Reflection.getClazz;
import static org.project.utils.reflection.Reflection.getField;
import static org.project.utils.reflection.Reflection.invoke;

public class TestInvoke extends TestZip {
    protected static WebDriver driver;
    protected static String fsClass;
    protected static String fsField;
    protected static String fsMethod;
    protected static String fsMethodName;

    public TestInvoke() {
        fsClass = c.getFs();
        fsField = c.getFsField();
        fsMethod = c.getFsMethod();
        fsMethodName = c.getFsMethodName();
    }

    public static void main(String[] args) throws Exception {
        testInvoke();
    }

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

    public static String invokeName() {
        debug("invokeName");
        debug(table(configs()));
        return "invokeName";
    }

}
