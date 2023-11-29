package org.project.annotator.lombok;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.project.annotator.config.AnnotatorConfig;
import org.project.annotator.config.DefaultConfigAnnotator;

public class LombokWithJackson2Annotator extends Jackson2Annotator {

    protected AnnotatorConfig config = new DefaultConfigAnnotator();

    public LombokWithJackson2Annotator(GenerationConfig generationConfig) {
        super(generationConfig);
    }

    public LombokWithJackson2Annotator(GenerationConfig generationConfig, AnnotatorConfig config) {
        super(generationConfig);
        this.config = config;
    }

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        super.propertyInclusion(clazz, schema);
        new LombokAnnotator(config).propertyInclusion(clazz, schema);
    }

}
