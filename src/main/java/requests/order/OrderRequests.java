package requests.order;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.project.utils.request.BaseRequests;

import static constant.UrlConstants.ORDER_URL;

import models.order.Order;

public class OrderRequests extends BaseRequests<Order> {
    public OrderRequests() throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        super(ORDER_URL);
    }
}
