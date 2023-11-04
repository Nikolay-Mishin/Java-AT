
package auth;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Access access;
    @JsonProperty("refresh")
    private Refresh refresh;
    @JsonProperty("file")
    private File file;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("access")
    public Access getAccess() {
        return access;
    }

    @JsonProperty("access")
    public void setAccess(Access access) {
        this.access = access;
    }

    @JsonProperty("refresh")
    public Refresh getRefresh() {
        return refresh;
    }

    @JsonProperty("refresh")
    public void setRefresh(Refresh refresh) {
        this.refresh = refresh;
    }

    @JsonProperty("file")
    public File getFile() {
        return file;
    }

    @JsonProperty("file")
    public void setFile(File file) {
        this.file = file;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Token.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("access");
        sb.append('=');
        sb.append(((this.access == null)?"<null>":this.access));
        sb.append(',');
        sb.append("refresh");
        sb.append('=');
        sb.append(((this.refresh == null)?"<null>":this.refresh));
        sb.append(',');
        sb.append("file");
        sb.append('=');
        sb.append(((this.file == null)?"<null>":this.file));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.refresh == null)? 0 :this.refresh.hashCode()));
        result = ((result* 31)+((this.access == null)? 0 :this.access.hashCode()));
        result = ((result* 31)+((this.file == null)? 0 :this.file.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Token) == false) {
            return false;
        }
        Token rhs = ((Token) other);
        return (((((this.refresh == rhs.refresh)||((this.refresh!= null)&&this.refresh.equals(rhs.refresh)))&&((this.access == rhs.access)||((this.access!= null)&&this.access.equals(rhs.access))))&&((this.file == rhs.file)||((this.file!= null)&&this.file.equals(rhs.file))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
