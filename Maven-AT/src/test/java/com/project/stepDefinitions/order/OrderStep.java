package com.project.stepDefinitions.order;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import models.order.Order;
import requests.OrderRequests;
import utils.base.Model;
import utils.base.Step;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import static constant.UrlConstants.ORDER_URL;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static org.junit.Assert.assertEquals;
import static utils.constant.RequestConstants.METHOD_LOWER_CASE.post;

public class OrderStep extends Step<OrderRequests, Order> {

    private Long orderId;

    @ConstructorProperties({})
    public OrderStep() {
        super(new OrderRequests(), Order.class);
    }

    private Response getOrder() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = req.getOrder(orderId);
        out.println(resp.getStatusCode());
        return resp;
    }

    private Response createOrder(List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException, URISyntaxException {
        out.println(dataTable.get(0).get(0));
        out.println(parseInt(dataTable.get(0).get(1)));
        Order order = Order.builder()
            .id(parseInt(dataTable.get(0).get(1)))
            .petId(parseInt(dataTable.get(1).get(1)))
            .quantity(parseInt(dataTable.get(2).get(1)))
            .shipDate(dataTable.get(3).get(1))
            .status(dataTable.get(4).get(1))
            .complete(parseBoolean(dataTable.get(5).get(1)))
            .build();
        out.println(order);
        Order order2 = new Model<>(modelClass, dataTable, post, ORDER_URL).get();
        out.println(order2);
        Response resp = req.postOrder(order);
        orderId = resp.jsonPath().get("id");
        out.println(orderId);
        out.println(resp.getStatusCode());
        return resp;
    }

    private Response deleteOrder() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = req.deleteOrder(orderId);
        out.println(resp.getStatusCode());
        return resp;
    }

    @Когда("создать заказ статус {int}")
    public void createOrder(int statusCode, List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, URISyntaxException, IOException {
        Response resp = createOrder(dataTable);
        assertEquals(statusCode, resp.getStatusCode());
    }

    @Тогда("получить заказ статус {int}")
    public void getOrder(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = getOrder();
        assertEquals(statusCode, resp.getStatusCode());
    }

    @И("удалить заказ статус {int}")
    public void deleteOrder(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        Response resp = deleteOrder();
        assertEquals(statusCode, resp.getStatusCode());
    }

}
