package org.project.utils.test;

import static java.lang.Long.valueOf;

import java.io.IOException;
import java.util.Arrays;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.BaseConfig.BASE_CONFIG;
import static org.project.utils.config.BaseConfig.DEBUG_LEVEL;
import static org.project.utils.config.Config.configs;
import static org.project.utils.config.TestConfig.config;
import static org.project.utils.event.CucumberEventListener.getPlugins;

import org.project.utils.config.WebConfig;
import org.project.utils.event.CucumberEventListener;

public class App extends TestException {
    protected static WindowsDriver<WebElement> winDriver;
    protected static ChromeDriver webDriver;
    protected static RemoteWebDriver remoteDriver;

    public static void main(String[] args) throws Exception {
        debug("App:main");
        new CucumberEventListener();
        new App();
        //debug(configs());
        debug(uri);
        debug(Arrays.toString(getPlugins()));

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

        //printConfig();
    }

    public App() {
        new TestAuth();
        new TestReq();
        new TestFS();
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
        //debug("BASE_CONFIG: " + BASE_CONFIG);
        debug("WebConfig: " + WebConfig.config());
    }

}
