package org.project.annotator.pojo;

import java.util.List;

public class DefaultConfigAnnotator implements AnnotatorConfig {

    protected List<String> defaultAnnotations = List.of();

    @Override
    public boolean isSetDefaultAnnotations() {
        return true;
    }

    @Override
    public List<String> getDefaultAnnotations() {
        return defaultAnnotations;
    }

    @Override
    public void setDefaultAnnotations(String... annotations) {
        defaultAnnotations = List.of(annotations);
    }
}
