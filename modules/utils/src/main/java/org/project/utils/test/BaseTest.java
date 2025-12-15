package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.debug;
import static org.project.utils.config.TestConfig.getConfig;

import org.project.utils.config.TestBaseConfig;
import org.project.utils.reflection.SingleInstance;

/**
 * @param <T> extends TestBaseConfig
 */
public class BaseTest<T extends TestBaseConfig> extends SingleInstance<BaseTest<T>> {
    /**
     *
     */
    protected static BaseTest<? extends TestBaseConfig> i;
    /**
     *
     */
    protected T c;

    /**
     *
     */
    @ConstructorProperties({})
    public BaseTest() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        debug("BaseTest:init");
        c = getConfig();
        setInstance(this);
    }

    /**
     *
     * @throws NoSuchFieldException throws
     * @throws ClassNotFoundException throws
     * @throws IllegalAccessException throws
     */
    public static BaseTest<? extends TestBaseConfig> init()
        throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException
    {
        debug("BaseTest:instance");
        return SingleInstance.instance();
    }

    /**
     *
     * @return BaseTest {? extends TestBaseConfig}
     * @throws NoSuchFieldException throws
     * @throws ClassNotFoundException throws
     * @throws IllegalAccessException throws
     */
    public static BaseTest<? extends TestBaseConfig> instance() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        return SingleInstance.getInstance();
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public T c() {
        return (T) i.c;
    }

}
