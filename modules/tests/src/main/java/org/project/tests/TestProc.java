package org.project.tests;

import static java.lang.System.setProperty;

import java.beans.ConstructorProperties;
import java.io.IOException;
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

import static org.project.tests.windriver.RemoteWebDriver.getWinDriver;
import static org.project.tests.windriver.RemoteWebDriver.winDriverHost;
import static org.project.tests.windriver.RemoteWebDriver.winDriverName;
import static org.project.tests.windriver.WinDriver.findByClass;
import static org.project.tests.windriver.WinDriver.start;
import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.entries;
import static org.project.utils.config.DriverConfig.config;
import static org.project.utils.stream.GobblerStream.stream;

import org.project.tests.windriver.Capabilities;
import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestProc<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestFind<T, W, D> {
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
    @ConstructorProperties({})
    public TestProc() {
        debug("TestProc:init");
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testBaseProc() throws Exception {
        d = start(notepadPath);
        WebElement wrk = findByClass("Edit");
        wrk.sendKeys("Привет Appium!");
        d.quit();
        pb = new ProcessBuilder("taskkill", "/f", "/IM", winDriverName());//.inheritIO();
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
    public static void testProc() throws IOException {
        pb = new ProcessBuilder(getWinDriver());//.inheritIO();
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

        d = new RemoteWebDriver(new URL(winDriverHost()), cap);
        assertNotNull(d);

        WebElement el = d.findElement(By.className("Edit"));
        el.sendKeys("Appium");

        stream(p);
        //transfer(p);

        d.quit();
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
        setProperty("webdriver.chrome.driver", config().getChromeDriver());
        // Initialize the Chrome driver
        web = new ChromeDriver(options);
        assertNotNull(web);

        web.get(url);
    }

}
