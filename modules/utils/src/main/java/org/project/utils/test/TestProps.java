package org.project.utils.test;

import static java.lang.System.getProperty;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import static org.project.utils.Helper.debug;
import static org.project.utils.base.Properties.getSortedProps;
import static org.project.utils.base.Properties.props;
import static org.project.utils.base.Properties.propsMap;
import static org.project.utils.base.Properties.propsMapKeys;
import static org.project.utils.request.Request.getParamsUri;

import org.project.utils.base.Properties;
import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestProps<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestException<T, W, D> {

    /**
     *
     * @throws NoSuchFieldException throws
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    @ConstructorProperties({})
    public TestProps() throws NoSuchFieldException, IllegalAccessException {
        debug("TestProps:init");
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     */
    public static void printProps() throws ReflectiveOperationException {
        Properties props = props();
        debug("empty: " + props.isEmpty());

        Set<Object> devKeys = props.keySet();
        Collection<Object> devValues = props.values();
        String devEnv = props.getProperty("ENV");
        debug("devKeys: " + devKeys);
        debug("devValues: " + devValues);

        debug("propsMap: " + propsMap());
        debug("sortedProps: " + getSortedProps());
        debug("props: " + props);

        debug("entrySet: " + props.entrySet());
        debug("keySet: " + props.keySet());

        debug("map: " + props.map());

        debug("propsMapKeys: " + propsMapKeys());

        printPropsMapKeys(p -> p.replace("props", "props.utils"));
        printPropsMapKeys();

        debug("props.dev: " + propsMap("props.dev"));

        debug("dev.env:", devEnv);

        printConfig();

        debug(uri);
        debug(getParamsUri(uri, "id", 1, "token", "test"));
    }

    /**
     *
     */
    public static void printPropsMapKeys() {
        printPropsMapKeys(p -> p);
    }

    /**
     * @param cb Function {String, String}
     */
    public static void printPropsMapKeys(Function<String, String> cb) {
        propsMapKeys().forEach(p -> debug((p = cb.apply(p)) + ":", getProperty(p)));
    }

    /**
     *
     */
    public static void printPropsMap() {
        propsMap().get("props.web").forEach((k, v) -> debug(k + "-> " + v));
    }

    /**
     *
     */
    public static void printSortedProps() {
        props().forEach((k, v) -> debug(k + "-> " + v));
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     */
    public static void printGetSortedProps() throws ReflectiveOperationException {
        getSortedProps().forEach((k, v) -> debug(k + "-> " + v));
    }

}
