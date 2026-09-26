/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * PromptController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-09-26 22:42
 */
@RestController
public class PromptController {

    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;

    @Resource(name = "qwen")
    private ChatModel qwenChatModel;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;

    /**
     * 流式返回调用
     *
     * @param question
     * @return
     */
    @GetMapping(value = "/prompt/chat")
    public Flux<String> chat(@RequestParam(value = "question", defaultValue = "你好") String question) {
        return deepseekChatClient.prompt()
                .system("你是一个法律助手，只回答法律问题，其他问题回复，我只能回答法律相关问题，其他无可奉告")
                .user(question)
                .stream()
                .content();
    }

}
