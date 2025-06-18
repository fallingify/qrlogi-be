package com.qrlogi.domain.product.entity;

import com.qrlogi.domain.product.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "model_code", nullable = false, unique = true)
    private String modelCode;

    @Column(name = "unit_price", nullable = false)
    private Long unitPrice;

    @Column(nullable = false)
    private String category;


    //수정
    public void restoreProduct(ProductDTO productDTO) {
        if(productDTO.getName() != null) this.name = productDTO.getName();
        if(productDTO.getModelCode() != null) this.modelCode = productDTO.getModelCode();
        if(productDTO.getUnitPrice() != null && productDTO.getUnitPrice() >= 0)
            this.unitPrice = productDTO.getUnitPrice();
        if(productDTO.getCategory() != null) this.category = productDTO.getCategory();

    }

}
