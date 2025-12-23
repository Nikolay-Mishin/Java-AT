package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.debug;
import static org.project.utils.fs.Library.updateLibrary;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestVersion<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestZip<T, W, D> {

    /**
     *
     * @throws NoSuchFieldException throws
     * @throws ClassNotFoundException throws
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws InstantiationException throws
     * @throws IllegalAccessException throws
     */
    @ConstructorProperties({})
    public TestVersion() throws NoSuchFieldException, IllegalAccessException {
        debug("TestVersion:init");
    }

    /**
     *
     */
    public static void testVersion() throws Exception {
        debug("testVersion");
        updateLibrary();
    }

}
