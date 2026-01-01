package org.project.tests.step;

import java.beans.ConstructorProperties;

import org.project.utils.request.BaseRequests;
import org.project.tests.model.Model;

/**
 *
 */
public class Requests extends BaseRequests<Model> {
    /**
     *
     * @throws Exception throws
     */
    @ConstructorProperties({})
    public Requests() throws Exception {
        super("pet");
    }
}
