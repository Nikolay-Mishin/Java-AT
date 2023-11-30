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

    protected GenerationConfig config = new DefaultAnnotatorConfig();
    protected Annotations annotations = new Annotations();
    protected Annotator annotator = new LombokAnnotator((AnnotatorConfig) config);

    public LombokWithJackson2Annotator(GenerationConfig config) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        super(config);
    }

    public GenerationConfig config() {
        return config;
    }

    public Annotations annotations() {
        return annotations;
    }

    public Annotator annotator() {
        return annotator;
    }

    public LombokWithJackson2Annotator setConfig(GenerationConfig config) {
        this.config = config;
        return this;
    }

    public LombokWithJackson2Annotator setAnnotations(Annotations annotations) {
        this.annotations = annotations;
        return this;
    }

    public LombokWithJackson2Annotator setAnnotator(Annotator annotator) {
        this.annotator = annotator;
        return this;
    }

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        super.propertyInclusion(clazz, schema);
        annotator.propertyInclusion(clazz, schema);
    }

}
