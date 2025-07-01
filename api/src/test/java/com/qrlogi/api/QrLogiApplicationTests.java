package com.qrlogi.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@Import({KafkaProducerMockConfig.class})
@ActiveProfiles("test")
class QrLogiApplicationTests {

    @Test
    void contextLoads() {  }

}
