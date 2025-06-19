package com.qrlogi.domain.product.service;

import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.product.entity.ProductSerial;
import com.qrlogi.domain.product.repository.ProductSerialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSerialService {

    private final ProductSerialRepository productSerialRepository;

    public void generateSerials4OrderItem(OrderItem orderItem) {
        int qty = orderItem.getOrderedQty();

        List<ProductSerial> serials = new ArrayList<>();

        for (int i = 1; i <= qty; i++) {
            ProductSerial serial = ProductSerial.builder()
                    .orderItem(orderItem)
                    .serial(generateSerial(orderItem, i))
                    .isScanned(false)
                    .build();
            serials.add(serial);
        }

    }

    //[Prefix]-[주문번호]-[제품ID]-[시퀀스번호]
    private String generateSerial(OrderItem orderItem, int i) {

    }


}
