package org.project.utils.test;

import static java.lang.Long.valueOf;

import java.io.IOException;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.project.utils.event.CucumberEventListener;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.Config.configs;

public class App extends TestException {
    protected static WindowsDriver<WebElement> winDriver;
    protected static ChromeDriver webDriver;
    protected static RemoteWebDriver remoteDriver;

    public static void main(String[] args) throws Exception {
        debug("App:main");
        new CucumberEventListener(cPlugins);
        debug(configs());
        //testException();
        testMain();
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

        driver.quit();
        remoteDriver.quit();
        webDriver.quit();
        winDriver.quit();

        org.project.utils.windriver.RemoteWebDriver.quit();
        org.project.utils.windriver.WebDriver.quit();
        org.project.utils.windriver.WinDriver.quit();
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

}
