package org.project.utils.windriver;

import java.util.List;

import org.openqa.selenium.By;

/**
 *
 */
public class WebElement extends Actions {

    /**
     * Основным элементом взаимодействия с внешним миром являются «рычаги».
     * <p>Их можно удобно описать классом с полями {@code name}, {@code className} и {@code automationId} и хранить в отдельных классах по блокам.
     * <p>By.name – по полю {@code Name}, {@code By.className} — по полю {@code ClassName} и {@code By.xpath} для более изощрённых условий поиска
     * <p>Так же мы можем прикрепляться к элементам другого элемента
     * @param by By
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement find(By by) {
        return d.findElement(by);
    }

    /**
     *
     * @param el WebElement
     * @param by By
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement find(org.openqa.selenium.WebElement el, By by) {
        return el.findElement(by);
    }

    /**
     * Список элементов - элементы будут добавлены в порядке tab-ордера
     * @param by By
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> list(By by) {
        return d.findElements(by);
    }

    /**
     *
     * @param el WebElement
     * @param by By
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> list(org.openqa.selenium.WebElement el, By by) {
        return el.findElements(by);
    }

    /**
     * Один элемент, поиск по полю ID
     * @param id String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findById(String id) {
        //return find(By.id(id));
        return d.findElementById(id);
    }

    /**
     * Один элемент, поиск по полю ID
     * @param el WebElement
     * @param id String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findById(org.openqa.selenium.WebElement el, String id) {
        return find(el, By.id(id));
    }

    /**
     * Один элемент, поиск по полю Name
     * @param name String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByName(String name) {
        //return find(By.name(name));
        return d.findElementByName(name);
    }

    /**
     * Один элемент, поиск по полю Name
     * @param el WebElement
     * @param name String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByName(org.openqa.selenium.WebElement el, String name) {
        return find(el, By.name(name));
    }

    /**
     * Один элемент, поиск по полю ClassName
     * @param className String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByClass(String className) {
        //return find(By.className(className));
        return d.findElementByClassName(className);
    }

    /**
     * Один элемент, поиск по полю ClassName
     * @param el WebElement
     * @param className String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByClass(org.openqa.selenium.WebElement el, String className) {
        return find(el, By.className(className));
    }

    /**
     * Один элемент, поиск по полю Tag
     * @param tagName String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByTag(String tagName) {
        //return find(By.className(className));
        return d.findElementByTagName(tagName);
    }

    /**
     * Один элемент, поиск по полю Tag
     * @param el WebElement
     * @param tagName String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByTag(org.openqa.selenium.WebElement el, String tagName) {
        return find(el, By.tagName(tagName));
    }

    /**
     * Найдёт элемент, на котором стоит фокус.
     * <p>Если первые два механизма прикрепления очень узкоспециализированы - работают строго в иерархической структуре и строго с полями {@code Name} и {@code ClassName},
     * <p>то для работы с иными случаями нам потребуется третий механизм, а именно {@code By.xpath}.
     * <p>В этом механизме всё строго по канонам использования xpath (во всяком случае, с использованных случаях всё работало как нужно).
     * <p>С помощью xpath можно получить и обрабатывать поля {@code IsOffscreen}, {@code AutomationId}, {@code HasKeyboardFocus}:
     * @param xpath String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByXpath(String xpath) {
        //return find(By.xpath(xpath));
        return d.findElementByXPath(xpath);
    }

    /**
     * Найдёт элемент, на котором стоит фокус
     * @param el WebElement
     * @param xpath String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByXpath(org.openqa.selenium.WebElement el, String xpath) {
        return find(el, By.xpath(xpath));
    }

    /**
     * Один элемент, поиск по полю Css
     * @param css String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByCss(String css) {
        //return find(By.className(className));
        return d.findElementByCssSelector(css);
    }

    /**
     * Один элемент, поиск по полю Css
     * @param el WebElement
     * @param css String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByCss(org.openqa.selenium.WebElement el, String css) {
        return find(el, By.cssSelector(css));
    }

    /**
     * Один элемент, поиск по полю Link
     * @param css String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByLink(String css) {
        //return find(By.className(className));
        return d.findElementByLinkText(css);
    }

    /**
     * Один элемент, поиск по полю Link
     * @param el WebElement
     * @param css String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByLink(org.openqa.selenium.WebElement el, String css) {
        return find(el, By.linkText(css));
    }

    /**
     * Один элемент, поиск по полю PartialLink
     * @param css String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByPartLink(String css) {
        //return find(By.className(className));
        return d.findElementByPartialLinkText(css);
    }

    /**
     * Один элемент, поиск по полю PartialLink
     * @param el WebElement
     * @param css String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByPartLink(org.openqa.selenium.WebElement el, String css) {
        return find(el, By.partialLinkText(css));
    }

    /**
     * Список элемент, поиск по полю ID
     * @param id String
     * @return List { WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listById(String id) {
        //return find(By.id(id));
        return d.findElementsById(id);
    }

    /**
     * Список элемент, поиск по полю ID
     * @param el WebElement
     * @param id String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listById(org.openqa.selenium.WebElement el, String id) {
        return list(el, By.id(id));
    }

    /**
     * Список элемент, поиск по полю Name
     * @param name String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByName(String name) {
        //return list(By.name(name));
        return d.findElementsByName(name);
    }

    /**
     * Список элемент, поиск по полю Name
     * @param el WebElement
     * @param name String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByName(org.openqa.selenium.WebElement el, String name) {
        return list(el, By.name(name));
    }

    /**
     * Список элемент, поиск по полю ClassName
     * @param className String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByClass(String className) {
        //return list(By.className(className));
        return d.findElementsByClassName(className);
    }

    /**
     * Список элемент, поиск по полю ClassName
     * @param el WebElement
     * @param className String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByClass(org.openqa.selenium.WebElement el, String className) {
        return list(el, By.className(className));
    }

    /**
     * Список элемент, поиск по полю Tag
     * @param tagName String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByTag(String tagName) {
        //return find(By.className(className));
        return d.findElementsByTagName(tagName);
    }

    /**
     * Список элемент, поиск по полю Tag
     * @param el WebElement
     * @param tagName String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByTag(org.openqa.selenium.WebElement el, String tagName) {
        return list(el, By.tagName(tagName));
    }

    /**
     * Список элементов, на которых стоит фокус
     * @param xpath String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByXpath(String xpath) {
        //return list(By.xpath(xpath));
        return d.findElementsByXPath(xpath);
    }

    /**
     * Список элементов, на которых стоит фокус
     * @param el WebElement
     * @param xpath String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByXpath(org.openqa.selenium.WebElement el, String xpath) {
        return list(el, By.xpath(xpath));
    }

    /**
     * Список элементов, поиск по полю Css
     * @param css String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByCss(String css) {
        //return find(By.className(className));
        return d.findElementsByCssSelector(css);
    }

    /**
     * Список элементов, поиск по полю Css
     * @param el WebElement
     * @param css String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByCss(org.openqa.selenium.WebElement el, String css) {
        return list(el, By.cssSelector(css));
    }

    /**
     * Список элементов, поиск по полю Link
     * @param css String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByLink(String css) {
        //return find(By.className(className));
        return d.findElementsByLinkText(css);
    }

    /**
     * Список элементов, поиск по полю Link
     * @param el WebElement
     * @param css String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByLink(org.openqa.selenium.WebElement el, String css) {
        return list(el, By.linkText(css));
    }

    /**
     * Список элементов, поиск по полю PartialLink
     * @param css String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByPartLink(String css) {
        //return find(By.className(className));
        return d.findElementsByPartialLinkText(css);
    }

    /**
     * Список элементов, поиск по полю PartialLink
     * @param el WebElement
     * @param css String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByPartLink(org.openqa.selenium.WebElement el, String css) {
        return list(el, By.partialLinkText(css));
    }
}
