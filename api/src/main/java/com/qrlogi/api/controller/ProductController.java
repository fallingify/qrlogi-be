package com.qrlogi.api.controller;

import com.qrlogi.domain.product.dto.ProductDTO;
import com.qrlogi.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping
    public ResponseEntity<ProductDTO> registerProduct(@RequestBody ProductDTO productDTO) {

        return ResponseEntity.ok(productService.registerProduct(productDTO));
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId) {

        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {

        return ResponseEntity.ok(productService.getAllProducts());
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<Void>  deleteProduct(@PathVariable Long productId) {

        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long productId, @RequestBody ProductDTO productDTO) {

       return ResponseEntity.ok(productService.updateProduct(productId, productDTO));

    }







}
