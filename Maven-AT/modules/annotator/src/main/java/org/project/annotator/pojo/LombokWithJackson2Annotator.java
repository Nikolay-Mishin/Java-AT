package org.project.annotator.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.project.annotator.Annotator;

public class LombokWithJackson2Annotator extends Jackson2Annotator {

    public LombokWithJackson2Annotator(GenerationConfig generationConfig) {
        super(generationConfig);
    }

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        super.propertyInclusion(clazz, schema);
        new Annotator().setPropertyInclusion(clazz, schema);
    }

}
