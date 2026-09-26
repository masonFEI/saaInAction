/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SaaLLMConfig
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-09-26 22:39
 */
@Configuration
public class SaaLLMConfig {

    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    // 模型名称常量定义，一套系统多模型共存
    private final String DEEPSEEK_MODEL = "deepseek-v3";

    private final String QWEN_MODEL = "qwen-max";

    @Bean(name = "deepseek")
    public ChatModel deepSeek() {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(apiKey).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(DEEPSEEK_MODEL).build())
                .build();
    }

    @Bean(name = "qwen")
    public ChatModel qwen() {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(apiKey).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(QWEN_MODEL).build())
                .build();
    }

    @Bean(name = "deepseekChatClient")
    public ChatClient deepseekChatClient(@Qualifier("deepseek") ChatModel deepseek) {
        return ChatClient.builder(deepseek)
                .defaultOptions(ChatOptions.builder().model(DEEPSEEK_MODEL).build())
                .build();
    }

    @Bean(name = "qwenChatClient")
    public ChatClient qwenChatClient(@Qualifier("qwen") ChatModel qwen) {
        return ChatClient.builder(qwen)
                .defaultOptions(ChatOptions.builder().model(QWEN_MODEL).build())
                .build();
    }

}
