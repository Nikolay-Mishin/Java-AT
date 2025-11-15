package org.project.utils.windriver;

import static java.util.Arrays.stream;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.openqa.selenium.Keys.*;

import io.appium.java_client.*;
import io.appium.java_client.driverscripts.ScriptOptions;
import io.appium.java_client.driverscripts.ScriptValue;
import io.appium.java_client.screenrecording.*;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.http.HttpMethod;
import org.testng.Assert;

import static org.project.utils.Helper.*;
import static org.project.utils.Process.run;
import static org.project.utils.reflection.Reflection.*;
import static org.project.utils.windriver.DriverBaseConfig.*;

import org.project.utils.constant.Capabilities;
import org.project.utils.Process;

public class WinDriver<T> {
    protected static DriverBaseConfig c = BASE_CONFIG;
    //protected static WebDriver driver;
    protected static WindowsDriver<WebElement> driver;
    protected static DesiredCapabilities cap = new DesiredCapabilities();
    protected static String winDriver = WINDRIVER;
    protected static String winDriverName = WINDRIVER_NAME;
    protected static String winDriverHost = WINDRIVER_HOST;
    protected static boolean experimental = c.getExperimental();
    protected static Process p;
    protected static ProcessBuilder pb;
    protected static Actions action;

    public WinDriver() throws ClassNotFoundException {
        gen();
    }

    public void gen() throws ClassNotFoundException {
        debug(getGenericClass());
    }

    public static DriverBaseConfig config() {
        return c;
    }

    //[ConfigInitialize]
    public static DriverBaseConfig config(DriverBaseConfig config)  {
        return c = init(config);
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T driver() {
        return (T) driver;
    }

    public static <T extends WebDriver> T driver(T driver) {
        return (T) (WinDriver.driver = (WindowsDriver<WebElement>) driver);
    }

    public static DesiredCapabilities cap() {
        return cap;
    }

    public static org.openqa.selenium.Capabilities getCapabilities() {
        return driver.getCapabilities();
    }

    public static DesiredCapabilities cap(DesiredCapabilities cap) {
        return WinDriver.cap = cap;
    }

    public static Actions action() {
        return action;
    }

    public static Actions action(Actions action) {
        return WinDriver.action = action;
    }

    public static <T extends WebDriver> Actions action(T driver) {
        return action = new Actions(driver);
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
    // public static WebDriver start() throws MalformedURLException, IllegalAccessException {
    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T start() throws MalformedURLException, IllegalAccessException, ClassNotFoundException {
        return (T) start(setCap());
    }

    public static WindowsDriver<WebElement> start(String app, String... params) throws IOException, IllegalAccessException, ClassNotFoundException {
        start();
        run(app, params);
        return driver;
    }

    public static WindowsDriver<WebElement> start(DriverBaseConfig config) throws MalformedURLException, IllegalAccessException, ClassNotFoundException {
        config(config);
        start();
        return driver;
    }

    //public static WebDriver start(DesiredCapabilities cap) throws MalformedURLException, IllegalAccessException {
    public static <T extends WebDriver> T start(DesiredCapabilities cap) throws MalformedURLException, ClassNotFoundException {
        open();
        return (T) start(new WindowsDriver<>(new URL(winDriverHost), cap));
    }

    //public static WebDriver start(DesiredCapabilities cap) throws MalformedURLException, IllegalAccessException {
    public static <T extends WebDriver> T start(T driver) throws MalformedURLException, ClassNotFoundException {
        Assert.assertNotNull(driver);
        // Прикрепить переменную драйвера к собственно Winium драйверу
        //driver = new RemoteWebDriver(new URL(appDriverUrl), cap); //на этом порту по умолчанию висит Winium драйвер
        //invoke();
        debug(getCallingClassName());
        debug(getCallingChildClassName());
        driver(driver); //на этом порту по умолчанию висит Winium драйвер
        action(driver);
        return driver;
    }

    public static void open() throws MalformedURLException {
        open(winDriver);
    }

    public static void open(String app) throws MalformedURLException {
        Process.open(app);
    }

    // Navigate to the webpage where localStorage data is stored
    public static <T extends WebDriver> T get(String url) {
        driver.get(url);
        return (T) driver;
    }

    //[Capabilities]
    public static DesiredCapabilities setCap() throws IllegalAccessException {
        if (experimental) cap.setCapability("ms:experimental-webdriver", experimental);
        Map<String, Object> map = getObjectFields(new Capabilities());
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
        quit(driver, driverName);
        driver = null;
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
            driver = null;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void timeout(long t) {
        //webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(t));
        driver.manage().timeouts().implicitlyWait(t, TimeUnit.MILLISECONDS);
    }

    public static void timeout() {
        timeout(1000);
    }

    public static void setLocation(Location loc) {
        driver.setLocation(loc);
    }

    public static String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public static URL getRemoteAddress() {
        return driver.getRemoteAddress();
    }

    public static String getContext() {
        return driver.getContext();
    }

    public static void setLogLevel(Level lvl) {
        driver.setLogLevel(lvl);
    }

    public static void resetApp() {
        driver.resetApp();
    }

    public static Keyboard getKeyboard() {
        return driver.getKeyboard();
    }

    public static Mouse getMouse() {
        return driver.getMouse();
    }

    public static ScreenOrientation getOrientation() {
        return driver.getOrientation();
    }

    public static String getAutomationName() {
        return driver.getAutomationName();
    }

    public static String getTitle() {
        return driver.getTitle();
    }

    public static Map<String, Object> getStatus() {
        return driver.getStatus();
    }

    public static String getPageSource() {
        return driver.getPageSource();
    }

    public static String getDeviceTime() {
        return driver.getDeviceTime();
    }

    public static String getDeviceTime(String format) {
        return driver.getDeviceTime(format);
    }

    public static Map<String, String> getAppStringMap() {
        return driver.getAppStringMap();
    }

    public static SessionId getSessionId() {
        return driver.getSessionId();
    }

    public static List<Map<String, Object>> getAllSessionDetails() {
        return driver.getAllSessionDetails();
    }

    public static Map<String, Object> getSessionDetails() {
        return driver.getSessionDetails();
    }

    public static Object getSessionDetail(String detail) {
        return driver.getSessionDetail(detail);
    }

    public static Map<String, Object> getSettings() {
        return driver.getSettings();
    }

    public static HasSettings setSetting(Setting setting, Object value) {
        return driver.setSetting(setting, value);
    }

    public static HasSettings setSetting(String settingName, Object value) {
        return driver.setSetting(settingName, value);
    }

    public static HasSettings setSettings(Map<String, Object> settings) {
        return driver.setSettings(settings);
    }

    public static HasSettings setSettings(EnumMap<Setting, Object> settings) {
        return driver.setSettings(settings);
    }

    public static ExecuteMethod getExecuteMethod() {
        return driver.getExecuteMethod();
    }

    public static Object execMethod(String s, Map<String, ?> map) {
        return getExecuteMethod().execute(s, map);
    }

    public static void addCommand(HttpMethod httpMethod, String url, String methodName) {
        driver.addCommand(httpMethod, url, methodName);
    }

    public static CommandExecutor getCommandExecutor() {
        return driver.getCommandExecutor();
    }

    public static Response execCommand(Command command) throws IOException {
        return getCommandExecutor().execute(command);
    }

    public static Response execCommand(SessionId sessionId, String name, Map<String, ?> params) throws IOException {
        return getCommandExecutor().execute(getCommand(sessionId, name, params));
    }

    public static Command getCommand(SessionId sessionId, String name, Map<String, ?> params) {
        return new Command(sessionId, name, params);
    }

    public static Response execute(String command) {
        return driver.execute(command);
    }

    public static Response execute(String driverCommand, Map<String, ?> parameters) {
        return driver.execute(driverCommand, parameters);
    }

    public static Object executeScript(String script, Object... arg) {
        return driver.executeScript(script, arg);
    }

    public static Object executeAsyncScript(String script, Object... arg) {
        return driver.executeAsyncScript(script, arg);
    }

    public static ScriptValue executeScript(String script) {
        return driver.executeDriverScript(script);
    }

    public static ScriptValue executeScript(String driverCommand, ScriptOptions options) {
        return driver.executeDriverScript(driverCommand, options);
    }

    public static <X> X getScreenshotAs(OutputType<X> outputType) {
        return driver.getScreenshotAs(outputType);
    }

    public static String startRecordingScreen() {
        return driver.startRecordingScreen();
    }

    public static <T extends BaseStartScreenRecordingOptions> String startRecordingScreen(T options) {
        return driver.startRecordingScreen(options);
    }

    public static String stopRecordingScreen() {
        return driver.stopRecordingScreen();
    }

    public static <T extends BaseStopScreenRecordingOptions> String stopRecordingScreen(T options) {
        return driver.stopRecordingScreen(options);
    }

    /**
     * Основным элементом взаимодействия с внешним миром являются «рычаги».
     * Их можно удобно описать классом с полями name, className и automationId и хранить в отдельных классах по блокам.
     * By.name – по полю Name, By.className — по полю ClassName и By.xpath для более изощрённых условий поиска
     * Так же мы можем прикрепляться к элементам другого элемента
     */
    public static WebElement find(By by) {
        return driver.findElement(by);
    }

    public static WebElement find(WebElement el, By by) {
        return el.findElement(by);
    }

    //список элементов - элементы будут добавлены в порядке tab-ордера
    public static List<WebElement> list(By by) {
        return driver.findElements(by);
    }

    public static List<WebElement> list(WebElement el, By by) {
        return el.findElements(by);
    }

    //один элемент, поиск по полю AccessibilityId
    public static WebElement findByAccessId(String id) {
        return driver.findElementByAccessibilityId(id);
    }

    //один элемент, поиск по полю ID
    public static WebElement findById(String id) {
        //return find(By.id(id));
        return driver.findElementById(id);
    }

    public static WebElement findById(WebElement el, String id) {
        return find(el, By.id(id));
    }

    //один элемент, поиск по полю Name
    public static WebElement findByName(String name) {
        //return find(By.name(name));
        return driver.findElementByName(name);
    }

    public static WebElement findByName(WebElement el, String name) {
        return find(el, By.name(name));
    }

    //один элемент, поиск по полю ClassName
    public static WebElement findByClass(String className) {
        //return find(By.className(className));
        return driver.findElementByClassName(className);
    }

    public static WebElement findByClass(WebElement el, String className) {
        return find(el, By.className(className));
    }

    //один элемент, поиск по полю ClassName
    public static WebElement findByTag(String tagName) {
        //return find(By.className(className));
        return driver.findElementByTagName(tagName);
    }

    public static WebElement findByTag(WebElement el, String tagName) {
        return find(el, By.tagName(tagName));
    }

    /**
     * Если первые два механизма прикрепления очень узкоспециализированы - работают строго в иерархической структуре и строго с полями Name и ClassName,
     * то для работы с иными случаями нам потребуется третий механизм, а именно By.xpath.
     * В этом механизме всё строго по канонам использования xpath (во всяком случае, с использованных случаях всё работало как нужно).
     * С помощью xpath можно получить и обрабатывать поля IsOffscreen, AutomationId, HasKeyboardFocus:
     */
    // найдёт элемент, на котором стоит фокус
    public static WebElement findByXpath(String xpath) {
        //return find(By.xpath(xpath));
        return driver.findElementByXPath(xpath);
    }

    public static WebElement findByXpath(WebElement el, String xpath) {
        return find(el, By.xpath(xpath));
    }

    //один элемент, поиск по полю Css
    public static WebElement findByCss(String css) {
        //return find(By.className(className));
        return driver.findElementByCssSelector(css);
    }

    public static WebElement findByCss(WebElement el, String css) {
        return find(el, By.cssSelector(css));
    }

    //один элемент, поиск по полю Link
    public static WebElement findByLink(String css) {
        //return find(By.className(className));
        return driver.findElementByLinkText(css);
    }

    public static WebElement findByLink(WebElement el, String css) {
        return find(el, By.linkText(css));
    }

    //один элемент, поиск по полю PartialLink
    public static WebElement findByPartLink(String css) {
        //return find(By.className(className));
        return driver.findElementByPartialLinkText(css);
    }

    public static WebElement findByPartLink(WebElement el, String css) {
        return find(el, By.partialLinkText(css));
    }

    //один элемент, поиск по полю AutomationID
    public static WebElement findByAid(String id) {
        return driver.findElementByWindowsUIAutomation(id);
    }

    //один элемент, поиск по полю Image
    public static WebElement findByImage(String b64) {
        return driver.findElementByImage(b64);
    }

    //один элемент, поиск по полю Custom Selector
    public static WebElement findBySelector(String selector) {
        return driver.findElementByCustom(selector);
    }

    //один элемент, поиск по полю AccessibilityId
    public static List<WebElement> listByAccessId(String id) {
        return driver.findElementsByAccessibilityId(id);
    }

    //один элемент, поиск по полю ID
    public static List<WebElement> listById(String id) {
        //return find(By.id(id));
        return driver.findElementsById(id);
    }

    public static List<WebElement> listById(WebElement el, String id) {
        return list(el, By.id(id));
    }

    public static List<WebElement> listByName(String name) {
        //return list(By.name(name));
        return driver.findElementsByName(name);
    }

    public static List<WebElement> listByName(WebElement el, String name) {
        return list(el, By.name(name));
    }

    public static List<WebElement> listByClass(String className) {
        //return list(By.className(className));
        return driver.findElementsByClassName(className);
    }

    public static List<WebElement> listByClass(WebElement el, String className) {
        return list(el, By.className(className));
    }

    //один элемент, поиск по полю ClassName
    public static List<WebElement> listByTag(String tagName) {
        //return find(By.className(className));
        return driver.findElementsByTagName(tagName);
    }

    public static List<WebElement> listByTag(WebElement el, String tagName) {
        return list(el, By.tagName(tagName));
    }

    // найдёт элемент, на котором стоит фокус
    public static List<WebElement> listByXpath(String xpath) {
        //return list(By.xpath(xpath));
        return driver.findElementsByXPath(xpath);
    }

    public static List<WebElement> listByXpath(WebElement el, String xpath) {
        return list(el, By.xpath(xpath));
    }

    //один элемент, поиск по полю Css
    public static List<WebElement> listByCss(String css) {
        //return find(By.className(className));
        return driver.findElementsByCssSelector(css);
    }

    public static List<WebElement> listByCss(WebElement el, String css) {
        return list(el, By.cssSelector(css));
    }

    //один элемент, поиск по полю Link
    public static List<WebElement> listByLink(String css) {
        //return find(By.className(className));
        return driver.findElementsByLinkText(css);
    }

    public static List<WebElement> listByLink(WebElement el, String css) {
        return list(el, By.linkText(css));
    }

    //один элемент, поиск по полю PartialLink
    public static List<WebElement> listByPartLink(String css) {
        //return find(By.className(className));
        return driver.findElementsByPartialLinkText(css);
    }

    public static List<WebElement> listByPartLink(WebElement el, String css) {
        return list(el, By.partialLinkText(css));
    }

    //один элемент, поиск по полю AutomationID
    public static List<WebElement> listByAid(String id) {
        return driver.findElementsByWindowsUIAutomation(id);
    }

    //один элемент, поиск по полю Image
    public static List<WebElement> listByImage(String b64) {
        return driver.findElementsByImage(b64);
    }

    //один элемент, поиск по полю Custom Selector
    public static List<WebElement> listBySelector(String selector) {
        return driver.findElementsByCustom(selector);
    }

    /**
     * Также нам может потребоваться имитация ввода клавиш с клавиатуры.
     * В обычном порядке это делается методом sendKeys(«Последовательность символов») у элемента.
     * Однако, следует помнить, что некоторые символы используются как служебные и их надо экранировать
     * (например, "+" это Shift, и для того, чтобы ввести "+", нужно передать последовательность "+=").
     * Для удобства пользования кодом можно написать обёртку, которая бы автоматически заменяла все "+" на "+=", но тут как кому удобнее.
     * Подробнее ознакомиться со стандартами передач комбинаций клавиш можно, например, тут.
     * Тем не менее, возникали проблемы с корректной передачей нажатий стрелок на клавиатуре, так что к сожалению,
     * при текущей версии драйвера придётся искать обходные пути.
     * где wrk – имя WebElement, от центра которого будем двигать мышкой;
     * x, y – расстояние, на которое будем двигать (положительное значение x двигает курсор вправо, положительное y – вниз).
     */
    public static Actions move(WebElement el) {
        return action.moveToElement(el);
    }

    public static Actions move(WebElement el, int x, int y) {
        return action.moveToElement(el, x, y);
    }

    public static Actions move(int x, int y) {
        return action.moveByOffset(x, y);
    }

    public static Actions click() {
        return action.click();
    }

    public static Actions click(WebElement el) {
        return action.click(el);
    }

    public static Actions doubleClick() {
        return action.doubleClick();
    }

    public static Actions doubleClick(WebElement el) {
        return action.doubleClick(el);
    }

    public static Actions clickAndHold() {
        return action.clickAndHold();
    }

    public static Actions clickAndHold(WebElement el) {
        return action.clickAndHold(el);
    }

    public static Actions contextClick() {
        return action.contextClick();
    }

    public static Actions contextClick(WebElement el) {
        return action.contextClick(el);
    }

    public static Actions dragAndDrop(WebElement el, WebElement target) {
        return action.dragAndDrop(el, target);
    }

    public static Actions dragAndDrop(WebElement el, int x, int y) {
        return action.dragAndDropBy(el, x, y);
    }

    public static Actions keys(CharSequence... keys) {
        return action.sendKeys(keys);
    }

    public static Actions keys(WebElement el, CharSequence... keys) {
        return action.sendKeys(el, keys);
    }

    public static void keysEl(WebElement el, CharSequence... keys) {
        el.sendKeys(keys);
    }

    /*public static Actions chord(CharSequence key, CharSequence... keys) {
        return keys(key, Keys.chord(keys));
    }*/

    public static Actions chord(CharSequence key, CharSequence... keys) {
        return keys(Keys.chord(key, (CharSequence) stream(keys)));
    }

    public static Actions chord(WebElement el, CharSequence... keys) {
        return keys(el, Keys.chord(keys));
    }

    public static void chordEl(WebElement el, CharSequence... keys) {
        keysEl(el, Keys.chord(keys));
    }

    public static Actions down(CharSequence key) {
        return action.keyDown(key);
    }

    public static Actions down(WebElement el, CharSequence key) {
        return action.keyDown(el, key);
    }

    public static Actions up(CharSequence key) {
        return action.keyUp(key);
    }

    public static Actions up(WebElement el, CharSequence key) {
        return action.keyUp(el, key);
    }

    public static Actions ctrl(CharSequence... keys) {
        return chord(CONTROL, keys);
    }

    public static Actions alt(CharSequence... keys) {
        return chord(ALT, keys);
    }

    public static Actions shift(CharSequence... keys) {
        return chord(SHIFT, keys);
    }

    public static Actions leftCtrl(CharSequence... keys) {
        return chord(LEFT_CONTROL, keys);
    }

    public static Actions leftAlt(CharSequence... keys) {
        return chord(LEFT_ALT, keys);
    }

    public static Actions leftShift(CharSequence... keys) {
        return chord(LEFT_SHIFT, keys);
    }

    public static Actions enter() {
        return chord(ENTER);
    }

    public static Actions esc() {
        return chord(CANCEL);
    }

    public static Actions delete() {
        return chord(DELETE);
    }

    public static Actions space() {
        return chord(SPACE);
    }

    public static Actions tab() {
        return chord(TAB);
    }

    public static Actions backSpace() {
        return chord(BACK_SPACE);
    }

    public static Actions arrowUp() {
        return chord(ARROW_UP);
    }

    public static Actions arrowDown() {
        return chord(ARROW_DOWN);
    }

    public static Actions arrowRight() {
        return chord(ARROW_RIGHT);
    }

    public static Actions arrowLeft() {
        return chord(ARROW_LEFT);
    }

    public static Actions copy() {
        return ctrl("c");
    }

    public static Actions paste() {
        return ctrl("v");
    }

    public static Actions save() {
        return ctrl("s");
    }

    public static Actions selectAll() {
        return ctrl("a");
    }

    public static Actions saveFile() {
        return alt("s");
    }

    public static Actions saveFile(String filePath) {
        action.sendKeys(filePath);
        return saveFile();
    }

    public static Action build() {
        return action.build();
    }

    public static Actions perform() {
        action.perform();
        return action;
    }

    public static Action performBuild() {
        Action action = build();
        action.perform();
        return action;
    }
}
