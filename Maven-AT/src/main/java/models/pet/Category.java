package models.pet;

import lombok.Builder;
import lombok.Data;
import utils.base.Model;

@Data
@Builder
public class Category extends Model {
	private String name;
	private int id;
}
