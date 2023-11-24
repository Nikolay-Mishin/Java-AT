
package models.pojo.pet;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "category",
    "name",
    "photoUrls",
    "tags",
    "status"
})
@Generated("jsonschema2pojo")
public class Pet {

    @JsonProperty("id")
    public Long id;
    @JsonProperty("category")
    public Category category;
    @JsonProperty("name")
    public String name;
    @JsonProperty("photoUrls")
    public List<String> photoUrls = new ArrayList<String>();
    @JsonProperty("tags")
    public List<Tag> tags = new ArrayList<Tag>();
    @JsonProperty("status")
    public String status;

}
