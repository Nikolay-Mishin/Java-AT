package models.pet;

import lombok.Builder;
import lombok.Data;
import utils.base.Model;

@Data
@Builder
public class TagsItem extends Model<TagsItem> {
	private String name;
	private int id;
}
