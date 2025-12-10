package org.project.utils.test;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.When;

import org.project.utils.base.BaseStep;

public class Step extends BaseStep<Requests, Order> {

    @ConstructorProperties({})
    public Step() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    public void getOrder(int statusCode, int id) throws Exception {
        assertEquals(statusCode, get(id).getStatusCode());
    }

    @When("создать заказ gen статус {int}")
    public void postOrder(int statusCode, List<List<String>> dataTable) throws Exception {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

}
