package com.qrlogi.domain.product.service;

import com.qrlogi.domain.product.dto.ProductDTO;
import com.qrlogi.domain.product.entity.Product;
import com.qrlogi.domain.product.repository.ProductRepository;
import com.qrlogi.domain.product.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    @Transactional
    public ProductDTO registerProduct(ProductDTO productDTO) {

        Product newOne = Product.builder()
                .name(productDTO.getName())
                .modelCode(productDTO.getModelCode())
                .unitPrice(productDTO.getUnitPrice())
                .category(productDTO.getCategory())
                .build();

        return ProductDTO.toDTO(productRepository.save(newOne));
    }

    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {

        Product product = productValidator.validateProductExists(productId);
        product.restoreProduct(productDTO);

        return ProductDTO.toDTO(productRepository.save(product));

    }


    public void deleteProduct(Long productId) {

        productRepository.deleteById(productId);
    }


    public List<ProductDTO> getAllProducts() {

        return productRepository.findAll().stream()
                .map(ProductDTO::toDTO)
                .toList();
    }


    public ProductDTO getProductById(Long productId) {

        return ProductDTO.toDTO(productValidator.validateProductExists(productId));
    }
}
