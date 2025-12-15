package org.project.utils.test;

import static java.util.ResourceBundle.Control;
import static java.util.ResourceBundle.getBundle;

import java.util.ResourceBundle;

import static org.project.utils.Helper.debug;

import org.project.utils.base.UTF8Control;

/**
 *
 */
public class TestUTF8 extends TestEntries {

    /**
     *
     */
    public static void init() {
        debug("TestUTF8:init");
        TestEntries.init();
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
