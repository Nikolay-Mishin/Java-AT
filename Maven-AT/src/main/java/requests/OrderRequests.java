package requests;

import io.restassured.response.Response;
import jdk.jfr.Description;
import models.order.Order;
import pojo.schema.store.order.OrderSchema;
import utils.Request;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static constant.UrlConstants.ORDER_URL;
import static utils.constant.RequestConstants.METHOD.*;

public class OrderRequests extends Request {

    // запрос создания заказа
    @Description("Place an order for a pet")
    private Response _postOrder(Object order)
        throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return new Request(POST, ORDER_URL)
            .body(order)
            .response();
    }

    // запрос создания заказа
    @Description("Place an order for a pet")
    public Response postOrder(Order order)
        throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _postOrder(order);
    }

    public Response postOrder(pojo.json.store.order.Order order)
        throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _postOrder(order);
    }

    // запрос создания заказа
    @Description("Place an order for a pet")
    public Response postOrder(OrderSchema order)
        throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return _postOrder(order);
    }

    // запрос получения заказа
    @Description("Find purchase order by ID")
    public Response getOrder(Long orderId)
        throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Request request = new Request(GET, ORDER_URL, orderId);
        return request.response();
    }

    @Description("Delete purchase order by ID")
    public Response deleteOrder(Long orderId)
        throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return new Request(DELETE, ORDER_URL, orderId).response();
    }

}
