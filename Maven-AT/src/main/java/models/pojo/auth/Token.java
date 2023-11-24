
package models.pojo.auth;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "access",
    "refresh",
    "file"
})
@Generated("jsonschema2pojo")
public class Token {

    @JsonProperty("access")
    public Access access;
    @JsonProperty("refresh")
    public Refresh refresh;
    @JsonProperty("file")
    public File file;

}
