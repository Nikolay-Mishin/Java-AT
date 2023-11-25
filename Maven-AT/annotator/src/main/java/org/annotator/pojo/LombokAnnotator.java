package org.annotator.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.jsonschema2pojo.AbstractAnnotator;

import java.lang.annotation.Annotation;

import static java.lang.System.out;

public class LombokAnnotator extends AbstractAnnotator {

    public Class<? extends Annotation> getAnnotation(String property) {
        return switch (property) {
            case "lombok-builder" -> Builder.class;
            case "lombok-data" -> Data.class;
            case "lombok-to-string" -> ToString.class;
            default -> throw new IllegalStateException("Unexpected value: " + property);
        };
    }

    public void setAnnotation(JDefinedClass clazz, String property) {
        Class<? extends Annotation> annotation = getAnnotation(property);
        if (!annotation.equals(IllegalStateException.class)) {
            clazz.annotate(annotation);
        }
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
        }
    }

    @Override
    public boolean isAdditionalPropertiesSupported() {
        return false;
    }

}
