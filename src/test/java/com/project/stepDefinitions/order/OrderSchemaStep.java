package com.project.stepDefinitions.order;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.When;

import org.project.utils.base.BaseStep;

import pojo.schema.store.order.OrderSchema;
import requests.order.OrderSchemaRequests;

public class OrderSchemaStep extends BaseStep<OrderSchemaRequests, OrderSchema> {

    @ConstructorProperties({})
    public OrderSchemaStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    @When("создать заказ schema статус {int}")
    public void postOrder(int statusCode, List<List<String>> dataTable) throws ReflectiveOperationException, URISyntaxException, IOException {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

}
