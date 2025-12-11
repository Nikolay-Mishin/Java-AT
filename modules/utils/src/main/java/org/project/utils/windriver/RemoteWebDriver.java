package org.project.utils.windriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.junit.Assert.assertNotNull;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;

import static org.project.utils.Helper.debug;
import static org.project.utils.Process.run;
import static org.project.utils.Thread.setTimeout;
import static org.project.utils.config.DriverBaseConfig.WINDRIVER;
import static org.project.utils.config.DriverBaseConfig.WINDRIVER_HOST;
import static org.project.utils.config.DriverBaseConfig.WINDRIVER_NAME;
import static org.project.utils.reflection.Reflection.getCallingClassSimpleName;

import org.project.utils.Process;
import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.DriverConfig;

public class RemoteWebDriver extends WebElement {
    protected static DriverBaseConfig c = DriverConfig.config();
    protected static Capabilities cap;
    protected static long sleep = c.getSleep();
    protected static String winDriver = WINDRIVER;
    protected static String winDriverName = WINDRIVER_NAME;
    protected static String winDriverHost = WINDRIVER_HOST;
    protected static Process p;
    protected static ProcessBuilder pb;

    public static DriverBaseConfig config() {
        return c;
    }

    //[ConfigInitialize]
    public static DriverBaseConfig config(DriverBaseConfig config)  {
        return init(config);
    }

    public static org.openqa.selenium.Capabilities getCapabilities() {
        return d.getCapabilities();
    }

    public static Capabilities cap() {
        return cap;
    }

    public static Capabilities cap(Capabilities capabilities) {
        return cap = capabilities;
    }

    public static <T extends DesiredCapabilities> Capabilities cap(DriverBaseConfig config) {
        return cap(new Capabilities(config));
    }

    public static <T extends DesiredCapabilities> Capabilities cap(T capabilities) {
        return cap(new Capabilities(capabilities));
    }

    //[ConfigInitialize]
    public static DriverBaseConfig init(DriverBaseConfig config) {
        c = config;
        cap(config);
        winDriver = config.getWindriver();
        winDriverName = config.getWindriverName();
        winDriverHost = config.getWindriverHost();
        debug(c);
        debug(cap);
        debug(winDriver);
        debug(winDriverName);
        debug(winDriverHost);
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
    public static <T extends WebDriver> T start() throws Exception {
        return start(cap(c));
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T start(String app, String... params) throws Exception {
        start();
        if (params.length > 0) init(app, params);
        return (T) d;
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T start(DriverBaseConfig config) throws Exception {
        config(config);
        start(cap);
        return (T) d;
    }

    public static <T extends WebDriver> T start(DesiredCapabilities cap) throws Exception {
        return start(cap, sleep);
    }

    public static <T extends WebDriver> T start(DesiredCapabilities cap, long sleep) throws Exception {
        return start(cap, sleep, 2);
    }

    public static <T extends WebDriver> T start(DesiredCapabilities cap, long sleep, int index) throws Exception {
        return setTimeout(sleep, () -> { open(); return null; }, o -> getDriver(cap, index + 3));
    }

    public static <T extends org.openqa.selenium.remote.RemoteWebDriver> T start(T driver)
        throws MalformedURLException, ClassNotFoundException
    {
        assertNotNull(driver);
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

    public static <T extends WebDriver> T getDriver(DesiredCapabilities cap) throws MalformedURLException, ClassNotFoundException {
        return getDriver(cap, 0);
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T getDriver(DesiredCapabilities cap, int index) throws MalformedURLException, ClassNotFoundException {
        // Прикрепить переменную драйвера к собственно Winium драйверу
        return (T) (switch (getCallingClassSimpleName(index + 3)) {
            case "WinDriver": yield start(new WindowsDriver<>(new URL(winDriverHost), cap));
            case "WebDriver": yield start(new ChromeDriver(cap));
            default: yield start(new org.openqa.selenium.remote.RemoteWebDriver(new URL(winDriverHost), cap));
        });
    }

    //[AppSessionQuit]
    public static void quit() {
        quit(winDriverName);
    }

    protected static void quit(String driverName) {
        quit(d, driverName);
    }

    protected static void quit(WebDriver driver, String driverName) {
        // The instance of WinAppDriver will be freed once last test is complete
        if (driver != null) quit(driver);
        stop(driverName);
    }

    protected static void quit(WebDriver driver) {
        debug("quit: " + driver);
        driver.quit();
    }

    public static void stop() {
        stop(winDriverName);
    }

    protected static void stop(String driverName) {
        debug("stop: " + driverName);
        d = null;
        try {
            new Process("taskkill ", "/f", "/IM", driverName);
        }
        catch (IOException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
    }

    public static void quit(WebDriver... driver) {
        quit(winDriverName, driver);
    }

    protected static void quit(String driverName, WebDriver... driver) {
        for (WebDriver d : driver) quit(d);
        stop(driverName);
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
