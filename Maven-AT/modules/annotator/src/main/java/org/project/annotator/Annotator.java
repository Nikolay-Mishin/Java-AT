package org.project.annotator;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.AbstractAnnotator;
import org.project.annotator.pojo.AnnotatorConfig;
import org.project.annotator.pojo.DefaultConfigAnnotator;

import java.lang.annotation.Annotation;

import static java.lang.System.out;

public class Annotator extends AbstractAnnotator {

    protected AnnotatorConfig config = new DefaultConfigAnnotator();

    public Annotator(AnnotatorConfig config) {
        setConfig(config);
    }

    public Annotator() {}

    public Annotator setConfig(AnnotatorConfig config) {
        this.config = config;
        return this;
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
        config.getDefaultAnnotations().forEach(annotation -> setAnnotation(clazz, annotation));
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
        if (config.isSetDefaultAnnotations()) setDefaultAnnotations(clazz);
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
