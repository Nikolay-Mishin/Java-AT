package org.project.utils.test;

import org.project.utils.request.BaseRequests;

public class Requests extends BaseRequests<Order> {
    public Requests() throws Exception {
        super("store/order");
    }
}
