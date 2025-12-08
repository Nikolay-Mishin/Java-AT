package org.project.utils.config;

import java.util.List;

import org.jsonschema2pojo.GenerationConfig;

public interface AnnotatorConfig extends GenerationConfig {

    boolean isSetDefaultAnnotations();

    AnnotatorConfig isSetDefaultAnnotations(boolean value);

    List<String> defaultAnnotations();

    AnnotatorConfig defaultAnnotations(String... annotations);

}
