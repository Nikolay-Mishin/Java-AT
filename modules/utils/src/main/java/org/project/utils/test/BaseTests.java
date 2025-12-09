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
import static org.project.utils.base.Properties.props;
import static org.project.utils.base.Properties.propsMap;
import static org.project.utils.base.Properties.propsMapKeys;
import static org.project.utils.config.BaseConfig.DEBUG_LEVEL;
import static org.project.utils.config.Config.configs;
import static org.project.utils.request.Request.getParamsUri;
import static org.project.utils.test.CucumberRunTest.setOptions;
import static org.project.utils.test.TestAuth.*;
import static org.project.utils.test.TestConfig.*;
import static org.project.utils.test.TestEntries.*;
import static org.project.utils.test.TestFS.*;
import static org.project.utils.test.TestReq.*;

import org.project.utils.base.Properties;
import org.project.utils.config.WebConfig;

public class BaseTests extends TestException {
    protected static WindowsDriver<WebElement> winDriver;
    protected static ChromeDriver webDriver;
    protected static RemoteWebDriver remoteDriver;

    public BaseTests() {
        new TestAuth();
        new TestReq();
        new TestFS();
    }

    public static void main(String[] args) throws Exception {
        setOptions();
        debug("BaseTests:main");
        new BaseTests();
        printConfig();

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

    public static void printConfig() throws ReflectiveOperationException {
        printProps();
        debug(configs());
        debug("DEBUG_LEVEL: " + DEBUG_LEVEL);
        debug("WebConfig: " + WebConfig.config());
        debug(uri);
        debug(getParamsUri(uri, "id", 1, "token", "test"));
    }

    public static void printProps() throws ReflectiveOperationException {
        Properties props = props();
        debug("empty: " + props.isEmpty());

        Set<Object> devKeys = props.keySet();
        Collection<Object> devValues = props.values();
        String devEnv = props.getProperty("ENV");
        debug("devKeys: " + devKeys);
        debug("devValues: " + devValues);

        debug("propsMap: " + propsMap());
        debug("sortedProps: " + props.sortedProps());
        debug("props: " + props);

        debug("entrySet: " + props.entrySet());
        debug("keySet: " + props.keySet());

        debug("map: " + props.map());

        debug("propsMapKeys: " + propsMapKeys());

        debug("props.utils.dev:", getProperty("props.utils.dev"));
        debug("props.utils.web:", getProperty("props.utils.web"));
        debug("props.utils.test:", getProperty("props.utils.test"));
        debug("props.utils.win:", getProperty("props.utils.win"));

        debug("props.dev:", getProperty("props.dev"));
        debug("props.web:", getProperty("props.web"));
        debug("props.test:", getProperty("props.test"));
        debug("props.win:", getProperty("props.win"));

        debug("props.dev: " + propsMap().get("props.dev"));

        debug("dev.env:", devEnv);

        //printPropsMap();
        //printSortedProps();
    }

    public static void printPropsMap() {
        propsMap().get("props.web").forEach((k, v) -> debug(k + "-> " + v));
    }

    public static void printSortedProps() throws ReflectiveOperationException {
        props().forEach((k, v) -> debug(k + "-> " + v));
        //props().sortedProps().forEach((k, v) -> debug(k + "-> " + v));
    }

}
