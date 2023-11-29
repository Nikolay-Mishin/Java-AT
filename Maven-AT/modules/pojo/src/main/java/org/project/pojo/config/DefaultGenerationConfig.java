package org.project.pojo.config;

import org.jsonschema2pojo.Annotator;
import org.jsonschema2pojo.SourceType;
import org.project.annotator.lombok.LombokAnnotator;

public class DefaultGenerationConfig extends org.jsonschema2pojo.DefaultGenerationConfig {

    @Override public boolean isGenerateBuilders() {
        return false;
    }

    @Override public boolean isUseLongIntegers() {
        return true;
    }

    @Override public boolean isIncludeGetters() {
        return false;
    }

    @Override public boolean isIncludeSetters() {
        return false;
    }

    @Override public boolean isIncludeHashcodeAndEquals() {
        return false;
    }

    @Override public boolean isIncludeToString() {
        return false;
    }

    @Override public boolean isIncludeAdditionalProperties() {
        return false;
    }
    @Override public boolean isInitializeCollections() {
        return false;
    }

    @Override public SourceType getSourceType() {
        return SourceType.JSONSCHEMA;
    }

    @Override public Class<? extends Annotator> getCustomAnnotator() {
        return LombokAnnotator.class;
    }

    @Override public boolean isIncludeGeneratedAnnotation() {
        return true;
    }

}
