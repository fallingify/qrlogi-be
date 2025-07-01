package com.qrlogi.api.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;


@Configuration
@Profile("!test")
public class RedissonConfig {

    @Value("${spring.redis.config:classpath:redisson.yml}")
    private Resource redissonConfigFile;

    @Bean
    public RedissonClient redissonClient() throws IOException {
        Config config = Config.fromYAML(redissonConfigFile.getInputStream());
        return Redisson.create(config);
    }

}
