package models.pet;

import java.beans.ConstructorProperties;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pet {

	private List<String> photoUrls;
	private String name;
	private int id;
	private Category category;
	private List<TagsItem> tags;
	private String status;

    @ConstructorProperties({"name", "id", "status"})
    public Pet(List<String> photoUrls, String name, int id, Category category, List<TagsItem> tags, String status) {
        this.photoUrls = photoUrls;
        this.name = name;
        this.id = id;
        this.category = category;
        this.tags = tags;
        this.status = status;
    }
}
