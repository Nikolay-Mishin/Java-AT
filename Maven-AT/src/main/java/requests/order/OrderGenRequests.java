package requests.order;

import org.project.utils.request.BaseRequests;
import pojo.gen.store.order.Order;

import static constant.UrlConstants.ORDER_URL;

public class OrderGenRequests extends BaseRequests<Order> {

    public OrderGenRequests() {
        super(ORDER_URL);
    }

}
