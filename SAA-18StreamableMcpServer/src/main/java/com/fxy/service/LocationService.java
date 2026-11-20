/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.fxy.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * LocationService
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-10-12 10:28
 */
@Service
public class LocationService {

    @Tool(description = "根据城市名称获取配置的虚拟坐标")
    public String getLocationByCity(String city) {
        Map<String, String> maps = Map.of("上海", "虚拟坐标为(aa,bb)", "北京", "虚拟坐标为(cc,dd)", "深圳", "虚拟坐标为(ee,ff)");
        return maps.getOrDefault(city, "抱歉，该城市暂未配置虚拟坐标");
    }

}
