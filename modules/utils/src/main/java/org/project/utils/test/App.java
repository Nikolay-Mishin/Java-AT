package org.project.utils.test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import io.appium.java_client.windows.WindowsDriver;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.project.utils.Helper.debug;
import static org.project.utils.constant.RequestConstants.METHOD.GET;
import static org.testng.Assert.assertEquals;

import org.project.utils.request.Request;

public class App extends TestZip {
    protected static WebDriver driver;
    protected static WindowsDriver<WebElement> winDriver;
    protected static ChromeDriver webDriver;
    protected static RemoteWebDriver remoteDriver;

    public static void main(String[] args)
        throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, URISyntaxException, ClassNotFoundException, InstantiationException
    {
        //testMain();
        //testApi();
        //testJson();
        testZip();
    }

    public static void testMain() throws IOException, IllegalAccessException, ClassNotFoundException {
        driver = org.project.utils.windriver.RemoteWebDriver.start();
        remoteDriver = org.project.utils.windriver.RemoteWebDriver.start();
        webDriver = org.project.utils.windriver.WebDriver.start();
        winDriver = org.project.utils.windriver.WinDriver.start();
        org.project.utils.windriver.WinDriver.printCall();
        //org.project.utils.windriver.WebDriver.stop();
        //org.project.utils.windriver.WebDriver.quit();
    }

    public static void testApi() throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Response resp1 = new Request(GET, "store/order", 0).response();

        debug(resp1);
        debug(resp1.asString());
        debug(resp1.asPrettyString());
        debug(resp1.asInputStream());
        debug(resp1.asByteArray());
        debug(resp1.contentType());
        debug(resp1.headers());
        debug(resp1.sessionId());
        debug(resp1.cookies());
        debug(resp1.detailedCookies());

        Request req1 = new Request(GET, "store/order", 1);
        String resp2 = req1.string();

        debug(req1);
        debug(resp2);

        Request req = new Request(GET).uri(uri);
        Response resp = req.response();
        req.printFullPath();
        req.printUrl();
        debug(req.baseUri());
        debug(resp);
        debug(resp);

        assertEquals(resp.getStatusCode(), 200);
    }

}
