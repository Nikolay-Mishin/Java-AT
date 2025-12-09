package com.project.stepDefinitions.order;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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
    public void postOrder(int statusCode, List<List<String>> dataTable) throws ReflectiveOperationException, URISyntaxException, IOException {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

    @Then("получить заказ статус {int}")
    public void getOrder(int statusCode) throws ReflectiveOperationException, MalformedURLException, URISyntaxException {
        assertEquals(statusCode, get().getStatusCode());
    }

    @And("удалить заказ статус {int}")
    public void deleteOrder(int statusCode) throws ReflectiveOperationException, MalformedURLException, URISyntaxException {
        assertEquals(statusCode, delete().getStatusCode());
    }

}
