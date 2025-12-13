package org.project.utils.annotator.lombok;

import java.beans.ConstructorProperties;

import org.project.utils.config.AnnotatorConfig;

/**
 *
 */
public class LombokAnnotator extends LombokBaseAnnotator {

    /**
     *
     */
    @ConstructorProperties({})
    public LombokAnnotator() {
        init();
    }

    /**
     *
     * @param config AnnotatorConfig
     */
    @ConstructorProperties({"config"})
    public LombokAnnotator(AnnotatorConfig config) {
        super(config);
        init();
    }

    /**
     *
     */
    protected void init()  {
        config.defaultAnnotations("lombok-builder", "lombok-data");
    }

}
