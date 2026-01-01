package org.project.testng.run;

import static java.lang.System.out;

import java.beans.ConstructorProperties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import static org.project.utils.Helper.debug;

import org.project.utils.config.CucumberRunConfig;
import org.project.utils.config.TestBaseConfig;

/**
 * класс для запуска тестов Testng
 * @param <T> extends TestBaseConfig
 */
public class TestngRunTest<T extends TestBaseConfig> extends CucumberRunConfig<T> {

    /**
     * CucumberRunTest:init
     */
    @ConstructorProperties({})
    public TestngRunTest() {
        out.println("TestngRunTest:init");
    }

    /**
     * setUp
     */
    @BeforeClass
    public static void setUp() {
        debug("setUp");
    }

    /**
     * setUp:app
     */
    @BeforeClass
    public void setUpApp() {
        debug("setUp:app");
    }

    /**
     * setUp:staticSuite
     */
    @BeforeSuite
    public static void setUpSuite() {
        debug("setUp:suite");
    }

    /**
     * setUp:suite
     */
    @BeforeSuite
    public void setUpSuiteApp() {
        debug("setUp:suiteApp");
    }

    /**
     * testSetUp
     * @param filePath String
     */
    @BeforeTest
    @Parameters(value={"filePath"})
    public void testSetUp(String filePath) {
        debug("testSetUp: " + filePath);
    }

}
