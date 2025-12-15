package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.project.utils.Helper.debug;
import static org.project.utils.Thread.setTimeout;
import static org.project.utils.config.WebConfig.getConfig;
import static org.project.utils.windriver.RemoteWebDriver.open;
import static org.project.utils.windriver.WebDriver.ls;
import static org.project.utils.windriver.WebDriver.start;

import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.windriver.WinDriver;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestWinDriver<T extends TestBaseConfig> extends TestProc<T> {
    /**
     *
     */
    protected static WebBaseConfig w;
    /**
     *
     */
    protected static String url;
    /**
     *
     */
    protected static WindowsDriver<WebElement> winDriver;
    /**
     *
     */
    protected static RemoteWebDriver remoteDriver;

    /**
     *
     */
    @ConstructorProperties({})
    public TestWinDriver() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("TestWinDriver:init");
        w = getConfig();
        url = w.getBaseUrl();
    }

    /**
     *
     * @throws IOException throws
     * @throws InterruptedException throws
     */
    public static void testTimeout() throws IOException, InterruptedException {
        debug("testTimeout");
        open();
        org.project.utils.windriver.WinDriver.quit();
        setTimeout(() -> { open(); return null; });
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testDriver() throws Exception {
        debug("testDriver");
        driver = org.project.utils.windriver.RemoteWebDriver.start();
        remoteDriver = org.project.utils.windriver.RemoteWebDriver.start();
        webDriver = org.project.utils.windriver.WebDriver.start();
        winDriver = org.project.utils.windriver.WinDriver.start();
        org.project.utils.windriver.WinDriver.printCall();
        org.project.utils.windriver.WinDriver.quit(driver, remoteDriver, webDriver, winDriver);
        org.project.utils.windriver.WinDriver.start("");
    }

    /**
     *
     * @throws Exception throws
     */
    public static void testWinDriver() throws Exception {
        WinDriver.start();
        start(url);
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     * @throws MalformedURLException throws
     */
    public static void testWebDriver() throws ReflectiveOperationException, MalformedURLException {
        start(url);
        // Set the localStorage data
        ls.set("accessToken", "");
        ls.set("refreshToken", "");
        // Print the localStorage data
        debug("localStorage data: " + ls.items());
        debug("localStorage item: " + ls.get("accessToken"));
        quit();
    }

}
