package org.project.utils.test;

import org.openqa.selenium.WebDriver;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.toTable;
import static org.project.utils.config.Config.configs;
import static org.project.utils.reflection.Reflection.getClazz;
import static org.project.utils.reflection.Reflection.getField;
import static org.project.utils.reflection.Reflection.invoke;

public class TestInvoke extends TestZip {
    protected static WebDriver driver;

    public static void main(String[] args) throws Exception {
        testInvoke();
    }

    public static void testInvoke() throws Exception {
        debug("testInvoke");
        getClazz("org.project.utils.test.TestInvoke", "::");
        getClazz("org.project.utils.test.TestInvoke::driver", "::");
        getClazz("org.project.utils.test.TestInvoke::invokeName", "::");
        debug(getField("org.project.utils.test.TestInvoke::driver"));
        debug(getField("org.project.utils.test.TestInvoke"));

        Requests req = new Requests();
        req.init("baseUrl");
        req.post().printFullPath();
        req.uri("https://googlechromelabs.github.io/");
        req.post().printFullPath();
        req.endpoint("id");
        req.post().printFullPath();
        req.endpoint(1);
        req.post().printFullPath();

        //debug(invoke("org.project.utils.test.TestInvoke", "invokeName"));
        debug(invoke("org.project.utils.test.TestInvoke::invokeName"));
    }

    public static String invokeName() {
        debug("invokeName");
        debug(toTable(configs()));
        return "invokeName";
    }

}
