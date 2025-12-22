package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.DriverConfig.config;
import static org.project.utils.fs.Version.getVersion;

import org.project.utils.config.TestBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestVersion<T extends TestBaseConfig> extends TestZip<T> {
    /**
     *
     */
    protected static String chromePath;

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
    public TestVersion() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("TestFS:init");
        chromePath = config().getChrome();
    }

    /**
     *
     */
    public static void testVersion() {
        debug("testVersion: " + chromePath);
        debug("Version: " + getVersion(chromePath));
    }

}
