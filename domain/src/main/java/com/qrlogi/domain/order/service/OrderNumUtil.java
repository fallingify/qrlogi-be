package com.qrlogi.domain.order.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class OrderNumUtil {

    private static final SecureRandom random = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    static String getOrderNum() {
        byte[] randomBytes = new byte[9];
        random.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
