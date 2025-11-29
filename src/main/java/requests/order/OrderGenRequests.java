package requests.order;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.project.utils.request.BaseRequests;

import static constant.UrlConstants.ORDER_URL;

import pojo.gen.store.order.Order;

public class OrderGenRequests extends BaseRequests<Order> {
    public OrderGenRequests() throws MalformedURLException, URISyntaxException {
        super(ORDER_URL);
    }
}
