/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.controller;

import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Text2ImageController
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-03 11:48
 */
@RestController
public class Text2ImageController {

    public static final String IMAGE_MODEL = "wan2.2-t2i-flash";

    @Resource
    private ImageModel imageModel;


    /**
     * 测试url：http://localhost:8009/t2i/image?prompt=鹈鹕
     *
     * @param prompt
     * @return
     */
    @GetMapping(value = "/t2i/image")
    public String image(@RequestParam(name = "prompt", defaultValue = "刺猬") String prompt) {
        return imageModel.call(
                        new ImagePrompt(prompt, DashScopeImageOptions.builder().withModel(IMAGE_MODEL).build())
                )
                .getResult()
                .getOutput()
                .getUrl();
    }

}
