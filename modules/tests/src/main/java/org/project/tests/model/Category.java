package org.project.tests.model;

import lombok.Builder;
import lombok.Data;

/**
 *
 */
@Data
@Builder
public class Category {
    private String name;
    private int id;
}
