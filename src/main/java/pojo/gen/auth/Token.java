
package pojo.gen.auth;

import javax.annotation.processing.Generated;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Generated("jsonschema2pojo")
public class Token {

    public Access access;
    public Refresh refresh;
    public File file;

}
