package org.project.annotator.lombok;

import org.project.annotator.config.AnnotatorConfig;

import java.lang.reflect.InvocationTargetException;

public class LombokAnnotator extends LombokBaseAnnotator {

    public LombokAnnotator() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        init();
    }

    public LombokAnnotator(AnnotatorConfig config) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        super(config);
        init();
    }

    protected void init()  {
        config.setDefaultAnnotations("lombok-builder", "lombok-data");
    }

}
