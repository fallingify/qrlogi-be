package com.qrlogi.api.controller;

import com.qrlogi.domain.document.service.LabelPrintService;
import com.qrlogi.domain.order.entity.Orders;
import com.qrlogi.domain.order.validator.OrderValidator;
import com.qrlogi.domain.orderitem.entity.OrderItemSerial;
import com.qrlogi.domain.orderitem.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/labels")
@RequiredArgsConstructor
public class LabelPrintController {

    private final LabelPrintService labelPrintService;
    private final OrderValidator orderValidator;
    private final OrderItemService orderItemService;


    @GetMapping(value = "/download/{orderNum}", produces = "application/pdf")
    public ResponseEntity<byte[]> downloadLabelFile(@PathVariable("orderNum") String orderNum){

        Orders order = orderValidator.validateByOrderNumber(orderNum);
        List<OrderItemSerial> serials = orderItemService.getSerialsByOrderNum(order);
        ByteArrayOutputStream outputStream = labelPrintService.generateStickerPDF(serials);


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"labels-" + orderNum + ".pdf\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(outputStream.size()))
                .body(outputStream.toByteArray());

    }



}
