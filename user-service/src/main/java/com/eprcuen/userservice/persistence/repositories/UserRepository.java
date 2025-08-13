package com.eprcuen.userservice.persistence.repositories;

import com.eprcuen.userservice.persistence.entities.UserApp;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * UserRepository interface for managing UserApp entities.
 * Extends ReactiveCrudRepository for reactive data access.
 * Provides methods to find users by username and email, and check if an email exists.
 *
 * @author caito
 *
 */
public interface UserRepository extends ReactiveCrudRepository<UserApp, Long> {
    Mono<UserApp> findByUsername(String username);
    Mono<UserApp> findByEmail(String email);
    Mono<Boolean> existsByEmail(String email);
}
