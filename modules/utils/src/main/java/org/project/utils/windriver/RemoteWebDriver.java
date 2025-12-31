package org.project.utils.windriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toHexString;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;

import static java.time.Duration.ofSeconds;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.notNull;
import static org.project.utils.Process.run;
import static org.project.utils.Thread.setTimeout;
import static org.project.utils.config.DriverBaseConfig.WINDRIVER;
import static org.project.utils.config.DriverBaseConfig.WINDRIVER_HOST;
import static org.project.utils.config.DriverBaseConfig.WINDRIVER_NAME;
import static org.project.utils.reflection.Reflection.getCallingClassSimpleName;
import static org.project.utils.reflection.Reflection.isExtends;
import static org.project.utils.test.TestWinDriver.remoteDriver;
import static org.project.utils.test.TestWinDriver.webDriver;
import static org.project.utils.test.TestWinDriver.winDriver;

import org.project.utils.Process;
import org.project.utils.base.HashMap;
import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.DriverConfig;
import org.project.utils.function.SupplierWithExceptions;

/**
 *
 */
public class RemoteWebDriver<T extends org.openqa.selenium.remote.RemoteWebDriver> extends WebElement<org.openqa.selenium.remote.RemoteWebDriver> {
    /**
     *
     */
    protected static DriverBaseConfig c = DriverConfig.config();
    /**
     *
     */
    protected static WindowsDriver<org.openqa.selenium.WebElement> r;
    /**
     *
     */
    protected static List<String> drivers = List.of("WinDriver", "WebDriver", "RemoteWebDriver");
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
    protected static long timeout = c.getTimeout();
    /**
     *
     */
    protected static long sleepStart = c.getSleepStart();
    /**
     *
     */
    protected static long timeoutStart = c.getTimeoutStart();
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
     */
    protected static Class<WindowsDriver> windowsDriver = WindowsDriver.class;
    /**
     *
     */
    protected static Class<ChromeDriver> webDriver = ChromeDriver.class;
    /**
     *
     */
    protected static Class<org.openqa.selenium.remote.RemoteWebDriver> remoteDriver = org.openqa.selenium.remote.RemoteWebDriver.class;

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
     * @throws ReflectiveOperationException throws
     */
    public static DriverBaseConfig config(DriverBaseConfig config) throws ReflectiveOperationException {
        return init(config);
    }

    /**
     *
     * @param driver  WindowsDriver {WebElement}
     * @return WindowsDriver {WebElement}
     */
    public static WindowsDriver<org.openqa.selenium.WebElement> root(WindowsDriver<org.openqa.selenium.WebElement> driver) {
        return r = driver;
    }

    /**
     *
     * @return WindowsDriver {WebElement}
     */
    public static WindowsDriver<org.openqa.selenium.WebElement> root() {
        return r;
    }

    /**
     *
     * @return List {String}
     */
    public static List<String> drivers() {
        return drivers;
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
     * @param app String
     * @return Capabilities
     * @throws ReflectiveOperationException throws
     */
    public static Capabilities cap(String app) throws ReflectiveOperationException {
        return cap(new Capabilities(app));
    }

    /**
     * You set the Handle as one of the capabilities
     * @param app String
     * @param handle boolean
     * @return Capabilities
     * @throws ReflectiveOperationException throws
     */
    public static Capabilities cap(String app, boolean handle) throws ReflectiveOperationException {
        return cap(new Capabilities(app, handle));
    }

    /**
     *
     * @param config DriverBaseConfig
     * @return Capabilities
     * @throws ReflectiveOperationException throws
     */
    public static Capabilities cap(DriverBaseConfig config) throws ReflectiveOperationException {
        return cap(new Capabilities(config));
    }

    /**
     *
     * @param capabilities T
     * @return Capabilities
     * @param <T> extends DesiredCapabilities
     * @throws ReflectiveOperationException throws
     */
    public static <T extends DesiredCapabilities> Capabilities cap(T capabilities) throws ReflectiveOperationException {
        return cap(new Capabilities(capabilities));
    }

    /**
     *
     * @return long
     */
    public static long sleep() {
        return sleep;
    }

    /**
     *
     * @return long
     */
    public static long getTimeout() {
        return timeout;
    }

    /**
     *
     * @return long
     */
    public static long sleepStart() {
        return sleepStart;
    }

    /**
     *
     * @return long
     */
    public static long timeoutStart() {
        return timeoutStart;
    }

    /**
     * ConfigInitialize
     * @param config DriverBaseConfig
     * @return DriverBaseConfig
     * @throws ReflectiveOperationException throws
     */
    public static DriverBaseConfig init(DriverBaseConfig config) throws ReflectiveOperationException {
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
    public static void init(String app, String... params) throws IOException, IllegalAccessException {
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
    public static <T extends WebDriver> T start(String app, String... params) throws Exception {
        if (params.length > 0) init(app, params);
        return start(cap(app));
    }

    /**
     * Run root application session
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T startRoot() throws Exception {
        debug("startRoot");
        return (T) start(getWinDriver());
    }

    /**
     *
     * @param app String
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T startAttach(String app) throws Exception {
        return startAttach(app, sleep);
    }

    /**
     *
     * @param app String
     * @param sleep long
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T startAttach(String app, long sleep) throws Exception {
        return startAttach(app, sleep, timeout);
    }

    /**
     *
     * @param app String
     * @param sleep long
     * @param timeout long
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T startAttach(String app, long sleep, long timeout) throws Exception {
        return start(sleep, timeout, () -> attachApp(app));
    }

    /**
     *
     * @param appClass String
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T startAttachClass(String appClass) throws Exception {
        return startAttachClass(appClass, sleep);
    }

    /**
     *
     * @param appClass String
     * @param sleep long
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T startAttachClass(String appClass, long sleep) throws Exception {
        return startAttachClass(appClass, sleep, timeout);
    }

    /**
     *
     * @param appClass String
     * @param sleep long
     * @param timeout long
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T startAttachClass(String appClass, long sleep, long timeout) throws Exception {
        return start(sleep, timeout, () -> attachAppClass(appClass));
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
        return start(cap, sleep, timeout);
    }

    /**
     *
     * @param cap DesiredCapabilities
     * @param sleep long
     * @param timeout long
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T start(DesiredCapabilities cap, long sleep, long timeout) throws Exception {
        return start(cap, 2, sleep, timeout);
    }

    /**
     *
     * @param cap DesiredCapabilities
     * @param index int
     * @param sleep long
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T start(DesiredCapabilities cap, int index, long sleep) throws Exception {
        return start(cap, index, sleep, timeout);
    }

    /**
     *
     * @param cap DesiredCapabilities
     * @param index int
     * @param sleep long
     * @param timeout long
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T start(DesiredCapabilities cap, int index, long sleep, long timeout) throws Exception {
        return start(sleep, timeout, () -> getDriver(cap, index + 4));
    }

    /**
     *
     * @param cb SupplierWithExceptions {T, E}
     * @return T
     * @param <T> extends WebDriver
     * @param <E> extends Exception
     * @throws Exception throws
     */
    public static <T extends WebDriver, E extends Exception> T start(SupplierWithExceptions<T, E> cb) throws Exception {
        return start(sleep, cb);
    }

    /**
     *
     * @param sleep long
     * @param cb SupplierWithExceptions {T, E}
     * @return T
     * @param <T> extends WebDriver
     * @param <E> extends Exception
     * @throws Exception throws
     */
    public static <T extends WebDriver, E extends Exception> T start(long sleep, SupplierWithExceptions<T, E> cb) throws Exception {
        return start(sleep, timeout, cb);
    }

    /**
     *
     * @param sleep long
     * @param timeout long
     * @param cb SupplierWithExceptions {T, E}
     * @return T
     * @param <T> extends WebDriver
     * @param <E> extends Exception
     * @throws Exception throws
     */
    public static <T extends WebDriver, E extends Exception> T start(long sleep, long timeout, SupplierWithExceptions<T, E> cb) throws Exception {
        if (sleep == 0) {
            open();
            return cb.get();
        } else return setTimeout(sleep, timeout, () -> { open(); return null; }, o -> cb.get());
    }

    /**
     *
     * @param driver T
     * @return T
     * @param <T> extends RemoteWebDriver
     * @throws MalformedURLException throws
     * @throws ClassNotFoundException throws
     */
    public static <T extends org.openqa.selenium.remote.RemoteWebDriver> T start(T driver) throws MalformedURLException, ClassNotFoundException {
        return driver(driver);
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
     * @throws MalformedURLException throws
     * @throws InterruptedException throws
     */
    public static void openTimeout() throws MalformedURLException, InterruptedException {
        open(sleep);
    }

    /**
     *
     * @param sleep long
     * @throws MalformedURLException throws
     * @throws InterruptedException throws
     */
    public static void open(long sleep) throws MalformedURLException, InterruptedException {
        open(sleep, timeout);
    }

    /**
     *
     * @param sleep long
     * @param timeout long
     * @throws MalformedURLException throws
     * @throws InterruptedException throws
     */
    public static void open(long sleep, long timeout) throws MalformedURLException, InterruptedException {
        setTimeout(sleep, timeout, () -> { open(); return null; });
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
    public static <T extends WebDriver> T getDriver(DesiredCapabilities cap) throws Exception {
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
    public static <T extends WebDriver> T getDriver(DesiredCapabilities cap, int index) throws Exception {
        // Прикрепить переменную драйвера к собственно Winium драйверу
        //return getDriver(cap, getCallingClassSimpleName(index + 3));
        return getDriver(cap, getCallingClassSimpleName(index, c -> isExtends(c, RemoteWebDriver.class)));
    }

    /**
     * Прикрепить переменную драйвера к собственно Winium драйверу
     * @param cap DesiredCapabilities
     * @param className String
     * @return T
     * @param <T> extends WebDriver
     * @throws MalformedURLException throws
     * @throws ClassNotFoundException throws
     */
    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T getDriver(DesiredCapabilities cap, String className) throws Exception {
        return (T) (switch (className) {
            case "WinDriver": yield getWinDriver(cap);
            case "WebDriver": yield getWebDriver(cap);
            default: yield getRemoteDriver(cap);
        });
    }

    /**
     *
     * @return WindowsDriver {WebElement}
     * @throws Exception throws
     */
    public static WindowsDriver<org.openqa.selenium.WebElement> getWinDriver() throws Exception {
        return notNull(r) ? r : root(getWinDriver(cap("Root")));
    }

    /**
     *
     * @param handleHex String
     * @return WindowsDriver {WebElement}
     * @throws Exception throws
     */
    public static WindowsDriver<org.openqa.selenium.WebElement> getWinDriver(String handleHex) throws Exception {
        return getWinDriver(cap(handleHex, true));
    }

    /**
     *
     * @param cap DesiredCapabilities
     * @return WindowsDriver {WebElement}
     * @throws Exception throws
     */
    @SuppressWarnings("unchecked")
    public static WindowsDriver<org.openqa.selenium.WebElement> getWinDriver(DesiredCapabilities cap) throws Exception {
        return winDriver(WinDriver.driver(getDriver(windowsDriver, cap)));
    }

    /**
     *
     * @param options ChromeOptions
     * @return ChromeDriver
     * @throws MalformedURLException throws
     * @throws ReflectiveOperationException throws
     */
    public static ChromeDriver getWebDriver(ChromeOptions options) throws MalformedURLException, ReflectiveOperationException {
        return getWebDriver(() -> options);
    }

    /**
     *
     * @param cap DesiredCapabilities
     * @return ChromeDriver
     * @throws MalformedURLException throws
     * @throws ReflectiveOperationException throws
     */
    public static ChromeDriver getWebDriver(DesiredCapabilities cap) throws MalformedURLException, ReflectiveOperationException {
        return getWebDriver(() -> new HashMap<>(org.openqa.selenium.Capabilities.class).values(cap));
    }

    /**
     *
     * @param cb SupplierWithExceptions {Object, E}
     * @return ChromeDriver
     * @param <E> extends Exception
     * @throws MalformedURLException throws
     * @throws ReflectiveOperationException throws
     * @throws E throws
     */
    public static <E extends Exception> ChromeDriver getWebDriver(SupplierWithExceptions<Object, E> cb)
        throws MalformedURLException, ReflectiveOperationException, E
    {
        return webDriver(start(create(webDriver, cb.get())));
    }

    /**
     *
     * @param cap DesiredCapabilities
     * @return org.openqa.selenium.remote.RemoteWebDriver
     * @throws Exception throws
     */
    public static org.openqa.selenium.remote.RemoteWebDriver getRemoteDriver(DesiredCapabilities cap) throws Exception {
        return remoteDriver(getDriver(remoteDriver, cap));
    }

    /**
     *
     * @param clazz Class {T}
     * @param cap DesiredCapabilities
     * @return T
     * @param <T> extends RemoteWebDriver
     * @throws Exception throws
     */
    public static <T extends org.openqa.selenium.remote.RemoteWebDriver> T getDriver(Class<T> clazz, DesiredCapabilities cap) throws Exception {
        return start(create(clazz, new HashMap<>(URL.class, org.openqa.selenium.Capabilities.class).values(new URL(winDriverHost), cap)));
    }

    /**
     * You attach to the already running application
     * @param app String
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T attachApp(String app) throws Exception {
        return attachAppHex(handleHex(app));
    }

    /**
     * You attach to the already running application
     * @param appClass String
     * @return T
     * @param <T> extends WebDriver
     * @throws Exception throws
     */
    public static <T extends WebDriver> T attachAppClass(String appClass) throws Exception {
        return attachAppHex(handleHexClass(appClass));
    }

    /**
     * You attach to the already running application
     * @param handleHex String
     * @return T
     * @param <T> extends WebDriver
     */
    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T attachAppHex(String handleHex) {
        try {
            debug("handleHex: " + handleHex);
            return (T) start(getWinDriver(handleHex));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Here you find the already running application and get the handle
     * @param app String
     * @return String
     * @throws Exception throws
     */
    public static String handleHex(String app) throws Exception {
        return handleHex(d -> findByName(app));
    }

    /**
     * Here you find the already running application and get the handle
     * @param appClass String
     * @return String
     * @throws Exception throws
     */
    public static String handleHexClass(String appClass) throws Exception {
        return handleHex(d -> findByClass(appClass));
    }

    /**
     * Here you find the already running application and get the handle
     * @param cb Function {WindowsDriver, String}
     * @return String
     * @param <T> extends RemoteWebDriver
     * @throws Exception throws
     */
    public static <T extends org.openqa.selenium.remote.RemoteWebDriver> String handleHex(Function<T, org.openqa.selenium.WebElement> cb) throws Exception {
        debug("handleHex");
        org.openqa.selenium.WebElement handleEl = cb.apply(startRoot());
        debug("handleEl: " + handleEl);
        String handleStr = handleEl.getAttribute("NativeWindowHandle");
        int handleInt = parseInt(handleStr);
        return toHexString(handleInt);
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
        d = null;
    }

    /**
     *
     * @param driver WebDriver
     * @param driverName String
     */
    protected static void quit(WebDriver driver, String driverName) {
        // The instance of WinAppDriver will be freed once last test is complete
        quit(driver);
        stop(driverName);
    }

    /**
     *
     * @param driver WebDriver
     */
    protected static void quit(WebDriver driver) {
        debug("quit: " + driver);
        if (driver != null) driver.quit();
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
        stop(driverName, sleep);
    }

    /**
     *
     * @param sleep long
     * @param driverName String
     */
    protected static void stop(String driverName, long sleep) {
        stop(driverName, sleep, timeout);
    }

    /**
     *
     * @param driverName String
     * @param sleep long
     * @param timeout long
     */
    protected static void stop(String driverName, long sleep, long timeout) {
        debug("stop: " + driverName);
        debug("stop: " + sleep);
        try {
            if (sleep == 0) {
                new Process("taskkill ", "/f", "/IM", driverName);
            } else setTimeout(sleep, timeout, () -> { new Process("taskkill ", "/f", "/IM", driverName); return null; });
        }
        catch (IOException e) {
            e.printStackTrace();
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
     */
    public static void timeout() {
        timeout(sleep);
    }

    /**
     *
     * @param t long
     */
    public static void timeout(long t) {
        timeout(t, MILLISECONDS);
    }

    /**
     *
     * @param t long
     */
    public static void timeoutS(long t) {
        timeout(ofSeconds(t).getSeconds(), SECONDS);
    }

    /**
     *
     * @param t long
     */
    public static void timeout(long t, TimeUnit timeUnit) {
        d.manage().timeouts().implicitlyWait(t, timeUnit);
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
