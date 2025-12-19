package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertNotNull;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.table;
import static org.project.utils.config.Config.configs;
import static org.project.utils.reflection.Reflection.getClazz;
import static org.project.utils.reflection.Reflection.getField;
import static org.project.utils.reflection.Reflection.invoke;
import static org.project.utils.test.TestProc.webDriver;
import static org.project.utils.windriver.RemoteWebDriver.start;

import org.openqa.selenium.chrome.ChromeDriver;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.windriver.RemoteWebDriver;

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
     * @param d WebDriver
     * @return WebDriver
     */
    public static WebDriver driver(WebDriver d) {
        driver = d;
        assertNotNull(driver);
        return driver;
    }

    /**
     *
     * @return WebDriver
     */
    public static WebDriver driver() throws Exception {
        return driver(start());
    }

    /**
     *
     * @param app String
     * @param params String[]
     * @return WebDriver
     */
    public static WebDriver driver(String app, String... params) throws Exception {
        return driver(start(app, params));
    }

    /**
     *
     * @param url String
     * @return ChromeDriver
     */
    public static ChromeDriver driverUrl(String url) throws Exception {
        driver();
        return webDriver(url);
    }

    /**
     *
     * @param url String
     * @param app String
     * @param params String[]
     * @return ChromeDriver
     */
    public static ChromeDriver driverUrl(String url, String app, String... params) throws Exception {
        driver(app, params);
        return webDriver(url);
    }

    /**
     *
     */
    public static void quit() {
        RemoteWebDriver.quit();
        driver = null;
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
