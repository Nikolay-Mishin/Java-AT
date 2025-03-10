package org.project.utils.windriver;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import static org.project.utils.constant.UrlConstants.*;
import static org.project.utils.Helper.setProp;

public class WebDriver extends WinDriver {
    protected static JavascriptExecutor js;
    public static LocalStorage ls;

    //[ProcessInitialize]
    public static void init() throws IOException {
        init(CHROME_DRIVER, WINDRIVER_PARAM);
    }

    public static void open() {
        setProp("webdriver.chrome.driver", CHROME_DRIVER);
        // Initialize the Chrome driver
        driver = new ChromeDriver();
        Assert.assertNotNull(driver);
        // Cast WebDriver to JavascriptExecutor
        js = (JavascriptExecutor) driver;
        ls = new LocalStorage(js);
    }

    public static void stop() {
        stop(CHROME_DRIVER);
    }

    //[ClassInitialize]
    public static org.openqa.selenium.WebDriver start(String url) {
        open();
        // Navigate to the webpage where localStorage data is stored
        driver.get(url);
        return driver;
    }

    //[AppSessionQuit]
    public static void quit() {
        WebDriver.quit();
        js = null;
        ls = null;
    }
}
