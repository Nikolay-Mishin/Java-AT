package org.project.utils.config;

import java.util.List;

/**
 *
 */
public class AnnotatorBaseConfig extends GenerationBaseConfig implements AnnotatorConfig {
    /**
     *
     */
    protected boolean isSetDefaultAnnotations = false;
    /**
     *
     */
    protected List<String> defaultAnnotations = List.of();

    @Override
    public boolean isSetDefaultAnnotations() {
        return isSetDefaultAnnotations;
    }

    @Override
    public AnnotatorConfig isSetDefaultAnnotations(boolean value) {
        isSetDefaultAnnotations = value;
        return this;
    }

    @Override
    public List<String> defaultAnnotations() {
        return defaultAnnotations;
    }

    @Override
    public AnnotatorConfig defaultAnnotations(String... annotations) {
        isSetDefaultAnnotations(true);
        defaultAnnotations = List.of(annotations);
        return this;
    }
}
