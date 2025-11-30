package org.project.utils.request;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class AuthBaseRequests<T> extends BaseRequests<T> {
    public AuthBaseRequests(String baseUrl) throws MalformedURLException, URISyntaxException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        super(baseUrl);
    }
}
