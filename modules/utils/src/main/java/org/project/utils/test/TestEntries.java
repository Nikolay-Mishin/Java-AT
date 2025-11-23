package org.project.utils.test;

import java.util.Arrays;

import static org.project.utils.Helper.*;
import static org.project.utils.Helper.debug;

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

        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        debug(Arrays.toString(array));

        debug(Arrays.toString(rotate(array, 1)));
        //debug(Arrays.toString(array));
        debug(Arrays.toString(rotate(array, -1)));
        //debug(Arrays.toString(array));

        debug(Arrays.toString(shift(array)));
        debug(Arrays.toString(shiftSkip(array)));
        debug(Arrays.toString(shiftList(array)));

        debug(Arrays.toString(pop(array)));
        debug(Arrays.toString(popCopy(array)));
        debug(Arrays.toString(popSkip(array)));
    }

}
