package org.project.annotator.lombok;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.project.annotator.Annotations;
import org.project.annotator.Annotator;
import org.project.annotator.config.AnnotatorConfig;
import org.project.annotator.config.DefaultAnnotatorConfig;

import java.lang.reflect.InvocationTargetException;

public class LombokWithJackson2Annotator extends Jackson2Annotator {

    protected AnnotatorConfig config = new DefaultAnnotatorConfig();
    protected Annotations annotations = new Annotations();
    protected Annotator annotator = new LombokAnnotator(config);

    public LombokWithJackson2Annotator(GenerationConfig generationConfig) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        super(generationConfig);
    }

    public AnnotatorConfig config() {
        return config;
    }

    protected LombokWithJackson2Annotator setConfig(AnnotatorConfig config) {
        this.config = config;
        return this;
    }

    protected LombokWithJackson2Annotator setAnnotations(Annotations annotations) {
        this.annotations = annotations;
        return this;
    }

    protected LombokWithJackson2Annotator setAnnotator(Annotator annotator) {
        this.annotator = annotator;
        return this;
    }

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        super.propertyInclusion(clazz, schema);
        annotator.propertyInclusion(clazz, schema);
    }

}
