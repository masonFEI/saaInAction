/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * McpClientController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-06 19:56
 */
@RestController
public class McpClientController {


    @Resource
    private ChatClient chatClient;// 使用了mcp支持

    @Resource
    private ChatModel chatModel;// 没有纳入tool支持，普通调用


    /**
     * http://localhost:8015/mcpclient/chat?msg=上海
     *
     * @param msg
     * @return
     */
    @GetMapping("/mcpclient/chat")
    public Flux<String> chat(String msg) {
        System.out.println("使用了mcp");
        return chatClient.prompt(msg)
                .stream()
                .content();
    }

    /**
     * http://localhost:8015/mcpclient/chat?msg=上海
     *
     * @param msg
     * @return
     */
    @GetMapping("/mcpclient/chat2")
    public Flux<String> chat2(String msg) {
        System.out.println("未使用mcp");

        return chatModel.stream(msg);
    }


}
