package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import static org.junit.Assert.assertNotNull;

import org.openqa.selenium.JavascriptExecutor;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.sb;
import static org.project.utils.request.Request.getParams;
import static org.project.utils.request.Request.getParamsSlash;
import static org.project.utils.windriver.WebDriver.ls;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestWeb<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestWinDriver<T, W, D> {
    /**
     *
     */
    protected static String endpoint;
    /**
     *
     */
    protected static String tokenK;
    /**
     *
     */
    protected static String token;
    /**
     *
     */
    protected static String project;

    /**
     *
     */
    @ConstructorProperties({})
    public TestWeb() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("TestWeb:init");
        endpoint = w.getEndpoint();
        tokenK = w.getTokenKey();
        token = w.getToken();
        project = url(w.getProjectId(), token);
        debug("getParams: " + getParamsSlash(tokenK, token));
    }

    /**
     *
     * @param project int
     * @param token String
     * @return String
     */
    public static String url(int project, String token) {
        return sb(endpoint, project, getParams(tokenK, token));
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     * @throws MalformedURLException throws
     */
    public static void testWeb() throws ReflectiveOperationException, MalformedURLException {
        webDriver = org.project.utils.windriver.WebDriver.start(url);
        assertNotNull(webDriver);

        // Cast WebDriver to JavascriptExecutor
        JavascriptExecutor js = webDriver;

        // Execute JavaScript to retrieve item from localStorage
        String setItemScript = "localStorage.setItem(arguments[0], arguments[1])";
        String getItemsScript = "return JSON.stringify(localStorage);";
        String getItemScript = "return localStorage.getItem('accessToken');";

        //js.executeScript(setItemScript, "accessToken", "");
        //js.executeScript(setItemScript, "refreshToken", "");
        String localStorageData = (String) js.executeScript(getItemsScript);
        String localStorageItem = (String) js.executeScript(getItemScript);
        //webDriver.executeScript(setItemScript);

        // Print the localStorage data
        debug("localStorage item: " + localStorageItem);
        debug("localStorage data: " + localStorageData);

        ls.set("accessToken", "");
        ls.set("refreshToken", "");

        // Print the localStorage data
        debug("localStorage item: " + ls.get("accessToken"));
        debug("localStorage data: " + ls.items());

        // Navigate to the webpage where localStorage data is stored
        debug("project: " + project);
        webDriver.get(project);

        /*
        assertFalse(driverWeb.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).isSelected());
        driverWeb.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).click();
        assertTrue(driverWeb.findElement(By.cssSelector("input[id*='SeniorCitizenDiscount']")).isSelected());
        //count total number of checkbox
        debug(driverWeb.findElements(By.cssSelector("input[type='checkbox']")).size());
        */
        //quit();
    }

}
