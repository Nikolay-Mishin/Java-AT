package org.project.utils.test;

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

public class BaseTests extends TestDriver {

    public BaseTests() {
        debug("BaseTests:main");
        new TestAuth();
        new TestReq();
        new TestFS();
    }

    public BaseTests(int m) throws Exception {
        testMain(m);
    }

    public BaseTests(int m, int n) throws Exception {
        testMain(m);
        testBase(n);
    }

    public static void main(String[] args) throws Exception {
        setOptions(BASE_CONFIG);
        new BaseTests(3);
    }

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
