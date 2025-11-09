package org.project.pojo;

import java.io.IOException;

import org.project.utils.config.WebBaseConfig;

public class PojoGenerator {

    public PojoGenerator(WebBaseConfig webConfig) throws IOException {
        new JsonSchemaToClass(webConfig);
        new JsonToClass(webConfig);
    }

}
