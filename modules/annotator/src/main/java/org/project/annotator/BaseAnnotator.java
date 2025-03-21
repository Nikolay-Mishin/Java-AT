package org.project.annotator;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.AbstractAnnotator;
import org.jsonschema2pojo.Annotator;
import org.project.annotator.config.AnnotatorConfig;
import org.project.annotator.config.DefaultAnnotatorConfig;
import org.project.utils.exception.AssertException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import static org.project.utils.Helper.debug;

public class BaseAnnotator extends AbstractAnnotator implements Annotator {

    protected AnnotatorConfig config = new DefaultAnnotatorConfig();
    protected Annotations annotations = new Annotations();

    public BaseAnnotator() {
    }

    public BaseAnnotator(AnnotatorConfig config) {
        config(config);
    }

    public BaseAnnotator(Annotations annotations) {
        annotations(annotations);
    }

    public BaseAnnotator(AnnotatorConfig config, Annotations annotations) {
        config(config);
        annotations(annotations);
    }

    public AnnotatorConfig config() {
        return config;
    }

    public Annotations annotations() {
        return annotations;
    }

    public BaseAnnotator config(AnnotatorConfig config) {
        this.config = config;
        return this;
    }

    public BaseAnnotator annotations(Annotations annotations) {
        this.annotations = annotations;
        return this;
    }

    protected Class<? extends Annotation> annotation(String property) {
        return annotations.get(property);
    }

    protected void annotation(JDefinedClass clazz, String property) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        debug(property);
        Class<? extends Annotation> annotation = annotation(property);
        try {
            new AssertException(annotation);
        } catch (AssertionError e) {
            throw new IllegalStateException("Unexpected value: " + property);
        }
        clazz.annotate(annotation);
    }

    protected void setDefaultAnnotations(JDefinedClass clazz) {
        if (!config.isSetDefaultAnnotations()) return;
        config.defaultAnnotations().forEach(annotation -> {
            try {
                annotation(clazz, annotation);
            } catch (InvocationTargetException|NoSuchMethodException|IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /*@Override
    public void propertyField(JFieldVar field, JDefinedClass clazz, String property, JsonNode propertyNode) {
        annotation(clazz, property);
    }*/

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        JsonNode additionalProperties = schema.get("additionalProperties");
        try {
            additionalProperties.fieldNames().forEachRemaining(property -> {
                try {
                    annotation(clazz, property);
                } catch (InvocationTargetException|NoSuchMethodException|IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            return;
        } catch (NullPointerException e) {
            debug("No additionalProperties defined for", clazz.fullName());
        } catch (IllegalStateException e) {
            debug("No annotations defined for", clazz.fullName());
        }
        setDefaultAnnotations(clazz);
    }

    @Override
    public boolean isAdditionalPropertiesSupported() {
        return false;
    }

}
