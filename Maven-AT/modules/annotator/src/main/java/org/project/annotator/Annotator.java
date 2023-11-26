package org.project.annotator;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.AbstractAnnotator;

import java.lang.annotation.Annotation;
import java.util.List;

import static java.lang.System.out;

public class Annotator extends AbstractAnnotator {

    protected final List<String> defaultAnnotations = List.of();
    protected final boolean setDefaultAnnotations;

    public Annotator(boolean setDefaultAnnotations) {
        this.setDefaultAnnotations = setDefaultAnnotations;
    }

    public Annotator() {
        setDefaultAnnotations = true;
    }

    protected Class<? extends Annotation> getAnnotation(String property) {
        return switch (property) {
            case "default" -> Annotation.class;
            case "annotation" -> Annotation.class;
            default -> throw new IllegalStateException("Unexpected value: " + property);
        };
    }

    protected void setAnnotation(JDefinedClass clazz, String property) {
        out.println(property);
        Class<? extends Annotation> annotation = getAnnotation(property);
        if (!annotation.equals(IllegalStateException.class)) {
            clazz.annotate(annotation);
        }
    }

    protected void setDefaultAnnotations(JDefinedClass clazz) {
        defaultAnnotations.forEach(annotation -> setAnnotation(clazz, annotation));
    }

    public void setPropertyInclusion(JDefinedClass clazz, JsonNode schema) {
        JsonNode additionalProperties = schema.get("additionalProperties");
        try {
            additionalProperties.fieldNames().forEachRemaining(property -> setAnnotation(clazz, property));
            return;
        } catch (NullPointerException e) {
            out.printf("No additionalProperties defined for %s.%n", clazz.fullName());
        } catch (IllegalStateException e) {
            out.printf("No annotations defined for %s.%n", clazz.fullName());
        }
        if (setDefaultAnnotations) setDefaultAnnotations(clazz);
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
