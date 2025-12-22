package org.project.utils.test;

import java.beans.ConstructorProperties;

import static java.lang.Long.valueOf;

import static org.project.utils.Helper.debug;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestNumber<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends BaseTest<T, W, D> {

    /**
     *
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    @ConstructorProperties({})
    public TestNumber() throws NoSuchFieldException, IllegalAccessException {
        debug("TestNumber:init");
    }

    /**
     *
     */
    public static void testLong() {
        debug("testLong");
        int _int = 0;
        long _long = Integer.valueOf(_int).longValue();

        _long((long) _int);
        _long((long) Integer.valueOf(_int));
        _long(Integer.valueOf(_int).longValue());

        _long(_long);
        _long(valueOf(0));
        _long(valueOf(_int));
        _long(valueOf(_long));
    }

    /**
     *
     * @param id Long
     */
    public static void _long(Long id) {
        debug(id);
    }

}
