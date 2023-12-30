package com.project.stepDefinitions.order;

import io.cucumber.java.ru.Когда;
import org.project.utils.base.BaseStep;
import pojo.json.store.order.Order;
import requests.order.OrderJsonRequests;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;

import static config.WebConfig.BASE_CONFIG;
import static org.junit.Assert.assertEquals;

public class OrderJsonStep extends BaseStep<OrderJsonRequests, Order> {

    @ConstructorProperties({})
    public OrderJsonStep() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(BASE_CONFIG);
    }

    @Когда("создать заказ json статус {int}")
    public void postOrder(int statusCode, List<List<String>> dataTable)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, URISyntaxException, IOException {
        assertEquals(statusCode, post(dataTable).getStatusCode());
    }

}
