package models.pet;

import lombok.Builder;
import lombok.Data;
import utils.base.Model;

import java.util.List;

@Data
@Builder
public class Pet extends Model<Pet> {
	private List<String> photoUrls;
	private String name;
	private Long id;
    private Category category;
	private List<TagsItem> tags;
	private String status;
}
