package org.project.annotator.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.project.annotator.Annotator;

import java.util.List;

import static java.lang.System.out;

public class LombokWithJackson2Annotator extends Jackson2Annotator {

    LombokAnnotator annotator = new LombokAnnotator();
    Annotator defaultAnnotator = new Annotator();

    protected final List<String> defaultAnnotations = List.of("lombok-builder", "lombok-data");

    public LombokWithJackson2Annotator(GenerationConfig generationConfig) {
        super(generationConfig);
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
