/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * ChatHelloController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-09-14 20:21
 */
@RestController
public class ChatHelloController {

    @Resource(name = "ollamaChatModel")
    private ChatModel chatModel;

    /**
     * 通用调用
     *
     * @param msg
     * @return
     */
    @GetMapping(value = "/ollama/chat")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        String result = chatModel.call(msg);
        return result;
    }

    /**
     * 流式返回调用
     *
     * @param msg
     * @return
     */
    @GetMapping(value = "/ollama/streamchat")
    public Flux<String> stream(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        Flux<String> result = chatModel.stream(msg);
        return result;
    }

}
