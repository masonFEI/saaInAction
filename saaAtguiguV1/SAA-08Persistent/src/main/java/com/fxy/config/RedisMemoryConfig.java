/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.config;

import com.alibaba.cloud.ai.memory.redis.RedisChatMemoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RedisMemoryConfig
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-02 11:38
 */
@Configuration
public class RedisMemoryConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.password}")
    private String password;

    @Bean
    public RedisChatMemoryRepository redisChatMemoryRepository() {
        RedisChatMemoryRepository repository = RedisChatMemoryRepository.builder()
                .host(host)
                .port(port)
                .password(password)
                .build();
        return repository;
    }


}
