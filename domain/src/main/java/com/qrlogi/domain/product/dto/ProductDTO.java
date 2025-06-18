package com.qrlogi.domain.product.dto;

import com.qrlogi.domain.product.entity.Product;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String modelCode;
    private Long unitPrice;
    private String category;

    public static ProductDTO toDTO(@Valid Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getModelCode(),
                product.getUnitPrice(),
                product.getCategory()
        );

    }
}
