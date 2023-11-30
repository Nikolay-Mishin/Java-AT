package org.project.pojo;

import com.sun.codemodel.JCodeModel;
import org.jetbrains.annotations.NotNull;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.project.annotator.config.AnnotatorConfig;
import org.project.annotator.config.DefaultAnnotatorConfig;
import org.project.annotator.lombok.LombokAnnotator;
import org.project.utils.config.WebBaseConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;

import static java.lang.System.out;
import static org.project.utils.Helper.notEquals;
import static org.project.utils.config.Config.config;
import static org.project.utils.config.Config.setConfig;
import static org.project.utils.fs.FS.getPath;

public class JsonSchemaToClass {

    protected static String schemaRoot = config().getJsonSchemaRoot();
    protected static String outputDirectory = config().getPojoRoot();
    protected static String targetPackage = config().getTargetPackage();
    protected String inputJsonUrl;
    protected String _packageName = targetPackage;
    protected String javaClassName;
    protected JCodeModel jcodeModel = new JCodeModel();
    protected AnnotatorConfig config = new DefaultAnnotatorConfig();
    protected Annotator annotator = new LombokAnnotator(config);

    public JsonSchemaToClass(WebBaseConfig baseConfig) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        setConfig(baseConfig);
        create();
    }

    protected void create() throws IOException {
        create("store/order", "Order");
        //create("pet", "Pet");
    }

    protected void create(String inputJsonUrl, String javaClassName) throws IOException {
        init(inputJsonUrl, targetPackage, javaClassName);
        generate(getSchemaPath(), outputDirectory, _packageName, javaClassName);
    }

    protected void create(URL inputJsonUrl, String javaClassName) throws IOException {
        init(inputJsonUrl, targetPackage, javaClassName);
        generate(getSchemaPath(), outputDirectory, targetPackage, javaClassName);
    }

    protected void create(URI inputJsonUrl, String javaClassName) throws IOException {
        init(inputJsonUrl, targetPackage, javaClassName);
        generate(getSchemaPath(), outputDirectory, targetPackage, javaClassName);
    }

    protected void create(String inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputJavaClassDirectory, packageName, javaClassName);
    }

    protected void create(URL inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        init(inputJsonUrl, packageName, javaClassName);
        generate(getSchemaPath(), outputJavaClassDirectory, packageName, javaClassName);
    }

    protected void create(URI inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
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
        out.println("schemaRoot: " + schemaRoot);
        out.println("outputDirectory: " + outputDirectory);
        out.println("targetPackage: " + targetPackage);
        this.inputJsonUrl = inputJsonUrl;
        if (notEquals(packageName, "")) this._packageName += "." + inputJsonUrl.replace("/", ".");
        this.javaClassName = javaClassName;
    }

    protected String getSchemaPath() {
        return getPath(schemaRoot, inputJsonUrl, javaClassName.toLowerCase() + (config.getSourceType() == SourceType.JSONSCHEMA ? ".schema" : "") + ".json");
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
        out.println(packageName);
        out.println(javaClassName);
        buildJCodeModel(generateJType(jcodeModel, config, annotator, javaClassName, packageName, inputJsonUrl), outputJavaClassDirectory);
    }

    public static JCodeModel generateJType
        (JCodeModel jcodeModel, GenerationConfig config, Annotator annotator, String inputJsonUrl, String packageName, String javaClassName) throws IOException {
        return _generateJType(jcodeModel, config, annotator, javaClassName, packageName, inputJsonUrl);
    }

    public static JCodeModel generateJType(GenerationConfig config, Annotator annotator, String inputJsonUrl, String packageName, String javaClassName)
        throws IOException {
        return _generateJType(new JCodeModel(), config, annotator, javaClassName, packageName, inputJsonUrl);
    }

    protected static JCodeModel _generateJType
        (JCodeModel jcodeModel, GenerationConfig config, Annotator annotator, String inputJsonUrl, String packageName, String javaClassName) throws IOException {
        getSchemaMapper(config, annotator).generate(jcodeModel, javaClassName, packageName, new File(inputJsonUrl).toURI().toURL());
        return jcodeModel;
    }

    @NotNull
    public static SchemaMapper getSchemaMapper(GenerationConfig config, Annotator annotator) {
        return new SchemaMapper(new RuleFactory(config, annotator, new SchemaStore()), new SchemaGenerator());
    }

    public static void buildJCodeModel(JCodeModel jcodeModel, String outputJavaClassDirectory) throws IOException {
        jcodeModel.build(new File(outputJavaClassDirectory));
    }

}
