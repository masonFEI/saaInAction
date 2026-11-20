/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * NoToolCallingController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-05 22:27
 */
@RestController
public class NoToolCallingController {

    @Resource
    private ChatModel chatModel;


    /**
     * 测试url：http://localhost:8013/notoolcall/chat?msg=你是谁现在几点了
     *
     * @param msg
     * @return
     */
    @GetMapping("/notoolcall/chat")
    public Flux<String> chat(String msg) {
        return chatModel.stream(msg);
    }

}
