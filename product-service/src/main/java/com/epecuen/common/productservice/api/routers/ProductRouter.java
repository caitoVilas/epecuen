package com.epecuen.common.productservice.api.routers;

import com.epecuen.common.productservice.api.handlers.ProductHandler;
import com.epecuen.common.productservice.api.models.requests.ProductRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
 * ProductRouter class defines the
 * API endpoints for product-related operations.
 * It uses Spring WebFlux's functional programming model
 * to define routes and handlers for
 * managing products.
 *
 * @author Caito
 *
 */
@Configuration
public class ProductRouter {
    private static final String PATH = "/epecuen/api/v1/products";

    /**
     * Defines the routes for product-related operations.
     * Each route is associated with a handler method in ProductHandler.
     *
     * @param handler the ProductHandler instance to handle requests
     * @return RouterFunction that maps requests to handlers
     */
    @Bean
    @RouterOperations({
        @RouterOperation(
            path = PATH,
            beanClass = ProductHandler.class,
            beanMethod = "getAllProducts",
            produces = {"application/json"},
            method = RequestMethod.GET,
            operation = @Operation(
                    operationId = "getAllProducts",
                    summary = "Get all products",
                    responses = {
                            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
                            @ApiResponse(responseCode = "404", description = "No products found")
                    }
            )
        ),
            @RouterOperation(
                    path = PATH,
                    beanClass = ProductHandler.class,
                    beanMethod = "createProduct",
                    produces = {"application/json"},
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "createProduct",
                            summary = "Create products",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Products created successfully"),
                                    @ApiResponse(responseCode = "400", description = "bad request")
                            },
                            requestBody = @RequestBody(content = @Content(
                                    schema = @Schema(implementation = ProductRequest.class)))
                    )
            ),
            @RouterOperation(
                    path = PATH + "/category/{category}",
                    beanClass = ProductHandler.class,
                    beanMethod = "getByCategory",
                    produces = {"application/json"},
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getByCategory",
                            summary = "Get products by category",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
                                    @ApiResponse(responseCode = "404", description = "No products found")
                            },
                            parameters = @Parameter(in = ParameterIn.PATH, name = "category",
                                    description = "Category of the products to retrieve")
                    )
            ),
            @RouterOperation(
                    path = PATH + "/id/{id}",
                    beanClass = ProductHandler.class,
                    beanMethod = "productById",
                    produces = {"application/json"},
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "productById",
                            summary = "Get products by Id",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
                                    @ApiResponse(responseCode = "404", description = "No products found")
                            },
                            parameters = @Parameter(in = ParameterIn.PATH, name = "id",
                                    description = "Id of the products to retrieve")
                    )
            ),
            @RouterOperation(
                    path = PATH + "/name/{name}",
                    beanClass = ProductHandler.class,
                    beanMethod = "getByName",
                    produces = {"application/json"},
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getByName",
                            summary = "Get products by Name",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
                                    @ApiResponse(responseCode = "404", description = "No products found")
                            },
                            parameters = @Parameter(in = ParameterIn.PATH, name = "name",
                                    description = "Category of the products to retrieve")
                    )
            ),
            @RouterOperation(
                    path = PATH + "/change-status/{id}",
                    beanClass = ProductHandler.class,
                    beanMethod = "changeStatus",
                    produces = {"application/json"},
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            operationId = "changeStatus",
                            summary = "eneable or disable a product",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Status changed successfully"),
                                    @ApiResponse(responseCode = "404", description = "No products found")
                            },
                            parameters = @Parameter(in = ParameterIn.PATH, name = "id",
                                    description = "Id of the product to change status")
                    )
            ),
            @RouterOperation(
                    path = PATH + "/delete/{id}",
                    beanClass = ProductHandler.class,
                    beanMethod = "deleteProduct",
                    produces = {"application/json"},
                    method = RequestMethod.DELETE,
                    operation = @Operation(
                            operationId = "deleteProduct",
                            summary = "Delete a product",
                            responses = {
                                    @ApiResponse(responseCode = "204", description = "No Content"),
                                    @ApiResponse(responseCode = "404", description = "No products found")
                            },
                            parameters = @Parameter(in = ParameterIn.PATH, name = "id",
                                    description = "Delete a product by Id")
                    )
            ),
            @RouterOperation(
                    path = PATH + "/update-stock/{id}/{stock}",
                    beanClass = ProductHandler.class,
                    beanMethod = "updateStock",
                    produces = {"application/json"},
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            operationId = "updateStock",
                            summary = "update the stock of a product",
                            responses = {
                                    @ApiResponse(responseCode = "20", description = "Stock updated successfully"),
                                    @ApiResponse(responseCode = "404", description = "No products found")
                            },
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id",
                                    description = "Delete a product by Id"),
                            @Parameter(in = ParameterIn.PATH, name = "stock",
                                            description = "New stock value for the product")}
                    )
            ),
    })
    RouterFunction<ServerResponse> routes(ProductHandler handler){
        return RouterFunctions.route()
                .GET(PATH, handler::getAllProducts)
                .POST(PATH, handler::createProduct)
                .GET(PATH + "/category/{category}", handler::getByCategory)
                .GET(PATH + "/id/{id}", handler::productById)
                .GET(PATH + "/name/{name}", handler::getByName)
                .PUT(PATH + "/change-status/{id}", handler::changeStatus)
                .DELETE(PATH + "/delete/{id}", handler::deleteProduct)
                .PUT(PATH + "/update-stock/{id}/{stock}", handler::updateStock)
                .build();
    }
}
