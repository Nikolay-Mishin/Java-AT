package org.project.annotator.lombok;

import org.project.annotator.config.AnnotatorConfig;

import java.lang.reflect.InvocationTargetException;

public class LombokAnnotator extends LombokBaseAnnotator {

    public LombokAnnotator() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        config.setDefaultAnnotations("lombok-builder", "lombok-data");
    }

    public LombokAnnotator(AnnotatorConfig config) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        super(config);
    }

}
