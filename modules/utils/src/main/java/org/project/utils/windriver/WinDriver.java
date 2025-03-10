package org.project.utils.windriver;

import static java.lang.System.out;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import static org.project.utils.constant.UrlConstants.*;
import static org.project.utils.Helper.getObjectFields;

import org.project.utils.constant.Capabilities;
import org.project.utils.base.Process;

public class WinDriver {
    protected static final String appDriverUrl = WINDRIVER_HOST;
    protected static WebDriver driver;
    protected static ProcessBuilder processBuilder;
    protected static Process process;
    protected static Actions defaultActions;
    protected static Actions actions;

    //[ProcessInitialize]
    protected static void init(String driver, String param) throws IOException {
        process = new Process(driver, param);
        processBuilder = process.processBuilder();
    }

    public static void init() throws IOException {
        init(WINDRIVER, WINDRIVER_PARAM);
    }

    //[ProcessDestroy]
    public static void destroy() {
        process.destroy();
        processBuilder = null;
        process = null;
    }

    public static void open() {
        try {
            Desktop desktop = Desktop.getDesktop();

            File file = new File(WINDRIVER);

            /* Check if there is support for Desktop or not */
            if(!Desktop.isDesktopSupported()) {
                System.out.println("not supported");
                return;
            }

            if (file.exists()) {
                System.out.println("Open " + WINDRIVER + "\n");
                desktop.open(file);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Encountered Exception\n");
            throw new RuntimeException(e);
        }
    }


    protected static void stop(String driver) {
        try {
            new Process("taskkill ", "/f", "/IM", driver);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void stop() {
        stop(WINDRIVER_NAME);
    }

    //[ClassInitialize]
    public static WebDriver start(String app) throws MalformedURLException, IllegalAccessException {
        open();
        // Прикрепить переменную драйвера к собственно Winium драйверу
        DesiredCapabilities cap = new DesiredCapabilities();
        if (EXPERIMENTAL) cap.setCapability("ms:experimental-webdriver", EXPERIMENTAL);
        Map<String, Object> map = getObjectFields(new Capabilities());
        out.println(map);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            out.println(k + ": " + v);
            if (v != "") cap.setCapability(k, v);
        }
        driver = new RemoteWebDriver(new URL(appDriverUrl), cap); //на этом порту по умолчанию висит Winium драйвер
        Assert.assertNotNull(driver);
        return driver;
    }

    //[AppSessionQuit]
    public static void quit() {
        // The instance of WinAppDriver will be freed once last test is complete
        // WinDriver.stop();
        if (driver != null) driver.quit();
        driver = null;
        stop();
    }

    /**
     * Основным элементом взаимодействия с внешним миром являются «рычаги».
     * Их можно удобно описать классом с полями name, className и automationId и хранить в отдельных классах по блокам.
     * By.name – по полю Name, By.className — по полю ClassName и By.xpath для более изощрённых условий поиска
     * Так же мы можем прикрепляться к элементам другого элемента
     */
    protected static WebElement find(By by) {
        return driver.findElement(by);
    }

    protected static WebElement find(By by, WebElement el) {
        return el.findElement(by);
    }

    //список элементов - элементы будут добавлены в порядке tab-ордера
    protected static List<WebElement> findList(By by) {
        return driver.findElements(by);
    }

    protected static List<WebElement> findList(By by, WebElement el) {
        return el.findElements(by);
    }

    //один элемент, поиск по полю Name
    public static WebElement findByName(String name) {
        return find(By.name(name));
    }

    public static WebElement findByName(String name, WebElement el) {
        return find(By.name(name), el);
    }

    //один элемент, поиск по полю ClassName
    public static WebElement findByClassName(String className) {
        return find(By.className(className));
    }

    public static WebElement findByClassName(String className, WebElement el) {
        return find(By.className(className), el);
    }

    /**
     * Если первые два механизма прикрепления очень узкоспециализированы - работают строго в иерархической структуре и строго с полями Name и ClassName,
     * то для работы с иными случаями нам потребуется третий механизм, а именно By.xpath.
     * В этом механизме всё строго по канонам использования xpath (во всяком случае, с использованных случаях всё работало как нужно).
     * С помощью xpath можно получить и обрабатывать поля IsOffscreen, AutomationId, HasKeyboardFocus:
     */
    public static WebElement findByXpath(String xpath) {
        return find(By.xpath(xpath)); //найдёт элемент, на котором стоит фокус
    }

    public static WebElement findByXpath(String xpath, WebElement el) {
        return find(By.xpath(xpath), el); //найдёт элемент, на котором стоит фокус
    }

    public static List<WebElement> findListByName(String name) {
        return findList(By.name(name));
    }

    public static List<WebElement> findListByName(String name, WebElement el) {
        return findList(By.name(name), el);
    }

    public static List<WebElement> findListByClassName(String className) {
        return findList(By.className(className));
    }

    public static List<WebElement> findListByClassName(String className, WebElement el) {
        return findList(By.className(className), el);
    }

    public static List<WebElement> findListByXpath(String xpath) {
        return findList(By.xpath(xpath)); //найдёт элемент, на котором стоит фокус
    }

    public static List<WebElement> findListByXpath(String xpath, WebElement el) {
        return findList(By.xpath(xpath), el); //найдёт элемент, на котором стоит фокус
    }

    public static void timeout(long t) {
        //webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(t));
        driver.manage().timeouts().implicitlyWait(t, TimeUnit.MILLISECONDS);
    }

    public static void timeout() {
        timeout(1000);
    }

    public static Actions setActions() {
        return actions != null ? actions : (defaultActions = actions = new Actions(driver));
    }

    public static Actions resetActions() {
        return actions = defaultActions;
    }

    public static Actions actions() throws InterruptedException {
        return setActions();
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
        return actions().moveToElement(el);
    }

    public static Actions move(int x, int y) throws InterruptedException {
        return actions().moveByOffset(x, y);
    }

    public static Actions move(WebElement el, int x, int y) throws InterruptedException {
        return move(el).moveByOffset(x, y);
    }

    public static Actions click() throws InterruptedException {
        return actions().click();
    }

    public static Actions click(WebElement el) throws InterruptedException {
        return actions().click(el);
    }

    public static Action perform() throws InterruptedException {
        Action enter = actions().build();
        enter.perform();
        return enter;
    }
}
