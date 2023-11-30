package org.project.annotator.config;

import java.util.List;

public class DefaultAnnotatorConfig extends DefaultGenerationConfig implements AnnotatorConfig {

    protected boolean isSetDefaultAnnotations = false;
    protected List<String> annotations = List.of();

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
    public List<String> getDefaultAnnotations() {
        return annotations;
    }

    @Override
    public AnnotatorConfig setDefaultAnnotations(String... annotations) {
        isSetDefaultAnnotations(true);
        this.annotations = List.of(annotations);
        return this;
    }
}
