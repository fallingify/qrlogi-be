package com.qrlogi.api;

import com.qrlogi.api.config.GoogleSheetConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import({KafkaProducerMockConfig.class})
@ActiveProfiles("test")
@ImportAutoConfiguration(exclude = GoogleSheetConfig.class)
class QrLogiApplicationTests {

    @Test
    void contextLoads() {  }

}
