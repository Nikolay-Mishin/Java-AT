package org.project.utils.test;

import static java.lang.System.setProperty;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Objects;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.entries;
import static org.project.utils.config.DriverBaseConfig.BASE_CONFIG;
import static org.project.utils.config.DriverBaseConfig.WINDRIVER;
import static org.project.utils.config.DriverBaseConfig.WINDRIVER_HOST;
import static org.project.utils.config.DriverBaseConfig.WINDRIVER_NAME;
import static org.project.utils.stream.GobblerStream.stream;
import static org.project.utils.windriver.WebDriver.start;
import static org.project.utils.windriver.WinDriver.findByClass;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.windriver.Capabilities;
import org.project.utils.windriver.WebDriver;
import org.project.utils.windriver.WinDriver;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestProc<T extends TestBaseConfig, D extends DriverBaseConfig> extends TestFind<T, D> {
    /**
     *
     */
    protected static ProcessBuilder pb;
    /**
     *
     */
    protected static Process p;
    /**
     *
     */
    protected static ChromeDriver webDriver;

    /**
     *
     * @throws NoSuchFieldException throws
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    @ConstructorProperties({})
    public TestProc() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("TestProc:init");
    }

    /**
     * @param driver ChromeDriver
     * @return ChromeDriver
     */
    public static ChromeDriver webDriver(ChromeDriver driver) {
        webDriver = driver;
        assertNotNull(webDriver);
        return webDriver;
    }

    /**
     *
     * @return ChromeDriver
     * @throws Exception throws
     */
    public static ChromeDriver webDriver() throws Exception {
        return webDriver(start());
    }

    /**
     *
     * @param url String
     * @return ChromeDriver
     * @throws Exception throws
     */
    public static ChromeDriver webDriver(String url) throws Exception {
        return webDriver(start(url));
    }

    /**
     *
     */
    public static void quitWeb() {
        WebDriver.quit();
        webDriver = null;
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testBaseProc() throws Exception {
        driver = WinDriver.start(notepadPath);
        WebElement wrk = findByClass("Edit");
        wrk.sendKeys("Привет Appium!");
        driver.quit();
        pb = new ProcessBuilder("taskkill", "/f", "/IM", WINDRIVER_NAME);//.inheritIO();
        p = pb.start();

        stream(p);
        //transfer(p);

        quitWeb();
    }

    /**
     *
     * @throws IOException throws
     * @throws ReflectiveOperationException throws
     */
    public static void testProc() throws IOException, ReflectiveOperationException {
        pb = new ProcessBuilder(WINDRIVER);//.inheritIO();
        p = pb.start();

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("ms:experimental-webdriver", true);
        Map<String, Object> map = entries(new Capabilities());
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

        driver = new RemoteWebDriver(new URL(WINDRIVER_HOST), cap);
        assertNotNull(driver);

        WebElement el = driver.findElement(By.className("Edit"));
        el.sendKeys("Appium");

        stream(p);
        //transfer(p);

        driver.quit();
        p.destroy();
    }

    /**
     *
     */
    public static void testProcWeb() {
        DesiredCapabilities cap = new DesiredCapabilities();
        // Additional options using ChromeOptions
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--start-maximized"); // Maximize the browser window
        options.addArguments("--disable-infobars"); // Disable the info bars
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        // Set the path to the ChromeDriver executable
        setProperty("webdriver.chrome.driver", BASE_CONFIG.getChromeDriver());
        // Initialize the Chrome driver
        webDriver = new ChromeDriver(options);
        assertNotNull(webDriver);

        webDriver.get(url);
    }

}
