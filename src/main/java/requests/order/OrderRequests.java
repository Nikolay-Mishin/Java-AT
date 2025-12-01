package requests.order;

import org.project.utils.request.BaseRequests;

import static constant.UrlConstants.ORDER_URL;

import models.order.Order;

public class OrderRequests extends BaseRequests<Order> {
    public OrderRequests() throws Exception {
        super(ORDER_URL);
    }
}
