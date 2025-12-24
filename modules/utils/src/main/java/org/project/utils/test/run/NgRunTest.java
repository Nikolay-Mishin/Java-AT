package org.project.utils.test.run;

import static java.lang.System.out;

import java.beans.ConstructorProperties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.project.utils.Helper.debug;

import org.project.utils.config.TestBaseConfig;

public class NgRunTest<T extends TestBaseConfig> extends CucumberConfigTest<T> {

    /**
     * CucumberRunTest:init
     */
    @ConstructorProperties({})
    public NgRunTest() {
        out.println("NgRunTest:init");
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

    /**
     * test:init
     */
    @Test(description="Test init")
    public void testInit() {
        debug("test:init");
    }

}
