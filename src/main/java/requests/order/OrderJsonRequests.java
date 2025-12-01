package requests.order;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.project.utils.request.BaseRequests;

import static constant.UrlConstants.ORDER_URL;

import pojo.json.store.order.Order;

public class OrderJsonRequests extends BaseRequests<Order> {
    public OrderJsonRequests() throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        super(ORDER_URL);
    }
}
