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
import java.util.function.Function;

import static io.cucumber.core.cli.Main.run;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.forEach;
import static org.project.utils.config.TestBaseConfig.BASE_CONFIG;
import static org.project.utils.event.CucumberEventListener.getPlugins;

import org.project.utils.config.TestBaseConfig;
import org.project.utils.event.CucumberEventListener;

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
public class CucumberRunTest {

    public CucumberRunTest() {
        //setOptions();
    }

    @BeforeClass
    public static void setUp() throws ReflectiveOperationException {
        setOptions();
    }

    public static String[] setOptions() throws ReflectiveOperationException {
        return setOptions(getOptions());
    }

    public static String[] setOptions(TestBaseConfig config) throws ReflectiveOperationException {
        return setOptions(getOptions(config));
    }

    public static String[] setOptions(String plugins) throws ReflectiveOperationException {
        return setOptions(getOptions(plugins));
    }

    public static String[] setOptions(String[] options) {
        return setOptions(options, o -> o);
    }

    public static String[] setCliOptions() throws ReflectiveOperationException {
        return setCliOptions(BASE_CONFIG);
    }

    public static String[] setCliOptions(TestBaseConfig config) throws ReflectiveOperationException {
        return setCliOptions(getOptions(config));
    }

    public static String[] setCliOptions(String plugins) throws ReflectiveOperationException {
        return setCliOptions(getOptions(plugins));
    }

    public static String[] setCliOptions(String[] options) {
        return setOptions(options, o -> run(options, currentThread().getContextClassLoader()));
    }

    public static <R> String[] setOptions(String[] options, Function<String[], R> cb) {
        out.println("options: " + Arrays.toString(options));
        cb.apply(options);
        return options;
    }

    public static String[] getOptions() throws ReflectiveOperationException {
        return getOptions(BASE_CONFIG);
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
            if (p.contains("CucumberEventListener")) {
                String[] args = p.split(":");
                try {
                    if (args.length == 1) new CucumberEventListener();
                    else new CucumberEventListener(args[1]);
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                    //throw new RuntimeException(e);
                }
            }
        });
        return options.toArray(String[]::new);
    }

    public static void setTagsProps() {
        String tags = getProperty("cucumber.tags", "@smoke");
        setProperty("cucumber.tags", tags);
        debug(tags);
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
            setProperty("cucumber.tags", tags);
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
