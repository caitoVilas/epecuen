package com.eprcuen.userservice.services.contracts;

import com.eprcuen.userservice.api.models.requests.UserRequest;
import com.eprcuen.userservice.api.models.responses.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/*
 * UserService interface defining user-related operations.
 * Includes methods for creating a user and retrieving all users.
 *
 * @author eprcuen
 *
 */
public interface UserService {
    Mono<UserResponse> createUser(UserRequest request);
    Flux<List<UserResponse>> getAllUsers();
}
