package requests.order;

import models.order.Order;
import org.project.utils.request.BaseRequests;

import static constant.UrlConstants.ORDER_URL;

public class OrderRequests extends BaseRequests<Order> {

    public OrderRequests() {
        super(ORDER_URL);
    }

}
