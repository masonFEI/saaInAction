/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * RagController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-04 22:49
 */
@RestController
public class RagController {

    @Resource(name = "qwenChatClient")
    private ChatClient chatClient;

    @Resource
    private VectorStore vectorStore;

    /**
     *
     * 测试url：http://localhost:8012/rag4aiops?msg=00000
     *
     * @param msg
     * @return
     */
    @GetMapping("/rag4aiops")
    public Flux<String> rag(String msg) {

        String systemInfo = """
                你是一个运维工程师，按照给出的编码给出对应鼓掌解释，否则回复找不到信息
                """;

        RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore).build())
                .build();

        return chatClient.prompt()
                .system(systemInfo)
                .user(msg)
                .advisors(advisor).stream().content();
    }


}
