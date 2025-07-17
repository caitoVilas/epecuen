package com.epecuen.common.productservice.utils.mappers;

import com.epecuen.common.enums.Category;
import com.epecuen.common.enums.Currency;
import com.epecuen.common.productservice.api.models.requests.ProductRequest;
import com.epecuen.common.productservice.api.models.responses.ProductResponse;
import com.epecuen.common.productservice.persistence.entities.Product;

import java.time.LocalDateTime;

/**
 * ProductMapper class provides utility methods to map between ProductRequest, ProductResponse, and Product entity.
 * It is used to convert API request and response models to the persistence entity and vice versa.
 *
 * @author Caito
 *
 */
public class ProductMapper {

    /**
     * Maps a ProductRequest object to a Product entity.
     *
     * @param request the ProductRequest object to map
     * @return a Product entity populated with data from the request
     */
    public static Product mapToEntity(ProductRequest request){
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(Category.valueOf(request.getCategory()))
                .subCategory(request.getSubCategory())
                .imageUrl(request.getImageUrl())
                .packageType(request.getPackageType())
                .content(request.getContent())
                .price(request.getPrice())
                .currency(Currency.valueOf(request.getCurrency()))
                .active(true)
                .stock(request.getStock())
                .supplierId(request.getSupplierId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Maps a Product entity to a ProductResponse object.
     *
     * @param product the Product entity to map
     * @return a ProductResponse object populated with data from the product entity
     */
    public static ProductResponse mapTODto(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory().name())
                .subCategory(product.getSubCategory())
                .imageUrl(product.getImageUrl())
                .packageType(product.getPackageType())
                .content(product.getContent())
                .price(product.getPrice())
                .currency(product.getCurrency().name())
                .active(product.getActive())
                .stock(product.getStock())
                .supplierId(product.getSupplierId())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
