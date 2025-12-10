package com.project.stepDefinitions.order;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.When;

import org.project.utils.base.BaseStep;

import pojo.gen.store.order.Order;
import requests.order.OrderGenRequests;

public class OrderGenStep extends BaseStep<OrderGenRequests, Order> {

    @ConstructorProperties({})
    public OrderGenStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    @When("создать заказ gen статус {int}")
    public void postOrder(int statusCode, List<List<String>> dataTable) throws Exception {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

}
