
package pojo.schema.store.order;

import javax.annotation.processing.Generated;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Generated("jsonschema2pojo")
public class OrderSchema {

    public Long id;
    public Long petId;
    public Long quantity;
    public String shipDate;
    public String status;
    public Boolean complete;

}
