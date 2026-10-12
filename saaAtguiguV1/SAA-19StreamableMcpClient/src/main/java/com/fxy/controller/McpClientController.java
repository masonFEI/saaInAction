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
 * McpClientController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-12 10:55
 */
@RestController
public class McpClientController {

    @Resource
    private ChatClient chatClient;

    /**
     * http://localhost:8019/streamablemcpclient/chat?msg=深圳
     * 
     * @param msg
     * @return
     */
    @GetMapping("/streamablemcpclient/chat")
    public Flux<String> chat(String msg) {
        return chatClient.prompt(msg).stream().content();
    }

}
