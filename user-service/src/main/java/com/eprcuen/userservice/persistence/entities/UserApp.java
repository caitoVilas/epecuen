package com.eprcuen.userservice.persistence.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * UserApp entity representing a user in the system.
 * Implements UserDetails for Spring Security integration.
 * Includes fields for user information and account status.
 * Uses Lombok annotations for boilerplate code reduction.
 *
 * @author caito
 *
 */
@Table(name = "users")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class UserApp implements UserDetails {
    @Id
    private Long id;
    private String username;
    private String email;
    private String telephone;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }
}
