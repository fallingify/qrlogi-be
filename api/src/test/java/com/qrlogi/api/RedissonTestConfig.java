package com.qrlogi.api;

import org.mockito.Mockito;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class RedissonTestConfig {

    @Bean
    @Primary
    public RedissonClient redissonFakeClientForTest() {
        return Mockito.mock(RedissonClient.class);
    }

}
