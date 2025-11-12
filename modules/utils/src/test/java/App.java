import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import org.project.utils.base.HashMap;
import org.project.utils.config.BaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.windriver.DriverBaseConfig;
import org.project.utils.windriver.WebDriver;

import static org.project.utils.Helper._equals;
import static org.project.utils.Helper.debug;
import static org.project.utils.config.BaseConfig.BASE_CONFIG;
import static org.project.utils.windriver.WebDriver.ls;
import static org.project.utils.config.Config.*;
import static org.project.utils.windriver.WinDriver.start;

public class App {
    public static void main(String[] args) throws IllegalAccessException, MalformedURLException {
        //testWeb();
        //testConfig();
        testWinDriverConfig();
        //testWinDriver();
    }

    public static void testWeb() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, MalformedURLException {
        String url = "https://tds-test.itorum.ru/";
        WebDriver.start(url);
        // Set the localStorage data
        ls.set("accessToken", "");
        ls.set("refreshToken", "");
        // Print the localStorage data
        debug("localStorage data: " + ls.items());
        debug("localStorage item: " + ls.get("accessToken"));
        WebDriver.quit();
    }

    public static void testConfig() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        debug(config().getClass());
        printConfigs();
        debug(config("config").getClass());
        debug(DriverBaseConfig.BASE_CONFIG);
        debug(config("win"));
        debug(config("win").getClass());
        debug(config("win", DriverBaseConfig.class).getClass());
        printConfigs();
        //debug(BASE_CONFIG);
        //debug(WebBaseConfig.BASE_CONFIG);
        compare(BASE_CONFIG);
        compare("web", WebBaseConfig.BASE_CONFIG);
        printConfigs();
        debug(BASE_CONFIG.getConfig());
        debug(WebBaseConfig.BASE_CONFIG.getConfig());
    }

    public static void printConfigs() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        HashMap<String, BaseConfig> map = configs();
        debug("map: "/*+ map*/);
        for (String k : map.keys()) {
            debug(k);
            debug(map.get(k).getClass());
        }
    }

    public static void testWinDriverConfig() {
        //debug(WebBaseConfig.BASE_CONFIG.getBaseUrl());
        //debug(BASE_CONFIG.getDebugLevel());
        debug(DriverBaseConfig.BASE_CONFIG.getIsWinium());
        debug(DriverBaseConfig.BASE_CONFIG.getWinium());
        debug(DriverBaseConfig.BASE_CONFIG.getWiniumHost());
        //debug(DriverBaseConfig.BASE_CONFIG.getWindriverPath());
        //debug(DriverBaseConfig.BASE_CONFIG.getWindriverName());
        //debug(DriverBaseConfig.BASE_CONFIG.getWindriver());
        //debug(DriverBaseConfig.BASE_CONFIG.getWindriverPort());
        //debug(DriverBaseConfig.BASE_CONFIG.getWindriverHost());
        //debug(DriverBaseConfig.BASE_CONFIG.getWebdriverParam());
        debug(DriverBaseConfig.BASE_CONFIG.getChromeDriver());
        //debug(DriverBaseConfig.BASE_CONFIG.getExperimental());
        //debug(DriverBaseConfig.IS_WINIUM);
        //debug(DriverBaseConfig.WINDRIVER_NAME);
        debug(DriverBaseConfig.WINDRIVER);
        //debug(DriverBaseConfig.WINDRIVER_PORT);
        debug(DriverBaseConfig.WINDRIVER_HOST);
        //debug(_equals(DriverBaseConfig.BASE_CONFIG.getWebdriverParam(), ""));
    }

    public static void testWinDriver() throws IllegalAccessException, MalformedURLException {
        start();
    }

}
