package com.eprcuen.userservice.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * UserResponse class representing the response model for user information.
 * Contains fields for user details such as id, username, email, telephone, and role.
 * Uses Lombok annotations for boilerplate code reduction.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class UserResponse implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String telephone;
    private String role;

}
