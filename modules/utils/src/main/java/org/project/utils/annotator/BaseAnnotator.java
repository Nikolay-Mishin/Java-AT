package org.project.utils.annotator;

import java.beans.ConstructorProperties;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.AbstractAnnotator;
import org.jsonschema2pojo.Annotator;

import static org.project.utils.Helper.debug;

import org.project.utils.config.AnnotatorConfig;
import org.project.utils.config.AnnotatorBaseConfig;
import org.project.utils.exception.AssertException;

/**
 *
 */
public class BaseAnnotator extends AbstractAnnotator implements Annotator {
    /**
     *
     */
    protected AnnotatorConfig config = new AnnotatorBaseConfig();
    /**
     *
     */
    protected Annotations annotations = new Annotations();

    /**
     *
     */
    @ConstructorProperties({"value"})
    public BaseAnnotator() {
    }

    /**
     *
     * @param config AnnotatorConfig
     */
    public BaseAnnotator(AnnotatorConfig config) {
        config(config);
    }

    /**
     *
     * @param annotations Annotations
     */
    public BaseAnnotator(Annotations annotations) {
        annotations(annotations);
    }

    /**
     *
     * @param config AnnotatorConfig
     * @param annotations Annotations
     */
    public BaseAnnotator(AnnotatorConfig config, Annotations annotations) {
        config(config);
        annotations(annotations);
    }

    /**
     *
     * @return AnnotatorConfig
     */
    public AnnotatorConfig config() {
        return config;
    }

    /**
     *
     * @return Annotations
     */
    public Annotations annotations() {
        return annotations;
    }

    /**
     *
     * @param config AnnotatorConfig
     * @return BaseAnnotator
     */
    public BaseAnnotator config(AnnotatorConfig config) {
        this.config = config;
        return this;
    }

    /**
     *
     * @param annotations Annotations
     * @return BaseAnnotator
     */
    public BaseAnnotator annotations(Annotations annotations) {
        this.annotations = annotations;
        return this;
    }

    /**
     *
     * @param property String
     * @return Class of Annotation
     */
    protected Class<? extends Annotation> annotation(String property) {
        return annotations.get(property);
    }

    /**
     *
     * @param clazz JDefinedClass
     * @param property String
     * @throws InvocationTargetException throws
     * @throws NoSuchMethodException throws
     * @throws IllegalAccessException throws
     */
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

    /**
     *
     * @param clazz JDefinedClass
     */
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
