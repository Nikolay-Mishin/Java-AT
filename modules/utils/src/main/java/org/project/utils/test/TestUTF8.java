package org.project.utils.test;

import static java.util.ResourceBundle.Control;
import static java.util.ResourceBundle.getBundle;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import static org.project.utils.Helper.debug;

import org.project.utils.base.UTF8Control;
import org.project.utils.config.TestBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestUTF8<T extends TestBaseConfig> extends TestEntries<T> {

    /**
     *
     */
    @ConstructorProperties({})
    public TestUTF8() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("TestUTF8:init");
    }

    /**
     *
     */
    public static void testUTF8() {
        testUTF8("org.project.utils.test", "test.utf8");
    }

    /**
     *
     * @param baseName String
     * @param key String
     */
    public static void testUTF8(String baseName, String key) {
        // Load the properties file with UTF-8 encoding
        Control control = new UTF8Control();
        ResourceBundle bundle = getBundle(baseName, control);

        // Get and print a value from the resource bundle
        String greeting = bundle.getString(key);
        debug(greeting);
    }

}
