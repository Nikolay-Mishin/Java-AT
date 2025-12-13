package org.project.utils.annotator;

import java.lang.annotation.Annotation;

import org.project.utils.base.HashMap;

/**
 *
 */
public class Annotations {
    /**
     *
     */
    protected HashMap<String, Class<? extends Annotation>> annotations = new HashMap<>();

    /**
     *
     * @param property String
     * @param clazz Class extends Annotation
     * @return Annotations
     */
    public Annotations set(String property, Class<? extends Annotation> clazz) {
        annotations.put(property, clazz);
        return this;
    }

    /**
     *
     * @param property String
     * @return Class  extends Annotation
     */
    public Class<? extends Annotation> get(String property) {
        return annotations.get(property);
    }

}
