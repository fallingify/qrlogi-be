package com.qrlogi.domain.document.entity;

import com.qrlogi.domain.inspection.entity.ScanLog;
import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.orderitem.entity.OrderItem;
import com.qrlogi.domain.orderitem.entity.OrderItemSerial;
import com.qrlogi.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sheet_row")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SheetRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rowIndex;

    @Column(nullable = false)
    private String sheetName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_serial_id", nullable = false)
    private OrderItemSerial orderItemSerial;


    @Column(nullable = false)
    @Builder.Default
    private boolean isUploaded = false;

    public void doUpload() {
        this.isUploaded = true;
    }

    public List<Object> toRecord(ScanLog scanLog) {
        OrderItem orderItem = orderItemSerial.getOrderItem();
        Orders order = orderItem.getOrder();
        Product product = orderItem.getProduct();

        return List.of(
                order.getOrderNumber(),
                product.getName(),
                product.getModelCode(),
                orderItemSerial.getSerial(),
                "scanned",
                scanLog.getScannedAt().toString(),
                scanLog.getScannedBy()
        );
    }




}
