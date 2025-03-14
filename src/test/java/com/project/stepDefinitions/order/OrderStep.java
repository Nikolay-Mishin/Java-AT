package com.project.stepDefinitions.order;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import models.order.Order;
import requests.order.OrderRequests;
import org.project.utils.base.BaseStep;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import static config.WebConfig.BASE_CONFIG;
import static org.junit.Assert.assertEquals;

public class OrderStep extends BaseStep<OrderRequests, Order> {

    @ConstructorProperties({})
    public OrderStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(BASE_CONFIG);
    }

    @Когда("создать заказ статус {int}")
    public void postOrder(int statusCode, List<List<String>> dataTable) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, URISyntaxException, IOException {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

    @Тогда("получить заказ статус {int}")
    public void getOrder(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        assertEquals(statusCode, get().getStatusCode());
    }

    @И("удалить заказ статус {int}")
    public void deleteOrder(int statusCode) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MalformedURLException, URISyntaxException {
        assertEquals(statusCode, delete().getStatusCode());
    }

}
