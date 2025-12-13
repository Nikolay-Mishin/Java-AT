package org.project.utils.config;

import java.util.List;

import org.jsonschema2pojo.GenerationConfig;

/**
 *
 */
public interface AnnotatorConfig extends GenerationConfig {
    /**
     *
     * @return boolean
     */
    boolean isSetDefaultAnnotations();
    /**
     *
     * @param value boolean
     * @return AnnotatorConfig
     */
    AnnotatorConfig isSetDefaultAnnotations(boolean value);
    /**
     *
     * @return List of String
     */
    List<String> defaultAnnotations();
    /**
     *
     * @param annotations String[]
     * @return AnnotatorConfig
     */
    AnnotatorConfig defaultAnnotations(String... annotations);
}
