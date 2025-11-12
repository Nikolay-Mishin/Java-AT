package org.project.utils.windriver;

import static java.util.Arrays.stream;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.Keys.*;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;

public class Actions {
    protected static WindowsDriver<WebElement> driver;
    protected static org.openqa.selenium.interactions.Actions action;

    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T driver() {
        return (T) driver;
    }

    public static WindowsDriver<WebElement> driver(WindowsDriver<WebElement> driver) {
        return Actions.driver = driver;
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T action() {
        return (T) action;
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T action(T action) {
        return (T) (Actions.action = action);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T action(WindowsDriver<WebElement> driver) {
        driver(driver);
        return (T) (action = new org.openqa.selenium.interactions.Actions(driver));
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

    public static void timeout(long t) {
        //webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(t));
        driver.manage().timeouts().implicitlyWait(t, TimeUnit.MILLISECONDS);
    }

    public static void timeout() {
        timeout(1000);
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
    public static <T extends org.openqa.selenium.interactions.Actions> T move(WebElement el) {
        return (T) action.moveToElement(el);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T move(WebElement el, int x, int y) {
        return (T) action.moveToElement(el, x, y);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T move(int x, int y) {
        return (T) action.moveByOffset(x, y);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T click() {
        return (T) action.click();
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T click(WebElement el) {
        return (T) action.click(el);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T doubleClick() {
        return (T) action.doubleClick();
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T doubleClick(WebElement el) {
        return (T) action.doubleClick(el);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T clickAndHold() {
        return (T) action.clickAndHold();
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T clickAndHold(WebElement el) {
        return (T) action.clickAndHold(el);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T contextClick() {
        return (T) action.contextClick();
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T contextClick(WebElement el) {
        return (T) action.contextClick(el);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T dragAndDrop(WebElement el, WebElement target) {
        return (T) action.dragAndDrop(el, target);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T dragAndDrop(WebElement el, int x, int y) {
        return (T) action.dragAndDropBy(el, x, y);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T keys(CharSequence... keys) {
        return (T) action.sendKeys(keys);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T keys(WebElement el, CharSequence... keys) {
        return (T) action.sendKeys(el, keys);
    }

    public static void keysEl(WebElement el, CharSequence... keys) {
        el.sendKeys(keys);
    }

    /*public static <T extends org.openqa.selenium.interactions.Actions> T chord(CharSequence key, CharSequence... keys) {
        return keys(key, Keys.chord(keys));
    }*/

    public static <T extends org.openqa.selenium.interactions.Actions> T chord(CharSequence key, CharSequence... keys) {
        return keys(Keys.chord(key, (CharSequence) stream(keys)));
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T chord(WebElement el, CharSequence... keys) {
        return keys(el, Keys.chord(keys));
    }

    public static void chordEl(WebElement el, CharSequence... keys) {
        keysEl(el, Keys.chord(keys));
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T down(CharSequence key) {
        return (T) action.keyDown(key);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T down(WebElement el, CharSequence key) {
        return (T) action.keyDown(el, key);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T up(CharSequence key) {
        return (T) action.keyUp(key);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T up(WebElement el, CharSequence key) {
        return (T) action.keyUp(el, key);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T ctrl(CharSequence... keys) {
        return chord(CONTROL, keys);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T alt(CharSequence... keys) {
        return chord(ALT, keys);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T shift(CharSequence... keys) {
        return chord(SHIFT, keys);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T leftCtrl(CharSequence... keys) {
        return chord(LEFT_CONTROL, keys);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T leftAlt(CharSequence... keys) {
        return chord(LEFT_ALT, keys);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T leftShift(CharSequence... keys) {
        return chord(LEFT_SHIFT, keys);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T enter() {
        return chord(ENTER);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T esc() {
        return chord(CANCEL);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T delete() {
        return chord(DELETE);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T space() {
        return chord(SPACE);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T tab() {
        return chord(TAB);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T backSpace() {
        return chord(BACK_SPACE);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T arrowUp() {
        return chord(ARROW_UP);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T arrowDown() {
        return chord(ARROW_DOWN);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T arrowRight() {
        return chord(ARROW_RIGHT);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T arrowLeft() {
        return chord(ARROW_LEFT);
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T copy() {
        return ctrl("c");
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T paste() {
        return ctrl("v");
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T save() {
        return ctrl("s");
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T selectAll() {
        return ctrl("a");
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T saveFile() {
        return alt("s");
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T saveFile(String filePath) {
        action.sendKeys(filePath);
        return saveFile();
    }

    public static Action build() {
        return action.build();
    }

    public static <T extends org.openqa.selenium.interactions.Actions> T perform() {
        action.perform();
        return (T) action;
    }

    public static Action performBuild() {
        Action action = build();
        action.perform();
        return action;
    }
}
