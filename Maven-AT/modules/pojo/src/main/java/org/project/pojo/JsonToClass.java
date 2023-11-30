package org.project.pojo;

import org.jsonschema2pojo.SourceType;
import org.project.utils.config.Config;
import org.project.utils.config.WebBaseConfig;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class JsonToClass extends JsonSchemaToClass {

    protected static String schemaRoot = Config.config().getJsonRoot();
    protected static String targetPackage = Config.config().getTargetJsonPackage();
    protected final SourceType sourceType = SourceType.JSON;

    public JsonToClass(WebBaseConfig baseConfig) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        super(baseConfig);
    }
}
