package org.project.utils.windriver;

import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.project.utils.Helper.debug;

public class WebElement extends Actions {
    protected static RemoteWebDriver d;

    public static void printClass() {
        debug(d.getClass());
    }

    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T driver() {
        return (T) d;
    }

    @SuppressWarnings("unchecked")
    public static <T extends RemoteWebDriver> T driver(T driver) {
        return (T) (d = driver);
    }

    /**
     * Основным элементом взаимодействия с внешним миром являются «рычаги».
     * Их можно удобно описать классом с полями name, className и automationId и хранить в отдельных классах по блокам.
     * By.name – по полю Name, By.className — по полю ClassName и By.xpath для более изощрённых условий поиска
     * Так же мы можем прикрепляться к элементам другого элемента
     */
    public static org.openqa.selenium.WebElement find(By by) {
        return d.findElement(by);
    }

    public static org.openqa.selenium.WebElement find(org.openqa.selenium.WebElement el, By by) {
        return el.findElement(by);
    }

    //список элементов - элементы будут добавлены в порядке tab-ордера
    public static List<org.openqa.selenium.WebElement> list(By by) {
        return d.findElements(by);
    }

    public static List<org.openqa.selenium.WebElement> list(org.openqa.selenium.WebElement el, By by) {
        return el.findElements(by);
    }

    //один элемент, поиск по полю ID
    public static org.openqa.selenium.WebElement findById(String id) {
        //return find(By.id(id));
        return d.findElementById(id);
    }

    public static org.openqa.selenium.WebElement findById(org.openqa.selenium.WebElement el, String id) {
        return find(el, By.id(id));
    }

    //один элемент, поиск по полю Name
    public static org.openqa.selenium.WebElement findByName(String name) {
        //return find(By.name(name));
        return d.findElementByName(name);
    }

    public static org.openqa.selenium.WebElement findByName(org.openqa.selenium.WebElement el, String name) {
        return find(el, By.name(name));
    }

    //один элемент, поиск по полю ClassName
    public static org.openqa.selenium.WebElement findByClass(String className) {
        //return find(By.className(className));
        return d.findElementByClassName(className);
    }

    public static org.openqa.selenium.WebElement findByClass(org.openqa.selenium.WebElement el, String className) {
        return find(el, By.className(className));
    }

    //один элемент, поиск по полю ClassName
    public static org.openqa.selenium.WebElement findByTag(String tagName) {
        //return find(By.className(className));
        return d.findElementByTagName(tagName);
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
        return d.findElementByXPath(xpath);
    }

    public static org.openqa.selenium.WebElement findByXpath(org.openqa.selenium.WebElement el, String xpath) {
        return find(el, By.xpath(xpath));
    }

    //один элемент, поиск по полю Css
    public static org.openqa.selenium.WebElement findByCss(String css) {
        //return find(By.className(className));
        return d.findElementByCssSelector(css);
    }

    public static org.openqa.selenium.WebElement findByCss(org.openqa.selenium.WebElement el, String css) {
        return find(el, By.cssSelector(css));
    }

    //один элемент, поиск по полю Link
    public static org.openqa.selenium.WebElement findByLink(String css) {
        //return find(By.className(className));
        return d.findElementByLinkText(css);
    }

    public static org.openqa.selenium.WebElement findByLink(org.openqa.selenium.WebElement el, String css) {
        return find(el, By.linkText(css));
    }

    //один элемент, поиск по полю PartialLink
    public static org.openqa.selenium.WebElement findByPartLink(String css) {
        //return find(By.className(className));
        return d.findElementByPartialLinkText(css);
    }

    public static org.openqa.selenium.WebElement findByPartLink(org.openqa.selenium.WebElement el, String css) {
        return find(el, By.partialLinkText(css));
    }

    //список элементов, поиск по полю ID
    public static List<org.openqa.selenium.WebElement> listById(String id) {
        //return find(By.id(id));
        return d.findElementsById(id);
    }

    public static List<org.openqa.selenium.WebElement> listById(org.openqa.selenium.WebElement el, String id) {
        return list(el, By.id(id));
    }

    public static List<org.openqa.selenium.WebElement> listByName(String name) {
        //return list(By.name(name));
        return d.findElementsByName(name);
    }

    public static List<org.openqa.selenium.WebElement> listByName(org.openqa.selenium.WebElement el, String name) {
        return list(el, By.name(name));
    }

    public static List<org.openqa.selenium.WebElement> listByClass(String className) {
        //return list(By.className(className));
        return d.findElementsByClassName(className);
    }

    public static List<org.openqa.selenium.WebElement> listByClass(org.openqa.selenium.WebElement el, String className) {
        return list(el, By.className(className));
    }

    //один элемент, поиск по полю ClassName
    public static List<org.openqa.selenium.WebElement> listByTag(String tagName) {
        //return find(By.className(className));
        return d.findElementsByTagName(tagName);
    }

    public static List<org.openqa.selenium.WebElement> listByTag(org.openqa.selenium.WebElement el, String tagName) {
        return list(el, By.tagName(tagName));
    }

    // найдёт элемент, на котором стоит фокус
    public static List<org.openqa.selenium.WebElement> listByXpath(String xpath) {
        //return list(By.xpath(xpath));
        return d.findElementsByXPath(xpath);
    }

    public static List<org.openqa.selenium.WebElement> listByXpath(org.openqa.selenium.WebElement el, String xpath) {
        return list(el, By.xpath(xpath));
    }

    //один элемент, поиск по полю Css
    public static List<org.openqa.selenium.WebElement> listByCss(String css) {
        //return find(By.className(className));
        return d.findElementsByCssSelector(css);
    }

    public static List<org.openqa.selenium.WebElement> listByCss(org.openqa.selenium.WebElement el, String css) {
        return list(el, By.cssSelector(css));
    }

    //один элемент, поиск по полю Link
    public static List<org.openqa.selenium.WebElement> listByLink(String css) {
        //return find(By.className(className));
        return d.findElementsByLinkText(css);
    }

    public static List<org.openqa.selenium.WebElement> listByLink(org.openqa.selenium.WebElement el, String css) {
        return list(el, By.linkText(css));
    }

    //один элемент, поиск по полю PartialLink
    public static List<org.openqa.selenium.WebElement> listByPartLink(String css) {
        //return find(By.className(className));
        return d.findElementsByPartialLinkText(css);
    }

    public static List<org.openqa.selenium.WebElement> listByPartLink(org.openqa.selenium.WebElement el, String css) {
        return list(el, By.partialLinkText(css));
    }
}
