package org.project;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.appium.java_client.windows.WindowsDriver;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebElement;
import org.project.utils.json.JsonSchema;
import org.project.utils.request.Request;

import static org.project.utils.Helper.*;
import static org.project.utils.constant.RequestConstants.METHOD.GET;

public class App {
    protected static WebDriver driver;
    protected static WindowsDriver<WebElement> winDriver;
    protected static ChromeDriver webDriver;
    protected static RemoteWebDriver remoteDriver;

    public static void main(String[] args) throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //testMain();
        //testApi();
        testJson();
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

    public static void testApi() throws IOException, URISyntaxException {
        Request req = new Request(GET, "path").uri("https://googlechromelabs.github.io/");
        req.printFullPath();
    }

    public static void testJson()
        throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        String ver = "142.0.7444.61";
        String uri = "https://googlechromelabs.github.io/";
        String endpoint = "chrome-for-testing/" + ver + ".json";

        Request req = new Request(GET, endpoint).uri(uri);
        String jsonStr = req.asString();

        debug(req.asPrettyString());

        JsonSchema json = new JsonSchema(jsonStr);
        String version = json.get("version", "string");
        JSONArray chromedriver = json.get("downloads.chromedriver", "array");
        Map<String, Object> map = json.toMap();

        debug(version);
        debug(chromedriver);
        debug(map);
        //debug(json.array("downloads.chromedriver"));

        debug(map.get("downloads"));
        debug(json.toMap("downloads"));

        List<JSONObject> list = json.toList("downloads.chromedriver", o -> o.get("platform").equals("win64"));
        debug(list);
        debug(json.toList("downloads.chromedriver", "platform", "win64"));

        List<JSONObject> list1 = JsonSchema.toList(chromedriver, o -> o.get("platform").equals("win64"));
        debug(list1);
        debug(JsonSchema.toList(chromedriver, "platform", "win64"));

        debug(json.arrayToMap("downloads.chromedriver"));
        debug(json.toMap("downloads.chromedriver", o -> ((JSONObject) o).get("platform").equals("win64")));
        debug(json.toMap("downloads.chromedriver", "platform", "win64"));
    }
}
