package org.project.utils.test;

import java.util.Arrays;

import static org.project.utils.Helper.*;

public class TestEntries extends App {

    public static void main(String[] args) {
        testEntries();
    }

    public static void testEntries() {
        Object obj = new Object(){ public int k = 1; };

        debug(entries(obj));
        debug(entriesList(obj));
        debug(entriesArray(obj));
        debug(Arrays.toString(entriesList(obj).get(0)));
        debug(Arrays.toString((Object[]) entriesArray(obj)[0]));
    }

}
