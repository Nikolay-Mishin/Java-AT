package org.project.utils.annotator.lombok;

import org.project.utils.config.AnnotatorConfig;

public class LombokAnnotator extends LombokBaseAnnotator {

    public LombokAnnotator() {
        init();
    }

    public LombokAnnotator(AnnotatorConfig config) {
        super(config);
        init();
    }

    protected void init()  {
        config.defaultAnnotations("lombok-builder", "lombok-data");
    }

}
