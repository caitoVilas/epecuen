package com.epecuen.common.productservice.api.models.responses;

import com.epecuen.common.enums.Category;
import com.epecuen.common.enums.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ProductResponse class represents the structure of a product response in the API.
 * It includes fields for product details such as id, name, description, category,
 * subCategory, imageUrl, packageType, content, price, currency, stock, supplierId,
 * and active status.
 *
 *  @author Caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class ProductResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String category;
    private String subCategory;
    private String imageUrl;
    private String packageType;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "########.##")
    private Double price;
    private String currency;
    private Integer stock;
    private Long supplierId;
    private Boolean active;
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
