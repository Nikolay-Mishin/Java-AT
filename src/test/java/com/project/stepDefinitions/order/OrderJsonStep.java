package com.project.stepDefinitions.order;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.When;

import org.project.utils.base.BaseStep;

import pojo.json.store.order.Order;
import requests.order.OrderJsonRequests;

public class OrderJsonStep extends BaseStep<OrderJsonRequests, Order> {

    @ConstructorProperties({})
    public OrderJsonStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    @When("создать заказ json статус {int}")
    public void postOrder(int statusCode, List<List<String>> dataTable) throws Exception {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

}
