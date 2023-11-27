
package pojo.json.auth;

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
    "value"
})
@Generated("jsonschema2pojo")
public class Access {

    @JsonProperty("value")
    public String value;

}
