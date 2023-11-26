package com.project.stepDefinitions.order;

import io.cucumber.java.ru.Когда;
import io.restassured.response.Response;
import models.pojo.schema.store.order.OrderSchema;
import requests.OrderRequests;
import utils.base.Model;
import utils.base.Step;
import utils.pojo.JsonSchemaToJavaClass;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

public class OrderSchemaStep extends Step<OrderRequests, OrderSchema> {

    private Long orderId;

    @ConstructorProperties({})
    public OrderSchemaStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {}

    private Response getOrder() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = req.getOrder(orderId);
        out.println(resp.getStatusCode());
        return resp;
    }

    private Response postOrder(List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        new JsonSchemaToJavaClass("store/order", "Order");
        OrderSchema order = new Model<>(modelClass, dataTable).get();
        out.println(order);
        Response resp = req.postOrder(order);
        orderId = resp.path("id");
        out.println(orderId);
        out.println(resp.getStatusCode());
        return resp;
    }

    @Когда("создать новый заказ статус {int}")
    public void postOrder(int statusCode, List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, URISyntaxException, IOException {
        assertEquals(statusCode, postOrder(dataTable).getStatusCode());
    }

}
