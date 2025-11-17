package org.project;

import java.io.*;
import java.util.*;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebElement;

import org.project.utils.constant.Capabilities;

import static org.project.utils.Helper.*;

public class App {
    protected static WebDriver driver;
    protected static WindowsDriver<WebElement> winDriver;
    protected static ChromeDriver webDriver;
    protected static RemoteWebDriver remoteDriver;

    public static void main(String[] args) throws IOException, IllegalAccessException, ClassNotFoundException {
        debug(org.project.utils.windriver.RemoteWebDriver.driver());
        debug(org.project.utils.windriver.WebElement.driver());

        remoteDriver = org.project.utils.windriver.RemoteWebDriver.start();
        org.project.utils.windriver.RemoteWebDriver.printClass();

        driver = org.project.utils.windriver.RemoteWebDriver.start();
        org.project.utils.windriver.RemoteWebDriver.printClass();

        //org.project.utils.windriver.WebDriver.printCall();
        //org.project.utils.windriver.RemoteWebDriver.printCall();
        //org.project.utils.windriver.Actions.printCall();

        webDriver = org.project.utils.windriver.WebDriver.start();
        org.project.utils.windriver.WebDriver.printClass();

        winDriver = org.project.utils.windriver.WinDriver.start();
        org.project.utils.windriver.WinDriver.printClass();
    }
}
