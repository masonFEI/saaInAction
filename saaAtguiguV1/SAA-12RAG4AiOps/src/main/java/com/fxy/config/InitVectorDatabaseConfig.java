/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.config;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.nio.charset.Charset;
import java.util.List;

/**
 * InitVectorDatabaseConfig
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-04 22:41
 */
@Configuration
public class InitVectorDatabaseConfig {


    @Autowired
    private VectorStore vectorStore;

    @Value("classpath:ops.txt")
    private Resource opsFile;


    @PostConstruct
    public void init() {
        //1.读取文件
        TextReader textReader = new TextReader(opsFile);
        textReader.setCharset(Charset.defaultCharset());

        // 2.文件转换为向量（开启分词）
        List<Document> list = new TokenTextSplitter().transform(textReader.read());

        // 3. 写入向量数据库RedisStack
        vectorStore.add(list);
    }


}
