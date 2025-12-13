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

/**
 *
 */
public class RemoteWebDriver extends WebElement {
    /**
     *
     */
    protected static DriverBaseConfig c = DriverConfig.config();
    /**
     *
     */
    protected static Capabilities cap;
    /**
     *
     */
    protected static long sleep = c.getSleep();
    /**
     *
     */
    protected static String winDriver = WINDRIVER;
    /**
     *
     */
    protected static String winDriverName = WINDRIVER_NAME;
    /**
     *
     */
    protected static String winDriverHost = WINDRIVER_HOST;
    /**
     *
     */
    protected static Process p;
    /**
     *
     */
    protected static ProcessBuilder pb;

    /**
     *
     * @return DriverBaseConfig
     */
    public static DriverBaseConfig config() {
        return c;
    }

    /**
     * ConfigInitialize
     * @param config DriverBaseConfig
     * @return DriverBaseConfig
     */
    public static DriverBaseConfig config(DriverBaseConfig config)  {
        return init(config);
    }

    /**
     *
     * @return Capabilities
     */
    public static org.openqa.selenium.Capabilities getCapabilities() {
        return d.getCapabilities();
    }

    /**
     *
     * @return Capabilities
     */
    public static Capabilities cap() {
        return cap;
    }

    /**
     *
     * @param capabilities Capabilities
     * @return Capabilities
     */
    public static Capabilities cap(Capabilities capabilities) {
        return cap = capabilities;
    }

    /**
     *
     * @param config DriverBaseConfig
     * @return Capabilities
     */
    public static Capabilities cap(DriverBaseConfig config) {
        return cap(new Capabilities(config));
    }

    /**
     *
     * @param capabilities T
     * @return Capabilities
     * @param <T> extends DesiredCapabilities
     */
    public static <T extends DesiredCapabilities> Capabilities cap(T capabilities) {
        return cap(new Capabilities(capabilities));
    }

    /**
     * ConfigInitialize
     * @param config DriverBaseConfig
     * @return DriverBaseConfig
     */
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

    /**
     * ProcessInitialize
     * @throws IOException throws
     * @throws IllegalAccessException throws
     */
    public static void init() throws IOException, IllegalAccessException {
        init(winDriver, c.getWebdriverParam());
    }

    /**
     *
     * @param app String
     * @param params String[]
     * @throws IOException throws
     * @throws IllegalAccessException throws
     */
    protected static void init(String app, String... params) throws IOException, IllegalAccessException {
        p = run(app, params);
        pb = p.pb();
    }

    /**
     * ProcessDestroy
     */
    public static void destroy() {
        p.destroy();
        p = null;
        pb = null;
    }

    /**
     * ClassInitialize
     * <p>linkNamed: {@link #start(DriverBaseConfig) start}
     * <p>linkplainNamed: {@linkplain #start(DriverBaseConfig) start}
     * <p>link: {@link #start(DriverBaseConfig)}
     * <p>linkplain: {@linkplain #start(DriverBaseConfig)}
     * @return {@link T}
     * @param <T> extends {@link WebDriver}
     * @throws Exception throws
     * @see WebDriver
     */
    public static <T extends WebDriver> T start() throws Exception {
        return start(cap(c));
    }

    /**
     *
     * @param app String
     * @param params String[]
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T start(String app, String... params) throws Exception {
        start();
        if (params.length > 0) init(app, params);
        return (T) d;
    }

    /**
     *
     * @param config DriverBaseConfig
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T start(DriverBaseConfig config) throws Exception {
        config(config);
        start(cap);
        return (T) d;
    }

    /**
     *
     * @param cap DesiredCapabilities
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T start(DesiredCapabilities cap) throws Exception {
        return start(cap, sleep);
    }

    /**
     *
     * @param cap DesiredCapabilities
     * @param sleep long
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T start(DesiredCapabilities cap, long sleep) throws Exception {
        return start(cap, sleep, 2);
    }

    /**
     *
     * @param cap DesiredCapabilities
     * @param sleep long
     * @param index int
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T start(DesiredCapabilities cap, long sleep, int index) throws Exception {
        return setTimeout(sleep, () -> { open(); return null; }, o -> getDriver(cap, index + 3));
    }

    /**
     *
     * @param driver T
     * @return T
     * @param <T> extends RemoteWebDriver
     * @throws MalformedURLException throws
     * @throws ClassNotFoundException throws
     */
    public static <T extends org.openqa.selenium.remote.RemoteWebDriver> T start(T driver)
        throws MalformedURLException, ClassNotFoundException
    {
        assertNotNull(driver);
        driver(driver);
        action(driver);
        printClass();
        return driver;
    }

    /**
     *
     * @throws MalformedURLException throws
     */
    public static void open() throws MalformedURLException {
        open(winDriver);
    }

    /**
     *
     * @param app String
     * @throws MalformedURLException throws
     */
    public static void open(String app) throws MalformedURLException {
        Process.open(app);
    }

    /**
     * Navigate to the webpage where localStorage data is stored
     * @param url String
     * @return T
     * @param <T> extends WebDriver
     */
    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T get(String url) {
        d.get(url);
        return (T) d;
    }

    /**
     *
     * @param cap DesiredCapabilities
     * @return T
     * @param <T> extends WebDriver
     * @throws MalformedURLException throws
     * @throws ClassNotFoundException throws
     */
    public static <T extends WebDriver> T getDriver(DesiredCapabilities cap) throws MalformedURLException, ClassNotFoundException {
        return getDriver(cap, 0);
    }

    /**
     *
     * @param cap DesiredCapabilities
     * @param index int
     * @return T
     * @param <T> extends WebDriver
     * @throws MalformedURLException throws
     * @throws ClassNotFoundException throws
     */
    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T getDriver(DesiredCapabilities cap, int index) throws MalformedURLException, ClassNotFoundException {
        // Прикрепить переменную драйвера к собственно Winium драйверу
        return (T) (switch (getCallingClassSimpleName(index + 3)) {
            case "WinDriver": yield start(new WindowsDriver<>(new URL(winDriverHost), cap));
            case "WebDriver": yield start(new ChromeDriver(cap));
            default: yield start(new org.openqa.selenium.remote.RemoteWebDriver(new URL(winDriverHost), cap));
        });
    }

    /**
     * AppSessionQuit
     */
    public static void quit() {
        quit(winDriverName);
    }

    /**
     *
     * @param driverName String
     */
    protected static void quit(String driverName) {
        quit(d, driverName);
    }

    /**
     *
     * @param driver WebDriver
     * @param driverName String
     */
    protected static void quit(WebDriver driver, String driverName) {
        // The instance of WinAppDriver will be freed once last test is complete
        if (driver != null) quit(driver);
        stop(driverName);
    }

    /**
     *
     * @param driver WebDriver
     */
    protected static void quit(WebDriver driver) {
        debug("quit: " + driver);
        driver.quit();
    }

    /**
     *
     */
    public static void stop() {
        stop(winDriverName);
    }

    /**
     *
     * @param driverName String
     */
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

    /**
     *
     * @param driver WebDriver[]
     */
    public static void quit(WebDriver... driver) {
        quit(winDriverName, driver);
    }

    /**
     *
     * @param driverName String
     * @param driver WebDriver[]
     */
    protected static void quit(String driverName, WebDriver... driver) {
        for (WebDriver d : driver) quit(d);
        stop(driverName);
    }

    /**
     *
     * @param t long
     */
    public static void timeout(long t) {
        //webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(t));
        d.manage().timeouts().implicitlyWait(t, TimeUnit.MILLISECONDS);
    }

    /**
     *
     */
    public static void timeout() {
        timeout(1000);
    }

    /**
     *
     * @return SessionId
     */
    public static SessionId getSessionId() {
        return d.getSessionId();
    }

    /**
     *
     * @return String
     */
    public static String getCurrentUrl() {
        return d.getCurrentUrl();
    }

    /**
     *
     * @param lvl Level
     */
    public static void setLogLevel(Level lvl) {
        d.setLogLevel(lvl);
    }

    /**
     *
     * @return Keyboard
     */
    public static Keyboard getKeyboard() {
        return d.getKeyboard();
    }

    /**
     *
     * @return Mouse
     */
    public static Mouse getMouse() {
        return d.getMouse();
    }

    /**
     *
     * @return String
     */
    public static String getTitle() {
        return d.getTitle();
    }

    /**
     *
     * @return String
     */
    public static String getPageSource() {
        return d.getPageSource();
    }

    /**
     *
     * @return CommandExecutor
     */
    public static CommandExecutor getCommandExecutor() {
        return d.getCommandExecutor();
    }

    /**
     *
     * @param command Command
     * @return Response
     * @throws IOException throws
     */
    public static Response execute(Command command) throws IOException {
        return getCommandExecutor().execute(command);
    }

    /**
     *
     * @param sessionId SessionId
     * @param name String
     * @param params Map {String, ?}
     * @return Response
     * @throws IOException throws
     */
    public static Response execute(SessionId sessionId, String name, Map<String, ?> params) throws IOException {
        return getCommandExecutor().execute(getCommand(sessionId, name, params));
    }

    /**
     *
     * @param sessionId SessionId
     * @param name String
     * @param params Map {String, ?}
     * @return Command
     */
    public static Command getCommand(SessionId sessionId, String name, Map<String, ?> params) {
        return new Command(sessionId, name, params);
    }

    /**
     *
     * @param script String
     * @param arg Object[]
     * @return Object
     */
    public static Object executeScript(String script, Object... arg) {
        return d.executeScript(script, arg);
    }

    /**
     *
     * @param script String
     * @param arg Object[]
     * @return Object
     */
    public static Object executeAsyncScript(String script, Object... arg) {
        return d.executeAsyncScript(script, arg);
    }

    /**
     *
     * @param outputType OutputType {X}
     * @return X
     * @param <X> X
     */
    public static <X> X getScreenshotAs(OutputType<X> outputType) {
        return d.getScreenshotAs(outputType);
    }

}
