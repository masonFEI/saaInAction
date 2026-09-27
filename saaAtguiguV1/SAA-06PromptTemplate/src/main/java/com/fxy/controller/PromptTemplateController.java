/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * PromptTemplateController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-09-27 22:33
 */
@RestController
public class PromptTemplateController {

    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;

    @Resource(name = "qwen")
    private ChatModel qwenChatModel;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;


    /**
     *
     *
     * * 使用PromptTemplate动态构建提示词
     * <p>
     * 通过占位符{topic}、{output_format}、{wordCount}动态插入内容
     * <p>
     * <p>
     * 测试地址：http://localhost:8006/prompttemplate/chat?topic=宇航员&output_format=JSON&wordCount=200
     *
     * @param topic         故事主题
     * @param output_format 输出格式
     * @param wordCount     字数要求
     * @return
     */
    @GetMapping("/prompttemplate/chat")
    public Flux<String> chat(String topic, String output_format, String wordCount) {
        PromptTemplate promptTemplate = new PromptTemplate("" +
                "讲一个关于{topic}的故事" +
                "并以{output_format}格式输出，" +
                "字数在{wordCount}左右");

        // PromptTemplate -> Prompt
        Prompt prompt = promptTemplate.create(Map.of("topic", topic, "output_format", output_format, "wordCount", wordCount));
        return deepseekChatClient.prompt(prompt).stream().content();
    }


}
