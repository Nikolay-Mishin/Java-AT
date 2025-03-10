package org.project.annotator.config;

import org.jsonschema2pojo.GenerationConfig;

import java.util.List;

public interface AnnotatorConfig extends GenerationConfig {

    boolean isSetDefaultAnnotations();

    AnnotatorConfig isSetDefaultAnnotations(boolean value);

    List<String> defaultAnnotations();

    AnnotatorConfig defaultAnnotations(String... annotations);

}
