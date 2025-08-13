package com.eprcuen.userservice.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * UserRequest class representing a request to create or update a user.
 * Contains fields for user information such as username, email, telephone,
 * password, and confirmPassword.
 * Uses Lombok annotations for boilerplate code reduction.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class UserRequest implements Serializable {
    private String username;
    private String email;
    private String telephone;
    private String password;
    private String confirmPassword;
}
