package com.epecuen.common.productservice.api.handlers;

import com.epecuen.common.productservice.api.models.requests.ProductRequest;
import com.epecuen.common.productservice.services.contracts.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * ProductHandler class handles incoming requests related to products.
 * It provides methods to retrieve all products, create a new product,
 * get products by category, get a product by ID, get products by name,
 * and change the status of a product.
 *
 * @author Caito
 *
 */
@Component
@RequiredArgsConstructor
public class ProductHandler {
    private final ProductService productService;

    /**
     * Handles the request to retrieve all products.
     *
     * @param request the server request
     * @return a Mono containing the server response with the list of products
     */
    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return productService.getAllProducts()
                .collectList()
                .flatMap(products -> ServerResponse.ok().bodyValue(products))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e -> ServerResponse.notFound().build());
    }

    /**
     * Handles the request to create a new product.
     *
     * @param request the server request containing the product details
     * @return a Mono containing the server response with the created product
     */
    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(ProductRequest.class)
                .flatMap(productService::createProduct)
                .flatMap(productResponse -> ServerResponse.ok().bodyValue(productResponse))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error creating product: " + e.getMessage()));
    }

    /**
     * Handles the request to retrieve products by category.
     *
     * @param request the server request containing the category
     * @return a Mono containing the server response with the list of products in the specified category
     */
    public Mono<ServerResponse> getByCategory(ServerRequest request) {
        String category = request.pathVariable("category");
        return productService.getByCategory(category)
                .collectList()
                .flatMap(products -> ServerResponse.ok().bodyValue(products))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e -> ServerResponse.notFound().build());
    }

    /**
     * Handles the request to retrieve a product by its ID.
     *
     * @param request the server request containing the product ID
     * @return a Mono containing the server response with the product details
     */
    public Mono<ServerResponse> productById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productService.productById(id)
                .flatMap(productResponse -> ServerResponse.ok().bodyValue(productResponse))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e -> ServerResponse.notFound().build());
    }

    /**
     * Handles the request to retrieve products by name.
     *
     * @param request the server request containing the product name
     * @return a Mono containing the server response with the list of products matching the name
     */
    public Mono<ServerResponse> getByName(ServerRequest request) {
        String name = request.pathVariable("name");
        return productService.getByName(name)
                .collectList()
                .flatMap(productResponse -> ServerResponse.ok().bodyValue(productResponse))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e -> ServerResponse.notFound().build());
    }

    /**
     * Handles the request to change the status of a product.
     *
     * @param request the server request containing the product ID
     * @return a Mono containing the server response indicating the status change
     */
    public Mono<ServerResponse> changeStatus(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productService.changeStatus(id)
                .then(ServerResponse.ok().build())
                .onErrorResume(e -> ServerResponse.notFound().build());
    }

    /**
     * Handles the request to delete a product.
     *
     * @param request the server request containing the product ID
     * @return a Mono containing the server response indicating the deletion
     */
    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productService.deleteProduct(id)
                .then(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse.notFound().build());
    }

    /**
     * Handles the request to update the stock of a product.
     *
     * @param request the server request containing the product ID and new stock value
     * @return a Mono containing the server response with the updated product details
     */
    public Mono<ServerResponse> updateStock(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Integer stock = Integer.valueOf(request.pathVariable("stock"));
        return productService.updateStock(id, stock)
                .flatMap(productResponse -> ServerResponse.ok().bodyValue(productResponse))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e -> ServerResponse.notFound().build());
    }
}
