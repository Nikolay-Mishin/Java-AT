import org.junit.BeforeClass;

import org.project.utils.test.CucumberRunTest;

import static config.TestConfig.BASE_CONFIG;

public class ApiTest extends CucumberRunTest {
    @BeforeClass
    public static void setUp() throws ReflectiveOperationException {
        setUp(BASE_CONFIG);
    }
}
