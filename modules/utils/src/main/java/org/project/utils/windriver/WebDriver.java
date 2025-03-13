package org.project.utils.windriver;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import static org.project.utils.windriver.Config.*;
import static org.project.utils.Helper.setProp;

public class WebDriver extends WinDriver {
    protected static ChromeDriver driver;
    protected static JavascriptExecutor js;
    public static LocalStorage ls;
    public static SessionStorage s;

    //[ProcessInitialize]
    public static void init() throws IOException {
        init(CHROME_DRIVER, WINDRIVER_PARAM);
    }

    public static void open() {
        DesiredCapabilities cap = new DesiredCapabilities();
        // Additional options using ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Maximize the browser window
        options.addArguments("--disable-infobars"); // Disable the info bars
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        //open(cap);
        open(options);
    }

    public static void open(ChromeOptions options) {
        setProp("webdriver.chrome.driver", CHROME_DRIVER);
        // Initialize the Chrome driver
        driver = new ChromeDriver(options);
        Assert.assertNotNull(driver);
        ls(driver);
    }

    public static void open(DesiredCapabilities cap) {
        setProp("webdriver.chrome.driver", CHROME_DRIVER);
        // Initialize the Chrome driver
        driver = new ChromeDriver(cap);
        Assert.assertNotNull(driver);
        ls(driver);
    }

    public static void ls(ChromeDriver driver) {
        // Cast WebDriver to JavascriptExecutor
        //js = (JavascriptExecutor) driver;
        //ls = new LocalStorage(js);
        ls = new LocalStorage(driver);
        s = new SessionStorage(driver);
    }

    public static void stop() {
        stop(CHROME_DRIVER);
    }

    //[ClassInitialize]
    public static ChromeDriver start(String url) {
        open();
        // Navigate to the webpage where localStorage data is stored
        driver.get(url);
        return driver;
    }

    //[AppSessionQuit]
    public static void quit() {
        quit(CHROME_DRIVER);
        js = null;
        ls = null;
        s = null;
    }
}
