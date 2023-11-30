package org.project.annotator;

import org.project.utils.base.HashMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class Annotations {

    protected HashMap<String, Class<? extends Annotation>> annotations = new HashMap<>();

    public Annotations set(String property, Class<? extends Annotation> clazz) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        annotations.put(property, clazz);
        return this;
    }

    public Class<? extends Annotation> getAnnotation(String property) {
        return annotations.get(property);
    }

}
