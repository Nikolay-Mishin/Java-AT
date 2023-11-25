package org.project.annotator.module;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import lombok.*;
import org.jsonschema2pojo.AbstractAnnotator;

import java.lang.annotation.Annotation;
import java.util.List;

import static java.lang.System.out;

public class LombokAnnotator extends AbstractAnnotator {

    private static final List<String> defaultAnnotations = List.of("lombok-builder", "lombok-data");

    private static Class<? extends Annotation> getAnnotation(String property) {
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

    private static void setAnnotation(JDefinedClass clazz, String property) {
        out.println(property);
        Class<? extends Annotation> annotation = getAnnotation(property);
        if (!annotation.equals(IllegalStateException.class)) {
            clazz.annotate(annotation);
        }
    }

    private static void setDefaultAnnotation(JDefinedClass clazz) {
        defaultAnnotations.forEach(annotation -> setAnnotation(clazz, annotation));
    }

    /*@Override
    public void propertyField(JFieldVar field, JDefinedClass clazz, String property, JsonNode propertyNode) {
        setAnnotation(clazz, property);
    }*/

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        JsonNode additionalProperties = schema.get("additionalProperties");
        try {
            additionalProperties.fieldNames().forEachRemaining(property -> setAnnotation(clazz, property));
        } catch (NullPointerException e) {
            out.printf("No additionalProperties defined for %s.%n", clazz.fullName());
            setDefaultAnnotation(clazz);
        }
    }

    @Override
    public boolean isAdditionalPropertiesSupported() {
        return false;
    }

}
