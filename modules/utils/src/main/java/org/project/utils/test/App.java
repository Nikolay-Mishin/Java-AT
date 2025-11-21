package org.project.utils.test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static java.lang.Long.valueOf;
import static org.project.utils.Helper.debug;

public class App extends TestZip {
    protected static WebDriver driver;
    protected static WindowsDriver<WebElement> winDriver;
    protected static ChromeDriver webDriver;
    protected static RemoteWebDriver remoteDriver;

    public static void main(String[] args)
        throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, URISyntaxException, ClassNotFoundException, InstantiationException
    {
        //testMain();
        //testApi();
        //testJson();
        //testZip();
        testLong();
    }

    public static void testLong() {
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

    public static void testMain() throws IOException, IllegalAccessException, ClassNotFoundException {
        driver = org.project.utils.windriver.RemoteWebDriver.start();
        remoteDriver = org.project.utils.windriver.RemoteWebDriver.start();
        webDriver = org.project.utils.windriver.WebDriver.start();
        winDriver = org.project.utils.windriver.WinDriver.start();
        org.project.utils.windriver.WinDriver.printCall();
        //org.project.utils.windriver.WebDriver.stop();
        //org.project.utils.windriver.WebDriver.quit();
    }

}
