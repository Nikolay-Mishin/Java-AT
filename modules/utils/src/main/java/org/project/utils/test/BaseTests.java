package org.project.utils.test;

import java.beans.ConstructorProperties;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.TestBaseConfig.BASE_CONFIG;
import static org.project.utils.test.CucumberRunTest.setOptions;
import static org.project.utils.test.TestAuth.testAuth;
import static org.project.utils.test.TestConfig.printConfig;
import static org.project.utils.test.TestConfig.testConfig;
import static org.project.utils.test.TestConfig.testWinDriverConfig;
import static org.project.utils.test.TestEntries.testEntries;
import static org.project.utils.test.TestFS.testAttrs;
import static org.project.utils.test.TestFS.testFS;
import static org.project.utils.test.TestNumber.testLong;
import static org.project.utils.test.TestReq.testReqGet;
import static org.project.utils.test.TestReq.testReqPost;
import static org.project.utils.test.TestReq.testReqTest;
import static org.project.utils.test.TestUTF8.testUTF8;

/**
 *
 */
public class BaseTests extends TestDriver {

    /**
     *
     * @param args String[]
     * @throws Exception throws
     */
    public static void main(String[] args) throws Exception {
        setOptions(BASE_CONFIG);
        new BaseTests(1, 9);
    }

    /**
     *
     */
    @ConstructorProperties({})
    public BaseTests() {
        init();
    }

    /**
     *
     * @param m {@link #testMain}
     * @throws Exception throws
     */
    @ConstructorProperties({"m"})
    public BaseTests(int m) throws Exception {
        init();
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
        init();
        testMain(m);
        testBase(n);
    }

    /**
     *
     */
    public static void init() {
        debug("BaseTests:main");
        new TestAuth();
        new TestReq();
        new TestFS();
    }

    /**
     * 0: printConfig();
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
     * 0: testException();
     * <p>1: testHeaders();
     * <p>2: testApi();
     * <p>3: testJson();
     * <p>4: testZip();
     * <p>5: testFS();
     * <p>6: testAttrs();
     * <p>7: testEntries();
     * <p>8: testReqTest();
     * <p>9: testAuth();
     * <p>10: testConfig();
     * <p>11: testWinDriverConfig();
     * <p>12: testInvoke();
     * <p>13: testHeaders(true);
     * <p>14: testPrintException();
     * <p>15: testFnExceptions();
     * <p>16: testUTF8();
     * <p>17: testReqPost();
     * <p>18: testReqGet();
     * @param n int
     * @throws Exception throws
     */
    public static void testBase(int n) throws Exception {
        switch (n) {
            case 0: testException();
                break;
            case 1: testHeaders();
                break;
            case 2: testApi();
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
            case 8: testReqTest();
                break;
            case 9: testAuth();
                break;
            case 10: testConfig();
                break;
            case 11: testWinDriverConfig();
                break;
            case 12: testInvoke();
                break;
            case 13: testHeaders(true);
                break;
            case 14: testPrintException();
                break;
            case 15: testFnExceptions();
                break;
            case 16: testUTF8();
                break;
            case 17: testReqPost();
                break;
            case 18: testReqGet();
                break;
        }
    }

}
