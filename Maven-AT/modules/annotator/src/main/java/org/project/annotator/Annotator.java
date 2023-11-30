package org.project.annotator;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.AbstractAnnotator;
import org.project.annotator.config.AnnotatorConfig;
import org.project.annotator.config.DefaultConfigAnnotator;
import org.project.utils.exception.AssertException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import static java.lang.System.out;

public abstract class Annotator extends AbstractAnnotator {

    protected AnnotatorConfig config = new DefaultConfigAnnotator();

    public Annotator(AnnotatorConfig config) {
        this.config = config;
    }

    public Annotator() {}

    protected abstract Class<? extends Annotation> getAnnotation(String property) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    protected void setAnnotation(JDefinedClass clazz, String property) {
        out.println(property);
        Class<? extends Annotation> annotation = getAnnotation(property);
        try {
            new AssertException(annotation);
        } catch (AssertionError e) {
            throw new IllegalStateException("Unexpected value: " + property);
        }
        clazz.annotate(annotation);
    }

    protected void setDefaultAnnotations(JDefinedClass clazz) {
        config.getDefaultAnnotations().forEach(annotation -> setAnnotation(clazz, annotation));
    }

    /*@Override
    public void propertyField(JFieldVar field, JDefinedClass clazz, String property, JsonNode propertyNode) {
        setAnnotation(clazz, property);
    }*/

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
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

    @Override
    public boolean isAdditionalPropertiesSupported() {
        return false;
    }

}
