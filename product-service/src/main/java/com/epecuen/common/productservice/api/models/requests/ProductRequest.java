package com.epecuen.common.productservice.api.models.requests;

import com.epecuen.common.enums.Category;
import com.epecuen.common.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ProductRequest class represents the structure of a product request in the API.
 * It includes fields for product details such as name, description, category,
 * subCategory, imageUrl, packageType, content, price, currency, stock, and supplierId.
 *
 * @author Caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class ProductRequest implements Serializable {
    private String name;
    private String description;
    private String category;
    private String subCategory;
    private String imageUrl;
    private String packageType;
    private String content;
    private Double price;
    private String currency;
    private Integer stock;
    private Long supplierId;
}
