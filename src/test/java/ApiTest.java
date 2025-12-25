import org.junit.BeforeClass;

import org.project.utils.test.run.CucumberRunTest;

import config.TestConfig;

public class ApiTest extends CucumberRunTest<TestConfig> {
    @BeforeClass
    public static void setUp() {
        new ApiTest();
    }
}
