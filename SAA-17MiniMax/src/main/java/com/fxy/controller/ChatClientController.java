/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * ChatClientController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-09 14:39
 */
@RestController
public class ChatClientController {

    @Resource
    private ChatClient minimaxChatClient;

    /**
     * minimax 流式响应
     *
     * 测试url:http://localhost:8017/minimax/chat?msg=你是谁
     *
     * @param msg
     * @return
     */
    @GetMapping(value = "/minimax/chat")
    public Flux<String> chat(String msg) {
        return minimaxChatClient.prompt().user(msg).stream().content();
    }

}
