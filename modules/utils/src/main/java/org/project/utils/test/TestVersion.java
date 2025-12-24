package org.project.utils.test;

import java.beans.ConstructorProperties;

import static org.project.utils.Helper.debug;
import static org.project.utils.fs.Library.checkLib;
import static org.project.utils.fs.Library.updateLib;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestVersion<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestZip<T, W, D> {

    /**
     *
     */
    @ConstructorProperties({})
    public TestVersion() {
        debug("TestVersion:init");
    }

    /**
     *
     */
    public static void testVersion() throws Exception {
        debug("testVersion");
        checkLib();
        //updateLib();
    }

}
