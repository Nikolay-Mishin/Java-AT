package org.project.utils.windriver;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.project.utils.constant.Capabilities;
import org.testng.Assert;

import static org.project.utils.Helper.*;

public class WebDriver extends WinDriver {
    protected static ChromeDriver driver;
    protected static ChromeOptions options = new ChromeOptions();
    protected static final String chromeDriver = c.getChromeDriver();
    public static JavascriptExecutor js;
    public static LocalStorage ls;
    public static SessionStorage s;

    public static ChromeDriver driver(ChromeDriver driver) {
        return WebDriver.driver = driver;
    }

    //[ProcessInitialize]
    public static void init() throws IOException, IllegalAccessException {
        init(c.getChromeDriver(), c.getWebdriverParam());
    }

    //[ClassInitialize]
    public static ChromeDriver start() {
        open();
        return driver;
    }

    //[ClassInitialize]
    public static ChromeDriver start(String url) {
        open();
        get(url);
        return driver;
    }

    // Navigate to the webpage where localStorage data is stored
    public static ChromeDriver get(String url) {
        driver.get(url);
        return driver;
    }

    public static void open() {
        setProp("webdriver.chrome.driver", chromeDriver);
        //open(setCap());
        open(options());
        Assert.assertNotNull(driver);
        s(driver);
    }

    //[Options]
    public static ChromeOptions options() {
        // Additional options using ChromeOptions
        options.addArguments("--start-maximized"); // Maximize the browser window
        options.addArguments("--disable-infobars"); // Disable the info bars
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        return options;
    }

    //[Capabilities]
    public static DesiredCapabilities setCap() {
        cap.setCapability(ChromeOptions.CAPABILITY, options());
        return cap;
    }

    // Initialize the Chrome driver
    public static void open(DesiredCapabilities cap) {
        driver = new ChromeDriver(cap);
    }

    // Initialize the Chrome driver
    public static void open(ChromeOptions options) {
        driver = new ChromeDriver(options);
    }

    public static void stop() {
        stop(chromeDriver);
    }

    //[AppSessionQuit]
    public static void quit() {
        quit(driver, chromeDriver);
        driver = null;
        js = null;
        ls = null;
        s = null;
    }

    public static void s(ChromeDriver driver) {
        // Cast WebDriver to JavascriptExecutor
        //js = (JavascriptExecutor) driver;
        //ls = new LocalStorage(js);
        ls = new LocalStorage(driver);
        s = new SessionStorage(driver);
    }
}
