package org.project.annotator.lombok;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.project.annotator.Annotations;
import org.project.annotator.BaseAnnotator;
import org.project.annotator.config.AnnotatorConfig;
import org.project.annotator.config.DefaultAnnotatorConfig;

public class LombokWithJackson2Annotator extends Jackson2Annotator {

    protected AnnotatorConfig config = new DefaultAnnotatorConfig();
    protected Annotations annotations = new Annotations();
    protected BaseAnnotator annotator = new LombokAnnotator(config);

    public LombokWithJackson2Annotator(AnnotatorConfig config) {
        super(config);
    }

    public GenerationConfig config() {
        return config;
    }

    public Annotations annotations() {
        return annotations;
    }

    public BaseAnnotator annotator() {
        return annotator;
    }

    public LombokWithJackson2Annotator config(AnnotatorConfig config) {
        this.config = config;
        return this;
    }

    public LombokWithJackson2Annotator annotations(Annotations annotations) {
        this.annotations = annotations;
        return this;
    }

    public LombokWithJackson2Annotator annotator(BaseAnnotator annotator) {
        this.annotator = annotator;
        return this;
    }

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        super.propertyInclusion(clazz, schema);
        annotator.propertyInclusion(clazz, schema);
    }

}
