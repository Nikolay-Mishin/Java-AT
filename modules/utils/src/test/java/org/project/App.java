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

        debug(org.project.utils.windriver.RemoteWebDriver.driver());
        debug(org.project.utils.windriver.WebElement.driver());
        debug(org.project.utils.windriver.RemoteWebDriver.driver().getClass());
        debug(org.project.utils.windriver.WebElement.driver().getClass());
        org.project.utils.windriver.RemoteWebDriver.printClass();
        org.project.utils.windriver.WebElement.printClass();

        driver = org.project.utils.windriver.RemoteWebDriver.start();

        debug(org.project.utils.windriver.RemoteWebDriver.driver());
        debug(org.project.utils.windriver.WebElement.driver());
        debug(org.project.utils.windriver.RemoteWebDriver.driver().getClass());
        debug(org.project.utils.windriver.WebElement.driver().getClass());
        org.project.utils.windriver.RemoteWebDriver.printClass();
        org.project.utils.windriver.WebElement.printClass();

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("ms:experimental-webdriver", true);
        Map<String, Object> map = getObjectFields(new Capabilities());
        debug(map);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            debug(k + ": " + v);
            debug(Objects.equals(k, "app"));
            //if (Objects.equals(k, "app")) v = app;
            if (v != "") {
                cap.setCapability(k, v);
            }
        }

        //webDriver = (ChromeDriver) new RemoteWebDriver(new URL(WINDRIVER_HOST), cap);
        //webDriver = org.project.utils.windriver.RemoteWebDriver.start();
        webDriver = org.project.utils.windriver.WebDriver.start();

        debug(org.project.utils.windriver.RemoteWebDriver.driver());
        debug(org.project.utils.windriver.WebElement.driver());
        debug(org.project.utils.windriver.RemoteWebDriver.driver().getClass());
        debug(org.project.utils.windriver.WebElement.driver().getClass());
        org.project.utils.windriver.RemoteWebDriver.printClass();
        org.project.utils.windriver.WebElement.printClass();

        winDriver = org.project.utils.windriver.RemoteWebDriver.start();

        debug(org.project.utils.windriver.RemoteWebDriver.driver());
        debug(org.project.utils.windriver.WebElement.driver());
        debug(org.project.utils.windriver.RemoteWebDriver.driver().getClass());
        debug(org.project.utils.windriver.WebElement.driver().getClass());
        org.project.utils.windriver.RemoteWebDriver.printClass();
        org.project.utils.windriver.WebElement.printClass();
    }
}
