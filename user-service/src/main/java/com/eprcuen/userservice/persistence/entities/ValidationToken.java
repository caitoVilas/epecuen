package com.eprcuen.userservice.persistence.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * ValidationToken represents a token used for email validation in the user service.
 * It contains fields for the token value, expiry date, and associated email.
 * This entity is mapped to the "validation_tokens" table in the database.
 * The token is used to verify the user's email address during the registration process.
 *
 * @author caito
 *
 */
@Table(name = "validation_tokens")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class ValidationToken {
    @Id
    private Long id;
    private String token;
    private String email;
    private LocalDateTime expiryDate;
}
