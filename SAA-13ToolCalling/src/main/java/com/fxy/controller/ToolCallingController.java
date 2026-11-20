/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import com.fxy.utils.DateTimeTools;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * ToolCallingController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-05 22:41
 */
@RestController
public class ToolCallingController {

    @Resource
    private ChatModel chatModel;

    /**
     * 测试url：http://localhost:8013/toolcall/chat?msg=你是谁现在几点了
     *
     * @param msg
     * @return
     */
    @GetMapping("toolcall/chat")
    public String chat(String msg) {
        // 1.工具注册到工具集合里
        ToolCallback[] tools = ToolCallbacks.from(new DateTimeTools());

        // 2. 将工具集配置进ChatOptions对象
        ToolCallingChatOptions options = ToolCallingChatOptions.builder().toolCallbacks(tools).build();

        // 3.构建提示词
        Prompt prompt = new Prompt(msg, options);

        // 4.调用大模型
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

    @Resource
    private ChatClient chatClient;

    /**
     * 测试url：http://localhost:8013/toolcall/chat2?msg=你是谁现在几点了
     *
     * @param msg
     * @return
     */
    @GetMapping("toolcall/chat2")
    public Flux<String> chat2(String msg) {
        return chatClient.prompt(msg).tools(new DateTimeTools()).stream().content();
    }

}
