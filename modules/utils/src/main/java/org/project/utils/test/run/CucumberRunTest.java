package org.project.utils.test.run;

import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * класс для запуска тестов Cucumber
 */
// аннотация: класс для запуска тестов Cucumber
@RunWith(Cucumber.class)
public class CucumberRunTest<T extends org.project.utils.config.TestBaseConfig> extends org.project.utils.config.CucumberConfig<T> {
}
