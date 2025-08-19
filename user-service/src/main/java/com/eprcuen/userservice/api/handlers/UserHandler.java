package com.eprcuen.userservice.api.handlers;

import com.eprcuen.userservice.api.models.requests.UserRequest;
import com.eprcuen.userservice.services.contracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * UserHandler class handles incoming requests related to users.
 * It provides methods to retrieve all users and create a new user.
 *
 * @author eprcuen
 *
 */
@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserService userService;

    /**
     * Handles the request to retrieve all users.
     *
     * @param request the server request
     * @return a Mono containing the server response with the list of users
     */
    public Mono<ServerResponse> getAll(ServerRequest request) {
        return userService.getAllUsers()
                .collectList()
                .flatMap(users -> ServerResponse.ok().bodyValue(users))
                .switchIfEmpty(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse.noContent().build());
    }

    /**
     * Handles the request to create a new user.
     *
     * @param request the server request containing the user details
     * @return a Mono containing the server response with the created user
     */
    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(UserRequest.class)
                .flatMap(userService::createUser)
                .flatMap(userResponse -> ServerResponse.ok().bodyValue(userResponse))
                .switchIfEmpty(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error creating User: "));
    }

}
