package com.qrlogi.domain.product.validator;

import com.qrlogi.domain.product.entity.Product;
import com.qrlogi.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;

    public Product validateProductExists(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

    }
}
