package com.qrlogi.domain.document.service;

import com.qrlogi.domain.orderitem.entity.OrderItemSerial;
import com.qrlogi.domain.orderitem.service.QrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
/*
    S3에 저장된 이미지를 불러와서 인쇄용 문서로 생성한다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LabelPrintService {

    private final QrService qrService;

    public ByteArrayOutputStream generateStickerPDF(List<OrderItemSerial> serials) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PDDocument pdDocument = new PDDocument()){

            PDPage pdPage = new PDPage(PDRectangle.A4);
            pdDocument.addPage(pdPage);

            PDPageContentStream pdContentStream = new PDPageContentStream(pdDocument, pdPage);
//            PDFont font = PDType1Font.HELVETICA_BOLD; //에러남? 한글미지원폰트에러
            InputStream fontStream = getClass().getResourceAsStream("/fonts/NanumGothicBold.ttf");
            PDFont font = PDType0Font.load(pdDocument, fontStream);

            int x = 50, y = 750;

            for(OrderItemSerial se : serials) {
                BufferedImage qrImg = qrService.getQrImageFromS3(se);
                BufferedImage barcodeImg = qrService.getBarcodeImageFromS3(se);

                PDImageXObject qrX = LosslessFactory.createFromImage(pdDocument, qrImg);
                PDImageXObject barX = LosslessFactory.createFromImage(pdDocument, barcodeImg);

                pdContentStream.drawImage(qrX, x, y, 100, 100);
                pdContentStream.drawImage(barX, x + 120, y + 40, 200, 60);

                pdContentStream.beginText();
                pdContentStream.setFont(font, 10);
                pdContentStream.newLineAtOffset(x, y - 15);
                pdContentStream.showText("Product : " + se.getOrderItem().getProductName());
                pdContentStream.newLineAtOffset(0, -12);
                pdContentStream.showText("Serial : " + se.getSerial());
                pdContentStream.endText();

                y -= 140;

                if (y < 100) {
                    pdContentStream.close();
                    pdPage = new PDPage(PDRectangle.A4);
                    pdDocument.addPage(pdPage);
                    pdContentStream = new PDPageContentStream(pdDocument, pdPage);
                    y = 750;
                }

            }

            pdContentStream.close();
            pdDocument.save(outputStream);




        } catch (IOException e) {
            log.error("qr(barcode)인쇄용 문서 생성 중 오류 발생, {}", e.getMessage());
            throw new RuntimeException(e);

        } catch (Exception e) {
            log.error("PDF 생성 중 예외 발생: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return outputStream;
    }




}
