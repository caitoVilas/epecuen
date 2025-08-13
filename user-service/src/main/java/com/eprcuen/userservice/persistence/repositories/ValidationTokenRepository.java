package com.eprcuen.userservice.persistence.repositories;

import com.eprcuen.userservice.persistence.entities.ValidationToken;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Repository interface for managing ValidationToken entities.
 * It extends ReactiveCrudRepository to provide reactive data access methods.
 *
 * @author caito
 *
 */
public interface ValidationTokenRepository extends ReactiveCrudRepository<ValidationToken, Long> {
}
