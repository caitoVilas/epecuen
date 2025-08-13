package com.eprcuen.userservice.api.routers;

import com.eprcuen.userservice.api.handlers.UserHandler;
import com.eprcuen.userservice.api.models.requests.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * UserRouter class defines the
 * API endpoints for user-related operations.
 * It uses Spring WebFlux's functional programming model
 * to define routes and handlers for
 * managing users.
 *
 * @author caito
 *
 */
@Configuration
public class UserRouter {
    private static final String PATH = "/epecuen/api/v1/users";

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = PATH,
                    beanClass = UserHandler.class,
                    beanMethod = "getAll",
                    produces = {"application/json"},
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getAll",
                            summary = "Get all suppliers",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Suppliers retrieved successfully"),
                                    @ApiResponse(responseCode = "404", description = "No Suppliers found")
                            }
                    )
            ),
            @RouterOperation(
                    path = PATH,
                    beanClass = UserHandler.class,
                    beanMethod = "create",
                    produces = {"application/json"},
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "create",
                            summary = "Create a new supplier",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "User created successfully"),
                                    @ApiResponse(responseCode = "400", description = "Invalid request data")
                            },
                            requestBody = @RequestBody(content = @Content(
                                    schema = @Schema(implementation = UserRequest.class)
                            ))
                    )
            ),
    })
    RouterFunction<ServerResponse> router(UserHandler handler){
    return RouterFunctions.route()
            .POST(PATH, handler::create)
            .GET(PATH, handler::getAll)
            .build();
    }

}
