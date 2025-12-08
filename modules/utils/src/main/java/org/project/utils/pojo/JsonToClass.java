package org.project.utils.pojo;

import java.io.IOException;

import org.jsonschema2pojo.SourceType;

import org.project.utils.config.AnnotatorBaseConfig;
import org.project.utils.config.WebConfig;
import org.project.utils.config.WebBaseConfig;

public class JsonToClass extends JsonSchemaToClass {

    public JsonToClass(WebBaseConfig webConfig) throws IOException {
        schemaRoot(WebConfig.config().getJsonRoot());
        targetPackage(WebConfig.config().getTargetJsonPackage());
        config(new AnnotatorBaseConfig() {
            @Override public SourceType getSourceType() {
                return SourceType.JSON;
            }
        });
        generate(webConfig);
    }

}
