package org.project.annotator.config;

import java.util.List;

public interface AnnotatorConfig {

    boolean isSetDefaultAnnotations();
    List<String> getDefaultAnnotations();
    void setDefaultAnnotations(String... annotations);

}
