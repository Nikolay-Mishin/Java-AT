package requests.order;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.project.utils.request.BaseRequests;

import static constant.UrlConstants.ORDER_URL;

import pojo.schema.store.order.OrderSchema;

public class OrderSchemaRequests extends BaseRequests<OrderSchema> {
    public OrderSchemaRequests() throws MalformedURLException, URISyntaxException, ReflectiveOperationException {
        super(ORDER_URL);
    }
}
