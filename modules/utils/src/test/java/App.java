import java.lang.reflect.InvocationTargetException;

import org.project.utils.base.HashMap;
import org.project.utils.config.BaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.windriver.Config;
import org.project.utils.windriver.WebDriver;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.BaseConfig.BASE_CONFIG;
import static org.project.utils.windriver.WebDriver.ls;
import static org.project.utils.config.Config.*;

public class App {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        //testWeb();
        testConfig();
    }

    public static void testWeb() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
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

    public static void testConfig()
        throws InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        debug(config().getClass());
        printConfigs();
        debug(config("config").getClass());
        debug(config("win", Config.class).getClass());
        printConfigs();
        debug(config("win").getClass());
        //debug(BASE_CONFIG);
        //debug(WebBaseConfig.BASE_CONFIG);
        compare(BASE_CONFIG);
        compare("web", WebBaseConfig.BASE_CONFIG);
        printConfigs();
        debug(BASE_CONFIG.getConfig());
        debug(WebBaseConfig.BASE_CONFIG.getConfig());
    }

    public static void printConfigs()
        throws InvocationTargetException, NoSuchMethodException, IllegalAccessException
    {
        HashMap<String, BaseConfig> map = configs();
        debug("map: "/*+ map*/);
        for (String k : map.keys()) {
            debug(k);
            debug(map.get(k).getClass());
        }
    }
}
