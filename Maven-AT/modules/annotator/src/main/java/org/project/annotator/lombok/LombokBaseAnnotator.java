package org.project.annotator.lombok;

import lombok.*;
import org.project.annotator.Annotations;
import org.project.annotator.Annotator;
import org.project.annotator.config.AnnotatorConfig;

import java.lang.reflect.InvocationTargetException;

public class LombokBaseAnnotator extends Annotator {

    public LombokBaseAnnotator() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        setAnnotations();
    }

    public LombokBaseAnnotator(AnnotatorConfig config) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        super(config);
        setAnnotations();
    }

    protected void setAnnotations() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        super.setAnnotations(new Annotations()
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
