import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// аннотация: класс для запуска тестов Cucumber
@RunWith(Cucumber.class)
// настройки Cucumber
@CucumberOptions(
    features = "src/test/resources", // директория тестов
    glue = "com.project", // название проекта
    plugin = {"html:results.html"} // формат вывода результата
)
public class PetTest {
}
