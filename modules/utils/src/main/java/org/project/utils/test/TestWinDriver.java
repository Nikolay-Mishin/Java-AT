package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertNotNull;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import static org.project.utils.Helper.debug;
import static org.project.utils.Thread.setTimeout;
import static org.project.utils.reflection.Reflection.isExtends;
import static org.project.utils.windriver.Actions.action;
import static org.project.utils.windriver.RemoteWebDriver.attachApp;
import static org.project.utils.windriver.RemoteWebDriver.attachAppClass;
import static org.project.utils.windriver.RemoteWebDriver.drivers;
import static org.project.utils.windriver.RemoteWebDriver.open;
import static org.project.utils.windriver.RemoteWebDriver.startAttach;
import static org.project.utils.windriver.RemoteWebDriver.startAttachClass;
import static org.project.utils.windriver.WebDriver.ls;
import static org.project.utils.windriver.WinDriver.start;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.windriver.RemoteWebDriver;
import org.project.utils.windriver.WinDriver;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestWinDriver<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestAuth<T, W, D> {
    /**
     *
     */
    protected static String url;
    /**
     *
     */
    protected static WebDriver d;
    /**
     *
     */
    protected static org.openqa.selenium.remote.RemoteWebDriver remote;
    /**
     *
     */
    protected static ChromeDriver web;
    /**
     *
     */
    protected static WindowsDriver<WebElement> app;
    /**
     *
     */
    protected static Actions a;
    /**
     *
     */
    protected static Action action;
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
    protected static String notepadPath;
    /**
     *
     */
    protected static String calcPath;

    /**
     *
     */
    @ConstructorProperties({})
    public TestWinDriver() {
        debug("TestWinDriver:init");
        url = w.getBaseUrl();
        notepadPath = win.getNotepad();
        calcPath = win.getCalc();
    }

    /**
     * @param driver WebDriver
     * @return WebDriver
     */
    public static WebDriver driver(WebDriver driver) {
        return setDriver(() -> d = driver);
    }

    /**
     * @param cb Supplier {T}
     * @return T
     * @param <T> extends WebDriver
     */
    public static <T extends WebDriver> T setDriver(Supplier<T> cb) {
        T d = cb.get();
        assertNotNull(d);
        setAction(d);
        return d;
    }

    /**
     *
     * @return WebDriver
     * @throws Exception throws
     */
    public static WebDriver driver() throws Exception {
        return driver(RemoteWebDriver.start());
    }

    /**
     *
     * @param app String
     * @param params String[]
     * @return WebDriver
     * @throws Exception throws
     */
    public static WebDriver driver(String app, String... params) throws Exception {
        return driver(RemoteWebDriver.start(app, params));
    }

    /**
     *
     * @param url String
     * @return ChromeDriver
     * @throws Exception throws
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
     * @throws Exception throws
     */
    public static ChromeDriver driverUrl(String url, String app, String... params) throws Exception {
        driver(app, params);
        return webDriver(url);
    }

    /**
     * @param driver RemoteWebDriver
     * @return RemoteWebDriver
     */
    public static org.openqa.selenium.remote.RemoteWebDriver remoteDriver(org.openqa.selenium.remote.RemoteWebDriver driver) {
        return setDriver(() -> remote = driver);
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
     * @throws Exception throws
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
     * @throws Exception throws
     */
    public static ChromeDriver remoteDriverUrl(String url, String app, String... params) throws Exception {
        remoteDriver(app, params);
        return webDriver(url);
    }

    /**
     * @param driver ChromeDriver
     * @return ChromeDriver
     */
    public static ChromeDriver webDriver(ChromeDriver driver) {
        web = driver;
        assertNotNull(web);
        return web;
    }

    /**
     *
     * @return ChromeDriver
     * @throws Exception throws
     */
    public static ChromeDriver webDriver() throws Exception {
        return webDriver(org.project.utils.windriver.WebDriver.start());
    }

    /**
     *
     * @param url String
     * @return ChromeDriver
     * @throws Exception throws
     */
    public static ChromeDriver webDriver(String url) throws Exception {
        return webDriver(org.project.utils.windriver.WebDriver.start(url));
    }

    /**
     *
     * @param driver WindowsDriver {WebElement}
     * @return WindowsDriver {WebElement}
     */
    public static WindowsDriver<WebElement> winDriver(WindowsDriver<WebElement> driver) {
        return setDriver(() -> app = driver);
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
     * @throws Exception throws
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
     * @throws Exception throws
     */
    public static ChromeDriver winDriverUrl(String url, String app, String... params) throws Exception {
        winDriver(app, params);
        return webDriver(url);
    }

    /**
     * @param driver T
     * @return Actions
     * @param <T> extends WebDriver
     */
    public static <T extends WebDriver> Actions setAction(T driver) {
        return a = action(driver);
    }

    /**
     *
     * @return Action
     */
    public static Action getAction() {
        return action = RemoteWebDriver.getAction();
    }

    /**
     *
     */
    public static void quit() {
        RemoteWebDriver.quit();
        d = null;
    }

    /**
     *
     */
    public static void quitRemote() {
        RemoteWebDriver.quit();
        remote = null;
    }

    /**
     *
     */
    public static void quitWeb() {
        org.project.utils.windriver.WebDriver.quit();
        web = null;
    }

    /**
     *
     */
    public static void quitWin() {
        WinDriver.quit();
        app = null;
    }

    /**
     *
     */
    public static void quitAll() {
        WinDriver.quit(d, remote, web, app);
        d = null;
        remote = null;
        web = null;
        app = null;
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
    public static void testTimeout() throws Exception {
        debug("testTimeout");
        open();
        quitWin();
        setTimeout(() -> { open(); return null; });
        webDriver();
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
        el = app.findElementByName("Текстовый редактор");
        debug(el);
        el = app.findElementByClassName("Notepad");
        debug(el);
        el = app.findElementByClassName("Edit");
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
        debug(app);
        el = app.findElementByAccessibilityId("num1Button");
        debug(el);
        el.click();

        el = app.findElementByName("Калькулятор");
        debug(el);
        el = app.findElementByClassName("ApplicationFrameWindow");
        debug(el);
        el = app.findElementByClassName("Windows.UI.Core.CoreWindow");
        debug(el);

        el = app.findElementByName("Один");
        debug(el);
        el.click();
        el = app.findElementByName("Плюс");
        debug(el);
        el.click();
        el = app.findElementByName("Семь");
        debug(el);
        el.click();
        el = app.findElementByName("Равно");
        debug(el);
        el.click();

        testHandleApp();
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testHandleApp() throws Exception {
        debug("testHandleApp");
        startHandle("Калькулятор");
        debug("calc: " + app);
        assertNotNull(app);
        app.findElementByName("Один").click();
        app.findElementByName("Плюс").click();
        app.findElementByName("Семь").click();
        app.findElementByName("Равно").click();
    }

}
