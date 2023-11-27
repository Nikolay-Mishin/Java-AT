package org.project.pojo;

import org.jsonschema2pojo.SourceType;

import java.io.IOException;

public class JsonToClass extends JsonSchemaToClass {

    protected static String schemaRoot = "src/main/resources/json";
    protected static String outputDirectory = JsonSchemaToClass.outputDirectory;
    protected static String packageName = "pojo.json";
    protected final SourceType sourceType = SourceType.JSON;

    public static void main(String[] args) throws Exception {
        test();
    }

    public static void setRoot(String schemaRoot, String outputDirectory, String packageName) {
        JsonToClass.schemaRoot = schemaRoot;
        JsonToClass.outputDirectory = outputDirectory;
        JsonToClass.packageName = packageName;
    }

    public JsonToClass() throws IOException {}

}
