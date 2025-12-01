package requests.order;

import org.project.utils.request.BaseRequests;

import static constant.UrlConstants.ORDER_URL;

import pojo.json.store.order.Order;

public class OrderJsonRequests extends BaseRequests<Order> {
    public OrderJsonRequests() throws Exception {
        super(ORDER_URL);
    }
}
