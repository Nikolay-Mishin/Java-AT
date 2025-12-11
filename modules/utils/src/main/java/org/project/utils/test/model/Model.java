
package org.project.utils.test.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Model {
    private Long petId;
    private int quantity;
    private int id;
    private String shipDate;
    private boolean complete;
    private String status;

    private List<String> photoUrls;
    private String name;
    private Category category;
    private List<TagsItem> tags;
}
