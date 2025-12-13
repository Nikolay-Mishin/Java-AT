package org.project.utils.windriver;

import static java.lang.System.setProperty;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.junit.Assert.assertNotNull;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 *
 */
public class WebDriver extends RemoteWebDriver {
    /**
     *
     */
    protected static ChromeOptions options = new ChromeOptions();
    /**
     *
     */
    protected static final String chromeDriver = c.getChromeDriver();
    /**
     *
     */
    protected static final String chromeDriverName = c.getChromeDriverName();
    /**
     *
     */
    public static JavascriptExecutor js;
    /**
     *
     */
    public static LocalStorage ls;
    /**
     *
     */
    public static SessionStorage s;

    /**
     *
     * @throws IOException throws
     * @throws IllegalAccessException throws
     */
    //[ProcessInitialize]
    public static void init() throws IOException, IllegalAccessException {
        init(c.getChromeDriver(), c.getWebdriverParam());
    }

    /**
     * ClassInitialize
     * @return ChromeDriver
     * @throws MalformedURLException throws
     * @throws ClassNotFoundException throws
     */
    //[ClassInitialize]
    public static ChromeDriver start() throws MalformedURLException, ClassNotFoundException {
        setProperty("webdriver.chrome.driver", chromeDriver);
        start(options());
        assertNotNull(d);
        s((ChromeDriver) d);
        return (ChromeDriver) d;
    }

    /**
     *
     * @param url String
     * @return ChromeDriver
     * @throws MalformedURLException throws
     * @throws ClassNotFoundException throws
     */
    public static ChromeDriver start(String url) throws MalformedURLException, ClassNotFoundException {
        start();
        return get(url);
    }

    /**
     * Initialize the Chrome driver
     * @param options ChromeOptions
     * @return ChromeDriver
     * @throws MalformedURLException throws
     * @throws ClassNotFoundException throws
     */
    public static ChromeDriver start(ChromeOptions options) throws MalformedURLException, ClassNotFoundException {
        return start(new ChromeDriver(options));
    }

    /**
     * Additional options using ChromeOptions: Maximize the browser window, Disable the info bars
     * @return ChromeOptions
     */
    public static ChromeOptions options() {
        return options.addArguments("--start-maximized", "--disable-infobars");
    }

    /**
     *
     * @param driver ChromeDriver
     */
    public static void s(ChromeDriver driver) {
        //js = (JavascriptExecutor) driver; // Cast WebDriver to JavascriptExecutor
        //ls = new LocalStorage(js);
        ls = new LocalStorage(driver);
        s = new SessionStorage(driver);
    }

    /**
     * AppSessionQuit
     */
    public static void quit() {
        quit(chromeDriverName);
        js = null;
        ls = null;
        s = null;
    }

    /**
     * DriverSessionQuit
     */
    public static void stop() {
        stop(chromeDriverName);
    }

}
