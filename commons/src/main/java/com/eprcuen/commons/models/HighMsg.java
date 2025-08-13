package com.eprcuen.commons.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * HighMsg class represents a message with high priority.
 * It contains the email and username of the user.
 * This class is used for sending high-priority messages in the system.
 *
 * @author caito
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class HighMsg implements Serializable {
    private String email;
    private String username;
    private String validationToken;
}
