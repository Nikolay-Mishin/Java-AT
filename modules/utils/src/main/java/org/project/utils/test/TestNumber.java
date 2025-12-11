package org.project.utils.test;

import static java.lang.Long.valueOf;

import static org.project.utils.Helper.debug;

public class TestNumber extends TestDriver {

    public static void main(String[] args) throws Exception {
        testLong();
    }

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

    public static void _long(Long id) {
        debug(id);
    }

}
