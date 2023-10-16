package models.pet;

import java.beans.ConstructorProperties;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pet {

	private final List<String> photoUrls;
	private final String name;
	private final int id;
	private final Category category;
	private final List<TagsItem> tags;
	private final String status;

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
