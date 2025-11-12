package org.project.utils.windriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;

public class WebElement implements org.openqa.selenium.WebElement {
    protected static WindowsDriver<WebElement> driver;

    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T driver() {
        return (T) driver;
    }

    public static WindowsDriver<WebElement> driver(WindowsDriver<WebElement> driver) {
        return WebElement.driver = driver;
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

    public static <T extends org.openqa.selenium.WebElement> T find(WebElement el, By by) {
        return (T) el.findElement(by);
    }

    //список элементов - элементы будут добавлены в порядке tab-ордера
    public static List<WebElement> list(By by) {
        return driver.findElements(by);
    }

    public static <T extends org.openqa.selenium.WebElement> List<T> list(WebElement el, By by) {
        return (List<T>) el.findElements(by);
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

    public static void timeout(long t) {
        //webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(t));
        driver.manage().timeouts().implicitlyWait(t, TimeUnit.MILLISECONDS);
    }

    public static void timeout() {
        timeout(1000);
    }

    @Override
    public void click() {

    }

    @Override
    public void submit() {

    }

    @Override
    public void sendKeys(CharSequence... charSequences) {

    }

    @Override
    public void clear() {

    }

    @Override
    public String getTagName() {
        return "";
    }

    @Override
    public String getAttribute(String s) {
        return "";
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public List<org.openqa.selenium.WebElement> findElements(By by) {
        return List.of();
    }

    @Override
    public org.openqa.selenium.WebElement findElement(By by) {
        return null;
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }

    @Override
    public Point getLocation() {
        return null;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public Rectangle getRect() {
        return null;
    }

    @Override
    public String getCssValue(String s) {
        return "";
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return null;
    }
}
