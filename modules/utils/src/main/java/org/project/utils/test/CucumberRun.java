package org.project.utils.test;

import static java.lang.System.getProperty;
import static java.lang.System.setProperty;
import static java.lang.System.out;
import static java.lang.Thread.currentThread;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static io.cucumber.core.cli.Main.run;
import static org.project.utils.Helper.forEach;
import static org.project.utils.event.CucumberEventListener.getPlugins;

// аннотация: класс для запуска тестов Cucumber
@RunWith(Cucumber.class)
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
//@Config.Sources({"classpath:cucumber.properties"})
public class CucumberRun {

    public CucumberRun() {
        //setOptions();
    }

    @BeforeClass
    public static void setUp() {
        setOptions();
        //setTags();
    }

    public static void setOptions() {
        List<String> options = new ArrayList<>();
        forEach(getPlugins(), p -> {
            options.add("--plugin");
            options.add(p);
        });
        setOptions(options.toArray(String[]::new));
    }

    public static void setOptions(String[] options) {
        out.println(Arrays.toString(options));
        run(options, currentThread().getContextClassLoader());
    }

    public static void setTagsProps() {
        String tags = getProperty("cucumber.tags", "@smoke");
        setProperty("cucumber.tags", tags);
        out.println(tags);
    }

    public static void setTags() {
        setTagsDir("src/test/resources/");
    }

    public static void setTagsDir(String cucumberPropsDir) {
        setTags(cucumberPropsDir + "cucumber.properties");
    }

    protected static void setTags(String cucumberPropsPath) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(cucumberPropsPath));
            String tags = props.getProperty("cucumber.tags", "@smoke");
            System.setProperty("cucumber.tags", tags);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
