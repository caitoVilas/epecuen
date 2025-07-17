package com.epecuen.common.productservice.services.contracts;

import com.epecuen.common.productservice.api.models.requests.ProductRequest;
import com.epecuen.common.productservice.api.models.responses.ProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ProductService interface defines the contract for product-related operations.
 * It provides a method to retrieve all products in a reactive manner.
 *
 * @author Caito
 *
 */
public interface ProductService {
    Flux<ProductResponse> getAllProducts();
    Mono<ProductResponse> createProduct(ProductRequest request);
    Flux<ProductResponse> getByCategory(String category);
    Mono<ProductResponse> productById(Long id);
    Flux<ProductResponse> getByName(String name);
    Mono<Void> changeStatus(Long id);
    Mono<Void> deleteProduct(Long id);
    Mono<ProductResponse> updateStock(Long id, Integer stock);
}
