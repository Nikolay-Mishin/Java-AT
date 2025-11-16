package org.project.utils.windriver;

import static java.lang.System.setProperty;
import static org.project.utils.Helper.debug;
import static org.project.utils.reflection.Reflection.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

public class WebDriver<T> extends RemoteWebDriver {
    protected static ChromeDriver d;
    protected static ChromeOptions options = new ChromeOptions();
    protected static final String chromeDriver = c.getChromeDriver();
    public static JavascriptExecutor js;
    public static LocalStorage ls;
    public static SessionStorage s;

    public WebDriver() throws ClassNotFoundException {
        //super();
        debug(getGenericClass());
    }

    public static ChromeDriver driver(ChromeDriver driver) throws MalformedURLException {
        return d = driver;
    }

    //[ProcessInitialize]
    public static void init() throws IOException, IllegalAccessException {
        init(c.getChromeDriver(), c.getWebdriverParam());
    }

    //[ClassInitialize]
    public static ChromeDriver start() throws MalformedURLException, ClassNotFoundException {
        setProperty("webdriver.chrome.driver", chromeDriver);
        //start(setCap());
        start(options());
        Assert.assertNotNull(d);
        s(d);
        return d;
    }

    public static ChromeDriver start(String url) throws MalformedURLException, ClassNotFoundException {
        start();
        return get(url);
    }

    // Initialize the Chrome driver
    public static ChromeDriver start(DesiredCapabilities cap) throws MalformedURLException, ClassNotFoundException {
        return start(new ChromeDriver(cap));
    }

    // Initialize the Chrome driver
    public static ChromeDriver start(ChromeOptions options) throws MalformedURLException, ClassNotFoundException {
        return start(new ChromeDriver(options));
    }

    /*public static ChromeDriver start(ChromeDriver driver) throws MalformedURLException {
        Assert.assertNotNull(driver);
        driver(driver);
        action(driver);
        return driver;
    }*/

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

    public static void s(ChromeDriver driver) {
        // Cast WebDriver to JavascriptExecutor
        //js = (JavascriptExecutor) driver;
        //ls = new LocalStorage(js);
        ls = new LocalStorage(driver);
        s = new SessionStorage(driver);
    }

    //[AppSessionQuit]
    public static void quit() {
        quit(chromeDriver);
        js = null;
        ls = null;
        s = null;
    }
}
