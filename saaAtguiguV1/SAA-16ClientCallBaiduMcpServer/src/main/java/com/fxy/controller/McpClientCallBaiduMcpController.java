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
 * McpClientCallBaiduMcpController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-07 10:45
 */
@RestController
public class McpClientCallBaiduMcpController {


    @Resource
    private ChatClient chatClient;

    @Resource
    private ChatModel chatModel;

    /**
     * 添加了MCP调用能力
     * <p>
     * http://localhost:8016/mcp/chat?msg=查询北纬39.9042东经116.4074天气
     * http://localhost:8016/mcp/chat?msg=查询昌平到天安门路线规划
     * http://localhost:8016/mcp/chat?msg=查询61。149.121.66归属地
     *
     * @param msg
     * @return
     */
    @GetMapping("/mcp/chat")
    public Flux<String> chat(String msg) {
        return chatClient.prompt(msg)
                .stream()
                .content();
    }

}
