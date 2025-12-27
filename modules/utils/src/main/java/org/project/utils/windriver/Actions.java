package org.project.utils.windriver;

import static java.util.Arrays.stream;

import static org.junit.Assert.assertNotNull;
import static org.openqa.selenium.Keys.ALT;
import static org.openqa.selenium.Keys.ARROW_DOWN;
import static org.openqa.selenium.Keys.ARROW_LEFT;
import static org.openqa.selenium.Keys.ARROW_RIGHT;
import static org.openqa.selenium.Keys.ARROW_UP;
import static org.openqa.selenium.Keys.BACK_SPACE;
import static org.openqa.selenium.Keys.CANCEL;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.DELETE;
import static org.openqa.selenium.Keys.ENTER;
import static org.openqa.selenium.Keys.ESCAPE;
import static org.openqa.selenium.Keys.LEFT_ALT;
import static org.openqa.selenium.Keys.LEFT_CONTROL;
import static org.openqa.selenium.Keys.LEFT_SHIFT;
import static org.openqa.selenium.Keys.SHIFT;
import static org.openqa.selenium.Keys.SPACE;
import static org.openqa.selenium.Keys.TAB;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.project.utils.test.TestWinDriver.setAction;
import static org.project.utils.test.TestWinDriver.setActions;
import static org.project.utils.test.TestWinDriver.setEl;

import org.project.utils.reflection.Instance;

/**
 *
 */
public class Actions<T extends RemoteWebDriver> extends Instance<T> {
    /**
     *
     */
    protected static RemoteWebDriver d;
    /**
     *
     */
    protected static org.openqa.selenium.interactions.Actions a;
    /**
     *
     */
    protected static Action action;
    /**
     *
     */
    protected static WebElement el;

    /**
     *
     * @return T
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T extends WebDriver> T driver() {
        return (T) d;
    }

    /**
     * You get the desktop session
     * @param driver T
     * @return T
     * @param <T> T
     */
    @SuppressWarnings("unchecked")
    public static <T extends RemoteWebDriver> T driver(T driver) {
        assertNotNull(driver);
        action(driver);
        return (T) (d = driver);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions action() {
        return a;
    }

    /**
     *
     * @param action Actions
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions action(org.openqa.selenium.interactions.Actions action) {
        return setActions(a = action);
    }

    /**
     *
     * @param driver T
     * @return Actions
     * @param <T> {@code extends} WebDriver
     */
    public static <T extends WebDriver> org.openqa.selenium.interactions.Actions action(T driver) {
        return action(new org.openqa.selenium.interactions.Actions(driver));
    }

    /**
     *
     * @return Action
     */
    public static Action getAction() {
        return setAction(action);
    }

    /**
     * You set WebElement
     * @param elem WebElement
     * @return WebElement
     */
    public static  WebElement el(WebElement elem) {
        return setEl(el = elem);
    }

    /**
     * Также нам может потребоваться имитация ввода клавиш с клавиатуры.
     * <p>В обычном порядке это делается методом sendKeys(«Последовательность символов») у элемента.
     * <p>Однако, следует помнить, что некоторые символы используются как служебные и их надо экранировать
     * <p>(например, "+" это Shift, и для того, чтобы ввести "+", нужно передать последовательность "+=").
     * <p>Для удобства пользования кодом можно написать обёртку, которая бы автоматически заменяла все "+" на "+=", но тут как кому удобнее.
     * <p>Подробнее ознакомиться со стандартами передач комбинаций клавиш можно, например, тут.
     * <p>Тем не менее, возникали проблемы с корректной передачей нажатий стрелок на клавиатуре, так что к сожалению,
     * <p>при текущей версии драйвера придётся искать обходные пути.
     * <p>где wrk – имя WebElement, от центра которого будем двигать мышкой;
     * <p>x, y – расстояние, на которое будем двигать (положительное значение x двигает курсор вправо, положительное y – вниз).
     * @param el WebElement
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions move(WebElement el) {
        return a.moveToElement(el);
    }

    /**
     *
     * @param el WebElement
     * @param x int
     * @param y int
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions move(WebElement el, int x, int y) {
        return a.moveToElement(el, x, y);
    }

    /**
     *
     * @param x int
     * @param y int
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions moveEl(int x, int y) {
        return a.moveToElement(el, x, y);
    }

    /**
     *
     * @param x int
     * @param y int
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions move(int x, int y) {
        return a.moveByOffset(x, y);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions click() {
        return a.click();
    }

    /**
     *
     * @param el WebElement
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions click(WebElement el) {
        return a.click(el);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions clickEl() {
        return a.click(el);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions doubleClick() {
        return a.doubleClick();
    }

    /**
     *
     * @param el WebElement
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions doubleClick(WebElement el) {
        return a.doubleClick(el);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions doubleClickEl() {
        return a.doubleClick(el);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions clickAndHold() {
        return a.clickAndHold();
    }

    /**
     *
     * @param el WebElement
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions clickAndHold(WebElement el) {
        return a.clickAndHold(el);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions clickAndHoldEl() {
        return a.clickAndHold(el);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions contextClick() {
        return a.contextClick();
    }

    /**
     *
     * @param el WebElement
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions contextClick(WebElement el) {
        return a.contextClick(el);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions contextClickEl() {
        return a.contextClick(el);
    }

    /**
     *
     * @param el WebElement
     * @param target WebElement
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions dragAndDrop(WebElement el, WebElement target) {
        return a.dragAndDrop(el, target);
    }

    /**
     *
     * @param el WebElement
     * @param x int
     * @param y int
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions dragAndDrop(WebElement el, int x, int y) {
        return a.dragAndDropBy(el, x, y);
    }

    /**
     *
     * @param target WebElement
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions dragAndDrop(WebElement target) {
        return a.dragAndDrop(el, target);
    }

    /**
     *
     * @param x int
     * @param y int
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions dragAndDrop(int x, int y) {
        return a.dragAndDropBy(el, x, y);
    }

    /**
     *
     * @param keys CharSequence[]
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions keys(CharSequence... keys) {
        return a.sendKeys(keys);
    }

    /**
     *
     * @param el WebElement
     * @param keys CharSequence[]
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions keys(WebElement el, CharSequence... keys) {
        return a.sendKeys(el, keys);
    }

    /**
     *
     * @param keys CharSequence[]
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions keysToEl(CharSequence... keys) {
        return a.sendKeys(el, keys);
    }

    /**
     *
     * @param el WebElement
     * @param keys CharSequence[]
     */
    public static void keysEl(WebElement el, CharSequence... keys) {
        el.sendKeys(keys);
    }

    /**
     *
     * @param keys CharSequence[]
     */
    public static void keysEl(CharSequence... keys) {
        el.sendKeys(keys);
    }

    /**
     *
     * @param key CharSequence
     * @param keys CharSequence[]
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions chord(CharSequence key, CharSequence... keys) {
        //return keys(key, Keys.chord(keys));
        return keys(Keys.chord(key, (CharSequence) stream(keys)));
    }

    /**
     *
     * @param el WebElement
     * @param keys CharSequence[]
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions chord(WebElement el, CharSequence... keys) {
        return keys(el, Keys.chord(keys));
    }

    /**
     *
     * @param keys CharSequence[]
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions chordToEl(CharSequence... keys) {
        return keys(el, Keys.chord(keys));
    }

    /**
     *
     * @param el WebElement
     * @param keys CharSequence[]
     */
    public static void chordEl(WebElement el, CharSequence... keys) {
        keysEl(el, Keys.chord(keys));
    }

    /**
     *
     * @param keys CharSequence[]
     */
    public static void chordEl(CharSequence... keys) {
        keysEl(el, Keys.chord(keys));
    }

    /**
     *
     * @param key CharSequence
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions down(CharSequence key) {
        return a.keyDown(key);
    }

    /**
     *
     * @param el WebElement
     * @param key CharSequence
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions down(WebElement el, CharSequence key) {
        return a.keyDown(el, key);
    }
    /**
     *
     * @param key CharSequence
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions downEl(CharSequence key) {
        return a.keyDown(el, key);
    }

    /**
     *
     * @param key CharSequence
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions up(CharSequence key) {
        return a.keyUp(key);
    }

    /**
     *
     * @param el WebElement
     * @param key CharSequence
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions up(WebElement el, CharSequence key) {
        return a.keyUp(el, key);
    }

    /**
     *
     * @param key CharSequence
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions upEl(CharSequence key) {
        return a.keyUp(el, key);
    }

    /**
     *
     * @param keys CharSequence[]
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions ctrl(CharSequence... keys) {
        return chord(CONTROL, keys);
    }

    /**
     *
     * @param keys CharSequence[]
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions alt(CharSequence... keys) {
        return chord(ALT, keys);
    }

    /**
     *
     * @param keys CharSequence[]
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions shift(CharSequence... keys) {
        return chord(SHIFT, keys);
    }

    /**
     *
     * @param keys CharSequence[]
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions leftCtrl(CharSequence... keys) {
        return chord(LEFT_CONTROL, keys);
    }

    /**
     *
     * @param keys CharSequence[]
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions leftAlt(CharSequence... keys) {
        return chord(LEFT_ALT, keys);
    }

    /**
     *
     * @param keys CharSequence[]
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions leftShift(CharSequence... keys) {
        return chord(LEFT_SHIFT, keys);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions enter() {
        return chord(ENTER);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions esc() {
        return chord(ESCAPE);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions cancel() {
        return chord(CANCEL);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions delete() {
        return chord(DELETE);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions space() {
        return chord(SPACE);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions tab() {
        return chord(TAB);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions backSpace() {
        return chord(BACK_SPACE);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions arrowUp() {
        return chord(ARROW_UP);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions arrowDown() {
        return chord(ARROW_DOWN);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions arrowRight() {
        return chord(ARROW_RIGHT);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions arrowLeft() {
        return chord(ARROW_LEFT);
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions copy() {
        return ctrl("c");
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions paste() {
        return ctrl("v");
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions save() {
        return ctrl("s");
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions selectAll() {
        return ctrl("a");
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions saveFile() {
        return alt("s");
    }

    /**
     *
     * @param filePath String
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions saveFile(String filePath) {
        a.sendKeys(filePath);
        return saveFile();
    }

    /**
     *
     * @return Action
     */
    public static Action build() {
        return a.build();
    }

    /**
     *
     * @return Actions
     */
    public static org.openqa.selenium.interactions.Actions perform() {
        a.perform();
        return a;
    }

    /**
     *
     * @return Action
     */
    public static Action performBuild() {
        action = build();
        action.perform();
        return action;
    }
}
