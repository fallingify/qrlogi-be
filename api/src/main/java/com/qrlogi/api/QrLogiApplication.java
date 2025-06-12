package com.qrlogi.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.qrlogi")
@EnableJpaRepositories(basePackages = {
        "com.qrlogi.user.repository",
        "com.qrlogi.buyer.repository"
})
@EntityScan(basePackages = {
        "com.qrlogi.user.entity",
        "com.qrlogi.buyer.entity"
})
public class QrLogiApplication {
    public static void main(String[] args) {
        SpringApplication.run(QrLogiApplication.class, args);
    }
}