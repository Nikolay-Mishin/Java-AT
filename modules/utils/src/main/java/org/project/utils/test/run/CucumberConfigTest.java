package org.project.utils.test.run;

import static java.lang.System.getProperty;
import static java.lang.System.out;
import static java.lang.System.setProperty;
import static java.lang.Thread.currentThread;

import java.beans.ConstructorProperties;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

import static io.cucumber.core.cli.Main.run;

import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.forEach;
import static org.project.utils.Helper.isNull;
import static org.project.utils.config.TestBaseConfig.BASE_CONFIG;
import static org.project.utils.event.CucumberEventListener.getPlugins;
import static org.project.utils.event.CucumberEventListener.initArg;
import static org.project.utils.reflection.Reflection.getField;
import static org.project.utils.reflection.Reflection.getGenericClass;

import org.project.utils.config.TestBaseConfig;
import org.project.utils.event.CucumberEventListener;
import org.project.utils.function.FunctionWithExceptions;

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
public class CucumberConfigTest<T extends TestBaseConfig> {

    /**
     * CucumberRunTest:init
     */
    @ConstructorProperties({})
    public CucumberConfigTest() {
        out.println("CucumberRunTest:init");
        //initField("BASE_CONFIG");
        //init("BASE_CONFIG");
        init(getGenericClass(getClass()), "BASE_CONFIG");
    }

    /**
     * setUp
     */
    @BeforeClass
    public static void setUp() {
        out.println("setUp");
    }

    /**
     *
     * @param field String
     * @return String[]
     */
    public static String[] init(String field) {
        return init(null, field);
    }

    /**
     *
     * @param clazz Class T
     * @param field String
     * @return String[]
     * @param <T> extends TestBaseConfig
     */
    @SuppressWarnings("unchecked")
    public static <T extends TestBaseConfig> String[] init(Class<T> clazz, String field) {
        return init(clazz, c -> (T) getField(clazz, field));
    }

    /**
     *
     * @param arg String
     * @return String[]
     */
    public static String[] initField(String arg) {
        return initField(null, arg);
    }

    /**
     *
     * @param clazz Class T
     * @param arg String
     * @return String[]
     * @param <T> extends TestBaseConfig
     */
    @SuppressWarnings("unchecked")
    public static <T extends TestBaseConfig> String[] initField(Class<T> clazz, String arg) {
        return init(clazz, c -> (T) initArg(clazz.getName() + "::" + arg));
    }

    /**
     *
     * @param clazz Class T
     * @param cb FunctionWithExceptions {T, E}
     * @return String[]
     * @param <T> extends TestBaseConfig
     * @param <E> extends ReflectiveOperationException
     */
    public static <T extends TestBaseConfig, E extends ReflectiveOperationException> String[] init(Class<T> clazz, FunctionWithExceptions<Class<T>, T, E> cb) {
        try {
            if (isNull(clazz)) clazz = getGenericClass();
            out.println("CucumberRunTest: " + clazz);
            return setOptions(cb.apply(clazz));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return setOptions();
        }
    }

    /**
     *
     * @return String[]
     */
    public static String[] setOptions() {
        out.println("setOptions: base");
        try {
            return setOptions(BASE_CONFIG);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param config TestBaseConfig
     * @return String[]
     * @throws ReflectiveOperationException throws
     */
    public static String[] setOptions(TestBaseConfig config) throws ReflectiveOperationException {
        out.println("setOptions: config");
        out.println(config);
        return setOptions(getOptions(config));
    }

    /**
     *
     * @param plugins String
     * @return String[]
     * @throws ReflectiveOperationException throws
     */
    public static String[] setOptions(String plugins) throws ReflectiveOperationException {
        return setOptions(getOptions(plugins));
    }

    /**
     *
     * @param options String[]
     * @return String[]
     */
    public static String[] setOptions(String[] options) {
        return setOptions(options, o -> o);
    }

    /**
     *
     * @param config TestBaseConfig
     * @return String[]
     * @throws ReflectiveOperationException throws
     */
    public static String[] setCliOptions(TestBaseConfig config) throws ReflectiveOperationException {
        return setCliOptions(getOptions(config));
    }

    /**
     *
     * @param plugins String
     * @return String[]
     * @throws ReflectiveOperationException throws
     */
    public static String[] setCliOptions(String plugins) throws ReflectiveOperationException {
        return setCliOptions(getOptions(plugins));
    }

    /**
     *
     * @param options String[]
     * @return String[]
     */
    public static String[] setCliOptions(String[] options) {
        out.println("setCliOptions: " + Arrays.toString(options));
        return setOptions(options, o -> run(options, currentThread().getContextClassLoader()));
    }

    /**
     *
     * @param options String[]
     * @param cb Function
     * @return String[]
     * @param <R> R
     */
    public static <R> String[] setOptions(String[] options, Function<String[], R> cb) {
        out.println("options: " + Arrays.toString(options));
        cb.apply(options);
        return options;
    }

    /**
     *
     * @param config TestBaseConfig
     * @return String[]
     * @throws ReflectiveOperationException throws
     */
    public static String[] getOptions(TestBaseConfig config) throws ReflectiveOperationException {
        return getOptions(getPlugins(config));
    }

    /**
     *
     * @param plugins String
     * @return String[]
     * @throws ReflectiveOperationException throws
     */
    public static String[] getOptions(String plugins) throws ReflectiveOperationException {
        return getOptions(getPlugins(plugins));
    }

    /**
     *
     * @param plugins String[]
     * @return String[]
     */
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

    /**
     *
     */
    public static void setTagsProps() {
        String tags = getProperty("cucumber.tags", "@smoke");
        setProperty("cucumber.tags", tags);
        debug(tags);
    }

    /**
     *
     */
    public static void setTags() {
        setTagsDir("src/test/resources/");
    }

    /**
     *
     * @param cucumberPropsDir String
     */
    public static void setTagsDir(String cucumberPropsDir) {
        setTags(cucumberPropsDir + "cucumber.properties");
    }

    /**
     *
     * @param cucumberPropsPath String
     */
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

    /**
     *
     */
    public void setTagsOptions() {
        List<String> tagsList = Arrays.asList("@smoke", "@regression");
        String[] tagsArray = tagsList.toArray(new String[0]);
        CucumberOptions options = this.getClass().getAnnotation(CucumberOptions.class);
        //options.tags(tagsArray);
    }

}
