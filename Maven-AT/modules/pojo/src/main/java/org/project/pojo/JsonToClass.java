package org.project.pojo;

import org.jsonschema2pojo.SourceType;
import org.project.annotator.config.DefaultAnnotatorConfig;
import org.project.utils.config.Config;
import org.project.utils.config.WebBaseConfig;

import java.io.IOException;

public class JsonToClass extends JsonSchemaToClass {

    public JsonToClass(WebBaseConfig baseConfig) throws IOException {
        schemaRoot(Config.config().getJsonRoot());
        targetPackage(Config.config().getTargetJsonPackage());
        config(new DefaultAnnotatorConfig() {
            @Override public SourceType getSourceType() {
                return SourceType.JSON;
            }
        });
        generate(baseConfig);
    }
}
