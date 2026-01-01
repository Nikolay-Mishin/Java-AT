package org.project.utils.test.run;

import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

import org.project.utils.config.CucumberConfig;
import org.project.utils.config.TestBaseConfig;

/**
 * класс для запуска тестов Cucumber
 * @param <T> extends TestBaseConfig
 */
// аннотация: класс для запуска тестов Cucumber
@RunWith(Cucumber.class)
public class CucumberRunTest<T extends TestBaseConfig> extends CucumberConfig<T> {
}
