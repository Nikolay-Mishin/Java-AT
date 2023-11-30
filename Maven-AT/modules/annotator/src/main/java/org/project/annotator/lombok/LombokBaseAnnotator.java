package org.project.annotator.lombok;

import lombok.*;
import org.project.annotator.Annotations;
import org.project.annotator.Annotator;
import org.project.annotator.config.AnnotatorConfig;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class LombokBaseAnnotator extends Annotator {

    public LombokBaseAnnotator(AnnotatorConfig config) {
        super(config);
    }

    public LombokBaseAnnotator() {}

    @Override
    protected Class<? extends Annotation> getAnnotation(String property) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        new Annotations()
            .set("lombok-builder", Builder.class)
            .getAnnotation(property);
        return switch (property) {
            case "lombok-builder" -> Builder.class;
            case "lombok-data" -> Data.class;
            case "lombok-getter" -> Getter.class;
            case "lombok-setter" -> Setter.class;
            case "lombok-equals-and-hash-code" -> EqualsAndHashCode.class;
            case "lombok-no-args-constructor" -> NoArgsConstructor.class;
            case "lombok-all-args-constructor" -> AllArgsConstructor.class;
            case "lombok-to-string" -> ToString.class;
            default -> throw new IllegalStateException("Unexpected value: " + property);
        };
    }

}
