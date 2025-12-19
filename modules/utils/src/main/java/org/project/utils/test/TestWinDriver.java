package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertNotNull;
import static org.project.utils.Helper.debug;
import static org.project.utils.Thread.setTimeout;
import static org.project.utils.config.WebConfig.getConfig;
import static org.project.utils.reflection.Reflection.isExtends;
import static org.project.utils.windriver.RemoteWebDriver.attachApp;
import static org.project.utils.windriver.RemoteWebDriver.attachAppClass;
import static org.project.utils.windriver.RemoteWebDriver.drivers;
import static org.project.utils.windriver.RemoteWebDriver.open;
import static org.project.utils.windriver.RemoteWebDriver.startAttach;
import static org.project.utils.windriver.RemoteWebDriver.startAttachClass;
import static org.project.utils.windriver.WebDriver.ls;
import static org.project.utils.windriver.WinDriver.start;

import org.openqa.selenium.chrome.ChromeDriver;
import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.windriver.RemoteWebDriver;
import org.project.utils.windriver.WinDriver;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestWinDriver<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestProc<T, D> {
    /**
     *
     */
    protected W w;
    /**
     *
     */
    protected static String url;
    /**
     *
     */
    protected static WindowsDriver<WebElement> winDriver;
    /**
     *
     */
    protected static org.openqa.selenium.remote.RemoteWebDriver remoteDriver;
    /**
     *
     */
    protected static WebElement el;
    /**
     *
     */
    protected static List<WebElement> list;

    /**
     *
     */
    @ConstructorProperties({})
    public TestWinDriver() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("TestWinDriver:init");
        w = getConfig();
        url = w.getBaseUrl();
    }

    /**
     *
     * @param driver WindowsDriver {WebElement}
     * @return WindowsDriver {WebElement}
     */
    public static WindowsDriver<WebElement> winDriver(WindowsDriver<WebElement> driver) {
        winDriver = driver;
        assertNotNull(winDriver);
        return winDriver;
    }

    /**
     *
     * @return WindowsDriver {WebElement}
     * @throws Exception throws
     */
    public static WindowsDriver<WebElement> winDriver() throws Exception {
        return winDriver(start());
    }

    /**
     *
     * @param app String
     * @param params String[]
     * @return WindowsDriver {WebElement}
     * @throws Exception throws
     */
    public static WindowsDriver<WebElement> winDriver(String app, String... params) throws Exception {
        return winDriver(start(app, params));
    }

    /**
     *
     * @param url String
     * @return ChromeDriver
     */
    public static ChromeDriver winDriverUrl(String url) throws Exception {
        winDriver();
        return webDriver(url);
    }

    /**
     *
     * @param url String
     * @param app String
     * @param params String[]
     * @return ChromeDriver
     */
    public static ChromeDriver winDriverUrl(String url, String app, String... params) throws Exception {
        winDriver(app, params);
        return webDriver(url);
    }

    /**
     * @param driver RemoteWebDriver
     * @return RemoteWebDriver
     */
    public static org.openqa.selenium.remote.RemoteWebDriver remoteDriver(org.openqa.selenium.remote.RemoteWebDriver driver) {
        remoteDriver = driver;
        assertNotNull(remoteDriver);
        return remoteDriver;
    }

    /**
     *
     * @return RemoteWebDriver
     * @throws Exception throws
     */
    public static org.openqa.selenium.remote.RemoteWebDriver remoteDriver() throws Exception {
        return remoteDriver(RemoteWebDriver.start());
    }

    /**
     *
     * @param app String
     * @param params String[]
     * @return RemoteWebDriver
     * @throws Exception throws
     */
    public static org.openqa.selenium.remote.RemoteWebDriver remoteDriver(String app, String... params) throws Exception {
        return remoteDriver(RemoteWebDriver.start(app, params));
    }

    /**
     *
     * @param url String
     * @return ChromeDriver
     */
    public static ChromeDriver remoteDriverUrl(String url) throws Exception {
        remoteDriver();
        return webDriver(url);
    }

    /**
     *
     * @param url String
     * @param app String
     * @param params String[]
     * @return ChromeDriver
     */
    public static ChromeDriver remoteDriverUrl(String url, String app, String... params) throws Exception {
        remoteDriver(app, params);
        return webDriver(url);
    }

    /**
     *
     */
    public static void quitWin() {
        WinDriver.quit();
        winDriver = null;
    }

    /**
     *
     */
    public static void quitRemote() {
        RemoteWebDriver.quit();
        remoteDriver = null;
    }

    /**
     *
     */
    public static void quitAll() {
        WinDriver.quit(driver, remoteDriver, webDriver, winDriver);
        driver = null;
        remoteDriver = null;
        webDriver = null;
        winDriver = null;
    }

    /**
     * @param app String
     * @return WindowsDriver {WebElement}
     * @throws Exception throws
     */
    public static WindowsDriver<WebElement> handleApp(String app) throws Exception {
        return winDriver(attachApp(app));
    }

    /**
     *
     * @param appClass String
     * @return WindowsDriver {WebElement}
     * @throws Exception throws
     */
    public static WindowsDriver<WebElement> handleAppClass(String appClass) throws Exception {
        return winDriver(attachAppClass(appClass));
    }

    /**
     *
     * @param app String
     * @return WindowsDriver {WebElement}
     * @throws Exception throws
     */
    public static WindowsDriver<WebElement> startHandle(String app) throws Exception {
        return winDriver(startAttach(app));
    }

    /**
     *
     * @param appClass String
     * @return WindowsDriver {WebElement}
     * @throws Exception throws
     */
    public static WindowsDriver<WebElement> startHandleClass(String appClass) throws Exception {
        return winDriver(startAttachClass(appClass));
    }

    /**
     *
     * @throws IOException throws
     * @throws InterruptedException throws
     */
    public static void testTimeout() throws IOException, InterruptedException {
        debug("testTimeout");
        open();
        quitWin();
        setTimeout(() -> { open(); return null; });
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testDriver() throws Exception {
        debug("testDriver");
        driver();
        remoteDriver();
        webDriver();
        winDriver();
        WinDriver.printCall();
        quitAll();
        winDriver("");
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testWinDriver() throws Exception {
        winDriverUrl(url);
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     * @throws MalformedURLException throws
     */
    public static void testWebDriver() throws Exception {
        webDriver(url);
        // Set the localStorage data
        ls.set("accessToken", "");
        ls.set("refreshToken", "");
        // Print the localStorage data
        debug("localStorage data: " + ls.items());
        debug("localStorage item: " + ls.get("accessToken"));
        quitWeb();
    }

    /**
     *
     */
    public static void testDriverInit() {
        debug("testDriverInit");
        List<String> drivers = drivers();
        debug("drivers: " + drivers);
        debug("contains: " + drivers.contains("WinDriver"));
        debug("contains: " + drivers.contains("ChromeDriver"));

        Class<WebDriver> iWebDriver = WebDriver.class;
        Class<org.openqa.selenium.remote.RemoteWebDriver> remoteWebDriver = org.openqa.selenium.remote.RemoteWebDriver.class;
        Class<RemoteWebDriver> remoteDriver = RemoteWebDriver.class;
        Class<WindowsDriver> windowsDriver = WindowsDriver.class;
        Class<ChromeDriver> chromeDriver = ChromeDriver.class;
        Class<WinDriver> winDriver = WinDriver.class;
        Class<org.project.utils.windriver.WebDriver> webDriver = org.project.utils.windriver.WebDriver.class;

        debug(windowsDriver);
        debug("extends: " + isExtends(windowsDriver, iWebDriver));
        debug("extends: " + isExtends(windowsDriver, remoteWebDriver));
        debug(chromeDriver);
        debug("extends: " + isExtends(chromeDriver, iWebDriver));
        debug("extends: " + isExtends(chromeDriver, remoteWebDriver));
        debug(remoteDriver);
        debug("extends: " + isExtends(remoteDriver, iWebDriver));
        debug("extends: " + isExtends(remoteDriver, remoteWebDriver));
        debug("extends: " + isExtends(remoteDriver, remoteDriver));
        debug(winDriver);
        debug("extends: " + isExtends(winDriver, iWebDriver));
        debug("extends: " + isExtends(winDriver, remoteWebDriver));
        debug("extends: " + isExtends(winDriver, remoteDriver));
        debug(webDriver);
        debug("extends: " + isExtends(webDriver, iWebDriver));
        debug("extends: " + isExtends(webDriver, remoteWebDriver));
        debug("extends: " + isExtends(webDriver, remoteDriver));
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testDriverApp() throws Exception {
        testAppEdit();
        testAppCalc();
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testAppEdit() throws Exception {
        debug("testAppEdit");
        //winDriver = WinDriver.start();
        winDriver();
        el = winDriver.findElementByName("Текстовый редактор");
        debug(el);
        el = winDriver.findElementByClassName("Notepad");
        debug(el);
        el = winDriver.findElementByClassName("Edit");
        debug(el);
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testAppCalc() throws Exception {
        debug("testAppCalc");
        debug(calcPath);
        winDriver(calcPath);
        debug(winDriver);
        el = winDriver.findElementByAccessibilityId("num1Button");
        debug(el);
        el.click();

        el = winDriver.findElementByName("Калькулятор");
        debug(el);
        el = winDriver.findElementByClassName("ApplicationFrameWindow");
        debug(el);
        el = winDriver.findElementByClassName("Windows.UI.Core.CoreWindow");
        debug(el);

        el = winDriver.findElementByName("Один");
        debug(el);
        el.click();
        el = winDriver.findElementByName("Плюс");
        debug(el);
        el.click();
        el = winDriver.findElementByName("Семь");
        debug(el);
        el.click();
        el = winDriver.findElementByName("Равно");
        debug(el);
        el.click();

        testHandleApp();
    }

    /**
     *
     */
    public static void testHandleApp() throws Exception {
        debug("testHandleApp");
        startHandle("Калькулятор");
        debug("calc: " + winDriver);
        assertNotNull(winDriver);
        winDriver.findElementByName("Один").click();
        winDriver.findElementByName("Плюс").click();
        winDriver.findElementByName("Семь").click();
        winDriver.findElementByName("Равно").click();
    }

}
