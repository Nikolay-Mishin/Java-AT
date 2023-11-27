package org.project.pojo;

import com.sun.codemodel.JCodeModel;
import org.jetbrains.annotations.NotNull;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.project.annotator.pojo.LombokAnnotator;
import org.project.annotator.pojo.LombokWithJackson2Annotator;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import static java.lang.System.out;
import static utils.Helper.notEquals;
import static utils.fs.FS.getPath;

public class JsonSchemaToClass {

    protected static String schemaRoot = "src/main/resources/schema";
    protected static String outputDirectory = "src/main/java";
    protected static String packageName = "pojo.schema";
    protected String inputJsonUrl;
    protected String _packageName = packageName;
    protected String javaClassName;
    protected JCodeModel jcodeModel;
    protected final boolean isGenerateBuilders = false;
    protected final boolean isUseLongIntegers = true;
    protected final boolean isIncludeGetters = false;
    protected final boolean isIncludeSetters = false;
    protected final boolean isIncludeHashcodeAndEquals = false;
    protected final boolean isIncludeToString = false;
    protected final boolean isIncludeAdditionalProperties = false;
    protected final SourceType sourceType = SourceType.JSONSCHEMA;
    protected final Class<? extends Annotator> customAnnotator = LombokAnnotator.class;
    protected final boolean isIncludeGeneratedAnnotation = true;

    public static void main(String[] args) throws Exception {
        test();
    }

    public static void setRoot(String schemaRoot, String outputDirectory, String packageName) {
        JsonSchemaToClass.schemaRoot = schemaRoot;
        JsonSchemaToClass.outputDirectory = outputDirectory;
        JsonSchemaToClass.packageName = packageName;
    }

    public static void test() throws IOException {
        new JsonSchemaToClass("store/order", "Order");
        //new JsonSchemaToClass("pet", "Pet");
    }

    public JsonSchemaToClass() throws IOException {
        test();
    }

    public JsonSchemaToClass(String inputJsonUrl, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputDirectory, _packageName, javaClassName);
    }

    public JsonSchemaToClass(URL inputJsonUrl, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputDirectory, packageName, javaClassName);
    }

    public JsonSchemaToClass(URI inputJsonUrl, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputDirectory, packageName, javaClassName);
    }

    public JsonSchemaToClass(String inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputJavaClassDirectory, packageName, javaClassName);
    }

    public JsonSchemaToClass(URL inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputJavaClassDirectory, packageName, javaClassName);
    }

    public JsonSchemaToClass(URI inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputJavaClassDirectory, packageName, javaClassName);
    }

    protected void init(String inputJsonUrl, String packageName, String javaClassName) {
        _init(inputJsonUrl, packageName, javaClassName);
    }

    protected void init(URL inputJsonUrl, String packageName, String javaClassName) {
        _init(inputJsonUrl.toString(), packageName, javaClassName);
    }

    protected void init(URI inputJsonUrl, String packageName, String javaClassName) {
        _init(inputJsonUrl.toString(), packageName, javaClassName);
    }

    protected void _init(String inputJsonUrl, String packageName, String javaClassName) {
        this.inputJsonUrl = inputJsonUrl;
        if (notEquals(packageName, "")) this._packageName += "." + inputJsonUrl.replace("/", ".");
        this.javaClassName = javaClassName;
        jcodeModel = new JCodeModel();
    }

    protected String getSchemaPath() {
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

    protected void _generate(String inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        out.println(inputJsonUrl);
        out.println(outputJavaClassDirectory);
        out.println(packageName);
        out.println(javaClassName);
        getSchemaMapper().generate(jcodeModel, javaClassName, packageName, new File(inputJsonUrl).toURI().toURL());
        jcodeModel.build(new File(outputJavaClassDirectory));
    }

    @NotNull
    protected SchemaMapper getSchemaMapper() {
        GenerationConfig config = new DefaultGenerationConfig() {
            @Override public boolean isGenerateBuilders() {return isGenerateBuilders;}
            @Override public boolean isUseLongIntegers() {return isUseLongIntegers;}
            @Override public boolean isIncludeGetters() {return isIncludeGetters;}
            @Override public boolean isIncludeSetters() {return isIncludeSetters;}
            @Override public boolean isIncludeHashcodeAndEquals() {return isIncludeHashcodeAndEquals;}
            @Override public boolean isIncludeToString() {return isIncludeToString;}
            @Override public boolean isIncludeAdditionalProperties() {return isIncludeAdditionalProperties;}
            @Override public SourceType getSourceType() {return sourceType;}
            @Override public Class<? extends Annotator> getCustomAnnotator() {return customAnnotator;}
            @Override public boolean isIncludeGeneratedAnnotation() {return isIncludeGeneratedAnnotation;}
        };
        Jackson2Annotator annotator;
        try {
            annotator = new LombokWithJackson2Annotator(config);
        } catch (Exception e) {
            annotator = new Jackson2Annotator(config);
        }
        return new SchemaMapper(new RuleFactory(config, annotator, new SchemaStore()), new SchemaGenerator());
    }

}
