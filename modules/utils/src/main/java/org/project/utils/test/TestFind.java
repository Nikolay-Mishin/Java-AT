package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.project.utils.Helper.debug;
import static org.project.utils.windriver.WinDriver.findByName;
import static org.project.utils.windriver.WinDriver.quit;
import static org.project.utils.windriver.WinDriver.start;
import static org.project.utils.windriver.WinDriver.timeout;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.DriverConfig;
import org.project.utils.config.TestBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestFind<T extends TestBaseConfig> extends TestAuth<T> {
    /**
     *
     */
    protected static DriverBaseConfig win;
    /**
     *
     */
    protected static String notepadPath;
    /**
     *
     */
    protected static String calcPath;

    /**
     *
     */
    @ConstructorProperties({})
    public TestFind() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("TestFind:init");
        win = DriverConfig.getConfig();
        notepadPath = win.getNotepad();
        calcPath = win.getCalc();
    }

    /**
     * TestMethod
     * @throws Exception throws
     */
    //[TestMethod]
    public static void makeOperation() throws Exception {
        driver = start(calcPath);
        WebElement el = findByName("txtNumber");
        el.clear();
        el.sendKeys("5");
        timeout();

        findByName("btnGetSquare").click();
        timeout(1000);

        WebElement txtResultTextElement = findByName("txtResult");
        assertNotNull(txtResultTextElement);

        assertEquals("25", txtResultTextElement.getText());
        quit();
    }

    /**
     * Основным элементом взаимодействия с внешним миром являются «рычаги».
     * <p>Их можно удобно описать классом с полями name, className и automationId и хранить в отдельных классах по блокам.
     * @param aid int
     * @throws Exception throws
     */
    public static void testFind(int aid) throws Exception {
        driver = start(notepadPath);
        // By.name – по полю Name, By.className — по полю ClassName и By.xpath для более изощрённых условий поиска
        WebElement wrk = driver.findElement(By.name("Name")); //один элемент, поиск по полю Name
        //список элементов с заданным полем ClassName - элементы будут добавлены в порядке tab-ордера
        List<WebElement> wrkL = driver.findElements(By.className("ClassName"));
        WebElement wrk1 = wrk.findElement(By.name("Значение поля Name")); //Так же мы можем прикрепляться к элементам другого элемента
        /**
         * Если первые два механизма прикрепления очень узкоспециализированы - работают строго в иерархической структуре и строго с полями Name и ClassName, то для работы с иными случаями нам потребуется третий механизм, а именно By.xpath.
         * В этом механизме всё строго по канонам использования xpath (во всяком случае, с использованных случаях всё работало как нужно).
         * С помощью xpath можно получить и обрабатывать поля IsOffscreen, AutomationId, HasKeyboardFocus:
         */
        WebElement field = wrk.findElement(By.xpath("*[@HasKeyboardFocus='True']")); //найдёт элемент у wrk, на котором стоит фокус
        /**
         * При использовании xpath удобно для отслеживания вероятных ошибок логировать всю строку, передаваемую в xpath:
         * будем искать элемент у текущего окна с каким-то заданным AutomationId = autId и у которого свойство IsOffscreen = False
         */
        String xpathStr = "*[(@AutomationId='" + aid + "') and (@IsOffscreen='False')]";
        debug("Performing xpath search: " + xpathStr);
        WebElement tWrk = wrk1.findElement(By.xpath(xpathStr));
        quit();
    }

}
