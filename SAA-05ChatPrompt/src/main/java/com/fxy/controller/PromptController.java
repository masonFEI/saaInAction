/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * PromptController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-09-26 22:42
 */
@RestController
public class PromptController {

    @Resource(name = "deepseek")
    private ChatModel  deepseekChatModel;

    @Resource(name = "qwen")
    private ChatModel  qwenChatModel;

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;

    /**
     * 流式返回调用
     *
     * @param question
     * @return
     */
    @GetMapping(value = "/prompt/chat")
    public Flux<String> chat(@RequestParam(value = "question", defaultValue = "你好") String question) {
        return deepseekChatClient.prompt().system("你是一个法律助手，只回答法律问题，其他问题回复，我只能回答法律相关问题，其他无可奉告").user(question).stream().content();
    }

    /**
     * 流式返回调用
     *
     * @param question
     * @return
     */
    @GetMapping(value = "/prompt/chat2")
    public Flux<String> chat2(@RequestParam(value = "question", defaultValue = "你好") String question) {

        SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手，每个故事控制在300字以内");

        UserMessage userMessage = new UserMessage(question);

        Prompt prompt = new Prompt(userMessage, systemMessage);

        return deepseekChatModel.stream(prompt).map(r -> r.getResults().getFirst().getOutput().getText());
    }

    /**
     * 流式返回调用
     *
     * @param question
     * @return
     */
    @GetMapping(value = "/prompt/chat3")
    public Flux<String> chat3(@RequestParam(value = "question", defaultValue = "你好") String question) {
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事的助手，每个故事控制在600字以内且以HTML格式返回");

        UserMessage userMessage = new UserMessage(question);

        Prompt prompt = new Prompt(userMessage, systemMessage);

        return deepseekChatModel.stream(prompt).map(r -> r.getResults().getFirst().getOutput().getText());
    }

    /**
     * 流式返回调用
     *
     * @param question
     * @return
     */
    @GetMapping(value = "/prompt/chat4")
    public String chat4(@RequestParam(value = "question", defaultValue = "你好") String question) {
        AssistantMessage assistantMessage = qwenChatClient.prompt().user(question).call().chatResponse().getResult().getOutput();

        return assistantMessage.getText();
    }

    /**
     * 流式返回调用
     *
     * @param city
     * @return
     */
    @GetMapping(value = "/prompt/chat5")
    public String chat5(@RequestParam(value = "city", defaultValue = "你好") String city) {
        String answer = deepseekChatClient.prompt().user(city + "未来三天天气情况如何").call().chatResponse().getResult().getOutput().getText();
        ToolResponseMessage toolResponseMessage = new ToolResponseMessage(List.of(new ToolResponseMessage.ToolResponse("1", "获得天气", city)));

        String toolResponse = toolResponseMessage.getText();
        String result = answer + toolResponse;

        return result;
    }

}
