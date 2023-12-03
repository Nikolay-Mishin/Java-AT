package org.project.pojo;

import com.sun.codemodel.JCodeModel;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.project.annotator.BaseAnnotator;
import org.project.annotator.config.AnnotatorConfig;
import org.project.annotator.config.DefaultAnnotatorConfig;
import org.project.annotator.lombok.LombokAnnotator;
import org.project.utils.config.Config;
import org.project.utils.config.WebBaseConfig;

import java.io.File;
import java.io.IOException;

import static java.lang.System.out;
import static org.project.utils.Helper.toUpperCaseFirst;
import static org.project.utils.fs.FS.fileList;

public class JsonSchemaToClass {

    protected String schemaRoot = Config.config().getJsonSchemaRoot();
    protected String outputDirectory = Config.config().getPojoRoot();
    protected String targetPackage = Config.config().getTargetPackage();
    protected File file;
    protected String inputJsonUrl;
    protected String packageName = targetPackage;
    protected String javaClassName;
    protected JCodeModel jcodeModel = new JCodeModel();
    protected AnnotatorConfig config = new DefaultAnnotatorConfig();
    protected BaseAnnotator annotator = new LombokAnnotator(config);

    public JsonSchemaToClass() {
    }

    public JsonSchemaToClass(WebBaseConfig webConfig) throws IOException {
        generate(webConfig);
    }

    public String schemaRoot()  {
        return schemaRoot;
    }

    public JsonSchemaToClass schemaRoot(String schemaRoot)  {
        this.schemaRoot = schemaRoot;
        return this;
    }

    public String outputDirectory()  {
        return outputDirectory;
    }

    public JsonSchemaToClass outputDirectory(String outputDirectory)  {
        this.outputDirectory = outputDirectory;
        return this;
    }

    public String targetPackage()  {
        return targetPackage;
    }

    public JsonSchemaToClass targetPackage(String targetPackage)  {
        this.targetPackage = targetPackage;
        return this;
    }

    public GenerationConfig config()  {
        return config;
    }

    public JsonSchemaToClass config(AnnotatorConfig config)  {
        this.config = config;
        return this;
    }

    public Annotator annotator()  {
        return annotator;
    }

    public JsonSchemaToClass annotator(BaseAnnotator annotator)  {
        this.annotator = annotator;
        return this;
    }

    protected void generate(WebBaseConfig webConfig) throws IOException {
        Config.config(webConfig);
        out.println("schemaRoot: " + schemaRoot);
        out.println("outputDirectory: " + outputDirectory);
        out.println("targetPackage: " + targetPackage);
        out.println("SourceType: " + config.getSourceType());
        out.println("isSetDefaultAnnotations: " + annotator.config().isSetDefaultAnnotations());
        fileList(schemaRoot).forEach(file -> {
            try {
                generate(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected void generate(File file) throws IOException {
        init(file);
        new SchemaMapper(new RuleFactory(config, annotator, new SchemaStore()), new SchemaGenerator())
            .generate(jcodeModel, javaClassName, packageName, file.toURI().toURL());
        jcodeModel.build(new File(outputDirectory));
    }

    protected void init(File file) {
        out.println(file);
        this.file = file;
        String parentUrl = file.getParent().replace("\\", "/");
        String fileName = file.getName().replace(".json", "").replace(".schema", "");
        inputJsonUrl = parentUrl.replace(schemaRoot + "/", "");
        packageName = targetPackage + "." + inputJsonUrl.replace("/", ".");
        javaClassName = toUpperCaseFirst(fileName);
        out.println(inputJsonUrl);
        out.println(packageName);
        out.println(javaClassName);
    }

}
