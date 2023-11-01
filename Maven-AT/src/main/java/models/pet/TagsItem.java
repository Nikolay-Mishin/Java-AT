package models.pet;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagsItem {
	private String name;
	private int id;
}
