package utils.fs;

import com.sun.codemodel.JCodeModel;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import static config.WebConfig.BASE_CONFIG;
import static utils.fs.FS.getPath;

public class JsonSchemaToJavaClass {

    private static final String schemaRoot = BASE_CONFIG.getJsonSchemaRoot();
    private static final String outputDirectory = BASE_CONFIG.getPojoRoot();
    private static final String packageName = BASE_CONFIG.getTargetPackage();

    public static void main(String[] args) throws Exception {
        new JsonSchemaToJavaClass("Pojo");
    }

    public JsonSchemaToJavaClass(String inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        generate(getSchemaPath(inputJsonUrl), outputJavaClassDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(URL inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        generate(getSchemaPath(inputJsonUrl.toString()), outputJavaClassDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(URI inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        generate(getSchemaPath(inputJsonUrl.toString()), outputJavaClassDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(String javaClassName) throws IOException {
        generate(getSchemaPath(javaClassName), outputDirectory, packageName, javaClassName);
    }

    private String getSchemaPath(String inputJsonUrl) {
        return getPath(schemaRoot, inputJsonUrl);
    }

    public static void generate(String inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        _generate(inputJsonUrl, outputJavaClassDirectory, packageName, javaClassName);
    }

    public static void generate(URL inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        _generate(inputJsonUrl.toString(), outputJavaClassDirectory, packageName, javaClassName);
    }

    public static void generate(URI inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        _generate(inputJsonUrl.toString(), outputJavaClassDirectory, packageName, javaClassName);
    }

    private static void _generate(String inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        JCodeModel jcodeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(jcodeModel, javaClassName, packageName, inputJsonUrl);

        jcodeModel.build(new File(outputJavaClassDirectory));
    }

}
