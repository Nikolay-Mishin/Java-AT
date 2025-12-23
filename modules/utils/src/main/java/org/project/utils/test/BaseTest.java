package org.project.utils.test;

import java.beans.ConstructorProperties;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.TestConfig.getConfig;

import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.DriverConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;
import org.project.utils.config.WebConfig;
import org.project.utils.reflection.SingleInstance;

/**
 * @param <T> extends TestBaseConfig
 */
public class BaseTest<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends SingleInstance<BaseTest<T, W, D>>
{
    /**
     *
     */
    protected static BaseTest<? extends TestBaseConfig, ? extends WebBaseConfig, ? extends DriverBaseConfig> i;
    /**
     *
     */
    protected T c = getConfig();
    /**
     *
     */
    protected W w = WebConfig.getConfig();
    /**
     *
     */
    protected D win = DriverConfig.getConfig();

    /**
     *
     * @throws NoSuchFieldException throws
     * @throws IllegalAccessException throws
     */
    @ConstructorProperties({})
    public BaseTest() {
        debug("BaseTest:init");
        setInstance(this);
    }

    /**
     *
     * @return BaseTests {T, W, D}
     * @param <T> extends TestBaseConfig
     * @param <W> extends WebBaseConfig
     * @param <D> extends DriverBaseConfig
     * @throws NoSuchFieldException throws
     * @throws ClassNotFoundException throws
     * @throws IllegalAccessException throws
     */
    public static <T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> BaseTest<T, W, D> instance() {
        return SingleInstance.getInstance();
    }

    /**
     *
     * @return T
     * @param <T> extends TestBaseConfig
     */
    @SuppressWarnings("unchecked")
    public static <T extends TestBaseConfig> T c() {
        return (T) i.c;
    }

    /**
     *
     * @return T
     * @param <T> extends WebBaseConfig
     */
    @SuppressWarnings("unchecked")
    public static <T extends WebBaseConfig> T w() {
        return (T) i.w;
    }

    /**
     *
     * @return T
     * @param <T> extends DriverBaseConfig
     */
    @SuppressWarnings("unchecked")
    public static <T extends DriverBaseConfig> T win() {
        return (T) i.win;
    }

}
