package org.project.pojo;

import com.sun.codemodel.JCodeModel;
import org.jetbrains.annotations.NotNull;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.project.annotator.IAnnotator;
import org.project.annotator.config.AnnotatorConfig;
import org.project.annotator.config.DefaultAnnotatorConfig;
import org.project.annotator.lombok.LombokAnnotator;
import org.project.utils.config.Config;
import org.project.utils.config.WebBaseConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

import static java.lang.System.out;
import static org.project.utils.Helper.toUpperCaseFirst;
import static org.project.utils.fs.FS.fileList;

public class JsonSchemaToClass {

    protected String schemaRoot = Config.config().getJsonSchemaRoot();
    protected String outputDirectory = Config.config().getPojoRoot();
    protected String targetPackage = Config.config().getTargetPackage();
    protected Path file;
    protected String inputJsonUrl;
    protected String packageName = targetPackage;
    protected String javaClassName;
    protected JCodeModel jcodeModel = new JCodeModel();
    protected AnnotatorConfig config = new DefaultAnnotatorConfig();
    protected IAnnotator annotator = new LombokAnnotator(config);

    public JsonSchemaToClass() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {}

    public JsonSchemaToClass(WebBaseConfig baseConfig) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        init(baseConfig);
    }

    public String schemaRoot()  {
        return schemaRoot;
    }

    public JsonSchemaToClass setSchemaRoot(String schemaRoot)  {
        this.schemaRoot = schemaRoot;
        return this;
    }

    public String outputDirectory()  {
        return outputDirectory;
    }

    public JsonSchemaToClass setOutputDirectory(String outputDirectory)  {
        this.outputDirectory = outputDirectory;
        return this;
    }

    public String targetPackage()  {
        return targetPackage;
    }

    public JsonSchemaToClass setTargetPackage(String targetPackage)  {
        this.targetPackage = targetPackage;
        return this;
    }

    public GenerationConfig config()  {
        return config;
    }

    public JsonSchemaToClass setConfig(AnnotatorConfig config)  {
        this.config = config;
        return this;
    }

    public Annotator annotator()  {
        return annotator;
    }

    public JsonSchemaToClass setAnnotator(IAnnotator annotator)  {
        this.annotator = annotator;
        return this;
    }

    protected void init(WebBaseConfig baseConfig) throws IOException {
        Config.setConfig(baseConfig);
        out.println("schemaRoot: " + schemaRoot);
        out.println("outputDirectory: " + outputDirectory);
        out.println("targetPackage: " + targetPackage);
        out.println("SourceType: " + config.getSourceType());
        out.println("isSetDefaultAnnotations: " + annotator.config().isSetDefaultAnnotations());
        fileList(schemaRoot).forEach(file -> {
            try {
                init(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected void init(Path file) throws IOException {
        out.println(file);
        this.file = file;
        String parentUrl = file.getParent().toString().replace("\\", "/");
        String fileName = file.getFileName().toString().replace(".json", "").replace(".schema", "");
        inputJsonUrl = parentUrl.replace(schemaRoot + "/", "");
        packageName = targetPackage + "." + inputJsonUrl.replace("/", ".");
        javaClassName = toUpperCaseFirst(fileName);
        generate();
    }

    protected void generate() throws IOException {
        out.println(inputJsonUrl);
        out.println(packageName);
        out.println(javaClassName);
        buildJCodeModel(generateJType(jcodeModel, config, annotator, javaClassName, packageName, file.toString()), outputDirectory);
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
