package com.project.stepDefinitions.order;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import org.project.utils.base.BaseStep;

import models.order.Order;
import requests.order.OrderRequests;

public class OrderStep extends BaseStep<OrderRequests, Order> {

    @ConstructorProperties({})
    public OrderStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    @When("создать заказ статус {int}")
    public void postOrder(int statusCode, List<List<String>> dataTable) throws Exception {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

    @Then("получить заказ статус {int}")
    public void getOrder(int statusCode) throws Exception {
        assertEquals(statusCode, get().getStatusCode());
    }

    @And("удалить заказ статус {int}")
    public void deleteOrder(int statusCode) throws Exception {
        assertEquals(statusCode, delete().getStatusCode());
    }

}
