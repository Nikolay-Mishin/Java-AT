package org.project.pojo;

import org.jsonschema2pojo.SourceType;
import org.project.annotator.config.DefaultAnnotatorConfig;
import org.project.utils.config.Config;
import org.project.utils.config.WebBaseConfig;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class JsonToClass extends JsonSchemaToClass {

    public JsonToClass(WebBaseConfig baseConfig) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        setSchemaRoot(Config.config().getJsonRoot());
        setTargetPackage(Config.config().getTargetJsonPackage());
        setConfig(new DefaultAnnotatorConfig() {
            @Override public SourceType getSourceType() {
                return SourceType.JSON;
            }
        });
        generate(baseConfig);
    }
}
