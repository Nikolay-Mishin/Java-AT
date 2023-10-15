package models.pet;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data
@Builder
public class Category {

	private String name;
	private int id;

    @ConstructorProperties({"name", "id"})
    public Category(String name, int id) {
        this.name = name;
        this.id = id;
    }

}
