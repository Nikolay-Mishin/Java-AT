package org.project.annotator.lombok;

import org.project.annotator.config.AnnotatorConfig;

public class LombokAnnotator extends LombokBaseAnnotator {

    public LombokAnnotator(AnnotatorConfig config) {
        super(config);
    }

    public LombokAnnotator() {
        config.setDefaultAnnotations("lombok-builder", "lombok-data");
    }

}
