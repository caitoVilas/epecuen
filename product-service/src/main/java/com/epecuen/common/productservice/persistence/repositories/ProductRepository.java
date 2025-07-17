package com.epecuen.common.productservice.persistence.repositories;

import com.epecuen.common.enums.Category;
import com.epecuen.common.productservice.persistence.entities.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ProductRepository interface extends ReactiveCrudRepository to provide reactive CRUD operations for Product entities.
 * It allows for non-blocking database interactions with Product data.
 *
 * @author Caito
 *
 */
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findAllByCategory(Category category);
    Flux<Product> findAllByNameContainingIgnoreCase(String name);
}
