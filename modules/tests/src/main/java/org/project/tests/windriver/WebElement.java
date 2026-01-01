package org.project.tests.windriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.project.tests.TestWinDriver.setList;

/**
 *
 */
public class WebElement<T extends RemoteWebDriver> extends Actions<T> {
    /**
     *
     */
    protected static List<org.openqa.selenium.WebElement> list;

    /**
     * You set List {WebElement}
     * @param elList List {WebElement}
     * @return WebElement
     */
    public static List<org.openqa.selenium.WebElement> list(List<org.openqa.selenium.WebElement> elList) {
        return setList(list = elList);
    }

    /**
     * Основным элементом взаимодействия с внешним миром являются «рычаги».
     * <p>Их можно удобно описать классом с полями {@code name}, {@code className} и {@code automationId} и хранить в отдельных классах по блокам.
     * <p>By.name – по полю {@code Name}, {@code By.className} — по полю {@code ClassName} и {@code By.xpath} для более изощрённых условий поиска
     * <p>Так же мы можем прикрепляться к элементам другого элемента
     * @param by By
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement find(By by) {
        return el(d.findElement(by));
    }

    /**
     *
     * @param el WebElement
     * @param by By
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement find(org.openqa.selenium.WebElement el, By by) {
        return el(el.findElement(by));
    }

    /**
     *
     * @param by By
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findEl(By by) {
        return find(el, by);
    }

    /**
     * Список элементов - элементы будут добавлены в порядке tab-ордера
     * @param by By
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> list(By by) {
        return list(d.findElements(by));
    }

    /**
     *
     * @param el WebElement
     * @param by By
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> list(org.openqa.selenium.WebElement el, By by) {
        return list(el.findElements(by));
    }

    /**
     *
     * @param by By
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listEl(By by) {
        return list(el, by);
    }

    /**
     * Один элемент, поиск по полю ID
     * @param id String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findById(String id) {
        //return find(By.id(id));
        return el(d.findElementById(id));
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
     * Один элемент, поиск по полю ID
     * @param id String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findElById(String id) {
        return findById(el, id);
    }

    /**
     * Один элемент, поиск по полю Name
     * @param name String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByName(String name) {
        //return find(By.name(name));
        return el(d.findElementByName(name));
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
     * Один элемент, поиск по полю Name
     * @param name String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findElByName(String name) {
        return findByName(el, name);
    }

    /**
     * Один элемент, поиск по полю ClassName
     * @param className String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByClass(String className) {
        //return find(By.className(className));
        return el(d.findElementByClassName(className));
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
     * Один элемент, поиск по полю ClassName
     * @param className String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findElByClass(String className) {
        return findByClass(el, className);
    }

    /**
     * Один элемент, поиск по полю Tag
     * @param tagName String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByTag(String tagName) {
        //return find(By.tagName(tagName));
        return el(d.findElementByTagName(tagName));
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
     * Один элемент, поиск по полю Tag
     * @param tagName String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findElByTag(String tagName) {
        return findByTag(el, tagName);
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
        return el(d.findElementByXPath(xpath));
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
     * Найдёт элемент, на котором стоит фокус
     * @param xpath String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findElByXpath(String xpath) {
        return findByXpath(el, xpath);
    }

    /**
     * Один элемент, поиск по полю Css
     * @param css String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByCss(String css) {
        //return find(By.cssSelector(css));
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
     * Один элемент, поиск по полю Css
     * @param css String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findElByCss(String css) {
        return findByCss(el, css);
    }

    /**
     * Один элемент, поиск по полю Link
     * @param link String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByLink(String link) {
        //return find(By.linkText(link));
        return d.findElementByLinkText(link);
    }

    /**
     * Один элемент, поиск по полю Link
     * @param el WebElement
     * @param link String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByLink(org.openqa.selenium.WebElement el, String link) {
        return find(el, By.linkText(link));
    }

    /**
     * Один элемент, поиск по полю Link
     * @param link String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findElByLink(String link) {
        return findByLink(el, link);
    }

    /**
     * Один элемент, поиск по полю PartialLink
     * @param partLink String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByPartLink(String partLink) {
        //return find(By.partialLinkText(partLink));
        return d.findElementByPartialLinkText(partLink);
    }

    /**
     * Один элемент, поиск по полю PartialLink
     * @param el WebElement
     * @param partLink String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findByPartLink(org.openqa.selenium.WebElement el, String partLink) {
        return find(el, By.partialLinkText(partLink));
    }

    /**
     * Один элемент, поиск по полю PartialLink
     * @param partLink String
     * @return WebElement
     */
    public static org.openqa.selenium.WebElement findElByPartLink(String partLink) {
        return findByPartLink(el, partLink);
    }

    /**
     * Список элемент, поиск по полю ID
     * @param id String
     * @return List { WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listById(String id) {
        //return list(By.id(id));
        return list(d.findElementsById(id));
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
     * Список элемент, поиск по полю ID
     * @param id String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listElById(String id) {
        return listById(el, id);
    }

    /**
     * Список элемент, поиск по полю Name
     * @param name String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByName(String name) {
        //return list(By.name(name));
        return list(d.findElementsByName(name));
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
     * Список элемент, поиск по полю Name
     * @param name String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listElByName(String name) {
        return listByName(el, name);
    }

    /**
     * Список элемент, поиск по полю ClassName
     * @param className String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByClass(String className) {
        //return list(By.className(className));
        return list(d.findElementsByClassName(className));
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
     * Список элемент, поиск по полю ClassName
     * @param className String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listElByClass(String className) {
        return listByClass(el, className);
    }

    /**
     * Список элемент, поиск по полю Tag
     * @param tagName String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByTag(String tagName) {
        //return list(By.tagName(tagName));
        return list(d.findElementsByTagName(tagName));
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
     * Список элемент, поиск по полю Tag
     * @param tagName String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listElByTag(String tagName) {
        return listByTag(el, tagName);
    }

    /**
     * Список элементов, на которых стоит фокус
     * @param xpath String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByXpath(String xpath) {
        //return list(By.xpath(xpath));
        return list(d.findElementsByXPath(xpath));
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
     * Список элементов, на которых стоит фокус
     * @param xpath String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listElByXpath(String xpath) {
        return listByXpath(el, xpath);
    }

    /**
     * Список элементов, поиск по полю Css
     * @param css String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByCss(String css) {
        //return list(By.cssSelector(css));
        return list(d.findElementsByCssSelector(css));
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
     * Список элементов, поиск по полю Css
     * @param css String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listElByCss(String css) {
        return listByCss(el, css);
    }

    /**
     * Список элементов, поиск по полю Link
     * @param link String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByLink(String link) {
        //return list(By.linkText(link));
        return d.findElementsByLinkText(link);
    }

    /**
     * Список элементов, поиск по полю Link
     * @param el WebElement
     * @param link String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByLink(org.openqa.selenium.WebElement el, String link) {
        return list(el, By.linkText(link));
    }

    /**
     * Список элементов, поиск по полю Link
     * @param link String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listElByLink(String link) {
        return listByLink(el, link);
    }

    /**
     * Список элементов, поиск по полю PartialLink
     * @param partLink String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByPartLink(String partLink) {
        //return list(By.partialLinkText(partLink));
        return d.findElementsByPartialLinkText(partLink);
    }

    /**
     * Список элементов, поиск по полю PartialLink
     * @param el WebElement
     * @param partLink String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listByPartLink(org.openqa.selenium.WebElement el, String partLink) {
        return list(el, By.partialLinkText(partLink));
    }

    /**
     * Список элементов, поиск по полю PartialLink
     * @param partLink String
     * @return List {WebElement}
     */
    public static List<org.openqa.selenium.WebElement> listElByPartLink(String partLink) {
        return listByPartLink(el, partLink);
    }

}
