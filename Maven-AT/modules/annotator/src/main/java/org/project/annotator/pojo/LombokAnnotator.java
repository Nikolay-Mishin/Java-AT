package org.project.annotator.pojo;

import lombok.*;
import org.project.annotator.Annotator;

import java.lang.annotation.Annotation;
import java.util.List;

public class LombokAnnotator extends Annotator {

    protected final List<String> defaultAnnotations = List.of("lombok-builder", "lombok-data");

    @Override
    protected Class<? extends Annotation> getAnnotation(String property) {
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
