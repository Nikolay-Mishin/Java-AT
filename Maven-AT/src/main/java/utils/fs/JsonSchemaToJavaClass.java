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
import static utils.Helper.notEquals;
import static utils.fs.FS.getPath;

public class JsonSchemaToJavaClass {

    //private static final String schemaRoot = BASE_CONFIG.getJsonSchemaRoot();
    //private static final String outputDirectory = BASE_CONFIG.getPojoRoot();
    //private static final String packageName = BASE_CONFIG.getTargetPackage();
    private static final String schemaRoot = "src/main/resources/schema";
    private static final String outputDirectory = "src/main/java";
    private static final String packageName = "models.pojo.com.generated";
    private SourceType sourceType;
    private String inputJsonUrl;
    private String outputJavaClassDirectory;
    private String _packageName = packageName;
    private String javaClassName;

    public static void main(String[] args) throws Exception {
        out.println(BASE_CONFIG.getJsonSchemaRoot());
        out.println(BASE_CONFIG.getPojoRoot());
        out.println(BASE_CONFIG.getTargetPackage());
        new JsonSchemaToJavaClass(SourceType.JSONSCHEMA, "store/order", "Order");
        new JsonSchemaToJavaClass(SourceType.JSON, "pet", "Pet");
    }

    public JsonSchemaToJavaClass(SourceType sourceType, String inputJsonUrl, String javaClassName) throws IOException {
        init(sourceType, inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputDirectory, _packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(SourceType sourceType, URL inputJsonUrl, String javaClassName) throws IOException {
        init(sourceType, inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(SourceType sourceType, URI inputJsonUrl, String javaClassName) throws IOException {
        init(sourceType, inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(SourceType sourceType, String inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        init(sourceType, inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputJavaClassDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(SourceType sourceType, URL inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        init(sourceType, inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputJavaClassDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(SourceType sourceType, URI inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        init(sourceType, inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputJavaClassDirectory, packageName, javaClassName);
    }

    private void init(SourceType sourceType, String inputJsonUrl, String packageName, String javaClassName) {
        _init(sourceType, inputJsonUrl, packageName, javaClassName);
    }

    private void init(SourceType sourceType, URL inputJsonUrl, String packageName, String javaClassName) {
        _init(sourceType, inputJsonUrl.toString(), packageName, javaClassName);
    }

    private void init(SourceType sourceType, URI inputJsonUrl, String packageName, String javaClassName) {
        _init(sourceType, inputJsonUrl.toString(), packageName, javaClassName);
    }

    private void _init(SourceType sourceType, String inputJsonUrl, String packageName, String javaClassName) {
        this.sourceType = sourceType;
        this.inputJsonUrl = inputJsonUrl;
        if (notEquals(packageName, "")) this._packageName += "." + inputJsonUrl.replace("/", ".");
        this.javaClassName = javaClassName;
        out.println(getSchemaPath());
    }

    private String getSchemaPath() {
        return getPath(schemaRoot, inputJsonUrl, javaClassName.toLowerCase() + (sourceType == SourceType.JSONSCHEMA ? ".schema" : "") + ".json");
    }

    public void generate(String inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        _generate(inputJsonUrl, outputJavaClassDirectory, packageName, javaClassName);
    }

    public void generate(URL inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        _generate(inputJsonUrl.toString(), outputJavaClassDirectory, packageName, javaClassName);
    }

    public void generate(URI inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        _generate(inputJsonUrl.toString(), outputJavaClassDirectory, packageName, javaClassName);
    }

    private void _generate(String inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        JCodeModel jcodeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return sourceType;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        URL _inputJsonUrl = new File(inputJsonUrl).toURI().toURL();
        out.println(_inputJsonUrl);
        out.println(outputJavaClassDirectory);
        out.println(packageName);
        out.println(javaClassName);
        mapper.generate(jcodeModel, javaClassName, packageName, _inputJsonUrl);
        //jcodeModel.build(new File(outputJavaClassDirectory));
    }

}
