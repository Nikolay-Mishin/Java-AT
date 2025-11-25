package org.project.utils.test;

import java.util.Arrays;

import static org.project.utils.Helper.*;

public class TestEntries {

    public static void main(String[] args) throws NoSuchFieldException {
        testEntries();
    }

    public static void testEntries() throws NoSuchFieldException {
        Object obj = new Object(){ public int k = 1; protected static int p = 11; private int pp = 111; };

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

        debug(obj.getClass().getField("k"));
    }

}
