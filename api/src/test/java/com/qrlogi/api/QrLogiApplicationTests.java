package com.qrlogi.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
class QrLogiApplicationTests {

    @TestConfiguration
    static class RedissonTestConfig {
        @Bean
        @Primary
        public RedissonClient redissonFakeClientForTest() {
            return Mockito.mock(RedissonClient.class); //에러난부분 임시 처리 -> fake Redisson
        }
    }


    @Test
    void contextLoads() {

    }

}
