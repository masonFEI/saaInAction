/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.config;

import com.fxy.service.LocationService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * McpServerConfig
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-12 10:35
 */
@Configuration
public class McpServerConfig {

    @Bean
    public ToolCallbackProvider locationToolCallbackProvider(LocationService locationService) {
        return MethodToolCallbackProvider.builder()
            //
            .toolObjects(locationService)
            //
            .build();
    }

}
