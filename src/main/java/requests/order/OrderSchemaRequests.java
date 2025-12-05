package requests.order;

import org.project.utils.request.BaseRequests;

import static constant.Endpoints.ORDER_URL;

import pojo.schema.store.order.OrderSchema;

public class OrderSchemaRequests extends BaseRequests<OrderSchema> {
    public OrderSchemaRequests() throws Exception {
        super(ORDER_URL);
    }
}
