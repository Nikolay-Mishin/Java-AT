package requests.order;

import org.project.utils.request.BaseRequests;
import pojo.schema.store.order.OrderSchema;

import static constant.UrlConstants.ORDER_URL;

public class OrderSchemaRequests extends BaseRequests<OrderSchema> {

    public OrderSchemaRequests() {
        super(ORDER_URL);
    }

}
