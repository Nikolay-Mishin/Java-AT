package models.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
	private Long petId;
	private int quantity;
	private int id;
	private String shipDate;
	private boolean complete;
	private String status;
}
