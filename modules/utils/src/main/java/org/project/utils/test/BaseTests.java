package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.TestBaseConfig.BASE_CONFIG;
import static org.project.utils.test.CucumberRunTest.setOptions;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;

/**
 *
 */
public class BaseTests<T extends TestBaseConfig, D extends DriverBaseConfig, W extends WebBaseConfig> extends TestWeb<T, D, W> {

    /**
     *
     * @param args String[]
     * @throws Exception throws
     */
    public static void main(String[] args) throws Exception {
        setOptions(BASE_CONFIG);
        new BaseTests<>(1);
        debug("instance: " + instance());
    }

    /**
     *
     */
    @ConstructorProperties({})
    public BaseTests() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("BaseTests:init");
    }

    /**
     *
     * @param m {@link #testMain}
     * @throws Exception throws
     */
    @ConstructorProperties({"m"})
    public BaseTests(int m) throws Exception {
        testMain(m);
    }

    /**
     *
     * @param m {@link #testMain}
     * @param n {@link #testBase}
     * @throws Exception throws
     */
    @ConstructorProperties({"m", "n"})
    public BaseTests(int m, int n) throws Exception {
        testMain(m);
        testBase(n);
    }

    /**
     * <p>0: printConfig();
     * <p>1: printProps();
     * <p>2: testTimeout();
     * <p>3: testDriver();
     * <p>4: testLong();
     * <p>5: printPropsMap();
     * <p>6: printSortedProps();
     * <p>7: printGetSortedProps();
     * @param n int
     * @throws Exception throws
     */
    public static void testMain(int n) throws Exception {
        debug("BaseTests:main");
        switch (n) {
            case 0: printConfig();
                break;
            case 1: printProps();
                break;
            case 2: testTimeout();
                break;
            case 3: testDriver();
                break;
            case 4: testLong();
                break;
            case 5: printPropsMap();
                break;
            case 6: printSortedProps();
                break;
            case 7: printGetSortedProps();
                break;
        }
    }

    /**
     * <p>1: testApi();
     * <p>2: testHeaders();
     * <p>3: testJson();
     * <p>4: testZip();
     * <p>5: testFS();
     * <p>6: testAttrs();
     * <p>7: testEntries();
     * <p>8: testAuth();
     * <p>9: testConfig();
     * <p>10: testWinDriverConfig();
     * <p>11: testInvoke();
     * <p>12: testHeaders(true);
     * <p>13: testException();
     * <p>14: testPrintException();
     * <p>15: testFnExceptions();
     * <p>16: testUTF8();
     * <p>17: testReqPost();
     * <p>18: testReqTest();
     * <p>19: testReqGet();
     * <p>20: testWeb();
     * <p>21: testWinDriver();
     * <p>22: testWebDriver();
     * <p>23: testBaseProc();
     * <p>24: testProc();
     * <p>25: testProcWeb();
     * <p>26: makeOperation();
     * <p>27: testFind(0);
     * @param n int
     * @throws Exception throws
     */
    public static void testBase(int n) throws Exception {
        debug("BaseTests:base");
        switch (n) {
            case 1: testApi();
                break;
            case 2: testHeaders();
                break;
            case 3: testJson();
                break;
            case 4: testZip();
                break;
            case 5: testFS();
                break;
            case 6: testAttrs();
                break;
            case 7: testEntries();
                break;
            case 8: testAuth();
                break;
            case 9: testConfig();
                break;
            case 10: testWinDriverConfig();
                break;
            case 11: testInvoke();
                break;
            case 12: testHeaders(true);
                break;
            case 13: testException();
                break;
            case 14: testPrintException();
                break;
            case 15: testFnExceptions();
                break;
            case 16: testUTF8();
                break;
            case 17: testReqPost();
                break;
            case 18: testReqTest();
                break;
            case 19: testReqGet();
                break;
            case 20: testWeb();
                break;
            case 21: testWinDriver();
                break;
            case 22: testWebDriver();
                break;
            case 23: testBaseProc();
                break;
            case 24: testProc();
                break;
            case 25: testProcWeb();
                break;
            case 26: makeOperation();
                break;
            case 27: testFind(0);
                break;
        }
    }

}
