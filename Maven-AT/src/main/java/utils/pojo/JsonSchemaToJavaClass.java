package utils.pojo;

import com.sun.codemodel.JCodeModel;
import org.jetbrains.annotations.NotNull;
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
    private static final String packageName = "models.pojo.schema";
    private String inputJsonUrl;
    private String outputJavaClassDirectory;
    private String _packageName = packageName;
    private String javaClassName;
    private JCodeModel jcodeModel;
    private final boolean isGenerateBuilders = false;
    private final boolean isUseLongIntegers = true;
    private final boolean isIncludeGetters = false;
    private final boolean isIncludeSetters = false;
    private final boolean isIncludeHashcodeAndEquals = false;
    private final boolean isIncludeToString = false;
    private final boolean isIncludeAdditionalProperties = false;
    public final SourceType sourceType = SourceType.JSONSCHEMA;

    public static void main(String[] args) throws Exception {
        out.println(BASE_CONFIG.getJsonSchemaRoot());
        out.println(BASE_CONFIG.getPojoRoot());
        out.println(BASE_CONFIG.getTargetPackage());
        new JsonSchemaToJavaClass("store/order", "Order");
        //new JsonSchemaToJavaClass("pet", "Pet");
    }

    public JsonSchemaToJavaClass(String inputJsonUrl, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputDirectory, _packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(URL inputJsonUrl, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(URI inputJsonUrl, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(String inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputJavaClassDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(URL inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputJavaClassDirectory, packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(URI inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputJavaClassDirectory, packageName, javaClassName);
    }

    private void init(String inputJsonUrl, String packageName, String javaClassName) {
        _init(inputJsonUrl, packageName, javaClassName);
    }

    private void init(URL inputJsonUrl, String packageName, String javaClassName) {
        _init(inputJsonUrl.toString(), packageName, javaClassName);
    }

    private void init(URI inputJsonUrl, String packageName, String javaClassName) {
        _init(inputJsonUrl.toString(), packageName, javaClassName);
    }

    private void _init(String inputJsonUrl, String packageName, String javaClassName) {
        this.inputJsonUrl = inputJsonUrl;
        if (notEquals(packageName, "")) this._packageName += "." + inputJsonUrl.replace("/", ".");
        this.javaClassName = javaClassName;
        jcodeModel = new JCodeModel();
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
        out.println(inputJsonUrl);
        out.println(outputJavaClassDirectory);
        out.println(packageName);
        out.println(javaClassName);
        getSchemaMapper().generate(jcodeModel, javaClassName, packageName, new File(inputJsonUrl).toURI().toURL());
        jcodeModel.build(new File(outputJavaClassDirectory));
    }

    @NotNull
    private SchemaMapper getSchemaMapper() {
        GenerationConfig config = new DefaultGenerationConfig() {
            @Override public boolean isGenerateBuilders() {return isGenerateBuilders;}
            @Override public boolean isUseLongIntegers() {return isUseLongIntegers;}
            @Override public boolean isIncludeGetters() {return isIncludeGetters;}
            @Override public boolean isIncludeSetters() {return isIncludeSetters;}
            @Override public boolean isIncludeHashcodeAndEquals() {return isIncludeHashcodeAndEquals;}
            @Override public boolean isIncludeToString() {return isIncludeToString;}
            @Override public boolean isIncludeAdditionalProperties() {return isIncludeAdditionalProperties;}
            @Override public SourceType getSourceType() {return sourceType;}
        };
        return new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
    }

}
