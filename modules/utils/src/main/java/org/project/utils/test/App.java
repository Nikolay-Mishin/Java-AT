package org.project.utils.test;

import static java.lang.Long.valueOf;
import static java.lang.System.getProperty;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.project.utils.Helper.debug;
import static org.project.utils.base.Register.getProps;
import static org.project.utils.base.Register.getPropsMap;
import static org.project.utils.base.Register.getSortedProps;
import static org.project.utils.config.BaseConfig.DEBUG_LEVEL;
import static org.project.utils.config.Config.configs;
import static org.project.utils.test.CucumberRunTest.setOptions;

import org.project.utils.base.SortedProperties;
import org.project.utils.config.WebConfig;

public class App extends TestException {
    protected static WindowsDriver<WebElement> winDriver;
    protected static ChromeDriver webDriver;
    protected static RemoteWebDriver remoteDriver;

    public App() throws ReflectiveOperationException {
        new TestAuth();
        new TestReq();
        new TestFS();
    }

    public static void main(String[] args) throws Exception {
        setOptions();
        debug("App:main");
        new App();
        printProps();
        //printConfig();
        debug(uri);

        //testException();
        //testMain();
        //testHeaders();
        //testApi();
        //testJson();
        //testZip();
        //testFS();
        //testAttrs();
        //testLong();
        //testEntries();
        //testReq();
        //testAuth();
        //testConfig();
        //testWinDriverConfig();
        //testPrintException();
        //testInvoke();
        //testHeaders(true);
        //testReqGet();
    }

    public static void testMain() throws IOException, IllegalAccessException, ClassNotFoundException {
        debug("testMain");
        driver = org.project.utils.windriver.RemoteWebDriver.start();
        remoteDriver = org.project.utils.windriver.RemoteWebDriver.start();
        webDriver = org.project.utils.windriver.WebDriver.start();
        winDriver = org.project.utils.windriver.WinDriver.start();
        org.project.utils.windriver.WinDriver.printCall();
        org.project.utils.windriver.WinDriver.quit(driver, remoteDriver, webDriver, winDriver);
    }

    public static void testLong() {
        debug("testLong");
        int _int = 0;
        long _long = Integer.valueOf(_int).longValue();

        _long((long) _int);
        _long((long) Integer.valueOf(_int));
        _long(Integer.valueOf(_int).longValue());

        _long(_long);
        _long(valueOf(0));
        _long(valueOf(_int));
        _long(valueOf(_long));
    }

    public static void _long(Long id) {
        debug(id);
    }

    public static void printConfig() {
        debug(configs());
        debug("DEBUG_LEVEL: " + DEBUG_LEVEL);
        debug("WebConfig: " + WebConfig.config());
    }

    public static void printProps() throws ReflectiveOperationException {
        SortedProperties props = getProps();
        debug("empty: " + props.isEmpty());

        Set<Object> devKeys = props.keySet();
        Collection<Object> devValues = props.values();
        String devEnv = props.getProperty("ENV");
        debug("devKeys: " + devKeys);
        debug("devValues: " + devValues);

        debug("propsMap: " + getPropsMap());
        debug("sortedProps: " + props.sortedProps());
        debug("props: " + props);
        debug("getSortedProps: " + getSortedProps());

        debug("props.dev:", getProperty("props.dev"));
        debug("props.web:", getProperty("props.web"));
        debug("props.test:", getProperty("props.test"));
        debug("props.win:", getProperty("props.win"));

        debug("dev.env:", devEnv);

        //printPropsMap();
        //printSortedPropsMap();
        //printSortedProps();
    }

    public static void printPropsMap() {
        getPropsMap().get("props.web").forEach((k, v) -> debug(k + "-> " + v));
    }

    public static void printSortedPropsMap() {
        getSortedProps().forEach((k, v) -> debug(k + "-> " + v));
    }

    public static void printSortedProps() throws ReflectiveOperationException {
        getProps().sortedProps().forEach((k, v) -> debug(k + "-> " + v));
    }

}
