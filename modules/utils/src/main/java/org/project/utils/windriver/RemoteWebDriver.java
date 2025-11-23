package org.project.utils.windriver;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.remote.*;
import org.testng.Assert;

import static org.project.utils.Helper.*;
import static org.project.utils.Process.run;
import static org.project.utils.reflection.Reflection.*;
import static org.project.utils.windriver.DriverBaseConfig.*;

import org.project.utils.constant.Capabilities;
import org.project.utils.Process;

public class RemoteWebDriver extends WebElement {
    protected static DriverBaseConfig c = BASE_CONFIG;
    protected static DesiredCapabilities cap = new DesiredCapabilities();
    protected static String winDriver = WINDRIVER;
    protected static String winDriverName = WINDRIVER_NAME;
    protected static String winDriverHost = WINDRIVER_HOST;
    protected static boolean experimental = c.getExperimental();
    protected static Process p;
    protected static ProcessBuilder pb;

    public static DriverBaseConfig config() {
        return c;
    }

    //[ConfigInitialize]
    public static DriverBaseConfig config(DriverBaseConfig config)  {
        return c = init(config);
    }

    public static DesiredCapabilities cap() {
        return cap;
    }

    public static org.openqa.selenium.Capabilities getCapabilities() {
        return d.getCapabilities();
    }

    public static DesiredCapabilities cap(DesiredCapabilities capabilities) {
        return cap = capabilities;
    }

    //[ConfigInitialize]
    public static DriverBaseConfig init(DriverBaseConfig config) {
        debug(config);
        winDriver = config.getWindriver();
        winDriverName = config.getWindriverName();
        winDriverHost = config.getWindriverHost();
        experimental = config.getExperimental();
        debug(winDriver);
        debug(winDriverName);
        debug(winDriverHost);
        debug(experimental);
        return config;
    }

    //[ProcessInitialize]
    public static void init() throws IOException, IllegalAccessException {
        init(winDriver, c.getWebdriverParam());
    }

    protected static void init(String app, String... params) throws IOException, IllegalAccessException {
        p = run(app, params);
        pb = p.pb();
    }

    //[ProcessDestroy]
    public static void destroy() {
        p.destroy();
        p = null;
        pb = null;
    }

    //[ClassInitialize]
    public static <T extends WebDriver> T start() throws MalformedURLException, IllegalAccessException, ClassNotFoundException {
        return start(setCap());
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T start(String app, String... params) throws IOException, IllegalAccessException, ClassNotFoundException {
        start();
        run(app, params);
        return (T) d;
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T start(DriverBaseConfig config) throws MalformedURLException, IllegalAccessException, ClassNotFoundException {
        config(config);
        start();
        return (T) d;
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T start(DesiredCapabilities cap) throws MalformedURLException, ClassNotFoundException {
        open();
        // Прикрепить переменную драйвера к собственно Winium драйверу
        return (switch (getCallingChildClassSimpleName()) {
            case "WinDriver": yield (T) start(new WindowsDriver<>(new URL(winDriverHost), cap));
            case "WebDriver": yield (T) start(new ChromeDriver(cap));
            default: yield (T) start(new org.openqa.selenium.remote.RemoteWebDriver(new URL(winDriverHost), cap));
        });
    }

    public static <T extends org.openqa.selenium.remote.RemoteWebDriver> T start(T driver)
        throws MalformedURLException, ClassNotFoundException
    {
        Assert.assertNotNull(driver);
        driver(driver);
        action(driver);
        printClass();
        return driver;
    }

    public static void open() throws MalformedURLException {
        open(winDriver);
    }

    public static void open(String app) throws MalformedURLException {
        Process.open(app);
    }

    // Navigate to the webpage where localStorage data is stored
    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T get(String url) {
        d.get(url);
        return (T) d;
    }

    //[Capabilities]
    public static DesiredCapabilities setCap() throws IllegalAccessException {
        if (experimental) cap.setCapability("ms:experimental-webdriver", experimental);
        Map<String, Object> map = entries(new Capabilities());
        debug(map);
        for (Entry<String, Object> entry : map.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            debug(k + ": " + v);
            if (v != "") cap.setCapability(k, v);
        }
        debug("experimental: " + experimental);
        return cap;
    }

    //[AppSessionQuit]
    public static void quit() {
        quit(winDriverName);
    }

    protected static void quit(String driverName) {
        quit(d, driverName);
        d = null;
    }

    //protected static void quit(WebDriver driver, String driverName) {
    protected static void quit(WebDriver driver, String driverName) {
        // The instance of WinAppDriver will be freed once last test is complete
        // WinDriver.stop();
        if (driver != null) driver.quit();
        stop(driverName);
    }

    public static void stop() {
        stop(winDriverName);
    }

    protected static void stop(String driverName) {
        try {
            new Process("taskkill ", "/f", "/IM", driverName);
            d = null;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void timeout(long t) {
        //webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(t));
        d.manage().timeouts().implicitlyWait(t, TimeUnit.MILLISECONDS);
    }

    public static void timeout() {
        timeout(1000);
    }

    public static SessionId getSessionId() {
        return d.getSessionId();
    }

    public static String getCurrentUrl() {
        return d.getCurrentUrl();
    }

    public static void setLogLevel(Level lvl) {
        d.setLogLevel(lvl);
    }

    public static Keyboard getKeyboard() {
        return d.getKeyboard();
    }

    public static Mouse getMouse() {
        return d.getMouse();
    }

    public static String getTitle() {
        return d.getTitle();
    }

    public static String getPageSource() {
        return d.getPageSource();
    }

    public static CommandExecutor getCommandExecutor() {
        return d.getCommandExecutor();
    }

    public static Response execute(Command command) throws IOException {
        return getCommandExecutor().execute(command);
    }

    public static Response execute(SessionId sessionId, String name, Map<String, ?> params) throws IOException {
        return getCommandExecutor().execute(getCommand(sessionId, name, params));
    }

    public static Command getCommand(SessionId sessionId, String name, Map<String, ?> params) {
        return new Command(sessionId, name, params);
    }

    public static Object executeScript(String script, Object... arg) {
        return d.executeScript(script, arg);
    }

    public static Object executeAsyncScript(String script, Object... arg) {
        return d.executeAsyncScript(script, arg);
    }

    public static <X> X getScreenshotAs(OutputType<X> outputType) {
        return d.getScreenshotAs(outputType);
    }
}
