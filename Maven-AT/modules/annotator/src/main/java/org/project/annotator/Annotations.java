package org.project.annotator;

import org.project.utils.base.HashMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import static java.lang.System.out;

public class Annotations {

    protected HashMap<String, Class<? extends Annotation>> annotations = new HashMap<>();
    protected HashMap<String, Class<? extends Annotation>> annotations2;

    public Annotations set(String property, Class<? extends Annotation> clazz) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        out.println(annotations);
        annotations.put(property, clazz);
        out.println(annotations);
        out.println(annotations.getClass());
        annotations2 = new HashMap<String, Class<? extends Annotation>>(property).values(clazz);
        out.println(annotations2);
        out.println(annotations2.getClass());
        return this;
    }

    public Class<? extends Annotation> getAnnotation(String property) {
        out.println(annotations);
        out.println(annotations.get(property));
        out.println(annotations2);
        out.println(annotations2.get(property));
        return annotations.get(property);
    }

}
