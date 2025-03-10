
package pojo.gen.pet;

import java.util.List;
import javax.annotation.processing.Generated;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Generated("jsonschema2pojo")
public class Pet {

    public Long id;
    public Category category;
    public String name;
    public List<String> photoUrls;
    public List<Tag> tags;
    public String status;

}
