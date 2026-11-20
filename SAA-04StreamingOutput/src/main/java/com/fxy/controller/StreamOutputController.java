/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * StreamOutputController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-09-25 13:44
 */
@RestController
public class StreamOutputController {

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
     * @param msg
     * @return
     */
    @GetMapping(value = "/stream/chatflux1")
    public Flux<String> chatflux1(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        Flux<String> result = deepseekChatModel.stream(msg);
        return result;
    }

    /**
     * 流式返回调用
     *
     * @param msg
     * @return
     */
    @GetMapping(value = "/stream/chatflux2")
    public Flux<String> chatflux2(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        Flux<String> result = qwenChatModel.stream(msg);
        return result;
    }


    /**
     * 流式返回调用
     *
     * @param msg
     * @return
     */
    @GetMapping(value = "/stream/chatflux3")
    public Flux<String> chatflux3(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return deepseekChatClient.prompt(msg).stream().content();
    }

    /**
     * 流式返回调用
     *
     * @param msg
     * @return
     */
    @GetMapping(value = "/stream/chatflux4", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatflux4(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        return qwenChatClient.prompt(msg).stream().content();
    }


}
