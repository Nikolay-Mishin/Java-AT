package org.project.testng;

import java.util.Iterator;
import java.util.List;

import org.testng.collections.Lists;

/**
 *
 */
public class Helper {

    /**
     *
     * @param iterator Iterator {T}
     * @return List {T}
     * @param <T> T
     */
    public static <T> List<T> newList(Iterator<T> iterator) {
        return Lists.newArrayList(iterator);
    }

}
