package org.project;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebElement;
import org.project.utils.config.ApiConfig;
import org.project.utils.constant.RequestConstants;
import org.project.utils.request.Request;

import static org.project.utils.config.ApiConfig.getRequestSpec;
import static org.project.utils.constant.RequestConstants.METHOD.GET;

public class App {
    protected static WebDriver driver;
    protected static WindowsDriver<WebElement> winDriver;
    protected static ChromeDriver webDriver;
    protected static RemoteWebDriver remoteDriver;

    public static void main(String[] args)
        throws IOException, IllegalAccessException, URISyntaxException, InvocationTargetException, NoSuchMethodException
    {
        //testMain();
        testApi();
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

    public static void testApi()
        throws MalformedURLException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        new Request(GET, "path");
        new Request(GET, "path").response();
    }
}
