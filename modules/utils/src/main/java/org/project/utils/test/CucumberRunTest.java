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

import static io.cucumber.core.cli.Main.run;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.aeonbits.owner.Config.Sources;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static org.project.utils.Helper.forEach;
import static org.project.utils.event.CucumberEventListener.getPlugins;

import org.project.utils.config.TestBaseConfig;

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
@Sources({"classpath:cucumber.properties"})
public class CucumberRunTest {

    public CucumberRunTest() {
        //setOptions();
    }

    @BeforeClass
    public static void setUp() {
        setOptions();
    }

    public static void setOptions(String[] options) {
        out.println(Arrays.toString(options));
        run(options, currentThread().getContextClassLoader());
    }

    public static void setOptions() {
        setOptions(getOptions());
    }

    public static void setOptions(TestBaseConfig config) throws ReflectiveOperationException {
        setOptions(getOptions(config));
    }

    public static void setOptions(String plugins) throws ReflectiveOperationException {
        setOptions(getOptions(plugins));
    }

    public static String[] getOptions() {
        return getOptions(getPlugins());
    }

    public static String[] getOptions(TestBaseConfig config) throws ReflectiveOperationException {
        return getOptions(getPlugins(config));
    }

    public static String[] getOptions(String plugins) throws ReflectiveOperationException {
        return getOptions(getPlugins(plugins));
    }

    public static String[] getOptions(String[] plugins) {
        List<String> options = new ArrayList<>();
        forEach(plugins, p -> {
            options.add("--plugin");
            options.add(p);
        });
        return options.toArray(String[]::new);
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

    public void setTagsOptions() {
        List<String> tagsList = Arrays.asList("@smoke", "@regression");
        String[] tagsArray = tagsList.toArray(new String[0]);
        CucumberOptions options = this.getClass().getAnnotation(CucumberOptions.class);
        //options.tags(tagsArray);
    }

}
