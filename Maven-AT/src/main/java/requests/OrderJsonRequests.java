package requests;

import io.restassured.response.Response;
import jdk.jfr.Description;
import pojo.gen.store.order.Order;
import org.project.utils.request.BaseRequests;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static constant.UrlConstants.ORDER_URL;

public class OrderJsonRequests extends BaseRequests<Order> {

    public OrderJsonRequests() {
        baseUrl = ORDER_URL;
    }

    public Response post(Order order)
        throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return super.post(order);
    }

    // запрос получения заказа
    @Override
    @Description("Find purchase order by ID")
    public Response get(Long orderId)
        throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return super.get(orderId);
    }

    @Override
    @Description("Delete purchase order by ID")
    public Response delete(Long orderId)
        throws MalformedURLException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return super.delete(orderId);
    }

}
