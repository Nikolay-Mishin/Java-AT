package org.project.pojo;

import org.jsonschema2pojo.SourceType;

import java.io.IOException;

import static org.project.utils.config.Config.config;

public class JsonToClass extends JsonSchemaToClass {

    protected static String schemaRoot = config().getJsonRoot();
    protected static String targetPackage = config().getTargetJsonPackage();
    protected final SourceType sourceType = SourceType.JSON;

    public JsonToClass() throws IOException {}

}
