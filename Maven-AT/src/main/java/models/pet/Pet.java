package models.pet;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pet {
	private List<String> photoUrls;
	private String name;
	private Long id;
    private Category category;
	private List<TagsItem> tags;
	private String status;
}
