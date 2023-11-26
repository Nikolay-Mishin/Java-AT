
package models.pojo.json.pet;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
@JsonPropertyOrder({
    "id",
    "name"
})
@Generated("jsonschema2pojo")
public class Tag {

    @JsonProperty("id")
    public Long id;
    @JsonProperty("name")
    public String name;

}
