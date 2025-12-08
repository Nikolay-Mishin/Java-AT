package org.project.utils.annotator.lombok;

import lombok.*;

import org.project.utils.annotator.Annotations;
import org.project.utils.annotator.BaseAnnotator;
import org.project.utils.config.AnnotatorConfig;

public class LombokBaseAnnotator extends BaseAnnotator {

    public LombokBaseAnnotator() {
        setAnnotations();
    }

    public LombokBaseAnnotator(AnnotatorConfig config) {
        super(config);
        setAnnotations();
    }

    protected void setAnnotations() {
        annotations(new Annotations()
            .set("lombok-builder", Builder.class)
            .set("lombok-data", Data.class)
            .set("lombok-getter", Getter.class)
            .set("lombok-setter", Setter.class)
            .set("lombok-equals-and-hash-code", EqualsAndHashCode.class)
            .set("lombok-no-args-constructor", NoArgsConstructor.class)
            .set("lombok-all-args-constructor", AllArgsConstructor.class)
            .set("lombok-to-string", ToString.class));
    }

}
