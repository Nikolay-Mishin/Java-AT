package org.project.annotator;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.AbstractAnnotator;
import org.project.annotator.config.AnnotatorConfig;
import org.project.annotator.config.DefaultAnnotatorConfig;
import org.project.utils.exception.AssertException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import static java.lang.System.out;

public class Annotator extends AbstractAnnotator {

    protected AnnotatorConfig config = new DefaultAnnotatorConfig();
    protected Annotations annotations = new Annotations();

    public Annotator() {}

    public Annotator(AnnotatorConfig config) {
        setConfig(config);
    }

    public Annotator(Annotations annotations) {
        setAnnotations(annotations);
    }

    public Annotator(AnnotatorConfig config, Annotations annotations) {
        setConfig(config);
        setAnnotations(annotations);
    }

    public AnnotatorConfig config() {
        return config;
    }

    public Annotations annotations() {
        return annotations;
    }

    public Annotator setConfig(AnnotatorConfig config) {
        this.config = config;
        return this;
    }

    public Annotator setAnnotations(Annotations annotations) {
        this.annotations = annotations;
        return this;
    }

    protected Class<? extends Annotation> getAnnotation(String property) {
        return annotations.getAnnotation(property);
    }

    protected void setAnnotation(JDefinedClass clazz, String property) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
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
        if (!config.isSetDefaultAnnotations()) return;
        config.getDefaultAnnotations().forEach(annotation -> {
            try {
                setAnnotation(clazz, annotation);
            } catch (InvocationTargetException|NoSuchMethodException|IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /*@Override
    public void propertyField(JFieldVar field, JDefinedClass clazz, String property, JsonNode propertyNode) {
        setAnnotation(clazz, property);
    }*/

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        JsonNode additionalProperties = schema.get("additionalProperties");
        try {
            additionalProperties.fieldNames().forEachRemaining(property -> {
                try {
                    setAnnotation(clazz, property);
                } catch (InvocationTargetException|NoSuchMethodException|IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            return;
        } catch (NullPointerException e) {
            out.printf("No additionalProperties defined for %s.%n", clazz.fullName());
        } catch (IllegalStateException e) {
            out.printf("No annotations defined for %s.%n", clazz.fullName());
        }
        setDefaultAnnotations(clazz);
    }

    @Override
    public boolean isAdditionalPropertiesSupported() {
        return false;
    }

}
