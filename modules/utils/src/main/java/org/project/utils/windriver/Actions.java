package org.project.utils.windriver;

import static java.util.Arrays.stream;

import static org.openqa.selenium.Keys.*;
import static org.project.utils.Helper.debug;
import static org.project.utils.reflection.Reflection.getCallingChildClassName;
import static org.project.utils.reflection.Reflection.getCallingClassName;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.*;

public class Actions {
    protected static org.openqa.selenium.interactions.Actions a;

    public static org.openqa.selenium.interactions.Actions action() {
        return a;
    }

    public static org.openqa.selenium.interactions.Actions action(org.openqa.selenium.interactions.Actions action) {
        return a = action;
    }

    public static <T extends WebDriver> org.openqa.selenium.interactions.Actions action(T driver) {
        return a = new org.openqa.selenium.interactions.Actions(driver);
    }

    public static void printCall() {
        debug(getCallingClassName());
        debug(getCallingChildClassName());
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
    public static org.openqa.selenium.interactions.Actions move(WebElement el) {
        return a.moveToElement(el);
    }

    public static org.openqa.selenium.interactions.Actions move(WebElement el, int x, int y) {
        return a.moveToElement(el, x, y);
    }

    public static org.openqa.selenium.interactions.Actions move(int x, int y) {
        return a.moveByOffset(x, y);
    }

    public static org.openqa.selenium.interactions.Actions click() {
        return a.click();
    }

    public static org.openqa.selenium.interactions.Actions click(WebElement el) {
        return a.click(el);
    }

    public static org.openqa.selenium.interactions.Actions doubleClick() {
        return a.doubleClick();
    }

    public static org.openqa.selenium.interactions.Actions doubleClick(WebElement el) {
        return a.doubleClick(el);
    }

    public static org.openqa.selenium.interactions.Actions clickAndHold() {
        return a.clickAndHold();
    }

    public static org.openqa.selenium.interactions.Actions clickAndHold(WebElement el) {
        return a.clickAndHold(el);
    }

    public static org.openqa.selenium.interactions.Actions contextClick() {
        return a.contextClick();
    }

    public static org.openqa.selenium.interactions.Actions contextClick(WebElement el) {
        return a.contextClick(el);
    }

    public static org.openqa.selenium.interactions.Actions dragAndDrop(WebElement el, WebElement target) {
        return a.dragAndDrop(el, target);
    }

    public static org.openqa.selenium.interactions.Actions dragAndDrop(WebElement el, int x, int y) {
        return a.dragAndDropBy(el, x, y);
    }

    public static org.openqa.selenium.interactions.Actions keys(CharSequence... keys) {
        return a.sendKeys(keys);
    }

    public static org.openqa.selenium.interactions.Actions keys(WebElement el, CharSequence... keys) {
        return a.sendKeys(el, keys);
    }

    public static void keysEl(WebElement el, CharSequence... keys) {
        el.sendKeys(keys);
    }

    public static org.openqa.selenium.interactions.Actions chord(CharSequence key, CharSequence... keys) {
        //return keys(key, Keys.chord(keys));
        return keys(Keys.chord(key, (CharSequence) stream(keys)));
    }

    public static org.openqa.selenium.interactions.Actions chord(WebElement el, CharSequence... keys) {
        return keys(el, Keys.chord(keys));
    }

    public static void chordEl(WebElement el, CharSequence... keys) {
        keysEl(el, Keys.chord(keys));
    }

    public static org.openqa.selenium.interactions.Actions down(CharSequence key) {
        return a.keyDown(key);
    }

    public static org.openqa.selenium.interactions.Actions down(WebElement el, CharSequence key) {
        return a.keyDown(el, key);
    }

    public static org.openqa.selenium.interactions.Actions up(CharSequence key) {
        return a.keyUp(key);
    }

    public static org.openqa.selenium.interactions.Actions up(WebElement el, CharSequence key) {
        return a.keyUp(el, key);
    }

    public static org.openqa.selenium.interactions.Actions ctrl(CharSequence... keys) {
        return chord(CONTROL, keys);
    }

    public static org.openqa.selenium.interactions.Actions alt(CharSequence... keys) {
        return chord(ALT, keys);
    }

    public static org.openqa.selenium.interactions.Actions shift(CharSequence... keys) {
        return chord(SHIFT, keys);
    }

    public static org.openqa.selenium.interactions.Actions leftCtrl(CharSequence... keys) {
        return chord(LEFT_CONTROL, keys);
    }

    public static org.openqa.selenium.interactions.Actions leftAlt(CharSequence... keys) {
        return chord(LEFT_ALT, keys);
    }

    public static org.openqa.selenium.interactions.Actions leftShift(CharSequence... keys) {
        return chord(LEFT_SHIFT, keys);
    }

    public static org.openqa.selenium.interactions.Actions enter() {
        return chord(ENTER);
    }

    public static org.openqa.selenium.interactions.Actions esc() {
        return chord(CANCEL);
    }

    public static org.openqa.selenium.interactions.Actions delete() {
        return chord(DELETE);
    }

    public static org.openqa.selenium.interactions.Actions space() {
        return chord(SPACE);
    }

    public static org.openqa.selenium.interactions.Actions tab() {
        return chord(TAB);
    }

    public static org.openqa.selenium.interactions.Actions backSpace() {
        return chord(BACK_SPACE);
    }

    public static org.openqa.selenium.interactions.Actions arrowUp() {
        return chord(ARROW_UP);
    }

    public static org.openqa.selenium.interactions.Actions arrowDown() {
        return chord(ARROW_DOWN);
    }

    public static org.openqa.selenium.interactions.Actions arrowRight() {
        return chord(ARROW_RIGHT);
    }

    public static org.openqa.selenium.interactions.Actions arrowLeft() {
        return chord(ARROW_LEFT);
    }

    public static org.openqa.selenium.interactions.Actions copy() {
        return ctrl("c");
    }

    public static org.openqa.selenium.interactions.Actions paste() {
        return ctrl("v");
    }

    public static org.openqa.selenium.interactions.Actions save() {
        return ctrl("s");
    }

    public static org.openqa.selenium.interactions.Actions selectAll() {
        return ctrl("a");
    }

    public static org.openqa.selenium.interactions.Actions saveFile() {
        return alt("s");
    }

    public static org.openqa.selenium.interactions.Actions saveFile(String filePath) {
        a.sendKeys(filePath);
        return saveFile();
    }

    public static Action build() {
        return a.build();
    }

    public static org.openqa.selenium.interactions.Actions perform() {
        a.perform();
        return a;
    }

    public static Action performBuild() {
        Action action = build();
        action.perform();
        return action;
    }
}
