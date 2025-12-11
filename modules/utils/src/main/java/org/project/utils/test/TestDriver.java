package org.project.utils.test;

import java.io.IOException;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.project.utils.Helper.debug;
import static org.project.utils.Thread.setTimeout;
import static org.project.utils.windriver.RemoteWebDriver.open;

public class TestDriver extends TestProps {
    protected static WindowsDriver<WebElement> winDriver;
    protected static ChromeDriver webDriver;
    protected static RemoteWebDriver remoteDriver;

    public static void main(String[] args) throws Exception {
        testTimeout();
        testDriver();
    }

    public static void testTimeout() throws IOException, InterruptedException {
        debug("testTimeout");
        open();
        org.project.utils.windriver.WinDriver.quit();
        setTimeout(() -> { open(); return null; });
    }

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

}
