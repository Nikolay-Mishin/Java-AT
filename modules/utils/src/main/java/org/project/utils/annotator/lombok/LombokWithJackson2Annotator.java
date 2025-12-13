package org.project.utils.annotator.lombok;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;

import org.project.utils.annotator.Annotations;
import org.project.utils.annotator.BaseAnnotator;
import org.project.utils.config.AnnotatorConfig;
import org.project.utils.config.AnnotatorBaseConfig;

/**
 *
 */
public class LombokWithJackson2Annotator extends Jackson2Annotator {
    /**
     *
     */
    protected AnnotatorConfig config = new AnnotatorBaseConfig();
    /**
     *
     */
    protected Annotations annotations = new Annotations();
    /**
     *
     */
    protected BaseAnnotator annotator = new LombokAnnotator(config);

    /**
     *
     * @param config AnnotatorConfig
     */
    @ConstructorProperties({"config"})
    public LombokWithJackson2Annotator(AnnotatorConfig config) {
        super(config);
    }

    /**
     *
     * @return GenerationConfig
     */
    public GenerationConfig config() {
        return config;
    }

    /**
     *
     * @return Annotations
     */
    public Annotations annotations() {
        return annotations;
    }

    /**
     *
     * @return BaseAnnotator
     */
    public BaseAnnotator annotator() {
        return annotator;
    }

    /**
     *
     * @param config AnnotatorConfig
     * @return LombokWithJackson2Annotator
     */
    public LombokWithJackson2Annotator config(AnnotatorConfig config) {
        this.config = config;
        return this;
    }

    /**
     *
     * @param annotations Annotations
     * @return LombokWithJackson2Annotator
     */
    public LombokWithJackson2Annotator annotations(Annotations annotations) {
        this.annotations = annotations;
        return this;
    }

    /**
     *
     * @param annotator BaseAnnotator
     * @return LombokWithJackson2Annotator
     */
    public LombokWithJackson2Annotator annotator(BaseAnnotator annotator) {
        this.annotator = annotator;
        return this;
    }

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        super.propertyInclusion(clazz, schema);
        annotator.propertyInclusion(clazz, schema);
    }

}
