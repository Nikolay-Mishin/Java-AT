package org.project.tests;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.entries;
import static org.project.utils.Helper.entriesArray;
import static org.project.utils.Helper.entriesList;
import static org.project.utils.Helper.rotate;
import static org.project.utils.Helper.pop;
import static org.project.utils.Helper.popCopy;
import static org.project.utils.Helper.popSkip;
import static org.project.utils.Helper.shift;
import static org.project.utils.Helper.shiftList;
import static org.project.utils.Helper.shiftSkip;
import static org.project.utils.reflection.Reflection.getField;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestEntries<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestNumber<T, W, D> {

    /**
     *
     */
    @ConstructorProperties({})
    public TestEntries() {
        debug("TestEntries:init");
    }

    /**
     *
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    public static void testEntries() throws NoSuchFieldException, IllegalAccessException {
        debug("testEntries");
        Object obj = new Object(){ public int k = 1; protected static int p = 11; private int pp = 111; };

        debug(entries(obj));
        debug(entriesList(obj));
        debug(entriesArray(obj));
        debug(entriesList(obj).get(0));
        debug(entriesArray(obj)[0]);

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

        debug(getField(obj, "k"));
    }

}
