package org.project.utils.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.*;

import org.junit.Test;

import static org.project.utils.Helper.debug;
import static org.project.utils.exception.UtilException.*;
import static org.project.utils.reflection.Reflection.*;

import org.project.utils.Helper;

public class TestException extends TestInvoke {

    public static void main(String[] args) throws IOException, URISyntaxException, ReflectiveOperationException {
        testPrintException();
        testException();
    }

    public static void testPrintException() throws ReflectiveOperationException {
        tryConsumerWithPrint((t) -> debug(getField("org.project.utils.test.TestInvoke::invokeName")));
        tryConsumerWithPrint(t -> debug(invoke("org.project.utils.test.TestInvoke::invokeName1")));
        tryConsumerWithPrint(t -> debug(invoke("org.project.utils.test.App1::invokeName")));
    }

    public static void testException() throws ReflectiveOperationException {
        tryConsumer(t -> debug("getClass: " + getClazz("org.project.utils.config.WebBaseConfig")));
        tryConsumer(() -> debug("getField: " + getField("org.project.utils.config.WebBaseConfig::BASE_CONFIG")));
        //trySupplier(() -> debug("getField: " + getField("org.project.utils.config.WebBaseConfig::BASE_CONFIG")));
        //trySupplier(t -> debug("getField: " + getField("org.project.utils.config.WebBaseConfig::BASE_CONFIG")));
    }

    @Test
    public void testConsumerWithCheckedExceptions() throws ClassNotFoundException {
        Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
            .forEach(rethrowConsumer(className -> debug(Class.forName(className))));

        Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
            .forEach(rethrowConsumer(Helper::debug));
    }

    @Test
    public void testFunctionWithCheckedExceptions() throws ClassNotFoundException {
        List<Class> classes1
            = Stream.of("Object", "Integer", "String")
            .map(rethrowFunction(className -> Class.forName("java.lang." + className)))
            .collect(Collectors.toList());

        List<Class> classes2
            = Stream.of("java.lang.Object", "java.lang.Integer", "java.lang.String")
            .map(rethrowFunction(Class::forName))
            .collect(Collectors.toList());
    }

    @Test
    public void testSupplierWithCheckedExceptions() throws UnsupportedEncodingException {
        Collector.of(
            rethrowSupplier(() -> new StringJoiner(new String(new byte[]{77, 97, 114, 107}, "UTF-8"))),
            StringJoiner::add, StringJoiner::merge, StringJoiner::toString);
    }

    @Test
    public void testUncheckExceptionThrownByMethod() {
        Class clazz1 = uncheck(() -> Class.forName("java.lang.String"));
        Class clazz2 = uncheck(Class::forName, "java.lang.String");
    }

    @Test (expected = ClassNotFoundException.class)
    public void testIfCorrectExceptionIsStillThrownByMethod() {
        Class clazz3 = uncheck(Class::forName, "INVALID");
    }

}
