/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import com.fxy.records.StudentRecord;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

/**
 * StructuredOutputController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-01 20:12
 */
@RestController
public class StructuredOutputController {


    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;


    /**
     * 测试url：http://localhost:8007/structuredoutput/chat?sname=fei&email=110@qq.com
     *
     * @param sname
     * @param email
     * @return
     */
    @GetMapping("/structuredoutput/chat")
    public StudentRecord chat(String sname, String email) {

        return qwenChatClient.prompt().user(new Consumer<ChatClient.PromptUserSpec>() {
            @Override
            public void accept(ChatClient.PromptUserSpec promptUserSpec) {
                promptUserSpec.text("学号1001,,我叫{sname},大学专业计算机科学与技术，邮箱{email}")
                        .param("sname", sname)
                        .param("email", email);
            }
        }).call().entity(StudentRecord.class);
    }


    /**
     * 测试url：http://localhost:8007/structuredoutput/chat2?sname=fei&email=110@qq.com
     *
     * @param sname
     * @param email
     * @return
     */
    @GetMapping("/structuredoutput/chat2")
    public StudentRecord chat2(String sname, String email) {

        String stringTemplate = """
                学号1002,,我叫{sname},大学专业软件工程，邮箱{email}
                """;

        return qwenChatClient.prompt()
                .user(promptUserSpec -> promptUserSpec.text(stringTemplate)
                        .param("sname", sname)
                        .param("email", email))
                .call()
                .entity(StudentRecord.class);
    }

}
