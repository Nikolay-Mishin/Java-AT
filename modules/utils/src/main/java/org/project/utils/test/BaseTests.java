package org.project.utils.test;

import java.beans.ConstructorProperties;

import static org.project.utils.Helper.debug;
import static org.project.utils.Thread.getSleep;
import static org.project.utils.Thread.timeout;
import static org.project.utils.config.Config.env;
import static org.project.utils.windriver.RemoteWebDriver.getTimeout;
import static org.project.utils.windriver.RemoteWebDriver.sleep;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.test.run.CucumberBaseTest;

/**
 *
 */
public class BaseTests<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestModel<T, W, D> {

    /**
     *
     * @param args String[]
     * @throws Exception throws
     */
    public static void main(String[] args) throws Exception {
        new CucumberBaseTest();
        new BaseTests<>(1);
        debug("c: " + c());
        debug("w: " + w());
        debug("win: " + win());
        debug("instance: " + instance());
        debug("sleep: " + getSleep());
        debug("timeout: " + timeout());
        debug("sleep: " + sleep());
        debug("timeout: " + getTimeout());
        debug("env: " + env());
    }

    /**
     *
     * @throws Exception throws
     */
    @ConstructorProperties({})
    public BaseTests() throws Exception {
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
     * <p>28: testModel();
     * <p>29: testDriverInit();
     * <p>30: testDriverApp();
     * <p>31: testAppEdit();
     * <p>32: testAppCalc();
     * <p>33: testHandleApp();
     * <p>34: testVersion();
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
            case 28: testModel();
                break;
            case 29: testDriverInit();
                break;
            case 30: testDriverApp();
                break;
            case 31: testAppEdit();
                break;
            case 32: testAppCalc();
                break;
            case 33: testHandleApp();
                break;
            case 34: testVersion();
                break;
        }
    }

}
