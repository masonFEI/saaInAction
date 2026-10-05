/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.config;

import cn.hutool.crypto.SecureUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;

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

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

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
//        vectorStore.add(list);

        // 解决上面第三步，向量数据重复问题，使用redis setnx命令处理
        // 4. 去重复版本
        String sourceMetaData = (String) textReader.getCustomMetadata().get("source");
        String textHash = SecureUtil.md5(sourceMetaData);
        String redisKey = "vector-XXX:" + textHash;

        // 判断是否存入过
        Boolean retFlag = redisTemplate.opsForValue().setIfAbsent(redisKey, "1");

        System.out.println("****retFlag:" + retFlag);

        if (Boolean.TRUE.equals(retFlag)) {
            // 键不存在，首次插入，可以保存进向量数据库
            vectorStore.add(list);
        } else {
            // 键已存在，跳过或者报错
//            throw new RuntimeException("数据已存在，避免重复插入");

            System.out.println("数据已存在，避免重复插入");
        }


    }


}
