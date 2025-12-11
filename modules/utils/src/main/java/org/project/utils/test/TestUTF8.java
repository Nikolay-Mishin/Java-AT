package org.project.utils.test;

import static java.util.ResourceBundle.Control;
import static java.util.ResourceBundle.getBundle;

import java.util.ResourceBundle;

import static org.project.utils.Helper.debug;

import org.project.utils.base.UTF8Control;

public class TestUTF8 {

    public static void main(String[] args) {
        testUTF8();
    }

    public static void testUTF8() {
        testUTF8("org.project.utils.test", "test.utf8");
    }

    public static void testUTF8(String baseName, String key) {
        // Load the properties file with UTF-8 encoding
        Control control = new UTF8Control();
        ResourceBundle bundle = getBundle(baseName, control);

        // Get and print a value from the resource bundle
        String greeting = bundle.getString(key);
        debug(greeting);
    }

}
