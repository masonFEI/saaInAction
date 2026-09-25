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

/**
 * ChatClientControllerV2
 *
 * ChatModel与 ChatClient
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-09-22 22:19
 */
@RestController
public class ChatClientControllerV2 {

    @Resource
    private ChatModel chatModel;

    @Resource
    private ChatClient dashscopeChatClientV2;

    /**
     * 通用调用
     *
     * @param msg
     * @return
     */
    @GetMapping(value = "/chatclientV2/dochat")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        String result = chatModel.call(msg);
        return result;
    }

    /**
     * 调用chatClient
     *
     * @param msg
     * @return
     */
    @GetMapping(value = "/chatclientV2/chatClient")
    public String doChatWithChatClient(@RequestParam(name = "msg", defaultValue = "2+9等于几") String msg) {
        return dashscopeChatClientV2.prompt().user(msg).call().content();
    }


}
