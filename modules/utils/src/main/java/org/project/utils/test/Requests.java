package org.project.utils.test;

import org.project.utils.request.BaseRequests;
import org.project.utils.test.model.Model;

public class Requests extends BaseRequests<Model> {
    public Requests() throws Exception {
        super("pet");
    }
}
