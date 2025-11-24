package org.project.utils.test;

import org.project.utils.request.BaseRequests;

public class Requests extends BaseRequests<Order> {

    public Requests() {
        super("store/order");
    }

}
