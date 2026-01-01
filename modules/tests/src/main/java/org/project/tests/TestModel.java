package org.project.tests;

import java.beans.ConstructorProperties;

import static org.project.utils.Helper.debug;
import static org.project.utils.base.Properties.propsMap;

import org.project.utils.base.Model;
import org.project.utils.config.DriverBaseConfig;
import org.project.utils.config.TestBaseConfig;
import org.project.utils.config.WebBaseConfig;

/**
 * @param <T> extends TestBaseConfig
 */
public class TestModel<T extends TestBaseConfig, W extends WebBaseConfig, D extends DriverBaseConfig> extends TestWeb<T, W, D> {

    /**
     *
     * @throws Exception throws
     */
    @ConstructorProperties({})
    public TestModel() throws Exception {
        debug("TestModel:init");
    }

    /**
     *
     * @throws ReflectiveOperationException throws
     */
    public static void testModel() throws ReflectiveOperationException {
        new Model<>(org.project.tests.model.Model.class, propsMap("props.dev"));
        new Model<>(org.project.tests.model.Model.class, "name", "username", "password", "pass");
    }

}
