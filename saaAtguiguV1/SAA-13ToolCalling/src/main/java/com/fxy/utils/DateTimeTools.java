/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.utils;

import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDateTime;

/**
 * DateTimeTools
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-05 22:34
 */
public class DateTimeTools {


    /**
     * 1.定义（function call/tool call）
     * <p>
     * 2.returnDirect
     * true = tool直接返回不走大模型，直接给客户
     * false = 默认值，拿到tool返回的结果，给大模型，最后由大模型回复
     *
     * @return
     */
    @Tool(description = "获取当前时间", returnDirect = false)
    public String getCurrentTime() {
        return LocalDateTime.now().toString();
    }

}
