package org.project.utils.pojo;

import java.beans.ConstructorProperties;
import java.io.IOException;

import org.project.utils.config.WebBaseConfig;

/**
 *
 */
public class PojoGenerator {
    /**
     *
     * @param webConfig WebBaseConfig
     * @throws IOException throws
     */
    @ConstructorProperties({"webConfig"})
    public PojoGenerator(WebBaseConfig webConfig) throws IOException {
        new JsonSchemaToClass(webConfig);
        new JsonToClass(webConfig);
    }
}
