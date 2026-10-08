/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

/**
 * ChatMemory4RedisController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-02 21:50
 */
@RestController
public class ChatMemory4RedisController {

    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    /**
     * 测试url：http://localhost:8008/chatmemory/chat?msg=2+5等于多少&userId=110110
     *
     * @param msg
     * @param userId
     * @return
     */
    @GetMapping("chatmemory/chat")
    public String chat(String msg, String userId) {

        return qwenChatClient.prompt(msg).advisors(new Consumer<ChatClient.AdvisorSpec>() {
            @Override
            public void accept(ChatClient.AdvisorSpec advisorSpec) {
                advisorSpec.param(CONVERSATION_ID, userId);
            }
        }).call().content();
    }

    /**
     * 测试url：http://localhost:8008/chatmemory/chat2?msg=2+5等于多少&userId=110
     *
     * @param msg
     * @param userId
     * @return
     */
    @GetMapping("chatmemory/chat2")
    public String chat2(String msg, String userId) {

        return deepseekChatClient.prompt(msg).advisors(new Consumer<ChatClient.AdvisorSpec>() {
            @Override
            public void accept(ChatClient.AdvisorSpec advisorSpec) {
                advisorSpec.param(CONVERSATION_ID, userId);
            }
        }).call().content();
    }

}
