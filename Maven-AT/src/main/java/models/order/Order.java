package models.order;

import lombok.Builder;
import lombok.Data;
import utils.base.Model;

@Data
@Builder
public class Order extends Model<Order> {
	private Long petId;
	private int quantity;
	private int id;
	private String shipDate;
	private boolean complete;
	private String status;
}
