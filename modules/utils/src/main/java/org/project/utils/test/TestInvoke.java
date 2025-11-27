package org.project.utils.test;

import java.io.IOException;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.toTable;
import static org.project.utils.config.Config.configs;
import static org.project.utils.reflection.Reflection.*;

public class TestInvoke extends TestZip {
    protected static WebDriver driver;

    public static void main(String[] args) throws IOException, ReflectiveOperationException, URISyntaxException {
        testInvoke();
    }

    public static void testInvoke() throws ReflectiveOperationException {
        getClazz("org.project.utils.test.TestInvoke", "::");
        getClazz("org.project.utils.test.TestInvoke::driver", "::");
        getClazz("org.project.utils.test.TestInvoke::invokeName", "::");
        debug(getField("org.project.utils.test.TestInvoke::driver"));
        debug(getField("org.project.utils.test.TestInvoke"));
        //debug(invoke("org.project.utils.test.TestInvoke", "invokeName"));
        debug(invoke("org.project.utils.test.TestInvoke::invokeName"));

        try {debug(getField("org.project.utils.test.TestInvoke::invokeName"));}
        catch (ReflectiveOperationException e) {e.printStackTrace();}

        try {debug(invoke("org.project.utils.test.TestInvoke::invokeName1"));}
        catch (ReflectiveOperationException e) {e.printStackTrace();}

        try {debug(invoke("org.project.utils.test.App1::invokeName"));}
        catch (ReflectiveOperationException e) {e.printStackTrace();}
    }

    public static String invokeName() {
        debug(toTable(configs()));
        return "invokeName";
    }

}
