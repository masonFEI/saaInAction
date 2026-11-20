/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SaaLLMConfig
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-09-13 23:19
 */
@Configuration
public class SaaLLMConfig {

    @Bean
    public ChatClient minimaxChatClient(ChatModel minimaxChatModel) {
        return ChatClient.builder(minimaxChatModel).build();
    }

}
