/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.config;

import com.fxy.service.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Configuration;

/**
 * McpServerConfig
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-06 15:03
 */
@Configuration
public class McpServerConfig {

    public ToolCallbackProvider weatherToolCallbackProvider(WeatherService weatherService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherService)
                .build();
    }

}
