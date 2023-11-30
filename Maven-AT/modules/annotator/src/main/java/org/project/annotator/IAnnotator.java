package org.project.annotator;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.project.annotator.config.AnnotatorConfig;

public interface IAnnotator extends org.jsonschema2pojo.Annotator {

    AnnotatorConfig config();

    Annotations annotations();

    Annotator setConfig(AnnotatorConfig config);

    Annotator setAnnotations(Annotations annotations);

    /*@Override
    void propertyField(JFieldVar field, JDefinedClass clazz, String property, JsonNode propertyNode);*/

    @Override
    void propertyInclusion(JDefinedClass clazz, JsonNode schema);

    @Override
    boolean isAdditionalPropertiesSupported();

}
