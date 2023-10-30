import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// аннотация: класс для запуска тестов Cucumber
@RunWith(Cucumber.class)
// настройки Cucumber
@CucumberOptions(
    features = "src/test/resources/features/order", // директория тестов
    glue = "com.project", // название проекта
    plugin = {"pretty", "html:results.html", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"} // формат вывода результата
)
public class OrderTest {
}
