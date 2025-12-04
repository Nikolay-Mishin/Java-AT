package org.project.utils.windriver;

import java.net.MalformedURLException;
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

public class WinDriver extends RemoteWebDriver {
    protected static WindowsDriver<WebElement> d;

    //[ClassInitialize]
    public static WindowsDriver<WebElement> start() throws MalformedURLException, IllegalAccessException, ClassNotFoundException {
        return start(setCap());
    }

    public static void setLocation(Location loc) {
        d.setLocation(loc);
    }

    public static URL getRemoteAddress() {
        return d.getRemoteAddress();
    }

    public static String getContext() {
        return d.getContext();
    }

    public static void resetApp() {
        d.resetApp();
    }

    public static ScreenOrientation getOrientation() {
        return d.getOrientation();
    }

    public static String getAutomationName() {
        return d.getAutomationName();
    }

    public static Map<String, Object> getStatus() {
        return d.getStatus();
    }

    public static String getDeviceTime() {
        return d.getDeviceTime();
    }

    public static String getDeviceTime(String format) {
        return d.getDeviceTime(format);
    }

    public static Map<String, String> getAppStringMap() {
        return d.getAppStringMap();
    }

    public static List<Map<String, Object>> getAllSessionDetails() {
        return d.getAllSessionDetails();
    }

    public static Map<String, Object> getSessionDetails() {
        return d.getSessionDetails();
    }

    public static Object getSessionDetail(String detail) {
        return d.getSessionDetail(detail);
    }

    public static Map<String, Object> getSettings() {
        return d.getSettings();
    }

    public static HasSettings setSetting(Setting setting, Object value) {
        return d.setSetting(setting, value);
    }

    public static HasSettings setSetting(String settingName, Object value) {
        return d.setSetting(settingName, value);
    }

    public static HasSettings setSettings(Map<String, Object> settings) {
        return d.setSettings(settings);
    }

    public static HasSettings setSettings(EnumMap<Setting, Object> settings) {
        return d.setSettings(settings);
    }

    public static void addCommand(HttpMethod httpMethod, String url, String methodName) {
        d.addCommand(httpMethod, url, methodName);
    }

    public static Response execute(String command) {
        return d.execute(command);
    }

    public static Response execute(String driverCommand, Map<String, ?> parameters) {
        return d.execute(driverCommand, parameters);
    }

    public static ExecuteMethod execMethod() {
        return d.getExecuteMethod();
    }

    public static Object execMethod(String s, Map<String, ?> map) {
        return execMethod().execute(s, map);
    }

    public static ScriptValue executeScript(String script) {
        return d.executeDriverScript(script);
    }

    public static ScriptValue executeScript(String driverCommand, ScriptOptions options) {
        return d.executeDriverScript(driverCommand, options);
    }

    public static String startRecordingScreen() {
        return d.startRecordingScreen();
    }

    public static <T extends BaseStartScreenRecordingOptions> String startRecordingScreen(T options) {
        return d.startRecordingScreen(options);
    }

    public static String stopRecordingScreen() {
        return d.stopRecordingScreen();
    }

    public static <T extends BaseStopScreenRecordingOptions> String stopRecordingScreen(T options) {
        return d.stopRecordingScreen(options);
    }

    /**
     * Основным элементом взаимодействия с внешним миром являются «рычаги».
     * Их можно удобно описать классом с полями name, className и automationId и хранить в отдельных классах по блокам.
     * By.name – по полю Name, By.className — по полю ClassName и By.xpath для более изощрённых условий поиска
     * Так же мы можем прикрепляться к элементам другого элемента
     */
    //один элемент, поиск по полю AccessibilityId
    public static WebElement findByAccessId(String id) {
        return d.findElementByAccessibilityId(id);
    }

    //один элемент, поиск по полю AutomationID
    public static WebElement findByAid(String id) {
        return d.findElementByWindowsUIAutomation(id);
    }

    //один элемент, поиск по полю Image
    public static WebElement findByImage(String b64) {
        return d.findElementByImage(b64);
    }

    //один элемент, поиск по полю Custom Selector
    public static WebElement findBySelector(String selector) {
        return d.findElementByCustom(selector);
    }

    //один элемент, поиск по полю AccessibilityId
    public static List<WebElement> listByAccessId(String id) {
        return d.findElementsByAccessibilityId(id);
    }

    //один элемент, поиск по полю AutomationID
    public static List<WebElement> listByAid(String id) {
        return d.findElementsByWindowsUIAutomation(id);
    }

    //один элемент, поиск по полю Image
    public static List<WebElement> listByImage(String b64) {
        return d.findElementsByImage(b64);
    }

    //один элемент, поиск по полю Custom Selector
    public static List<WebElement> listBySelector(String selector) {
        return d.findElementsByCustom(selector);
    }
}
