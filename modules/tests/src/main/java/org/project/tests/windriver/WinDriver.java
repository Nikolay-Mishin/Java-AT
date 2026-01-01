package org.project.tests.windriver;

import java.net.URL;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import io.appium.java_client.HasSettings;
import io.appium.java_client.Setting;
import io.appium.java_client.driverscripts.ScriptOptions;
import io.appium.java_client.driverscripts.ScriptValue;
import io.appium.java_client.screenrecording.BaseStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.BaseStopScreenRecordingOptions;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.http.HttpMethod;

/**
 *
 */
public class WinDriver extends RemoteWebDriver<WindowsDriver<WebElement>> {
    /**
     *
     */
    protected static WindowsDriver<WebElement> d;

    /**
     *
     * @return WindowsDriver {WebElement}
     */
    public static WindowsDriver<WebElement> driver() {
        return d;
    }

    /**
     *
     * @param driver WindowsDriver {WebElement}
     * @return WindowsDriver {WebElement}
     */
    public static WindowsDriver<WebElement> driver(WindowsDriver<WebElement> driver) {
        return d = driver;
    }

    /**
     * ClassInitialize
     * @return WindowsDriver {WebElement}
     * @throws Exception throws
     */
    public static WindowsDriver<WebElement> start() throws Exception {
        return driver(start(cap(c)));
    }

    /**
     * ClassInitialize
     * @param app String
     * @param params String[]
     * @return WindowsDriver {WebElement}
     * @throws Exception throws
     */
    public static WindowsDriver<WebElement> start(String app, String... params) throws Exception {
        return driver(RemoteWebDriver.start(app, params));
    }

    /**
     *
     * @param loc Location
     */
    public static void setLocation(Location loc) {
        d.setLocation(loc);
    }

    /**
     *
     * @return URL
     */
    public static URL getRemoteAddress() {
        return d.getRemoteAddress();
    }

    /**
     *
     * @return String
     */
    public static String getContext() {
        return d.getContext();
    }

    /**
     *
     */
    public static void resetApp() {
        d.resetApp();
    }

    /**
     *
     * @return ScreenOrientation
     */
    public static ScreenOrientation getOrientation() {
        return d.getOrientation();
    }

    /**
     *
     * @return String
     */
    public static String getAutomationName() {
        return d.getAutomationName();
    }

    /**
     *
     * @return Map {String, Object}
     */
    public static Map<String, Object> getStatus() {
        return d.getStatus();
    }

    /**
     *
     * @return Strin
     */
    public static String getDeviceTime() {
        return d.getDeviceTime();
    }

    /**
     *
     * @param format String
     * @return Strin
     */
    public static String getDeviceTime(String format) {
        return d.getDeviceTime(format);
    }

    /**
     *
     * @return Map {String, Object}
     */
    public static Map<String, String> getAppStringMap() {
        return d.getAppStringMap();
    }

    /**
     *
     * @return List {Map {String, Object}}
     */
    public static List<Map<String, Object>> getAllSessionDetails() {
        return d.getAllSessionDetails();
    }

    /**
     *
     * @return Map {String, Object}
     */
    public static Map<String, Object> getSessionDetails() {
        return d.getSessionDetails();
    }

    /**
     *
     * @param detail String
     * @return Object
     */
    public static Object getSessionDetail(String detail) {
        return d.getSessionDetail(detail);
    }

    /**
     *
     * @return Map {String, Object}
     */
    public static Map<String, Object> getSettings() {
        return d.getSettings();
    }

    /**
     *
     * @param setting Setting
     * @param value Object
     * @return HasSettings
     */
    public static HasSettings setSetting(Setting setting, Object value) {
        return d.setSetting(setting, value);
    }

    /**
     *
     * @param settingName String
     * @param value Object
     * @return HasSettings
     */
    public static HasSettings setSetting(String settingName, Object value) {
        return d.setSetting(settingName, value);
    }

    /**
     *
     * @param settings Map {String, Object}
     * @return HasSettings
     */
    public static HasSettings setSettings(Map<String, Object> settings) {
        return d.setSettings(settings);
    }

    /**
     *
     * @param settings EnumMap {String, Object}
     * @return HasSettings
     */
    public static HasSettings setSettings(EnumMap<Setting, Object> settings) {
        return d.setSettings(settings);
    }

    /**
     *
     * @param httpMethod HttpMethod
     * @param url String
     * @param methodName String
     */
    public static void addCommand(HttpMethod httpMethod, String url, String methodName) {
        d.addCommand(httpMethod, url, methodName);
    }

    /**
     *
     * @param command String
     * @return Response
     */
    public static Response execute(String command) {
        return d.execute(command);
    }

    /**
     *
     * @param driverCommand String
     * @param parameters Map {String, ?}
     * @return Response
     */
    public static Response execute(String driverCommand, Map<String, ?> parameters) {
        return d.execute(driverCommand, parameters);
    }

    /**
     *
     * @return ExecuteMethod
     */
    public static ExecuteMethod execMethod() {
        return d.getExecuteMethod();
    }

    /**
     *
     * @param s String
     * @param map Map {String, ?}
     * @return Object
     */
    public static Object execMethod(String s, Map<String, ?> map) {
        return execMethod().execute(s, map);
    }

    /**
     *
     * @param script String
     * @return ScriptValue
     */
    public static ScriptValue executeScript(String script) {
        return d.executeDriverScript(script);
    }

    /**
     *
     * @param driverCommand String
     * @param options ScriptOptions
     * @return ScriptValue
     */
    public static ScriptValue executeScript(String driverCommand, ScriptOptions options) {
        return d.executeDriverScript(driverCommand, options);
    }

    /**
     *
     * @return String
     */
    public static String startRecordingScreen() {
        return d.startRecordingScreen();
    }

    /**
     *
     * @param options T extends BaseStartScreenRecordingOptions
     * @return String
     * @param <T> T
     */
    public static <T extends BaseStartScreenRecordingOptions<T>> String startRecordingScreen(T options) {
        return d.startRecordingScreen(options);
    }

    /**
     *
     * @return String
     */
    public static String stopRecordingScreen() {
        return d.stopRecordingScreen();
    }

    /**
     *
     * @param options T extends BaseStopScreenRecordingOptions
     * @return String
     * @param <T> T
     */
    public static <T extends BaseStopScreenRecordingOptions<T>> String stopRecordingScreen(T options) {
        return d.stopRecordingScreen(options);
    }

    /**
     * Один элемент, поиск по полю AccessibilityId.
     * <p>Основным элементом взаимодействия с внешним миром являются «рычаги».
     * <p>Их можно удобно описать классом с полями name, className и automationId и хранить в отдельных классах по блокам.
     * <p>By.name – по полю Name, By.className — по полю ClassName и By.xpath для более изощрённых условий поиска
     * <p>Так же мы можем прикрепляться к элементам другого элемента
     * @param id String
     * @return WebElement
     */
    //один элемент, поиск по полю AccessibilityId
    public static WebElement findByAId(String id) {
        return el(d.findElementByAccessibilityId(id));
    }

    /**
     * Один элемент, поиск по полю AutomationID
     * @param id String
     * @return WebElement
     */
    public static WebElement findByAutoId(String id) {
        return el(d.findElementByWindowsUIAutomation(id));
    }

    /**
     * Один элемент, поиск по полю Image
     * @param b64 String
     * @return WebElement
     */
    public static WebElement findByImage(String b64) {
        return el(d.findElementByImage(b64));
    }

    /**
     * Один элемент, поиск по полю Custom Selector
     * @param selector String
     * @return WebElement
     */
    public static WebElement findBySelector(String selector) {
        return el(d.findElementByCustom(selector));
    }

    /**
     * Список элементов, поиск по полю AccessibilityId
     * @param id String
     * @return List {WebElement}
     */
    public static List<WebElement> listByAccessId(String id) {
        return list(d.findElementsByAccessibilityId(id));
    }

    /**
     * Список элементов, поиск по полю AutomationID
     * @param id String
     * @return List {WebElement}
     */
    public static List<WebElement> listByAid(String id) {
        return list(d.findElementsByWindowsUIAutomation(id));
    }

    /**
     * Список элементов, поиск по полю Image
     * @param b64 String
     * @return List {WebElement}
     */
    public static List<WebElement> listByImage(String b64) {
        return list(d.findElementsByImage(b64));
    }

    /**
     * Список элементов, поиск по полю Image
     * @param selector String
     * @return List {WebElement}
     */
    public static List<WebElement> listBySelector(String selector) {
        return list(d.findElementsByCustom(selector));
    }
}
