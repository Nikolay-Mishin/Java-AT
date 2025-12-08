package org.project.utils.test;

import static java.util.ResourceBundle.Control;
import static java.util.ResourceBundle.getBundle;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.project.utils.Helper.debug;

import org.project.utils.base.UTF8Control;

public class TestUTF8 {

    public static void main(String[] args) throws IOException {
        // Load the properties file with UTF-8 encoding
        Control control = new UTF8Control();
        ResourceBundle bundle = getBundle("org.project.utils.test", control);

        // Get and print a value from the resource bundle
        String greeting = bundle.getString("test.utf8");
        debug(greeting);
    }

}
