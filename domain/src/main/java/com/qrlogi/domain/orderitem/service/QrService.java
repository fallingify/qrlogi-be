package com.qrlogi.domain.orderitem.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service
@RequiredArgsConstructor
public class QrService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    //serialId로 qr생성
    //img로 변환
    //s3저장
    //url을 반환(접속용)
    //TODO : Barcode도 추가
    public String createQrUrl(String serialId) {

        ByteArrayOutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;

        try {
            BufferedImage qrImg = createQrImg(serialId); //qr 생성

            outputStream = new ByteArrayOutputStream();
            ImageIO.write(qrImg, "png", outputStream);
            byte[] imgBytes = outputStream.toByteArray();
            inputStream = new ByteArrayInputStream(imgBytes);

            String fileName = "qr/" + serialId + ".png";
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imgBytes.length);
            metadata.setContentType("image/png");

            amazonS3.putObject(
                    new PutObjectRequest(bucketName, fileName, inputStream, metadata)
            );

            return amazonS3.getUrl(bucketName, fileName).toString();


        } catch (IOException | WriterException e) {
            throw new RuntimeException("QR 이미지 생성 실패", e);

        } finally {
            try { if (inputStream != null) inputStream.close();  } catch (IOException ignored) {}
            try { if (outputStream != null) outputStream.close();  } catch (IOException ignored) {}
        }


    }

    public String createBarcodeUrl(String serialId) {

        ByteArrayOutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;

        try {
            BufferedImage qrImg = createBarcodeImg(serialId); //qr 생성

            outputStream = new ByteArrayOutputStream();
            ImageIO.write(qrImg, "png", outputStream);
            byte[] imgBytes = outputStream.toByteArray();
            inputStream = new ByteArrayInputStream(imgBytes);

            String fileName = "barcode/" + serialId + ".png";
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imgBytes.length);
            metadata.setContentType("image/png");

            amazonS3.putObject(
                    new PutObjectRequest(bucketName, fileName, inputStream, metadata)
            );

            return amazonS3.getUrl(bucketName, fileName).toString();


        } catch (IOException | WriterException e) {
            throw new RuntimeException("Barcode 이미지 생성 실패", e);

        } finally {
            try { if (inputStream != null) inputStream.close();  } catch (IOException ignored) {}
            try { if (outputStream != null) outputStream.close();  } catch (IOException ignored) {}
        }


    }

    //QRCode Image 생성
    private BufferedImage createQrImg(String serialId) throws WriterException {
        BitMatrix matrix = new QRCodeWriter().encode(serialId, BarcodeFormat.QR_CODE, 150, 150);
        return MatrixToImageWriter.toBufferedImage(matrix);

    }

    //Barcode Image 생성
    public BufferedImage createBarcodeImg(String serialId) throws WriterException {
        BitMatrix matrix = new Code128Writer().encode(serialId, BarcodeFormat.CODE_128, 400, 150);
        return MatrixToImageWriter.toBufferedImage(matrix);

    }


}
