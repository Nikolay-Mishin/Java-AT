package org.project.utils.windriver;

import java.util.*;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;

public class WebElement {
    protected static WindowsDriver<org.openqa.selenium.WebElement> driver;

    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T driver() {
        return (T) driver;
    }

    public static <T extends WebDriver> T driver(T driver) {
        return (T) (WebElement.driver = (WindowsDriver<org.openqa.selenium.WebElement>) driver);
    }

    /**
     * Основным элементом взаимодействия с внешним миром являются «рычаги».
     * Их можно удобно описать классом с полями name, className и automationId и хранить в отдельных классах по блокам.
     * By.name – по полю Name, By.className — по полю ClassName и By.xpath для более изощрённых условий поиска
     * Так же мы можем прикрепляться к элементам другого элемента
     */
    public static org.openqa.selenium.WebElement find(By by) {
        return driver.findElement(by);
    }

    public static org.openqa.selenium.WebElement find(org.openqa.selenium.WebElement el, By by) {
        return el.findElement(by);
    }

    //список элементов - элементы будут добавлены в порядке tab-ордера
    public static List<org.openqa.selenium.WebElement> list(By by) {
        return driver.findElements(by);
    }

    public static List<org.openqa.selenium.WebElement> list(org.openqa.selenium.WebElement el, By by) {
        return el.findElements(by);
    }

    //один элемент, поиск по полю AccessibilityId
    public static org.openqa.selenium.WebElement findByAccessId(String id) {
        return driver.findElementByAccessibilityId(id);
    }

    //один элемент, поиск по полю ID
    public static org.openqa.selenium.WebElement findById(String id) {
        //return find(By.id(id));
        return driver.findElementById(id);
    }

    public static org.openqa.selenium.WebElement findById(org.openqa.selenium.WebElement el, String id) {
        return find(el, By.id(id));
    }

    //один элемент, поиск по полю Name
    public static org.openqa.selenium.WebElement findByName(String name) {
        //return find(By.name(name));
        return driver.findElementByName(name);
    }

    public static org.openqa.selenium.WebElement findByName(org.openqa.selenium.WebElement el, String name) {
        return find(el, By.name(name));
    }

    //один элемент, поиск по полю ClassName
    public static org.openqa.selenium.WebElement findByClass(String className) {
        //return find(By.className(className));
        return driver.findElementByClassName(className);
    }

    public static org.openqa.selenium.WebElement findByClass(org.openqa.selenium.WebElement el, String className) {
        return find(el, By.className(className));
    }

    //один элемент, поиск по полю ClassName
    public static org.openqa.selenium.WebElement findByTag(String tagName) {
        //return find(By.className(className));
        return driver.findElementByTagName(tagName);
    }

    public static org.openqa.selenium.WebElement findByTag(org.openqa.selenium.WebElement el, String tagName) {
        return find(el, By.tagName(tagName));
    }

    /**
     * Если первые два механизма прикрепления очень узкоспециализированы - работают строго в иерархической структуре и строго с полями Name и ClassName,
     * то для работы с иными случаями нам потребуется третий механизм, а именно By.xpath.
     * В этом механизме всё строго по канонам использования xpath (во всяком случае, с использованных случаях всё работало как нужно).
     * С помощью xpath можно получить и обрабатывать поля IsOffscreen, AutomationId, HasKeyboardFocus:
     */
    // найдёт элемент, на котором стоит фокус
    public static org.openqa.selenium.WebElement findByXpath(String xpath) {
        //return find(By.xpath(xpath));
        return driver.findElementByXPath(xpath);
    }

    public static org.openqa.selenium.WebElement findByXpath(org.openqa.selenium.WebElement el, String xpath) {
        return find(el, By.xpath(xpath));
    }

    //один элемент, поиск по полю Css
    public static org.openqa.selenium.WebElement findByCss(String css) {
        //return find(By.className(className));
        return driver.findElementByCssSelector(css);
    }

    public static org.openqa.selenium.WebElement findByCss(org.openqa.selenium.WebElement el, String css) {
        return find(el, By.cssSelector(css));
    }

    //один элемент, поиск по полю Link
    public static org.openqa.selenium.WebElement findByLink(String css) {
        //return find(By.className(className));
        return driver.findElementByLinkText(css);
    }

    public static org.openqa.selenium.WebElement findByLink(org.openqa.selenium.WebElement el, String css) {
        return find(el, By.linkText(css));
    }

    //один элемент, поиск по полю PartialLink
    public static org.openqa.selenium.WebElement findByPartLink(String css) {
        //return find(By.className(className));
        return driver.findElementByPartialLinkText(css);
    }

    public static org.openqa.selenium.WebElement findByPartLink(org.openqa.selenium.WebElement el, String css) {
        return find(el, By.partialLinkText(css));
    }

    //один элемент, поиск по полю AutomationID
    public static org.openqa.selenium.WebElement findByAid(String id) {
        return driver.findElementByWindowsUIAutomation(id);
    }

    //один элемент, поиск по полю Image
    public static org.openqa.selenium.WebElement findByImage(String b64) {
        return driver.findElementByImage(b64);
    }

    //один элемент, поиск по полю Custom Selector
    public static org.openqa.selenium.WebElement findBySelector(String selector) {
        return driver.findElementByCustom(selector);
    }

    //один элемент, поиск по полю AccessibilityId
    public static List<org.openqa.selenium.WebElement> listByAccessId(String id) {
        return driver.findElementsByAccessibilityId(id);
    }

    //один элемент, поиск по полю ID
    public static List<org.openqa.selenium.WebElement> listById(String id) {
        //return find(By.id(id));
        return driver.findElementsById(id);
    }

    public static List<org.openqa.selenium.WebElement> listById(org.openqa.selenium.WebElement el, String id) {
        return list(el, By.id(id));
    }

    public static List<org.openqa.selenium.WebElement> listByName(String name) {
        //return list(By.name(name));
        return driver.findElementsByName(name);
    }

    public static List<org.openqa.selenium.WebElement> listByName(org.openqa.selenium.WebElement el, String name) {
        return list(el, By.name(name));
    }

    public static List<org.openqa.selenium.WebElement> listByClass(String className) {
        //return list(By.className(className));
        return driver.findElementsByClassName(className);
    }

    public static List<org.openqa.selenium.WebElement> listByClass(org.openqa.selenium.WebElement el, String className) {
        return list(el, By.className(className));
    }

    //один элемент, поиск по полю ClassName
    public static List<org.openqa.selenium.WebElement> listByTag(String tagName) {
        //return find(By.className(className));
        return driver.findElementsByTagName(tagName);
    }

    public static List<org.openqa.selenium.WebElement> listByTag(org.openqa.selenium.WebElement el, String tagName) {
        return list(el, By.tagName(tagName));
    }

    // найдёт элемент, на котором стоит фокус
    public static List<org.openqa.selenium.WebElement> listByXpath(String xpath) {
        //return list(By.xpath(xpath));
        return driver.findElementsByXPath(xpath);
    }

    public static List<org.openqa.selenium.WebElement> listByXpath(org.openqa.selenium.WebElement el, String xpath) {
        return list(el, By.xpath(xpath));
    }

    //один элемент, поиск по полю Css
    public static List<org.openqa.selenium.WebElement> listByCss(String css) {
        //return find(By.className(className));
        return driver.findElementsByCssSelector(css);
    }

    public static List<org.openqa.selenium.WebElement> listByCss(org.openqa.selenium.WebElement el, String css) {
        return list(el, By.cssSelector(css));
    }

    //один элемент, поиск по полю Link
    public static List<org.openqa.selenium.WebElement> listByLink(String css) {
        //return find(By.className(className));
        return driver.findElementsByLinkText(css);
    }

    public static List<org.openqa.selenium.WebElement> listByLink(org.openqa.selenium.WebElement el, String css) {
        return list(el, By.linkText(css));
    }

    //один элемент, поиск по полю PartialLink
    public static List<org.openqa.selenium.WebElement> listByPartLink(String css) {
        //return find(By.className(className));
        return driver.findElementsByPartialLinkText(css);
    }

    public static List<org.openqa.selenium.WebElement> listByPartLink(org.openqa.selenium.WebElement el, String css) {
        return list(el, By.partialLinkText(css));
    }

    //один элемент, поиск по полю AutomationID
    public static List<org.openqa.selenium.WebElement> listByAid(String id) {
        return driver.findElementsByWindowsUIAutomation(id);
    }

    //один элемент, поиск по полю Image
    public static List<org.openqa.selenium.WebElement> listByImage(String b64) {
        return driver.findElementsByImage(b64);
    }

    //один элемент, поиск по полю Custom Selector
    public static List<org.openqa.selenium.WebElement> listBySelector(String selector) {
        return driver.findElementsByCustom(selector);
    }
}
