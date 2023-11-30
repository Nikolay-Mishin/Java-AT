package org.project.annotator.config;

import java.util.List;

public interface AnnotatorConfig {

    boolean isSetDefaultAnnotations();

    AnnotatorConfig isSetDefaultAnnotations(boolean value);

    List<String> getDefaultAnnotations();

    AnnotatorConfig setDefaultAnnotations(String... annotations);

}
