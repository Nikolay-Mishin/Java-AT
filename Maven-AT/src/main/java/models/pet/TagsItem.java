package models.pet;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data
@Builder
public class TagsItem {

	private String name;
	private int id;

    @ConstructorProperties({"name", "id"})
    public TagsItem(String name, int id) {
        this.name = name;
        this.id = id;
    }

}
