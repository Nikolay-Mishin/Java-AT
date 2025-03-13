import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.debug;
import static org.project.utils.windriver.WebDriver.ls;
import static org.project.utils.windriver.WebDriver.*;

public class App {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        //testWeb();
        testConfig();
    }

    public static void testWeb() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String url = "https://tds-test.itorum.ru/";
        start(url);
        // Set the localStorage data
        ls.set("accessToken", "");
        ls.set("refreshToken", "");
        // Print the localStorage data
        debug("localStorage data: " + ls.items());
        debug("localStorage item: " + ls.get("accessToken"));
        quit();
    }

    public static void testConfig() {
        debug(config());
    }
}
