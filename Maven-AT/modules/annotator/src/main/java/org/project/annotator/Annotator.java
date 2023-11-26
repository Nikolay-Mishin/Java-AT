package org.project.annotator;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.AbstractAnnotator;
import org.jsonschema2pojo.GenerationConfig;

import java.lang.annotation.Annotation;
import java.util.List;

import static java.lang.System.out;

public class Annotator extends AbstractAnnotator {

    protected final List<String> defaultAnnotations = List.of("lombok-builder", "lombok-data");

    protected Class<? extends Annotation> getAnnotation(String property) {
        return Annotation.class;
    }

    protected void setAnnotation(JDefinedClass clazz, String property) {
        out.println(property);
        Class<? extends Annotation> annotation = getAnnotation(property);
        if (!annotation.equals(IllegalStateException.class)) {
            clazz.annotate(annotation);
        }
    }

    protected void setDefaultAnnotation(JDefinedClass clazz) {
        defaultAnnotations.forEach(annotation -> setAnnotation(clazz, annotation));
    }

    public void setPropertyInclusion(JDefinedClass clazz, JsonNode schema) {
        JsonNode additionalProperties = schema.get("additionalProperties");
        try {
            additionalProperties.fieldNames().forEachRemaining(property -> setAnnotation(clazz, property));
        } catch (NullPointerException e) {
            out.printf("No additionalProperties defined for %s.%n", clazz.fullName());
            setDefaultAnnotation(clazz);
        }
    }

    /*@Override
    public void propertyField(JFieldVar field, JDefinedClass clazz, String property, JsonNode propertyNode) {
        setAnnotation(clazz, property);
    }*/

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        setPropertyInclusion(clazz, schema);
    }

    @Override
    public boolean isAdditionalPropertiesSupported() {
        return false;
    }

}
