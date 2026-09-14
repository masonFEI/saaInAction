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

/**
 * ChatHelloController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-09-14 20:21
 */
@RestController
public class ChatHelloController {

    @Resource //接口模型，调用阿里云百炼平台
    private ChatModel chatModel;

    @GetMapping(value="/hello/dochat")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {
        String result = chatModel.call(msg);
        return result;
    }

}
