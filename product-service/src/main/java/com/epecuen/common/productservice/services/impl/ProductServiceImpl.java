package com.epecuen.common.productservice.services.impl;

import com.epecuen.common.enums.Category;
import com.epecuen.common.exceptions.NotFoundException;
import com.epecuen.common.logs.WriteLog;
import com.epecuen.common.productservice.api.models.requests.ProductRequest;
import com.epecuen.common.productservice.api.models.responses.ProductResponse;
import com.epecuen.common.productservice.persistence.repositories.ProductRepository;
import com.epecuen.common.productservice.services.contracts.ProductService;
import com.epecuen.common.productservice.utils.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ProductServiceImpl class implements the ProductService interface.
 * It provides methods to manage products, including retrieving all products,
 * creating a new product, getting products by category, getting a product by ID,
 * getting products by name, and changing the status of a product.
 *
 * @author Caito
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    /**
     * Retrieves all products from the repository and maps them to ProductResponse DTOs.
     *
     * @return a Flux of ProductResponse containing all products
     */
    @Override
    @Transactional(readOnly = true)
    public Flux<ProductResponse> getAllProducts() {
        log.info(WriteLog.logInfo("Get All Products Service"));
        return productRepository.findAll()
                .map(ProductMapper::mapTODto)
                .doOnError(err -> log.error(err.getMessage()));

    }

    /**
     * Creates a new product in the repository and maps it to a ProductResponse DTO.
     *
     * @param request the ProductRequest containing product details
     * @return a Mono of ProductResponse containing the created product
     */
    @Override
    @Transactional
    public Mono<ProductResponse> createProduct(ProductRequest request) {
        log.info(WriteLog.logInfo("Create Product Service"));
        var product = ProductMapper.mapToEntity(request);
        return productRepository.save(product)
                .doOnSuccess(savedProduct -> log.info(WriteLog.logInfo("Product created with ID: {}"),
                        savedProduct.getId()))
                .map(ProductMapper::mapTODto);
    }

    /**
     * Retrieves products by category from the repository and maps them to ProductResponse DTOs.
     *
     * @param category the category to filter products by
     * @return a Flux of ProductResponse containing products in the specified category
     */
    @Override
    @Transactional(readOnly = true)
    public Flux<ProductResponse> getByCategory(String category) {
        log.info(WriteLog.logInfo("Get By Category Service"));
        return productRepository.findAllByCategory(Category.valueOf(category))
                .map(ProductMapper::mapTODto)
                .switchIfEmpty(Flux.defer(() -> {
                    log.error(WriteLog.logError("No products found in category: " + category));
                    return Flux.error(new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "No se encontraron productos para la categoría: " + category
                    ));
        }));

    }
    /**
     * Retrieves a product by its ID from the repository and maps it to a ProductResponse DTO.
     *
     * @param id the ID of the product to retrieve
     * @return a Mono of ProductResponse containing the product with the specified ID
     */
    @Override
    @Transactional(readOnly = true)
    public Mono<ProductResponse> productById(Long id) {
        log.info(WriteLog.logInfo("Get By Id Service"));
        return productRepository.findById(Integer.parseInt(id.toString()))
                .map(ProductMapper::mapTODto)
                .switchIfEmpty(Mono.defer(() -> {;
                    log.error(WriteLog.logError("Product not found with ID: " + id));
                    return Mono.error(new NotFoundException("Product not found with ID: " + id));
                }));
    }

    /**
     * Retrieves products by name from the repository and maps them to ProductResponse DTOs.
     *
     * @param name the name to filter products by
     * @return a Flux of ProductResponse containing products with names containing the specified string
     */
    @Override
    @Transactional(readOnly = true)
    public Flux<ProductResponse> getByName(String name) {
        log.info(WriteLog.logInfo("Get By Name Service"));
        return productRepository.findAllByNameContainingIgnoreCase(name)
                .map(ProductMapper::mapTODto)
                .switchIfEmpty(Flux.defer(() -> {
                    log.error(WriteLog.logError("No products found with name: " + name));
                    return Flux.error(new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "No se encontraron productos con el nombre: " + name
                    ));
                }));
    }

    /**
     * Changes the status of a product by its ID, toggling its active state.
     *
     * @param id the ID of the product whose status is to be changed
     * @return a Mono that completes when the status change is done
     */
    @Override
    @Transactional
    public Mono<Void> changeStatus(Long id) {
        log.info(WriteLog.logInfo("Change Status Service"));
        return productRepository.findById(Integer.parseInt(id.toString()))
                .flatMap(product -> {
                    product.setActive(!product.getActive());
                    log.info(WriteLog.logInfo("Product {} status changed: {}"), product.getId(), product.getActive());
                    return productRepository.save(product);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.error(WriteLog.logError("Product not found with ID: " + id));
                    return Mono.error(new NotFoundException("Product not found with ID: " + id));
                }))
                .then();
    }

    /**
     * Deletes a product by its ID from the repository.
     *
     * @param id the ID of the product to delete
     * @return a Mono that completes when the deletion is done
     */
    @Override
    @Transactional
    public Mono<Void> deleteProduct(Long id) {
        log.info(WriteLog.logInfo("Delete Product Service"));
        return productRepository.findById(Integer.parseInt(id.toString()))
                .flatMap(product -> {
                    log.info(WriteLog.logInfo("Deleting product with ID: {}"), product.getId());
                    return productRepository.delete(product);
                })
                .then();
    }

    /**
     * Updates the stock of a product by its ID.
     *
     * @param id the ID of the product whose stock is to be updated
     * @param stock the amount to update the stock by
     * @return a Mono of ProductResponse containing the updated product
     */
    @Override
    @Transactional
    public Mono<ProductResponse> updateStock(Long id, Integer stock) {
        log.info(WriteLog.logInfo("Update Stock Service"));
        return productRepository.findById(Integer.parseInt(id.toString()))
                .flatMap(product -> {;
                    product.setStock(product.getStock() + stock);
                    log.info(WriteLog.logInfo("Product {} stock updated to: {}"), product.getId(), stock);
                    return productRepository.save(product);
                })
                .map(ProductMapper::mapTODto)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error(WriteLog.logError("Product not found with ID: " + id));
                    return Mono.error(new NotFoundException("Product not found with ID: " + id));
                }));
    }
}
