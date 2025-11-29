package org.project.utils.test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.project.utils.request.BaseRequests;

public class Requests extends BaseRequests<Order> {
    public Requests() throws MalformedURLException, URISyntaxException {
        super("store/order");
    }
}
