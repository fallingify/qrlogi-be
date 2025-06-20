package com.qrlogi.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.qrlogi")
@EntityScan("com.qrlogi.domain")
@EnableJpaRepositories("com.qrlogi.domain")
@EnableJpaAuditing
public class QrLogiApplication {
    public static void main(String[] args) {
        SpringApplication.run(QrLogiApplication.class, args);
    }
}