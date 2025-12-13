package org.project.utils.pojo;

import java.beans.ConstructorProperties;
import java.io.File;
import java.io.IOException;

import com.sun.codemodel.JCodeModel;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import static org.project.utils.Helper.debug;
import static org.project.utils.Helper.toUpperCaseFirst;
import static org.project.utils.fs.FS.fileList;

import org.project.utils.annotator.BaseAnnotator;
import org.project.utils.config.AnnotatorConfig;
import org.project.utils.config.AnnotatorBaseConfig;
import org.project.utils.annotator.lombok.LombokAnnotator;
import org.project.utils.config.WebConfig;
import org.project.utils.config.WebBaseConfig;

/**
 *
 */
public class JsonSchemaToClass {
    /**
     *
     */
    protected String schemaRoot = WebConfig.config().getJsonSchemaRoot();
    /**
     *
     */
    protected String outputDirectory = WebConfig.config().getPojoRoot();
    /**
     *
     */
    protected String targetPackage = WebConfig.config().getTargetPackage();
    /**
     *
     */
    protected File file;
    /**
     *
     */
    protected String inputJsonUrl;
    /**
     *
     */
    protected String packageName = targetPackage;
    /**
     *
     */
    protected String javaClassName;
    /**
     *
     */
    protected JCodeModel jcodeModel = new JCodeModel();
    /**
     *
     */
    protected AnnotatorConfig config = new AnnotatorBaseConfig();
    /**
     *
     */
    protected BaseAnnotator annotator = new LombokAnnotator(config);

    /**
     *
     */
    @ConstructorProperties({})
    public JsonSchemaToClass() {
    }

    /**
     *
     * @param webConfig WebBaseConfig
     * @throws IOException throws
     */
    @ConstructorProperties({"webConfig"})
    public JsonSchemaToClass(WebBaseConfig webConfig) throws IOException {
        generate(webConfig);
    }

    /**
     *
     * @return String
     */
    public String schemaRoot()  {
        return schemaRoot;
    }

    /**
     *
     * @param schemaRoot String
     * @return JsonSchemaToClass
     */
    public JsonSchemaToClass schemaRoot(String schemaRoot)  {
        this.schemaRoot = schemaRoot;
        return this;
    }

    /**
     *
     * @return String
     */
    public String outputDirectory()  {
        return outputDirectory;
    }

    /**
     *
     * @param outputDirectory String
     * @return JsonSchemaToClass
     */
    public JsonSchemaToClass outputDirectory(String outputDirectory)  {
        this.outputDirectory = outputDirectory;
        return this;
    }

    /**
     *
     * @return String
     */
    public String targetPackage()  {
        return targetPackage;
    }

    /**
     *
     * @param targetPackage String
     * @return JsonSchemaToClass
     */
    public JsonSchemaToClass targetPackage(String targetPackage)  {
        this.targetPackage = targetPackage;
        return this;
    }

    /**
     *
     * @return GenerationConfig
     */
    public GenerationConfig config()  {
        return config;
    }

    /**
     *
     * @param config AnnotatorConfig
     * @return JsonSchemaToClass
     */
    public JsonSchemaToClass config(AnnotatorConfig config)  {
        this.config = config;
        return this;
    }

    /**
     *
     * @return Annotator
     */
    public Annotator annotator()  {
        return annotator;
    }

    /**
     *
     * @param annotator BaseAnnotator
     * @return JsonSchemaToClass
     */
    public JsonSchemaToClass annotator(BaseAnnotator annotator)  {
        this.annotator = annotator;
        return this;
    }

    /**
     *
     * @param webConfig WebBaseConfig
     * @throws IOException throws
     */
    protected void generate(WebBaseConfig webConfig) throws IOException {
        WebConfig.config(webConfig);
        debug("schemaRoot: " + schemaRoot);
        debug("outputDirectory: " + outputDirectory);
        debug("targetPackage: " + targetPackage);
        debug("SourceType: " + config.getSourceType());
        debug("isSetDefaultAnnotations: " + annotator.config().isSetDefaultAnnotations());
        fileList(schemaRoot).forEach(file -> {
            try {
                generate(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     *
     * @param file File
     * @throws IOException throws
     */
    protected void generate(File file) throws IOException {
        init(file);
        new SchemaMapper(new RuleFactory(config, annotator, new SchemaStore()), new SchemaGenerator())
            .generate(jcodeModel, javaClassName, packageName, file.toURI().toURL());
        jcodeModel.build(new File(outputDirectory));
    }

    /**
     *
     * @param file File
     */
    protected void init(File file) {
        debug(file);
        this.file = file;
        String parentUrl = file.getParent().replace("\\", "/");
        String fileName = file.getName().replace(".json", "").replace(".schema", "");
        inputJsonUrl = parentUrl.replace(schemaRoot + "/", "");
        packageName = targetPackage + "." + inputJsonUrl.replace("/", ".");
        javaClassName = toUpperCaseFirst(fileName);
        debug(inputJsonUrl);
        debug(packageName);
        debug(javaClassName);
    }

}
