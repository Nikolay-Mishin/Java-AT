package org.project.tests;

import java.beans.ConstructorProperties;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.project.utils.Helper.debug;
import static org.project.utils.exception.UtilException.rethrowConsumer;
import static org.project.utils.exception.UtilException.rethrowFunction;
import static org.project.utils.exception.UtilException.rethrowSupplier;
import static org.project.utils.exception.UtilException.uncheck;
import static org.project.utils.exception.UtilException.tryConsumer;
import static org.project.utils.exception.UtilException.tryConsumerWithPrint;
import static org.project.utils.reflection.Reflection.getClazz;
import static org.project.utils.reflection.Reflection.getField;
import static org.project.utils.reflection.Reflection.invoke;

import org.project.utils.Helper;
import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestException<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestInvoke<T, W, D> {
    /**
     *
     */
    protected static String cPlugin;
    /**
     *
     */
    protected static String cPluginField;
    /**
     *
     */
    protected static String cPlugins;

    /**
     *
     */
    @ConstructorProperties({})
    public TestException() {
        debug("TestException:init");
        cPlugin = c.getCPlugin();
        cPluginField = c.getCPluginField();
        cPlugins = c.getCPlugins();
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     */
    public static void testPrintException() throws ReflectiveOperationException {
        debug("testPrintException");
        tryConsumerWithPrint((t) -> debug(getField(fsMethod)));
        tryConsumerWithPrint(t -> debug(invoke(fsMethod + "1")));
        tryConsumerWithPrint(() -> debug(invoke(fsClass + "1::" + fsMethodName)));
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     */
    public static void testException() throws ReflectiveOperationException {
        debug("testException");
        tryConsumer(t -> debug("getClass: " + getClazz(cPlugin)));
        tryConsumer(() -> debug("getField: " + getField(cPluginField)));
        //trySupplier(() -> debug("getField: " + getField(cPluginField)));
        //trySupplier(t -> debug("getField: " + getField(cPluginField)));
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     * @throws UnsupportedEncodingException throws
     */
    public static void testFnExceptions() throws ReflectiveOperationException, UnsupportedEncodingException {
        testConsumerWithCheckedExceptions();
        testFunctionWithCheckedExceptions();
        testSupplierWithCheckedExceptions();
        testUncheckExceptionThrownByMethod();
        testIfCorrectExceptionIsStillThrownByMethod();
    }

    /**
     *
     * @throws ClassNotFoundException throws
     */
    public static void testConsumerWithCheckedExceptions() throws ClassNotFoundException {
        debug("testConsumerWithCheckedExceptions");
        Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
            .forEach(rethrowConsumer(className -> debug(Class.forName(className))));

        Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
            .forEach(rethrowConsumer(Helper::debug));
    }

    /**
     *
     * @throws ClassNotFoundException throws
     */
    public static void testFunctionWithCheckedExceptions() throws ClassNotFoundException {
        debug("testFunctionWithCheckedExceptions");
        List<Class<?>> classes1
            = Stream.of("Object", "Integer", "String")
            .map(rethrowFunction(className -> Class.forName("java.lang." + className)))
            .collect(Collectors.toList());

        List<Class<?>> classes2
            = Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
            .map(rethrowFunction(Class::forName))
            .collect(Collectors.toList());
    }

    /**
     *
     * @throws UnsupportedEncodingException throws
     */
    public static void testSupplierWithCheckedExceptions() throws UnsupportedEncodingException {
        debug("testSupplierWithCheckedExceptions");
        Collector.of(
            rethrowSupplier(() -> new StringJoiner(new String(new byte[]{77, 97, 114, 107}, "UTF-8"))),
            StringJoiner::add, StringJoiner::merge, StringJoiner::toString);
    }

    /**
     *
     */
    public static void testUncheckExceptionThrownByMethod() {
        debug("testUncheckExceptionThrownByMethod");
        Class<?> clazz1 = uncheck(() -> Class.forName("java.lang.String"));
        Class<?> clazz2 = uncheck(Class::forName, "java.lang.String");
    }

    /**
     *
     */
    public static void testIfCorrectExceptionIsStillThrownByMethod() {
        debug("testIfCorrectExceptionIsStillThrownByMethod");
        Class<?> clazz3 = uncheck(Class::forName, "INVALID");
    }

}
