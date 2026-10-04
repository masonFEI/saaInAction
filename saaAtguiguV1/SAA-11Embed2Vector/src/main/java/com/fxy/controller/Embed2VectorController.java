/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Embed2VectorController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-04 13:51
 */
@RestController
public class Embed2VectorController {

    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private VectorStore vectorStore;


    /**
     *
     * 文本向量化
     * 测试url：http://localhost:8011/text2embed?msg=射雕英雄传
     *
     * @param msg
     * @return
     */
    @GetMapping("/text2embed")
    public EmbeddingResponse text2Embed(String msg) {

        EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of(msg),
                DashScopeEmbeddingOptions.builder().withModel("text-embedding-v3").build()));

        System.out.println(Arrays.toString(embeddingResponse.getResult().getOutput()));

        return embeddingResponse;
    }


    /**
     * 文本向量化后，存入向量数据库redisStack
     * 测试url：http://localhost:8011/embed2Vector/add
     */
    @GetMapping("/embed2Vector/add")
    public void add() {
        List<Document> documents = List.of(
                new Document("i study LLM"),
                new Document("i love java")
        );
        vectorStore.add(documents);
    }

    /**
     * 从向量数据库RedisStack查找，进行相似度查找
     * 测试url：http://localhost:8011/embed2Vector/search?msg=我想学习LLM
     *
     * @param msg
     * @return
     */
    @GetMapping("/embed2Vector/search")
    public List getAll(String msg){
        SearchRequest searchRequest = SearchRequest.builder().query(msg).topK(2).build();
        List<Document> list = vectorStore.similaritySearch(searchRequest);
        System.out.println(list);
        return list;
    }


}
