package org.project.utils.test;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.project.utils.request.BaseRequests;

public class Requests extends BaseRequests<Order> {
    public Requests() throws MalformedURLException, URISyntaxException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        super("store/order");
    }
}
