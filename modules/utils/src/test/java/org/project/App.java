package org.project;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import io.appium.java_client.windows.WindowsDriver;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebElement;
import org.project.utils.json.JsonSchema;
import org.project.utils.request.Request;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.toList;
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

    public static void testJson() throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String uri = "https://googlechromelabs.github.io/chrome-for-testing/142.0.7444.61.json";

        Request req = new Request(GET).uri(uri);
        String jsonStr = req.asString();
        debug(req.asPrettyString());

        JsonSchema json = new JsonSchema(jsonStr);
        String version = json.get("version", "string");
        JSONArray chromedriver = json.get("downloads.chromedriver", "array");
        Map<String, Object> map = json.toMap();

        debug(version);
        debug(chromedriver);
        debug(map);

        List<JSONObject> list = toList(chromedriver, o -> o.get("platform").equals("win64"));
        debug(list);

        Map<String, Object> map0 = list.get(0).toMap();
        debug(map0);
        debug(map0.get("url"));
        debug(map.get("downloads"));

        debug(json.toMap("downloads"));

        List<JSONObject> list1 = json.toList("downloads.chromedriver", o -> o.get("platform").equals("win64"));
        debug(list1);
        debug(json.toList("downloads.chromedriver", "platform", "win64"));

        List<JSONObject> list2 = JsonSchema.toList(chromedriver, o -> o.get("platform").equals("win64"));
        debug(list2);
        debug(JsonSchema.toList(chromedriver, "platform", "win64"));
    }
}
