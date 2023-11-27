package org.project.annotator.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.project.annotator.Annotator;

import static java.lang.System.out;

public class LombokWithJackson2Annotator extends Jackson2Annotator {

    Annotator annotator = new LombokAnnotator();
    Annotator defaultAnnotator = new Annotator();

    public LombokWithJackson2Annotator(GenerationConfig generationConfig) {
        super(generationConfig);
    }

    public LombokWithJackson2Annotator(GenerationConfig generationConfig, AnnotatorConfig config) {
        super(generationConfig);
        setAnnotator(annotator.setConfig(config), defaultAnnotator.setConfig(config));
    }

    public LombokWithJackson2Annotator(GenerationConfig generationConfig, Annotator annotator) {
        super(generationConfig);
        setAnnotator(annotator);
    }

    public LombokWithJackson2Annotator(GenerationConfig generationConfig, Annotator annotator, Annotator defaultAnnotator) {
        super(generationConfig);
        setAnnotator(annotator, defaultAnnotator);
    }

    public LombokWithJackson2Annotator(GenerationConfig generationConfig, Annotator annotator, AnnotatorConfig config) {
        super(generationConfig);
        setAnnotator(annotator.setConfig(config));
    }

    public LombokWithJackson2Annotator(GenerationConfig generationConfig, Annotator annotator, Annotator defaultAnnotator, AnnotatorConfig config) {
        super(generationConfig);
        setAnnotator(annotator.setConfig(config), defaultAnnotator.setConfig(config));
    }

    protected void setAnnotator(Annotator annotator, Annotator defaultAnnotator) {
        setAnnotator(annotator);
        this.defaultAnnotator = defaultAnnotator;
    }

    protected void setAnnotator(Annotator annotator) {
        this.annotator = annotator;
    }

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        super.propertyInclusion(clazz, schema);
        try {
            annotator.setPropertyInclusion(clazz, schema);
        } catch (NoClassDefFoundError e) {
            out.printf("No annotations defined for %s.%n", clazz.fullName());
            defaultAnnotator.setPropertyInclusion(clazz, schema);
        }
    }

}
