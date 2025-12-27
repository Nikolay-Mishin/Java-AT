package org.project.utils.config;

import io.cucumber.junit.CucumberOptions;

/**
 *
 * @param <T> extends TestBaseConfig
 */
// настройки Cucumber
@CucumberOptions(
    //monochrome = true,
    features = "src/test/resources/features", // директория тестов
    glue = "com.project", // название проекта
    plugin = {
        "pretty", "html:results.html" // формат вывода результата
        //"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        //"org.project.utils.event.CucumberEventListener:config.WebConfig"
    }
)
public class CucumberConfig<T extends TestBaseConfig> extends CucumberRunConfig<T> {
}
