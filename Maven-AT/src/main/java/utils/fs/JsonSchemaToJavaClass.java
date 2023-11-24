package utils.fs;

import com.sun.codemodel.JCodeModel;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import static config.WebConfig.BASE_CONFIG;
import static java.lang.System.out;
import static utils.fs.FS.getPath;

public class JsonSchemaToJavaClass {

    //private static final String schemaRoot = BASE_CONFIG.getJsonSchemaRoot();
    //private static final String outputDirectory = BASE_CONFIG.getPojoRoot();
    //private static final String packageName = BASE_CONFIG.getTargetPackage();
    private static final String schemaRoot = "src/main/resources/schema";
    private static final String outputDirectory = "src/main/java/models/pojo";
    private static final String packageName = "com.generated";

    public static void main(String[] args) throws Exception {
        out.println(BASE_CONFIG.getJsonSchemaRoot());
        out.println(BASE_CONFIG.getPojoRoot());
        out.println(BASE_CONFIG.getTargetPackage());
        out.println(schemaRoot);
        out.println(outputDirectory);
        out.println(packageName);
        out.println(getSchemaPath("store/order", "Order"));
        new JsonSchemaToJavaClass("store/order", "Order");
    }

    public JsonSchemaToJavaClass(String inputJsonUrl, String javaClassName) throws IOException {
        generate(getSchemaPath(inputJsonUrl, javaClassName), outputDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(URL inputJsonUrl, String javaClassName) throws IOException {
        generate(getSchemaPath(inputJsonUrl.toString(), javaClassName), outputDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(URI inputJsonUrl, String javaClassName) throws IOException {
        generate(getSchemaPath(inputJsonUrl.toString(), javaClassName), outputDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(String inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        generate(getSchemaPath(inputJsonUrl, javaClassName), outputJavaClassDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(URL inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        generate(getSchemaPath(inputJsonUrl.toString(), javaClassName), outputJavaClassDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(URI inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        generate(getSchemaPath(inputJsonUrl.toString(), javaClassName), outputJavaClassDirectory, packageName, javaClassName);
    }

    private static String getSchemaPath(String inputJsonUrl, String javaClassName) {
        return getPath(schemaRoot, inputJsonUrl, javaClassName.toLowerCase() + ".json");
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
        out.println(javaClassName);
        out.println(packageName);
        out.println(inputJsonUrl);
        URL _inputJsonUrl = new File(inputJsonUrl).toURI().toURL();
        out.println(_inputJsonUrl);
        mapper.generate(jcodeModel, javaClassName, packageName, _inputJsonUrl);

        jcodeModel.build(new File(outputJavaClassDirectory));
    }

}
