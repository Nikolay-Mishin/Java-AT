import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// аннотация: класс для запуска тестов Cucumber
@RunWith(Cucumber.class)
// настройки Cucumber
@CucumberOptions(
    features = "src/test/resources/features", // директория тестов
    glue = "com.project", // название проекта
    plugin = {"pretty", "html:results.html", // формат вывода результата
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        "event.CucumberEventListener:config.WebConfig"
    }
)
public class ApiTest {
}
