
package org.project.utils.test.model;

import lombok.Builder;
import lombok.Data;

/**
 *
 */
@Data
@Builder
public class AuthModel {
    private String username;
    private String password;
}
