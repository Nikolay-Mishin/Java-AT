package org.project.annotator.pojo;

public class LombokAnnotator extends LombokDefaultAnnotator {

    public LombokAnnotator(AnnotatorConfig config) {
        super(config);
    }

    public LombokAnnotator() {
        config.setDefaultAnnotations("lombok-builder", "lombok-data");
    }

}
