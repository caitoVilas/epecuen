package com.epecuen.common.productservice.persistence.entities;

import com.epecuen.common.enums.Category;
import com.epecuen.common.enums.Currency;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Product entity class representing a product in the product service.
 * It includes fields for product details such as name, description, category,
 * sub-category, image URL, package type, content, price, currency, stock,
 * supplier ID, and timestamps for creation and updates.
 *
 * @author Caito
 *
 */
@Table(name = "products")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class Product {
    @Id
    private Long id;
    private String name;
    private String description;
    private Category category;
    private String subCategory;
    private String imageUrl;
    private String packageType;
    private String content;
    private Double price;
    private Currency currency;
    private Boolean active;
    private Integer stock;
    private Long supplierId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
