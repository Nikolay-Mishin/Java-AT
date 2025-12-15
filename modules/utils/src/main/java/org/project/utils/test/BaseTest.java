package org.project.utils.test;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.TestConfig.getConfig;

import org.project.utils.config.TestBaseConfig;

/**
 *
 */
public class BaseTest {
    /**
     *
     */
    protected static TestBaseConfig c;

    /**
     *
     */
    public static void init() {
        debug("BaseTest:init");
        c = getConfig();
    }

}
