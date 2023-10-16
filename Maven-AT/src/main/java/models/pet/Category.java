package models.pet;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data
@Builder
public class Category {

	private final String name;
	private final int id;

    @ConstructorProperties({"name", "id"})
    public Category(String name, int id) {
        this.name = name;
        this.id = id;
    }

}
