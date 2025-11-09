package org.project.utils.windriver;

import static java.awt.Desktop.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import static org.project.utils.Helper.*;

import org.project.utils.constant.Capabilities;
import org.project.utils.Process;

public class WinDriver {
    protected static DriverBaseConfig c = config();
    //protected static WebDriver driver;
    protected static WindowsDriver<WebElement> driver;
    protected static DesiredCapabilities cap = new DesiredCapabilities();
    protected static final String winDriver = c.getWindriver();
    protected static final String winDriverName = c.getWindriverName();
    protected static final boolean experimental = c.getExperimental();
    protected static Process p;
    protected static ProcessBuilder pb;
    protected static Actions action;

    public static DriverBaseConfig config() {
        return c;
    }

    //[ConfigInitialize]
    public static DriverBaseConfig config(DriverBaseConfig config) {
        return c = config;
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T driver() {
        return (T) driver;
    }

    public static WindowsDriver<WebElement> driver(WindowsDriver<WebElement> driver) {
        return WinDriver.driver = driver;
    }

    public static DesiredCapabilities cap() {
        return cap;
    }

    public static DesiredCapabilities cap(DesiredCapabilities cap) {
        return WinDriver.cap = cap;
    }

    //[ProcessInitialize]
    public static void init() throws IOException {
        init(winDriver, c.getWebdriverParam());
    }

    protected static void init(String app, String... param) throws IOException {
        p = new Process(app, Arrays.toString(param));
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
    public static WindowsDriver<WebElement> start() throws MalformedURLException, IllegalAccessException {
        return start(setCap());
    }

    //[ClassInitialize]
    //public static WebDriver start(DesiredCapabilities cap) throws MalformedURLException, IllegalAccessException {
    public static WindowsDriver<WebElement> start(DesiredCapabilities cap) throws MalformedURLException, IllegalAccessException {
        open();
        // Прикрепить переменную драйвера к собственно Winium драйверу
        //driver = new RemoteWebDriver(new URL(appDriverUrl), cap); //на этом порту по умолчанию висит Winium драйвер
        driver = new WindowsDriver<>(new URL(c.getWindriverHost()), cap); //на этом порту по умолчанию висит Winium драйвер
        Assert.assertNotNull(driver);
        return driver;
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
        return cap;
    }

    public static void open() {
        try {
            Desktop desktop = getDesktop();

            File file = new File(winDriver);

            /* Check if there is support for Desktop or not */
            if(!isDesktopSupported()) {
                debug("not supported");
                return;
            }

            if (file.exists()) {
                debug("Open " + winDriver + "\n");
                desktop.open(file);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            debug("Encountered Exception\n");
            throw new RuntimeException(e);
        }
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
    public static List<WebElement> findList(By by) {
        return driver.findElements(by);
    }

    public static List<WebElement> findList(WebElement el, By by) {
        return el.findElements(by);
    }

    //один элемент, поиск по полю AccessibilityId
    public static WebElement findByAccessibilityId(String id) {
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
    public static WebElement findByClassName(String className) {
        //return find(By.className(className));
        return driver.findElementByClassName(className);
    }

    public static WebElement findByClassName(WebElement el, String className) {
        return find(el, By.className(className));
    }

    //один элемент, поиск по полю ClassName
    public static WebElement findByTagName(String tagName) {
        //return find(By.className(className));
        return driver.findElementByTagName(tagName);
    }

    public static WebElement findByTagName(WebElement el, String tagName) {
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
    public static WebElement findByAuto(String id) {
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
    public static List<WebElement> findListByAccessibilityId(String id) {
        return driver.findElementsByAccessibilityId(id);
    }

    //один элемент, поиск по полю ID
    public static List<WebElement> findListById(String id) {
        //return find(By.id(id));
        return driver.findElementsById(id);
    }

    public static List<WebElement> findListById(WebElement el, String id) {
        return findList(el, By.id(id));
    }

    public static List<WebElement> findListByName(String name) {
        //return findList(By.name(name));
        return driver.findElementsByName(name);
    }

    public static List<WebElement> findListByName(WebElement el, String name) {
        return findList(el, By.name(name));
    }

    public static List<WebElement> findListByClassName(String className) {
        //return findList(By.className(className));
        return driver.findElementsByClassName(className);
    }

    public static List<WebElement> findListByClassName(WebElement el, String className) {
        return findList(el, By.className(className));
    }

    //один элемент, поиск по полю ClassName
    public static List<WebElement> findListByTagName(String tagName) {
        //return find(By.className(className));
        return driver.findElementsByTagName(tagName);
    }

    public static List<WebElement> findListByTagName(WebElement el, String tagName) {
        return findList(el, By.tagName(tagName));
    }

    // найдёт элемент, на котором стоит фокус
    public static List<WebElement> findListByXpath(String xpath) {
        //return findList(By.xpath(xpath));
        return driver.findElementsByXPath(xpath);
    }

    public static List<WebElement> findListByXpath(WebElement el, String xpath) {
        return findList(el, By.xpath(xpath));
    }

    //один элемент, поиск по полю Css
    public static List<WebElement> findListByCss(String css) {
        //return find(By.className(className));
        return driver.findElementsByCssSelector(css);
    }

    public static List<WebElement> findListByCss(WebElement el, String css) {
        return findList(el, By.cssSelector(css));
    }

    //один элемент, поиск по полю Link
    public static List<WebElement> findListByLink(String css) {
        //return find(By.className(className));
        return driver.findElementsByLinkText(css);
    }

    public static List<WebElement> findListByLink(WebElement el, String css) {
        return findList(el, By.linkText(css));
    }

    //один элемент, поиск по полю PartialLink
    public static List<WebElement> findListByPartLink(String css) {
        //return find(By.className(className));
        return driver.findElementsByPartialLinkText(css);
    }

    public static List<WebElement> findListByPartLink(WebElement el, String css) {
        return findList(el, By.partialLinkText(css));
    }

    //один элемент, поиск по полю AutomationID
    public static List<WebElement> findListByAuto(String id) {
        return driver.findElementsByWindowsUIAutomation(id);
    }

    //один элемент, поиск по полю Image
    public static List<WebElement> findListByImage(String b64) {
        return driver.findElementsByImage(b64);
    }

    //один элемент, поиск по полю Custom Selector
    public static List<WebElement> findListBySelector(String selector) {
        return driver.findElementsByCustom(selector);
    }

    public static void timeout(long t) {
        //webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(t));
        driver.manage().timeouts().implicitlyWait(t, TimeUnit.MILLISECONDS);
    }

    public static void timeout() {
        timeout(1000);
    }

    public static Actions action() {
        return getAction();
    }

    public static Actions setAction() {
        return action = new Actions(driver);
    }

    protected static Actions getAction() {
        return action != null ? action : setAction();
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
    public static Actions move(WebElement el) throws InterruptedException {
        return action().moveToElement(el);
    }

    public static Actions move(int x, int y) throws InterruptedException {
        return action().moveByOffset(x, y);
    }

    public static Actions move(WebElement el, int x, int y) throws InterruptedException {
        return move(el).moveByOffset(x, y);
    }

    public static Actions click() throws InterruptedException {
        return action().click();
    }

    public static Actions click(WebElement el) throws InterruptedException {
        return action().click(el);
    }

    public static Actions keys(CharSequence... keys) throws InterruptedException {
        return action().sendKeys(keys);
    }

    public static Actions keys(WebElement el, CharSequence... keys) throws InterruptedException {
        return action().sendKeys(el, keys);
    }

    public static void keysEl(WebElement el, CharSequence... keys) {
        el.sendKeys(keys);
    }

    public static Actions keysChord(CharSequence... keys) {
        return action().sendKeys(Keys.chord(keys));
    }

    public static Actions keysChord(WebElement el, CharSequence... keys) {
        return action().sendKeys(el, Keys.chord(keys));
    }

    public static Actions keyChord(CharSequence key, CharSequence... keys) {
        return action().sendKeys(key, Keys.chord(keys));
    }

    public static void keysChordEl(WebElement el, CharSequence... keys) {
        el.sendKeys(Keys.chord(keys));
    }

    public static Actions ctrl(CharSequence... keys) {
        return keyChord(Keys.CONTROL, keys);
    }

    public static Actions alt(CharSequence... keys) {
        return keyChord(Keys.ALT, keys);
    }

    public static Actions delete() {
        return keysChord(Keys.DELETE);
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

    public static Action build() throws InterruptedException {
        return action().build();
    }

    public static Actions perform() throws InterruptedException {
        Actions action = action();
        action.perform();
        return action;
    }

    public static Action performBuild() throws InterruptedException {
        Action action = build();
        action.perform();
        return action;
    }

    public static Actions perform2() throws InterruptedException {
        return new WinDriver().perform();
    }
}
