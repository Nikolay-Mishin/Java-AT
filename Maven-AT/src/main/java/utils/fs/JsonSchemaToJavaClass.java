package utils.fs;

import com.sun.codemodel.JCodeModel;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.File;
import java.io.IOException;
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

    public JsonSchemaToJavaClass(URL inputJsonUrl, String outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
        generate(getSchemaUrl(inputJsonUrl), new File(outputJavaClassDirectory), packageName, javaClassName);
    }

    public JsonSchemaToJavaClass(String javaClassName) throws IOException {
        generate(getSchemaUrl(new URL(javaClassName)), new File(outputDirectory), packageName, javaClassName);
    }

    private URL getSchemaUrl(URL inputJsonUrl) throws IOException {
        return new URL(getPath(schemaRoot, inputJsonUrl.toString()));
    }

    public static void generate(URL inputJsonUrl, File outputJavaClassDirectory, String packageName, String javaClassName) throws IOException {
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

        jcodeModel.build(outputJavaClassDirectory);
    }

}
