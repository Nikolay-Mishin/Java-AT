package requests.order;

import org.project.utils.request.BaseRequests;
import pojo.json.store.order.Order;

import static constant.UrlConstants.ORDER_URL;

public class OrderJsonRequests extends BaseRequests<Order> {

    public OrderJsonRequests() {
        super(ORDER_URL);
    }

}
