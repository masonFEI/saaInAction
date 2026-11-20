/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
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
    private ChatModel                            deepseekChatModel;

    @Resource(name = "qwen")
    private ChatModel                            qwenChatModel;

    @Resource(name = "deepseekChatClient")
    private ChatClient                           deepseekChatClient;

    @Resource(name = "qwenChatClient")
    private ChatClient                           qwenChatClient;

    @Value("classpath:/prompttemplate/atguigu-template.txt")
    private org.springframework.core.io.Resource userTemplate;

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
        PromptTemplate promptTemplate = new PromptTemplate("讲一个关于{topic}的故事" + "并以{output_format}格式输出，" + "字数在{wordCount}左右");

        // PromptTemplate -> Prompt
        Prompt prompt = promptTemplate.create(Map.of("topic", topic, "output_format", output_format, "wordCount", wordCount));
        return deepseekChatClient.prompt(prompt).stream().content();
    }

    /**
     * 读取模版文件实现模版功能
     * <p>
     * <p>
     * 测试地址：http://localhost:8006/prompttemplate/chat2?topic=宇航员&output_format=JSON
     *
     * @param topic
     * @param output_format
     * @return
     */
    @GetMapping("/prompttemplate/chat2")
    public String chat2(String topic, String output_format) {
        PromptTemplate promptTemplate = new PromptTemplate(userTemplate);

        // PromptTemplate -> Prompt
        Prompt prompt = promptTemplate.create(Map.of("topic", topic, "output_format", output_format));
        return deepseekChatClient.prompt(prompt).call().content();
    }

    /**
     * 设定角色
     * <p>
     * 系统消息（SystemMessage）:设定AI的行为规则和功能边界（XXX助手/什么格式返回/字数控制多少）
     * 用户消息（UserMessage）:用户的提问/主题
     * <p>
     * 测试地址：http://localhost:8006/prompttemplate/chat3?sysTopic=法律&userTopic=知识产权法
     *
     * @return
     */
    @GetMapping("/prompttemplate/chat3")
    public String chat3(String sysTopic, String userTopic) {
        // 1.systemPromptTemplate
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("你是{systemTopic}助手，只回答相关{systemTopic}领域的问题");
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("systemTopic", sysTopic));
        // 2.userPromptTemplate
        PromptTemplate userPromptTemplate = new PromptTemplate("解释一下{userTopic}");
        Message userMessage = userPromptTemplate.createMessage(Map.of("userTopic", userTopic));
        // 3.组合多个Message->prompt
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        // 4.调用LLM
        return deepseekChatClient.prompt(prompt).call().content();
    }

    /**
     * 设定角色
     * <p>
     * 系统消息（SystemMessage）:设定AI的行为规则和功能边界（XXX助手/什么格式返回/字数控制多少）
     * 用户消息（UserMessage）:用户的提问/主题
     * <p>
     * 测试地址：http://localhost:8006/prompttemplate/chat4?sysTopic=法律&userTopic=知识产权法
     * <p>
     * deepseekChatModel
     *
     * @return
     */
    @GetMapping("/prompttemplate/chat4")
    public String chat4(String sysTopic, String userTopic) {
        // 1.systemPromptTemplate
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("你是{systemTopic}助手，只回答相关{systemTopic}领域的问题");
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("systemTopic", sysTopic));
        // 2.userPromptTemplate
        PromptTemplate userPromptTemplate = new PromptTemplate("解释一下{userTopic}");
        Message userMessage = userPromptTemplate.createMessage(Map.of("userTopic", userTopic));
        // 3.组合多个Message->prompt
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        // 4.调用LLM
        return deepseekChatModel.call(prompt).getResult().getOutput().getText();
    }

    /**
     * 设定角色
     *
     * @return
     */
    @GetMapping("/prompttemplate/chat5")
    public Flux<String> chat5(String question) {
        return deepseekChatClient.prompt().system("你是一个法律助手，只回答法律问题，其他问题回复，我只能回答法律相关问题，其他无可奉告").user(question).stream().content();
    }

}
