package com.project.stepDefinitions.order;

import io.cucumber.java.ru.Когда;
import pojo.gen.store.order.Order;
import requests.order.OrderGenRequests;
import org.project.utils.base.BaseStep;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderGenStep extends BaseStep<OrderGenRequests, Order> {

    @ConstructorProperties({})
    public OrderGenStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {}

    @Когда("создать заказ gen статус {int}")
    public void postOrder(int statusCode, List<List<String>> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, URISyntaxException, IOException {
        //new JsonToClass("store/order", "Order");
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

}
