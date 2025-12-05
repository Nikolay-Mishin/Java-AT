package requests.order;

import org.project.utils.request.BaseRequests;

import static constant.Endpoints.ORDER_URL;

import pojo.gen.store.order.Order;

public class OrderGenRequests extends BaseRequests<Order> {
    public OrderGenRequests() throws Exception {
        super(ORDER_URL);
    }
}
